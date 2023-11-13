package com.ads.main.vo.inquiry.req;

import com.ads.main.core.enums.inquiry.InquiryStatus;
import com.ads.main.entity.QAdInquiryEntity;
import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.querydsl.core.BooleanBuilder;
import jakarta.validation.constraints.NotNull;
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
public class AdInquiryAnswerVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer seq;

    @NotNull(message = "답변은 필수값 입니다.")
    private String answer;

}
