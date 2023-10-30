package com.ads.main.repository.jpa;

import com.ads.main.entity.RptQuizRawEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RptQuizRawRepository extends JpaRepository<RptQuizRawEntity, String>, JpaSpecificationExecutor<RptQuizRawEntity> {

}
