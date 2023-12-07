package com.ads.main.service.admin;


import com.ads.main.core.utils.PageMapper;
import com.ads.main.entity.AdvertiserAccountEntity;
import com.ads.main.entity.AdvertiserEntity;
import com.ads.main.entity.mapper.AdvertiserAccountConvert;
import com.ads.main.repository.jpa.AdvertiserAccountRepository;
import com.ads.main.repository.jpa.AdvertiserRepository;
import com.ads.main.repository.querydsl.QAdvertiserAccountRepository;
import com.ads.main.vo.admin.FilesVo;
import com.ads.main.vo.admin.advertiser.account.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.ads.main.core.enums.exception.AdvertiserException.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdvertiserAccountService {


    private final AdvertiserService advertiserService;
    private final FileService fileService;

    private final AdvertiserAccountConvert advertiserAccountConvert;

    private final AdvertiserAccountRepository advertiserAccountRepository;
    private final QAdvertiserAccountRepository qAdvertiserAccountRepository;

    private final AdvertiserRepository advertiserRepository;

    private PageMapper<AdvertiserAccountVo, AdvertiserAccountEntity> pageMapper;

    @PostConstruct
    private void initPageMappers() {
        pageMapper =  new PageMapper<>(advertiserAccountConvert);
    }

    @Transactional(readOnly = true)
    public Optional<AdvertiserAccountEntity> findUserEntityByAdvertiserSeq(Long seq) {
        return advertiserAccountRepository.findUserEntityBySeq(seq);
    }

    @Transactional(readOnly = true)
    public Optional<AdvertiserAccountEntity> findUserEntityByAdvertiserSeq(String bankAccount) {
        return advertiserAccountRepository.findAdvertiserAccountEntityByBankAccount(bankAccount);
    }

    @Transactional(readOnly = true)
    public Page<AdvertiserAccountVo> findAccounts(AdvertiserAccountSearchVo searchVo, Pageable pageable) {
        Page<AdvertiserAccountEntity> advertiserAccountEntities = qAdvertiserAccountRepository.searchAdvertiserAccounts(searchVo, pageable);
        return pageMapper.convert(advertiserAccountEntities);
    }

    @Deprecated
    @Transactional(readOnly = true)
    public boolean checkBankAccount(String bankAccount) {
        Optional<AdvertiserAccountEntity> advertiserEntityOptional = findUserEntityByAdvertiserSeq(bankAccount);
        return advertiserEntityOptional.isPresent();
    }

    public Long accountUsedCount(Long advertiserSeq) {
        return advertiserAccountRepository.countByAdvertiserEntityAdvertiserSeqAndAccountUse(advertiserSeq, true);
    }

    public boolean checkAccount(Long advertiserSeq, String bankCode, String bankAccount) {
        return qAdvertiserAccountRepository.searchAdvertiserAccount(advertiserSeq, bankCode, bankAccount).isPresent();
    }

    @Transactional
    public AdvertiserAccountVo register(AdvertiserAccountVo advertiserAccountVo) {

        Optional<AdvertiserEntity> advertiserEntityOptional = advertiserService.findUserEntityByAdvertiserSeq(advertiserAccountVo.getAdvertiserSeq());

        if (advertiserEntityOptional.isEmpty()) {
            throw ADVERTISER_NOT_FOUND.throwErrors();
        }

        if (checkAccount(advertiserAccountVo.getAdvertiserSeq(), advertiserAccountVo.getBankCode(), advertiserAccountVo.getBankAccount())) {
            throw ADVERTISER_ACCOUNT_ALREADY_EXISTS.throwErrors();
        }

        AdvertiserEntity advertiserEntity = advertiserEntityOptional.get();
        advertiserEntity.addAccount(advertiserAccountConvert.toEntity(advertiserAccountVo));

        FilesVo filesVo = advertiserAccountVo.getFile();
        fileService.move(filesVo, "계좌 파일");

        advertiserRepository.save(advertiserEntity);

        return advertiserAccountConvert
                .toDtoList(advertiserEntity.getAdvertiserAccountEntities())
                .stream()
                .filter(t -> t.getBankAccount().equals(advertiserAccountVo.getBankAccount()))
                .findFirst()
                .get();

    }

    @Transactional
    public AdvertiserAccountVo modify(AdvertiserAccountModifyVo advertiserAccountModifyVo) {

        Optional<AdvertiserAccountEntity> advertiserAccountEntityOptional = findUserEntityByAdvertiserSeq(advertiserAccountModifyVo.getSeq());

        if (advertiserAccountEntityOptional.isEmpty()) {
            throw ADVERTISER_ACCOUNT_NOT_FOUND.throwErrors();
        }

        AdvertiserAccountEntity advertiserAccountEntity = advertiserAccountEntityOptional.get();
        advertiserAccountConvert.updateFromDto(advertiserAccountModifyVo, advertiserAccountEntity);

        advertiserAccountRepository.save(advertiserAccountEntity);

        return advertiserAccountConvert.toDto(advertiserAccountEntity);
    }

    @Transactional
    public AdvertiserAccountVo procUsed(AdvertiserAccountUseVo advertiserAccountModifyVo, boolean use) {

        Optional<AdvertiserAccountEntity> advertiserAccountEntityOptional = advertiserAccountRepository.findUserEntityBySeqAndAdvertiserEntity_AdvertiserSeq(advertiserAccountModifyVo.getSeq(), advertiserAccountModifyVo.getAdvertiserSeq());

        if (advertiserAccountEntityOptional.isEmpty()) {
            throw ADVERTISER_ACCOUNT_NOT_FOUND.throwErrors();
        }

        AdvertiserAccountEntity advertiserAccountEntity = advertiserAccountEntityOptional.get();

        if (use) {
            advertiserAccountEntity.used();
        } else {
            advertiserAccountEntity.unused();
        }

        return advertiserAccountConvert.toDto(advertiserAccountEntity);
    }


    @Transactional
    public void delete (AdvertiserAccountDeleteVo deleteVo) {

        Optional<AdvertiserAccountEntity> advertiserAccountEntityOptional = advertiserAccountRepository.findUserEntityBySeqAndAdvertiserEntity_AdvertiserSeq(deleteVo.getSeq(), deleteVo.getAdvertiserSeq());

        if (advertiserAccountEntityOptional.isEmpty()) {
            throw ADVERTISER_ACCOUNT_NOT_FOUND.throwErrors();
        }

        AdvertiserAccountEntity advertiserAccountEntity = advertiserAccountEntityOptional.get();

        advertiserAccountRepository.delete(advertiserAccountEntity);
    }
}
