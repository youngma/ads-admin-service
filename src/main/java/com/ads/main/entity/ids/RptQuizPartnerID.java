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
public class RptQuizPartnerID implements Serializable {

    /**
     * 리포팅 일자
     */
    private String rptDate;

    /**
     * 그룹 코드
     */
    private String groupCode;
}
