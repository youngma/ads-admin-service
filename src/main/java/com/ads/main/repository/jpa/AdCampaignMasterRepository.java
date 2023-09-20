package com.ads.main.repository.jpa;

import com.ads.main.entity.AdCampaignEntity;
import com.ads.main.entity.AdCampaignMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AdCampaignMasterRepository extends JpaRepository<AdCampaignMasterEntity, Long>, JpaSpecificationExecutor<AdCampaignMasterEntity> {

    Optional<AdCampaignMasterEntity> findFirstByCampaignCode(String campaignCode);

}
