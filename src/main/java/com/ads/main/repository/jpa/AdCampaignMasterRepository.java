package com.ads.main.repository.jpa;

import com.ads.main.core.enums.campaign.CampaignStatus;
import com.ads.main.entity.AdCampaignMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AdCampaignMasterRepository extends JpaRepository<AdCampaignMasterEntity, Long>, JpaSpecificationExecutor<AdCampaignMasterEntity> {

    Optional<AdCampaignMasterEntity> findFirstByCampaignCode(String campaignCode);
    Long countByAdEndDateAfterAndAdStartDateBeforeAndCampaignStatusAndExposureStatus(LocalDateTime adStartDate, LocalDateTime adEndDate, CampaignStatus status, Boolean exposure);

}
