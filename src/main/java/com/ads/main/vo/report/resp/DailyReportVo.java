package com.ads.main.vo.report.resp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
public class DailyReportVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 리포팅 일자
     */
    private String rptDate;

}
