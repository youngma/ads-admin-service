package com.ads.main.repository.jpa;

import com.ads.main.entity.PartnerEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PartnerRepository extends JpaRepository<PartnerEntity, Long>, JpaSpecificationExecutor<PartnerEntity> {

    @EntityGraph(attributePaths = "filesEntity")
    Optional<PartnerEntity> findPartnerEntityByPartnerSeq(Long partnerSeq);

    Optional<PartnerEntity> findPartnerEntityByBusinessNumber(String businessNumber);

    Long countBy();

}

