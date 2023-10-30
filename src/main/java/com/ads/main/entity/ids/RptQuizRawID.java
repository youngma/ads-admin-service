package com.ads.main.entity.ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RptQuizRawID implements Serializable {

    /**
     * 광고 요청 코드
     */
    @Column(name = "REQUEST_ID", nullable = false)
    private String requestId;

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
