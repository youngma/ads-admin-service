package com.ads.main.repository.jpa;

import com.ads.main.entity.AdInquiryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AdInquiryRepository extends JpaRepository<AdInquiryEntity, Long>, JpaSpecificationExecutor<AdInquiryEntity> {

    Optional<AdInquiryEntity> findFirstBySeq(Long seq);
}
