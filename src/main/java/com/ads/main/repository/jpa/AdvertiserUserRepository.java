package com.ads.main.repository.jpa;

import com.ads.main.entity.AdvertiserUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AdvertiserUserRepository extends JpaRepository<AdvertiserUserEntity, Long>, JpaSpecificationExecutor<AdvertiserUserEntity> {

    Optional<AdvertiserUserEntity> findAdvertiserUserEntitiesByAdvertiserEntity_AdvertiserSeqAndUserEntity_UserSeq(Long advertiserSeq, Long userSeq);
}
