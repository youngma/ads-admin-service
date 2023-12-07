package com.ads.main.service.admin;


import com.ads.main.core.utils.PageMapper;
import com.ads.main.entity.PartnerAccountEntity;
import com.ads.main.entity.PartnerEntity;
import com.ads.main.entity.mapper.PartnerAccountConvert;
import com.ads.main.repository.jpa.PartnerAccountRepository;
import com.ads.main.repository.jpa.PartnerRepository;
import com.ads.main.repository.querydsl.QPartnerAccountRepository;
import com.ads.main.vo.admin.FilesVo;
import com.ads.main.vo.admin.partner.account.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.ads.main.core.enums.exception.PartnerException.PARTNER_ACCOUNT_NOT_FOUND;
import static com.ads.main.core.enums.exception.PartnerException.PARTNER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class PartnerAccountService {
    private final PartnerRepository partnerRepository;


    private final PartnerService partnerService;
    private final FileService fileService;

    private final PartnerAccountConvert partnerAccountConvert;

    private final PartnerAccountRepository partnerAccountRepository;
    private final QPartnerAccountRepository qPartnerAccountRepository;

    private PageMapper<PartnerAccountVo, PartnerAccountEntity> pageMapper;

    @PostConstruct
    private void initPageMappers() {
        pageMapper =  new PageMapper<>(partnerAccountConvert);
    }

    @Transactional(readOnly = true)
    public Optional<PartnerAccountEntity> findPartnerAccountEntityBySeq(Long seq) {
        return partnerAccountRepository.findPartnerAccountEntityBySeq(seq);
    }

    @Transactional(readOnly = true)
    public Optional<PartnerAccountEntity> findPartnerEntityByBankAccount(String bankAccount) {
        return partnerAccountRepository.findPartnerEntityByBankAccount(bankAccount);
    }

    @Transactional(readOnly = true)
    public Page<PartnerAccountVo> findAccounts(PartnerAccountSearchVo searchVo, Pageable pageable) {
        Page<PartnerAccountEntity> partnerAccountEntities = qPartnerAccountRepository.searchPartnerAccounts(searchVo, pageable);
        return pageMapper.convert(partnerAccountEntities);
    }

    @Deprecated
    @Transactional(readOnly = true)
    public boolean checkBankAccount(String bankAccount) {
        Optional<PartnerAccountEntity> partnerAccountEntity = findPartnerEntityByBankAccount(bankAccount);
        return partnerAccountEntity.isPresent();
    }

    public Long accountUsedCount(Long advertiserSeq) {
        return partnerAccountRepository.countByPartnerEntityPartnerSeqAndAccountUse(advertiserSeq, true);
    }

    public boolean checkAccount(Long advertiserSeq, String bankCode, String bankAccount) {
        return qPartnerAccountRepository.searchPartnerAccount(advertiserSeq, bankCode, bankAccount).isPresent();
    }

    @Transactional
    public PartnerAccountVo register(PartnerAccountRegisterVo partnerAccountVo) {

        Optional<PartnerEntity> partnerEntityOptional = partnerService.findPartnerBySeq(partnerAccountVo.getPartnerSeq());

        if (partnerEntityOptional.isEmpty()) {
            throw PARTNER_NOT_FOUND.throwErrors();
        }

        if (checkAccount(partnerAccountVo.getPartnerSeq(), partnerAccountVo.getBankCode(), partnerAccountVo.getBankAccount())) {
            throw PARTNER_ACCOUNT_NOT_FOUND.throwErrors();
        }

        PartnerEntity partnerEntity = partnerEntityOptional.get();
        partnerEntity.addAccount(partnerAccountConvert.toEntity(partnerAccountVo));

        FilesVo filesVo = partnerAccountVo.getFile();
        fileService.move(filesVo, "계좌");

        partnerRepository.save(partnerEntity);

        return partnerAccountConvert
                .toDtoList(partnerEntity.getPartnerAccountEntities())
                .stream()
                .filter(t -> t.getBankAccount().equals(partnerAccountVo.getBankAccount()))
                .findFirst()
                .get();

    }

    @Transactional
    public PartnerAccountVo modify(PartnerAccountModifyVo partnerAccountModifyVo) {

        Optional<PartnerAccountEntity> partnerAccountEntityOptional = findPartnerAccountEntityBySeq(partnerAccountModifyVo.getSeq());

        if (partnerAccountEntityOptional.isEmpty()) {
            throw PARTNER_ACCOUNT_NOT_FOUND.throwErrors();
        }

        PartnerAccountEntity partnerAccountEntity = partnerAccountEntityOptional.get();
        partnerAccountConvert.updateFromDto(partnerAccountModifyVo, partnerAccountEntity);

        partnerAccountRepository.save(partnerAccountEntity);

        return partnerAccountConvert.toDto(partnerAccountEntity);
    }

    @Transactional
    public PartnerAccountVo procUsed(PartnerAccountUseVo partnerAccountUseVo, boolean use) {

        Optional<PartnerAccountEntity> partnerAccountEntityOptional = partnerAccountRepository.findPartnerAccountEntityBySeqAndPartnerEntity_PartnerSeq(partnerAccountUseVo.getSeq(), partnerAccountUseVo.getPartnerSeq());

        if (partnerAccountEntityOptional.isEmpty()) {
            throw PARTNER_ACCOUNT_NOT_FOUND.throwErrors();
        }

        PartnerAccountEntity advertiserAccountEntity = partnerAccountEntityOptional.get();

        if (use) {
            advertiserAccountEntity.used();
        } else {
            advertiserAccountEntity.unused();
        }

        return partnerAccountConvert.toDto(advertiserAccountEntity);
    }


    @Transactional
    public void delete (PartnerAccountDeleteVo deleteVo) {

        Optional<PartnerAccountEntity> partnerAccountEntityOptional = partnerAccountRepository.findPartnerAccountEntityBySeqAndPartnerEntity_PartnerSeq(deleteVo.getSeq(), deleteVo.getPartnerSeq());

        if (partnerAccountEntityOptional.isEmpty()) {
            throw PARTNER_ACCOUNT_NOT_FOUND.throwErrors();
        }

        PartnerAccountEntity partnerAccountEntity = partnerAccountEntityOptional.get();

        partnerAccountRepository.delete(partnerAccountEntity);
    }
}
