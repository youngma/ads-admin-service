package com.ads.main.entity;

import com.ads.main.core.config.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * 퀴즈_광고_마스터
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "AD_QUIZ")
@DynamicUpdate
public class AdQuizEntity extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 순번
     */
    @Id
    @Column(name = "SEQ", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    /**
     * 캠페인 순번
     */
    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "AD_CAMPAIGN_SEQ", referencedColumnName = "SEQ")
    @ToString.Exclude
    private AdCampaignMasterEntity adCampaignMasterEntity;

    /**
     * 참여 방법
     */
    @Column(name = "USE_HOW")
    private String useHow;

    /**
     * 퀴즈 문제
     */
    @Column(name = "QUIZ_TITLE")
    private String quizTitle;

    /**
     * 퀴즈 정답
     */
    @Column(name = "QUIZ_ANSWER")
    private String quizAnswer;

    /**
     * 리스트 노출 이미지
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MAIN_IMAGE", referencedColumnName = "FILE_SEQ")
    private FilesEntity mainImage;

    /**
     * 상세 노출 이미지1
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DETAIL_IMAGE1", referencedColumnName = "FILE_SEQ")
    private FilesEntity detailImage1;

    /**
     * 상세 노출 이미지2
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DETAIL_IMAGE2", referencedColumnName = "FILE_SEQ")
    private FilesEntity detailImage2;

    /**
     * 랜딩 URL(PC)
     */
    @Column(name = "TARGET_URL_PC")
    private String targetUrlPc;

    /**
     * 랜딩 URL(MOBILE)
     */
    @Column(name = "TARGET_URL_MOBILE")
    private String targetUrlMobile;

    /**
     * 상품 코드
     */
    @Column(name = "GOODS_CODE")
    private String goodsCode;



    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        AdQuizEntity that = (AdQuizEntity) o;
        return getSeq() != null && Objects.equals(getSeq(), that.getSeq());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
