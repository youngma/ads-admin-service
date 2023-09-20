package com.ads.main.repository.jpa;

import com.ads.main.entity.PartnerAccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PartnerAccountRepository extends JpaRepository<PartnerAccountEntity, Long>, JpaSpecificationExecutor<PartnerAccountEntity> {


    @EntityGraph(attributePaths = "filesEntity")
    Optional<PartnerAccountEntity> findPartnerAccountEntityBySeq(Long seq);

    @EntityGraph(attributePaths = "filesEntity")
    Optional<PartnerAccountEntity> findPartnerAccountEntityBySeqAndPartnerEntity_PartnerSeq(Long seq, Long partnerSeq);

    Optional<PartnerAccountEntity> findPartnerEntityByBankAccount(String bankAccount);

    Page<PartnerAccountEntity> findAllByPartnerEntity_PartnerSeq(Long partnerSeq, Pageable pageable);

    Long countByPartnerEntityPartnerSeqAndAccountUse(Long partnerSeq, Boolean accountUse);

}
