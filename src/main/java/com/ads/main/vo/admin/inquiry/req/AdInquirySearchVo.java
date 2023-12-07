package com.ads.main.vo.admin.inquiry.req;

import com.ads.main.core.enums.inquiry.InquiryStatus;
import com.ads.main.entity.QAdInquiryEntity;
import com.querydsl.core.BooleanBuilder;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.ads.main.entity.QAdCampaignMasterEntity.adCampaignMasterEntity;
import static com.ads.main.entity.QAdInquiryEntity.adInquiryEntity;
import static com.ads.main.entity.QPartnerAdGroupEntity.partnerAdGroupEntity;


@Data
@Builder
public class AdInquirySearchVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 문의 종류
     */
//    private InquiryType inquiryType;

    /**
     * 상태
     */
    private InquiryStatus status;

    private String searchType;
    private String searchText;


    private LocalDateTime searchStartDt;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime searchEndDt;

    public void setStatus(String status) {
        this.status = InquiryStatus.of(status);
    }

    /**
     * 문의 사항 제목
     */
    private String title;


    public BooleanBuilder where() {
        BooleanBuilder builder = new BooleanBuilder();

        if (
                InquiryStatus.Request.equals(status)
                || InquiryStatus.Answer.equals(status)
        ) {
            builder.and(QAdInquiryEntity.adInquiryEntity.status.eq(status));
        }

        if (
               searchText != null && !searchText.isBlank()
        ) {
            if ("advertiserName".equals(searchType)) {
                builder.and(adCampaignMasterEntity.advertiserEntity.businessName.contains(searchText));
            } else if ("partnerName".equals(searchType)) {
                builder.and(partnerAdGroupEntity.partnerEntity.businessName.contains(searchText));
            } else if ("quizTitle".equals(searchType)) {
                builder.and(adInquiryEntity.quizTitle.contains(searchText));
            }
        }


        if (  searchStartDt != null &&  searchEndDt != null ) {
            builder .and(adInquiryEntity.requestAt.after(searchStartDt))
                    .and(adInquiryEntity.requestAt.before(searchEndDt));
        }

        return builder;
    }
}
