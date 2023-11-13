package com.ads.main.vo.inquiry.resp;

import com.ads.main.core.enums.inquiry.InquiryStatus;
import com.ads.main.core.enums.inquiry.InquiryType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@Data
public class AdInquiryVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    private String advertiserName;
    private String campaignName;
    private String businessName;
    private String groupName;

    /**
     * 순번
     */
    @NotNull(message = "seq can not null")
    private Long seq;


    /**
     * 문의 종류
     */
    private InquiryType inquiryType;
    private String inquiryTypeName;


    public String getInquiryTypeName() {
        return inquiryType.getName();
    }

    /**
     * 문의 사항 제목
     */
    private String title;


    /**
     * 문의 사항 내용
     */
    private String answer;


    /**
     * 상태
     */
    private InquiryStatus status;
    private String statusName;


    public String getStatusName() {
        return status.getName();
    }

    /**
     * 질문 등록 일시
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestAt;


    /**
     * 답변 등록 일시
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime answerAt;


    /**
     * 등록일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime insertedAt;


    /**
     * 등록자
     */
    private String insertedId;


    /**
     * 수정일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;


    /**
     * 수정자
     */
    private String updatedId;


    /**
     * 광고 지면 코드
     */
    private String groupCode;


    /**
     * 캠페인 코드
     */
    private String campaignCode;


    /**
     * 퀴즈 제목
     */
    private String quizTitle;


    /**
     * 전화번호
     */
    private String phone;

}
