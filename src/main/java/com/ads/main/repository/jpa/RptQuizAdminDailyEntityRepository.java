package com.ads.main.repository.jpa;

import com.ads.main.entity.RptQuizAdminDailyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RptQuizAdminDailyEntityRepository extends JpaRepository<RptQuizAdminDailyEntity, String>, JpaSpecificationExecutor<RptQuizAdminDailyEntity> {

}
