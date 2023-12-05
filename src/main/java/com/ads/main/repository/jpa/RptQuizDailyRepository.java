package com.ads.main.repository.jpa;

import com.ads.main.entity.RptQuizDailyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RptQuizDailyRepository extends JpaRepository<RptQuizDailyEntity, String>, JpaSpecificationExecutor<RptQuizDailyEntity> {

    List<RptQuizDailyEntity> findAllByRptDateBetweenOrderByRptDateDesc(String start, String end);
}
