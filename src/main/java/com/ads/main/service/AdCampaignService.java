package com.ads.main.service;


import com.ads.main.core.enums.campaign.CampaignStatus;
import com.ads.main.core.utils.PageMapper;
import com.ads.main.entity.AdCampaignMasterEntity;
import com.ads.main.entity.AdvertiserEntity;
import com.ads.main.entity.mapper.AdCampaignMasterConvert;
import com.ads.main.entity.mapper.AdQuizConvert;
import com.ads.main.entity.mapper.AdSmartStoreConvert;
import com.ads.main.repository.jpa.AdCampaignMasterRepository;
import com.ads.main.repository.jpa.AdvertiserRepository;
import com.ads.main.repository.querydsl.QAdvertiserCampaignMasterRepository;
import com.ads.main.service.AdvertiserService;
import com.ads.main.service.FileService;
import com.ads.main.vo.FilesVo;
import com.ads.main.vo.advertiser.campaign.req.AdCampaignMasterModifyVo;
import com.ads.main.vo.advertiser.campaign.req.AdCampaignMasterRegisterVo;
import com.ads.main.vo.advertiser.campaign.req.AdCampaignSearchVo;
import com.ads.main.vo.advertiser.campaign.req.AdCampaignStatusVo;
import com.ads.main.vo.advertiser.campaign.resp.AdCampaignMasterVo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.ads.main.core.enums.exception.AdCampaignException.AD_CAMPAIGN_STATUS_NOT_CHANGE;
import static com.ads.main.core.enums.exception.AdCampaignException.AD_NOT_FOUND;
import static com.ads.main.core.enums.exception.AdvertiserException.ADVERTISER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdCampaignService {


    private final FileService fileService;
    private final AdvertiserService advertiserService;

    private final AdCampaignMasterRepository adCampaignMasterRepository;
    private final AdvertiserRepository advertiserRepository;


//    private final QAdvertiserCampaignRepository qAdvertiserCampaignRepository;
    private final QAdvertiserCampaignMasterRepository qAdvertiserCampaignMasterRepository;


//    private final AdCampaignConvert adCampaignConvert;
    private final AdCampaignMasterConvert adCampaignMasterConvert;
    private final AdSmartStoreConvert adSmartStoreConvert;
    private final AdQuizConvert adQuizConvert;
    private PageMapper<AdCampaignMasterVo, AdCampaignMasterEntity> pageMapper;

    @PostConstruct
    private void initPageMappers() {
        pageMapper =  new PageMapper<>(adCampaignMasterConvert);
    }

    @Transactional(readOnly = true)
    public Page<AdCampaignMasterVo> findAdCampaigns(AdCampaignSearchVo adCampaignSearchVo, Pageable pageable) {
        Page<AdCampaignMasterEntity> advertiserAccountEntities = qAdvertiserCampaignMasterRepository.findAdCampaigns(adCampaignSearchVo, pageable);
        return pageMapper.convert(advertiserAccountEntities);
    }


    public boolean duplicateCheckByCampaignCode(String campaignCode) {
        return adCampaignMasterRepository.findFirstByCampaignCode(campaignCode).isPresent();
    }

    public AdCampaignMasterEntity getCampaignByCode(String campaignCode) {
        Optional<AdCampaignMasterEntity> adCampaignEntityOptional = adCampaignMasterRepository.findFirstByCampaignCode(campaignCode);
        return adCampaignEntityOptional.orElseThrow(AD_NOT_FOUND::throwErrors);
    }


    @Transactional
    public AdCampaignMasterVo register(AdCampaignMasterRegisterVo adCampaignRegVo) {

        log.info("# adCampaignVo => {}", adCampaignRegVo);

        adCampaignRegVo.validCheck();

        // 광고주 찾기
        Optional<AdvertiserEntity> advertiserEntityOptional = advertiserService.findUserEntityByAdvertiserSeq(adCampaignRegVo.getAdvertiserSeq());

        AdvertiserEntity advertiserEntity = advertiserEntityOptional.orElseThrow(ADVERTISER_NOT_FOUND::throwErrors);

        AdCampaignMasterEntity adCampaignEntity = adCampaignMasterConvert.toEntity(adCampaignRegVo);

        log.info("# adCampaignEntity => {}", adCampaignEntity);

        adCampaignEntity.request();
        adCampaignEntity.approval();

        // 10자리 캠페인 코드 생성
        String campaignCode = RandomStringUtils.randomAlphabetic(10);

        // 광고 코드 중복 체크
        boolean duplicateCheck = true;
        while(duplicateCheck) {
            duplicateCheck = duplicateCheckByCampaignCode(campaignCode);
        }

        if (adCampaignEntity.getAdSmartStoreEntity() != null) {
            FilesVo filesVo = adCampaignRegVo.getSmartStore().getImage();
            fileService.move(filesVo, "스마트 스토어 광고 이미지");
        }

        if (adCampaignEntity.getAdQuizEntity() != null) {
            FilesVo mainImage = adCampaignRegVo.getQuiz().getMainImage();
            FilesVo detailImage1 = adCampaignRegVo.getQuiz().getDetailImage1();
            FilesVo detailImage2 = adCampaignRegVo.getQuiz().getDetailImage2();

            fileService.move(mainImage, "퀴즈 광고 메인(썸네일)");
            fileService.move(detailImage1, "퀴즈 광고 상세1");
//            fileService.move(detailImage2, "퀴즈 광고 상세2");
        }


        adCampaignEntity.setCampaignCode(campaignCode);

        adCampaignEntity = advertiserEntity.addCampaign(adCampaignEntity);

        advertiserRepository.saveAndFlush(advertiserEntity);

        return adCampaignMasterConvert.toDto(adCampaignEntity);
    }


    @Transactional
    public AdCampaignMasterVo modify(AdCampaignMasterModifyVo adCampaignVo) {

        log.info("# adCampaignVo => {}", adCampaignVo);
        Optional<AdCampaignMasterEntity> adCampaignMasterEntityOptional = qAdvertiserCampaignMasterRepository.findAdCampaignMaster(adCampaignVo.getAdvertiserSeq(), adCampaignVo.getSeq());

        adCampaignMasterEntityOptional.ifPresentOrElse((adCampaignMasterEntity) -> {
            adCampaignMasterConvert.updateFromDto(adCampaignVo, adCampaignMasterEntity);

            if (adCampaignMasterEntity.getAdSmartStoreEntity() != null) {
                adSmartStoreConvert.updateFromDto(adCampaignVo.getSmartStore(), adCampaignMasterEntity.getAdSmartStoreEntity());
                FilesVo filesVo = adCampaignVo.getSmartStore().getImage();
                fileService.move(filesVo, "스마트 스토어 광고 이미지");
            }

            if (adCampaignMasterEntity.getAdQuizEntity() != null) {
                adQuizConvert.updateFromDto(adCampaignVo.getQuiz(), adCampaignMasterEntity.getAdQuizEntity());
                FilesVo mainImage = adCampaignVo.getQuiz().getMainImage();
                FilesVo detailImage1 = adCampaignVo.getQuiz().getDetailImage1();
                FilesVo detailImage2 = adCampaignVo.getQuiz().getDetailImage2();

                fileService.move(mainImage, "퀴즈 광고 메인(썸네일)");
                fileService.move(detailImage1, "퀴즈 광고 상세1");
//                fileService.move(detailImage2, "퀴즈 광고 상세2");
            }

            adCampaignMasterRepository.save(adCampaignMasterEntity);

        }, () -> { throw AD_NOT_FOUND.throwErrors(); });

        return adCampaignMasterConvert.toDto(adCampaignMasterEntityOptional.get());

    }

    public AdCampaignMasterVo status(CampaignStatus campaignStatus, AdCampaignStatusVo adCampaignStatusVo) {

        Optional<AdCampaignMasterEntity> adCampaignMasterEntityOptional = qAdvertiserCampaignMasterRepository.findAdCampaignMaster(adCampaignStatusVo.getAdvertiserSeq(), adCampaignStatusVo.getSeq());

        adCampaignMasterEntityOptional.ifPresentOrElse((adCampaignMasterEntity) -> {
            switch (campaignStatus) {
                case Approval -> adCampaignMasterEntity.approval();
                case Reject -> adCampaignMasterEntity.reject(adCampaignStatusVo.getMessage());
                case Hold -> adCampaignMasterEntity.hold(adCampaignStatusVo.getMessage());
                case Exposure -> adCampaignMasterEntity.exposure();
                case NonExposure -> adCampaignMasterEntity.non_exposure();
                default -> throw AD_CAMPAIGN_STATUS_NOT_CHANGE.throwErrors();
            }

            adCampaignMasterRepository.save(adCampaignMasterEntity);
        }, () -> { throw AD_NOT_FOUND.throwErrors(); });

        return adCampaignMasterConvert.toDto(adCampaignMasterEntityOptional.get());

    }

    public HashSet<String> mobiAdsAlreadyRegisteredCheck(String ifCode, HashSet<String> keys) {
        List<String> alreadyRegisteredKeys = qAdvertiserCampaignMasterRepository.findAlreadyRegisteredMobiAds(ifCode,keys);
        if (!alreadyRegisteredKeys.isEmpty()) {
            alreadyRegisteredKeys.forEach(keys::remove);
        }
        return keys;
    }
}
