package com.ads.main.entity;

import com.ads.main.entity.ids.RptQuizRawID;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 퀴즈 광고 통계 원본 데이터
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@IdClass(RptQuizRawID.class)
@Table(name = "RPT_QUIZ_RAW")
public class RptQuizRawEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 광고 요청 코드
     */
    @Id
    @Column(name = "REQUEST_ID", nullable = false)
    private String requestId;

    /**
     * 그룹 코드
     */
    @Id
    @Column(name = "GROUP_CODE", nullable = false)
    private String groupCode;

    /**
     * 광고 코드
     */
    @Id
    @Column(name = "CAMPAIGN_CODE", nullable = false)
    private String campaignCode;


    @Column(name = "USER_KEY", nullable = false)
    private String userKey;

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

    /**
     * 요청 일자
     */
    @Column(name = "REQUEST_AT", nullable = false)
    private LocalDateTime requestAt;

    /**
     * 노출 일자
     */
    @Column(name = "IMPRESSION_AT")
    private LocalDateTime impressionAt;

    /**
     * 힌트 클릭 일자
     */
    @Column(name = "HINT_AT")
    private LocalDateTime hintAt;

    /**
     * 상세 노출 일자
     */
    @Column(name = "DETAIL_AT")
    private LocalDateTime detailAt;

    /**
     * 정답 등록 일자
     */
    @Column(name = "ANSWER_AT")
    private LocalDateTime answerAt;

    /**
     * 클릭 처리 일자
     */
    @Column(name = "CLICK_AT")
    private LocalDateTime clickAt;


    /**
     * 광고 단가
     */
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


    /**
     * 사용자 지급 포인트
     */
    @Column(name = "AD_REWORD")
    private Long adReword;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        RptQuizRawEntity that = (RptQuizRawEntity) o;
        return getRequestId() != null && Objects.equals(getRequestId(), that.getRequestId())
                && getGroupCode() != null && Objects.equals(getGroupCode(), that.getGroupCode())
                && getCampaignCode() != null && Objects.equals(getCampaignCode(), that.getCampaignCode());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(requestId, groupCode, campaignCode);
    }
}
