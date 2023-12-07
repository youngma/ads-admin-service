package com.ads.main.vo.admin.inquiry.req;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
@Builder
public class AdInquiryAnswerVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer seq;

    @NotNull(message = "답변은 필수값 입니다.")
    private String answer;

}
