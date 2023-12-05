package com.ads.main.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * 퀴즈광고 일자별 통계
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "RPT_QUIZ_DAILY")
public class RptQuizDailyEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 리포팅 일자
     */
    @Id
    @Column(name = "RPT_DATE", nullable = false)
    private String rptDate;

    /**
     * 그룹 순번
     */
    @Column(name = "GROUP_CNT", nullable = false)
    private Long groupCnt;

    /**
     * 광고 코드
     */
    @Column(name = "CAMPAIGN_CNT", nullable = false)
    private Long campaignCnt;

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
     * 상세 노출 수
     */
    @Column(name = "DETAIL_CNT")
    private Long detailCnt;

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


    @Column(name = "AD_COST")
    private Long adCost;

    /**
     * 파트너 지급 금액
     */
    @Column(name = "PARTNER_COMMISSION")
    private Long partnerCommission;

    /**
     * 사용자 지급 금액
     */
    @Column(name = "USER_COMMISSION")
    private Long userCommission;


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        RptQuizDailyEntity that = (RptQuizDailyEntity) o;
        return getRptDate() != null && Objects.equals(getRptDate(), that.getRptDate());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
