package com.ads.main.repository.jpa;

import com.ads.main.entity.AdSmartStoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdSmartStoreRepository extends JpaRepository<AdSmartStoreEntity, Long>, JpaSpecificationExecutor<AdSmartStoreEntity> {

}
