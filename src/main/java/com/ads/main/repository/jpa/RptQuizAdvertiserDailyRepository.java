package com.ads.main.repository.jpa;

import com.ads.main.entity.RptQuizAdvertiserDailyEntity;
import com.ads.main.entity.ids.RptQuizAdvertiserID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RptQuizAdvertiserDailyRepository extends JpaRepository<RptQuizAdvertiserDailyEntity, RptQuizAdvertiserID>, JpaSpecificationExecutor<RptQuizAdvertiserDailyEntity> {

}
