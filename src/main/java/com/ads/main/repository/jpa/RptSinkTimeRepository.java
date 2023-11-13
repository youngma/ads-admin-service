package com.ads.main.repository.jpa;

import com.ads.main.entity.RptSinkTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface RptSinkTimeRepository extends JpaRepository<RptSinkTimeEntity, String>, JpaSpecificationExecutor<RptSinkTimeEntity> {

    Optional<RptSinkTimeEntity> findByScheduleName(String scheduleName);
}
