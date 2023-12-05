package com.ads.main.repository.jpa;

import com.ads.main.core.enums.advertiser.AdGroupStatus;
import com.ads.main.entity.PartnerAdGroupEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PartnerAdGroupRepository extends JpaRepository<PartnerAdGroupEntity, Long>, JpaSpecificationExecutor<PartnerAdGroupEntity> {


    @EntityGraph(attributePaths = { "logoFileEntity",  "pointIconFileEntity" })
    Optional<PartnerAdGroupEntity> findOneByGroupSeq(Long groupSeq);

    @EntityGraph(attributePaths = { "logoFileEntity",  "pointIconFileEntity" })
    Optional<PartnerAdGroupEntity> findOneByPartnerEntity_PartnerSeqAndGroupSeq(Long partnerSeq, Long groupSeq);
    Optional<PartnerAdGroupEntity> findOneByGroupCode(String groupSeq);

    Long countByGroupStatus(AdGroupStatus groupStatus);

}
