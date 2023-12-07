package com.ads.main.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ads.main.core.enums.common.Bank;
import com.ads.main.entity.AdvertiserAccountEntity;
import com.ads.main.vo.admin.advertiser.account.AdvertiserAccountSearchVo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.ads.main.entity.QAdvertiserAccountEntity.advertiserAccountEntity;

@Repository
@RequiredArgsConstructor
public class QAdvertiserAccountRepository {

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;


    public Page<AdvertiserAccountEntity> searchAdvertiserAccounts(AdvertiserAccountSearchVo searchVo, Pageable pageable) {

        List<AdvertiserAccountEntity> accounts = jpaQueryFactory
                .selectFrom(advertiserAccountEntity)
                .join(advertiserAccountEntity.filesEntity)
                .where(searchVo.where())
                .orderBy(advertiserAccountEntity.seq.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.select(advertiserAccountEntity.count())
                .from(advertiserAccountEntity)
                .where(searchVo.where())
                .fetchOne();

        return new PageImpl<>(accounts, pageable, Objects.requireNonNullElse(count, 0L));
    }

    public Optional<AdvertiserAccountEntity> searchAdvertiserAccount(
            Long advertiserSeq,
            String bankCode,
            String bankAccount
    ) {

        AdvertiserAccountEntity account = jpaQueryFactory.selectFrom(advertiserAccountEntity)
                .where(
                    advertiserAccountEntity.advertiserEntity.advertiserSeq.eq(advertiserSeq)
                    .and(advertiserAccountEntity.bankAccount.eq(bankAccount))
                    .and(advertiserAccountEntity.bankCode.eq(Bank.of(bankCode)))
                )
                .fetchOne();

        return Optional.ofNullable(account);
    }
}
