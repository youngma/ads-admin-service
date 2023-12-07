package com.ads.main.repository.jpa;

import com.ads.main.entity.RptQuizXCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RptQuizXCodeEntityRepository extends JpaRepository<RptQuizXCodeEntity, String>, JpaSpecificationExecutor<RptQuizXCodeEntity> {

}
