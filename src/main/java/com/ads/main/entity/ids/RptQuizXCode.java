package com.ads.main.entity.ids;

import jakarta.persistence.Column;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RptQuizXCode implements Serializable {

    /**
     * 광고 요청 코드
     */
    @Column(name = "RPT_DATE", nullable = false)
    private String rptDate;

    /**
     * 그룹 코드
     */
    @Column(name = "GROUP_CODE", nullable = false)
    private String groupCode;

    /**
     * 광고 코드
     */
    @Column(name = "CAMPAIGN_CODE", nullable = false)
    private String campaignCode;
}
