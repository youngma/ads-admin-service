package com.ads.main.entity;

import com.ads.main.core.enums.inquiry.InquiryStatus;
import com.ads.main.core.enums.inquiry.InquiryType;
import com.ads.main.core.enums.inquiry.convert.InquiryStatusConvert;
import com.ads.main.core.enums.inquiry.convert.InquiryTypeConvert;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * 문의사항
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "AD_INQUIRY")
public class AdInquiryEntity implements Serializable {

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
     * 문의 종류
     */
    @Column(name = "INQUIRY_TYPE")
    @Convert(converter = InquiryTypeConvert.class)
    private InquiryType inquiryType;

    /**
     * 문의 사항 제목
     */
    @Column(name = "TITLE")
    private String title;

    /**
     * 문의 사항 내용
     */
    @Column(name = "ANSWER")
    private String answer;

    public void setAnswer(String answer) {
        this.answer = answer;
        this.status = InquiryStatus.Answer;
        this.answerAt = LocalDateTime.now();
    }

    /**
     * 상태
     */
    @Column(name = "STATUS")
    @Convert(converter = InquiryStatusConvert.class)
    private InquiryStatus status;

    /**
     * 질문 등록 일시
     */
    @Column(name = "REQUEST_AT")
    private LocalDateTime requestAt;

    /**
     * 답변 등록 일시
     */
    @Column(name = "ANSWER_AT")
    private LocalDateTime answerAt;

    /**
     * 등록일자
     */
    @Column(name = "INSERTED_AT", nullable = false)
    private Date insertedAt;

    /**
     * 등록자
     */
    @Column(name = "INSERTED_ID")
    private String insertedId;

    /**
     * 수정일자
     */
    @Column(name = "UPDATED_AT", nullable = false)
    private Date updatedAt;

    /**
     * 수정자
     */
    @Column(name = "UPDATED_ID")
    private String updatedId;

    /**
     * 광고 지면 코드
     */
    @Column(name = "GROUP_CODE")
    private String groupCode;

    /**
     * 캠페인 코드
     */
    @Column(name = "CAMPAIGN_CODE")
    private String campaignCode;

    /**
     * 퀴즈 제목
     */
    @Column(name = "QUIZ_TITLE")
    private String quizTitle;

    /**
     * 전화번호
     */
    @Column(name = "PHONE")
    private String phone;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        AdInquiryEntity that = (AdInquiryEntity) o;
        return getSeq() != null && Objects.equals(getSeq(), that.getSeq());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
