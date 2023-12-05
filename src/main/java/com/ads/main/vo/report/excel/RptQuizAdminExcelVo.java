package com.ads.main.vo.report.excel;

import com.ads.main.core.annotation.ExcelColumnName;
import lombok.ToString;


@ToString
public class RptQuizAdminExcelVo {


    @ExcelColumnName(name = "일자")
    public String rptDate;

    /**
     * 광고주 수
     */
    @ExcelColumnName(name = "광고주 수")
    public Long advertiserCnt;


    /**
     * 광고 캠페인 수(진행)
     */
    @ExcelColumnName(name = "캠페인 수")
    public Long campaignCnt;


    /**
     * 매체사 수
     */
    @ExcelColumnName(name = "매체사 수")
    public Long partnerCnt;


    /**
     * 매체사 지면 수
     */
    @ExcelColumnName(name = "채널 수")
    public Long adGroupCnt;


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

    public RptQuizAdminExcelVo(String rptDate, Long advertiserCnt, Long campaignCnt, Long partnerCnt, Long adGroupCnt, Long impressionCnt, Long answerCnt, Long hintCnt, Long clickCnt, long adCost, long partnerCommission, long userCommission) {
        this.rptDate = rptDate;
        this.advertiserCnt = advertiserCnt;
        this.campaignCnt = campaignCnt;
        this.partnerCnt = partnerCnt;
        this.adGroupCnt = adGroupCnt;
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
