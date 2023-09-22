package com.ads.main.repository.jpa;

import com.ads.main.entity.AdQuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdQuizRepository extends JpaRepository<AdQuizEntity, Long>, JpaSpecificationExecutor<AdQuizEntity> {

}
