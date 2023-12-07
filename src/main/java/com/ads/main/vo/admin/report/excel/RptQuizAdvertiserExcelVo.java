package com.ads.main.vo.admin.report.excel;

import com.ads.main.core.annotation.ExcelColumnName;
import lombok.ToString;

import java.math.BigDecimal;


@ToString
public class RptQuizAdvertiserExcelVo {


    @ExcelColumnName(name = "일자")
    public String rptDate;

    @ExcelColumnName(name = "광고주 명")
    public String businessName;

    @ExcelColumnName(name = "광고 코드")
    public String campaignCode;

    @ExcelColumnName(name = "광고 명")
    public String campaignName;


    @ExcelColumnName(name = "노출 수")
    public Long impressionCnt;

    @ExcelColumnName(name = "클릭 수")
    public Long hintCnt;

    @ExcelColumnName(name = "CTR")
    public Double CTR;

    @ExcelColumnName(name = "참여자 수")
    public Long clickCnt;

    @ExcelColumnName(name = "광고 단가")
    public BigDecimal originAdCost;

    @ExcelColumnName(name = "광고 금액(합계)")
    public long adCost;

    public RptQuizAdvertiserExcelVo(String rptDate, String businessName, String campaignCode, String campaignName, Long impressionCnt, Long hintCnt, Long clickCnt, BigDecimal originAdCost, long adCost) {
        this.rptDate = rptDate;
        this.businessName = businessName;
        this.campaignCode = campaignCode;
        this.campaignName = campaignName;
        this.impressionCnt = impressionCnt;
        this.hintCnt = hintCnt;
        if (impressionCnt > 0) {
            this.CTR = (double) Math.round(((float) clickCnt / impressionCnt) * 100);
        } else {
            this.CTR = 0.00d;
        }
        this.clickCnt = clickCnt;
        this.originAdCost = originAdCost;
        this.adCost = adCost;
    }
}
