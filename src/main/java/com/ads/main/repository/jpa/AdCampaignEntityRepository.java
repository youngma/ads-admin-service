package com.ads.main.repository.jpa;

import com.ads.main.entity.AdCampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AdCampaignEntityRepository extends JpaRepository<AdCampaignEntity, Long>, JpaSpecificationExecutor<AdCampaignEntity> {

    Optional<AdCampaignEntity> findAdCampaignEntityByCampaignCode(String campaignCode);

}
