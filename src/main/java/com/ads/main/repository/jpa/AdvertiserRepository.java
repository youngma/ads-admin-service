package com.ads.main.repository.jpa;

import com.ads.main.core.enums.campaign.CampaignStatus;
import com.ads.main.entity.AdvertiserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AdvertiserRepository extends JpaRepository<AdvertiserEntity, Long>, JpaSpecificationExecutor<AdvertiserEntity> {

    @EntityGraph(attributePaths = "filesEntity")
    Optional<AdvertiserEntity> findAdvertiserEntityByAdvertiserSeq(Long advertiserSeq);

    Optional<AdvertiserEntity> findAdvertiserEntityByBusinessNumber(String businessNumber);

    List<AdvertiserEntity> findAdvertiserEntitiesByAdvertiserSeqIn(List<Long> advertiserEntities);

    Long countBy();

}
