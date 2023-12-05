package com.ads.main.service;


import com.ads.main.core.enums.advertiser.AdGroupStatus;
import com.ads.main.core.utils.PageMapper;
import com.ads.main.entity.*;
import com.ads.main.entity.mapper.PartnerAdGroupConvert;
import com.ads.main.repository.jpa.PartnerAdGroupRepository;
import com.ads.main.repository.jpa.PartnerAdMappingRepository;
import com.ads.main.repository.querydsl.QPartnerAdGroupRepository;
import com.ads.main.service.FileService;
import com.ads.main.service.PartnerService;
import com.ads.main.vo.partner.adGroup.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.ads.main.core.enums.exception.PartnerException.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PartnerAdGroupService {

    private final PartnerService partnerService;
    private final FileService fileService;


    private final PartnerAdGroupRepository partnerAdGroupRepository;
    private final PartnerAdMappingRepository partnerAdMappingRepository;
    private final QPartnerAdGroupRepository qPartnerAdGroupRepository;

    private final PartnerAdGroupConvert partnerAdGroupConvert;
    private PageMapper<PartnerAdGroupVo, PartnerAdGroupEntity> pageMapper;

    @PostConstruct
    private void initPageMappers() {
        pageMapper =  new PageMapper<>(partnerAdGroupConvert);
    }

    @Transactional(readOnly = true)
    public Optional<PartnerAdGroupEntity> findPartnerAdGroupEntityByGroupSeq(Long groupSeq) {
        return partnerAdGroupRepository.findOneByGroupSeq(groupSeq);
    }

    @Transactional(readOnly = true)
    public Optional<PartnerAdGroupEntity> findPartnerAdGroupEntityByPartnerSeqAndGroupSeq(Long partnerSeq, Long groupSeq) {
        return partnerAdGroupRepository.findOneByPartnerEntity_PartnerSeqAndGroupSeq(partnerSeq, groupSeq);
    }

    @Transactional(readOnly = true)
    public Optional<PartnerAdGroupEntity> findPartnerAdGroupEntityByGroupCode(String groupCode) {
        return partnerAdGroupRepository.findOneByGroupCode(groupCode);
    }

    @Transactional
    public boolean duplicateCheckByAdGroupCode(String adGroupCode) {
        return findPartnerAdGroupEntityByGroupCode(adGroupCode).isPresent();
    }

    @Transactional(readOnly = true)
    public Page<PartnerAdGroupVo> findAdGroups(PartnerAdGroupSearchVo searchVo, Pageable pageable) {
        Page<PartnerAdGroupEntity> partnerAccountEntities = qPartnerAdGroupRepository.searchAdGroups(searchVo, pageable);
        return pageMapper.convert(partnerAccountEntities);
    }

    @Transactional
    public PartnerAdGroupVo register(PartnerAdGroupRegisterVo registerVo) {

        Optional<PartnerEntity> partnerEntityOptional = partnerService.findPartnerBySeq(registerVo.getPartnerSeq());

        if (partnerEntityOptional.isEmpty()) {
            throw PARTNER_NOT_FOUND.throwErrors();
        }

        // 10자리 캠페인 코드 생성
        String adGroupCode = RandomStringUtils.randomAlphabetic(10);

        // 광고 코드 중복 체크
        boolean duplicateCheck = true;
        while(duplicateCheck) {
            duplicateCheck = duplicateCheckByAdGroupCode(adGroupCode);
        }

        registerVo.setGroupCode(adGroupCode);

        fileService.move(registerVo.getLogoFile(), "광고지면 - 로그 이미지");
        fileService.move(registerVo.getPointIconFile(), "광고지면 - 포인트 이미지");

        PartnerEntity partner = partnerEntityOptional.get();
        PartnerAdGroupEntity adGroupEntity =  partner.addAdGroup(partnerAdGroupConvert.toEntity(registerVo), registerVo.getMappingAds());

        // 지면 정보 먼저 저장
        PartnerAdGroupVo partnerAdGroupDto = partnerAdGroupConvert.toDto(adGroupEntity);
        return partnerAdGroupDto;
    }

    public PartnerAdGroupVo status(AdGroupStatus adGroupStatus, PartnerAdGroupStatusVo partnerAdGroupStatusVo) {

        Optional<PartnerAdGroupEntity> partnerAdGroupEntityOptional = partnerAdGroupRepository.findOneByPartnerEntity_PartnerSeqAndGroupSeq(partnerAdGroupStatusVo.getPartnerSeq(), partnerAdGroupStatusVo.getGroupSeq());

        if (partnerAdGroupEntityOptional.isEmpty()) {
            throw PARTNER_AD_GROUP_NOT_FOUND.throwErrors();
        }

        partnerAdGroupEntityOptional.ifPresent((partnerAdGroupEntity) -> {
            switch (adGroupStatus) {
                case Approval -> partnerAdGroupEntity.approval();
                case Reject -> partnerAdGroupEntity.reject(partnerAdGroupStatusVo.getMessage());
                case Hold -> partnerAdGroupEntity.hold(partnerAdGroupStatusVo.getMessage());
                default -> throw PARTNER_AD_GROUP_STATUS_NOT_CHANGE.throwErrors();
            }

            partnerAdGroupRepository.save(partnerAdGroupEntity);
        });

        return partnerAdGroupConvert.toDto(partnerAdGroupEntityOptional.get());

    }

    public PartnerAdGroupVo modify(PartnerAdGroupModifyVo modifyVo) {

        Optional<PartnerAdGroupEntity> partnerAdGroupEntityOptional = partnerAdGroupRepository.findOneByPartnerEntity_PartnerSeqAndGroupSeq(modifyVo.getPartnerSeq(), modifyVo.getGroupSeq());

        if (partnerAdGroupEntityOptional.isEmpty()) {
            throw PARTNER_AD_GROUP_NOT_FOUND.throwErrors();
        }

        partnerAdGroupEntityOptional.ifPresent((partnerAdGroupEntity) -> {

            fileService.move(modifyVo.getLogoFile(), "광고지면 - 로그 이미지");
            fileService.move(modifyVo.getPointIconFile(), "광고지면 - 포인트 이미지");

            partnerAdGroupConvert.updateFromDto(modifyVo, partnerAdGroupEntity);

            partnerAdGroupRepository.save(partnerAdGroupEntity);
        });

        return partnerAdGroupConvert.toDto(partnerAdGroupEntityOptional.get());
    }
}
