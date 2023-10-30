package com.ads.main.entity.ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RptQuizAdvertiserID implements Serializable {

    /**
     * 리포팅 일자
     */
    private String rptDate;

    /**
     * 캠페인 코드
     */
    private String campaignCode;
}
