package com.ads.main.entity;

import com.ads.main.entity.ids.RptQuizRawID;
import com.ads.main.entity.ids.RptQuizXCode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * 퀴즈광고 통계 데이터(상세)
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@IdClass(RptQuizXCode.class)
@Table(name = "RPT_QUIZ_X_CODE")
public class RptQuizXCodeEntity implements Serializable {

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
    @Id
    @Column(name = "GROUP_CODE", nullable = false)
    private String groupCode;

    /**
     * 광고 코드
     */
    @Id
    @Column(name = "CAMPAIGN_CODE", nullable = false)
    private String campaignCode;

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
    private Integer detailCnt;

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
    @Column(name = "AD_COST", nullable = false)
    private Long adCost;

    /**
     * 파트너 수수료
     */
    @Column(name = "PARTNER_COMMISSION", nullable = false)
    private Long partnerCommission;

    /**
     * 사용자 수수료
     */
    @Column(name = "USER_COMMISSION", nullable = false)
    private Long userCommission;

    /**
     * 사용자 지급 포인트
     */
    @Column(name = "AD_REWORD", nullable = false)
    private Long adReword;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        RptQuizXCodeEntity that = (RptQuizXCodeEntity) o;
        return getRptDate() != null && Objects.equals(getRptDate(), that.getRptDate());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
