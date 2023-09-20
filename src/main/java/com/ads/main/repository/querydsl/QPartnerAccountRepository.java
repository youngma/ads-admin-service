package com.ads.main.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ads.main.core.enums.common.Bank;
import com.ads.main.entity.PartnerAccountEntity;
import com.ads.main.vo.partner.account.PartnerAccountSearchVo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.ads.main.entity.QPartnerAccountEntity.partnerAccountEntity;

@Repository
@RequiredArgsConstructor
public class QPartnerAccountRepository {

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;


    public Page<PartnerAccountEntity> searchPartnerAccounts(PartnerAccountSearchVo searchVo, Pageable pageable) {

        List<PartnerAccountEntity> accounts = jpaQueryFactory.selectFrom(partnerAccountEntity)
                .where(searchVo.where())
                .orderBy(partnerAccountEntity.seq.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.select(partnerAccountEntity.count())
                .from(partnerAccountEntity)
                .where(searchVo.where())
                .fetchOne();

        return new PageImpl<>(accounts, pageable, Objects.requireNonNullElse(count, 0L));
    }

    public Optional<PartnerAccountEntity> searchPartnerAccount(
            Long advertiserSeq,
            String bankCode,
            String bankAccount
    ) {

        PartnerAccountEntity account = jpaQueryFactory.selectFrom(partnerAccountEntity)
                .where(
                        partnerAccountEntity.partnerEntity.partnerSeq.eq(advertiserSeq)
                    .and(partnerAccountEntity.bankAccount.eq(bankAccount))
                    .and(partnerAccountEntity.bankCode.eq(Bank.of(bankCode)))
                )
                .fetchOne();

        return Optional.ofNullable(account);
    }
}
