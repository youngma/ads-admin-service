package com.ads.main.repository.jpa;

import com.ads.main.entity.PartnerUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PartnerUserRepository extends JpaRepository<PartnerUserEntity, Long>, JpaSpecificationExecutor<PartnerUserEntity> {

    Optional<PartnerUserEntity> findPartnerUserEntitiesByPartnerEntity_PartnerSeqAndUserEntity_UserSeq(Long advertiserSeq, Long userSeq);

}
