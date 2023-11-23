package com.ads.main.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 어드민 일자별 통계
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "RPT_QUIZ_ADMIN_DAILY")
public class RptQuizAdminDailyEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 리포팅 일자
     */
    @Id
    @Column(name = "RPT_DATE", nullable = false)
    private String rptDate;

    /**
     * 광고주 수
     */
    @Column(name = "ADVERTISER_CNT")
    private Long advertiserCnt;

    /**
     * 광고 캠페인 수(진행)
     */
    @Column(name = "CAMPAIGN_CNT")
    private Long campaignCnt;

    /**
     * 매체사 수
     */
    @Column(name = "PARTNER_CNT")
    private Long partnerCnt;

    /**
     * 매체사 지면 수
     */
    @Column(name = "AD_GROUP_CNT")
    private Long adGroupCnt;

    /**
     * 광고 요청 수
     */
    @Column(name = "REQ_CNT")
    private Long reqCnt;

    /**
     * 광고 노출 수
     */
    @Column(name = "IMPRESSION_CNT")
    private Long impressionCnt;

    /**
     * 정답 사용자 수
     */
    @Column(name = "ANSWER_CNT")
    private Long answerCnt;

    /**
     * 광고 힌트 랜딩 수
     */
    @Column(name = "HINT_CNT")
    private Long hintCnt;

    /**
     * 광고 클릭 랜딩 수
     */
    @Column(name = "CLICK_CNT")
    private Long clickCnt;

    /**
     * 광고 금액
     */
    @Column(name = "AD_COST")
    private Long adCost;

    /**
     * 수수료(매체사)
     */
    @Column(name = "PARTNER_COMMISSION")
    private Long partnerCommission;

    /**
     * 수수료(고객)
     */
    @Column(name = "USER_COMMISSION")
    private Long userCommission;

    /**
     * 등록 일자
     */
    @Column(name = "INSERTED_AT", nullable = false)
    private Date insertedAt;

    /**
     * 수정 일자
     */
    @Column(name = "UPDATED_AT", nullable = false)
    private Date updatedAt;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        RptQuizAdminDailyEntity that = (RptQuizAdminDailyEntity) o;
        return getRptDate() != null && Objects.equals(getRptDate(), that.getRptDate());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
