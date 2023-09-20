package com.ads.main.repository.jpa;

import com.ads.main.entity.AdvertiserAccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AdvertiserAccountRepository extends JpaRepository<AdvertiserAccountEntity, Long>, JpaSpecificationExecutor<AdvertiserAccountEntity> {

    @EntityGraph(attributePaths = "filesEntity")
    Optional<AdvertiserAccountEntity> findUserEntityBySeq(Long seq);

    @EntityGraph(attributePaths = "filesEntity")
    Optional<AdvertiserAccountEntity> findUserEntityBySeqAndAdvertiserEntity_AdvertiserSeq(Long seq, Long AdvertiserSeq);

    @EntityGraph(attributePaths = "filesEntity")
    Optional<AdvertiserAccountEntity> findAdvertiserAccountEntityByBankAccount(String bankAccount);

    Page<AdvertiserAccountEntity> findAllByAdvertiserEntity_AdvertiserSeq(Long advertiserSeq, Pageable pageable);

    Long countByAdvertiserEntityAdvertiserSeqAndAccountUse(Long advertiserSeq, Boolean accountUse);

}
