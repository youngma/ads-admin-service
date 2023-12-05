package com.ads.main.vo.report.excel;

import com.ads.main.core.annotation.ExcelColumnName;
import lombok.ToString;

import java.math.BigDecimal;


@ToString
public class RptQuizPartnerExcelVo {


    @ExcelColumnName(name = "일자")
    public String rptDate;

    @ExcelColumnName(name = "매체 명")
    public String businessName;

    @ExcelColumnName(name = "채널 코드")
    public String groupCode;

    @ExcelColumnName(name = "채널 명")
    public String groupName;


    @ExcelColumnName(name = "노출 수")
    public Long impressionCnt;

    @ExcelColumnName(name = "클릭 수")
    public Long hintCnt;

    @ExcelColumnName(name = "CTR")
    public Double CTR;

    @ExcelColumnName(name = "참여자 수")
    public Long clickCnt;

    @ExcelColumnName(name = "매체사 리워드")
    public long partnerCommission;

    @ExcelColumnName(name = "사용자 리워드")
    public long userCommission;

    public RptQuizPartnerExcelVo(String rptDate, String businessName, String groupCode, String groupName, Long impressionCnt, Long hintCnt, Long clickCnt, long partnerCommission, long userCommission) {
        this.rptDate = rptDate;
        this.businessName = businessName;
        this.groupCode = groupCode;
        this.groupName = groupName;
        this.impressionCnt = impressionCnt;
        this.hintCnt = hintCnt;
        if (impressionCnt > 0) {
            this.CTR = (double) Math.round(((float) clickCnt / impressionCnt) * 100);
        } else {
            this.CTR = 0.00d;
        }
        this.clickCnt = clickCnt;
        this.partnerCommission = partnerCommission;
        this.userCommission = userCommission;
    }
}
