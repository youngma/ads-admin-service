package com.ads.main.entity;

import com.ads.main.entity.ids.RptQuizAdvertiserID;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 광고사 일자별 통계
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@IdClass(RptQuizAdvertiserID.class)
@Table(name = "RPT_QUIZ_ADVERTISER_DAILY")
public class RptQuizAdvertiserDailyEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 리포팅 일자
     */
    @Id
    @Column(name = "RPT_DATE", nullable = false)
    private String rptDate;

    /**
     * 캠페인 코드
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
     * 등록 일자
     */
    @Column(name = "INSERTED_AT", nullable = false)
    private LocalDateTime insertedAt;

    /**
     * 수정 일자
     */
    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * 수정 일자
     */
    @Column(name = "AD_COST", nullable = false)
    private Long adCost;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        RptQuizAdvertiserDailyEntity that = (RptQuizAdvertiserDailyEntity) o;
        return getRptDate() != null && Objects.equals(getRptDate(), that.getRptDate())
                && getCampaignCode() != null && Objects.equals(getCampaignCode(), that.getCampaignCode());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(rptDate, campaignCode);
    }
}
