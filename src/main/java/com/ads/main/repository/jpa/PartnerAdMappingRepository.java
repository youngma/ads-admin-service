package com.ads.main.repository.jpa;

import com.ads.main.entity.PartnerAdMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public interface PartnerAdMappingRepository extends JpaRepository<PartnerAdMappingEntity, Long>, JpaSpecificationExecutor<PartnerAdMappingEntity> {
    ArrayList<PartnerAdMappingEntity> findAllByPartnerAdGroupEntity_GroupSeq(Long groupSeq);
    Optional<PartnerAdMappingEntity> findFirstByPartnerAdGroupEntity_GroupSeqAndCampaignSeq(Long groupSeq, Long campaignSeq);
    void deleteAllByPartnerAdGroupEntity_GroupSeqAndCampaignSeqIn (Long groupSeq, List<Long> campaignSeq);
}
