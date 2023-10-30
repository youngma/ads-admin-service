package com.ads.main.repository.jpa;

import com.ads.main.entity.RptQuizPartnerDailyEntity;
import com.ads.main.entity.ids.RptQuizPartnerID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RptQuizPartnerDailyRepository extends JpaRepository<RptQuizPartnerDailyEntity, RptQuizPartnerID>, JpaSpecificationExecutor<RptQuizPartnerDailyEntity> {

}
