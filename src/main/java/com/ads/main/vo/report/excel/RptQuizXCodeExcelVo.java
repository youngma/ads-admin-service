package com.ads.main.vo.report.excel;

import com.ads.main.core.annotation.ExcelColumnName;
import com.ads.main.vo.report.resp.DailyReportVo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;



@ToString
public class RptQuizXCodeExcelVo {

    @ExcelColumnName(name = "일자")
    public String rptDate;

    @ExcelColumnName(name = "매체 명")
    public String partnerName;

    @ExcelColumnName(name = "채널 코드")
    public String groupCode;

    @ExcelColumnName(name = "채널 명")
    public String groupName;

    @ExcelColumnName(name = "광고주 명")
    public String advertiserName;

    @ExcelColumnName(name = "광고 코드")
    public String campaignCode;

    @ExcelColumnName(name = "광고 명")
    public String campaignName;

    /**
     * 광고 노출 수
     */
    @ExcelColumnName(name = "노출 수")

    public Long impressionCnt;

    /**
     * 정답 사용자 수
     */
    @ExcelColumnName(name = "참여자 수")
    public Long answerCnt;

    /**
     * 광고 힌트 랜딩 수
     */
    @ExcelColumnName(name = "클릭(힌트)")
    public Long hintCnt;

    /**
     * 광고 클릭 랜딩 수
     */
    @ExcelColumnName(name = "클릭(랜딩)")
    public Long clickCnt;


    @ExcelColumnName(name = "CTR")
    public Double CTR;

    @ExcelColumnName(name = "광고 금액(합계)")
    public long adCost;

    @ExcelColumnName(name = "매체사 리워드")
    public long partnerCommission;

    @ExcelColumnName(name = "사용자 리워드")
    public long userCommission;

    public RptQuizXCodeExcelVo(String rptDate, String partnerName, String groupCode, String groupName, String advertiserName, String campaignCode, String campaignName, Long impressionCnt, Long answerCnt, Long hintCnt, Long clickCnt, long adCost, long partnerCommission, long userCommission) {
        this.rptDate = rptDate;
        this.partnerName = partnerName;
        this.groupCode = groupCode;
        this.groupName = groupName;
        this.advertiserName = advertiserName;
        this.campaignCode = campaignCode;
        this.campaignName = campaignName;
        this.impressionCnt = impressionCnt;
        this.answerCnt = answerCnt;
        this.hintCnt = hintCnt;
        this.clickCnt = clickCnt;

        this.adCost = adCost;
        this.partnerCommission = partnerCommission;
        this.userCommission = userCommission;

        if (impressionCnt > 0) {
            this.CTR = (double) Math.round(((float) clickCnt / impressionCnt) * 100);
        } else {
            this.CTR = 0.00d;
        }
    }

}
