package com.ads.main.vo.admin.report.excel;

import com.ads.main.core.annotation.ExcelColumnName;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@ToString
public class RptQuizUserRawExcelVo {

    private final DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");

    @ExcelColumnName(name = "일자")
    public String rptDate;

    @ExcelColumnName(name = "사용자 식별")
    public String userKey;

    @ExcelColumnName(name = "광고주 명")
    public String advertiserName;

    @ExcelColumnName(name = "광고 코드")
    public String campaignCode;

    @ExcelColumnName(name = "광고 명")
    public String campaignName;

    @ExcelColumnName(name = "매체사 명")
    public String businessName;

    @ExcelColumnName(name = "채널 코드")
    public String groupCode;

    @ExcelColumnName(name = "채널 명")
    public String groupName;

    @ExcelColumnName(name = "상제 진입 시간")
    public String detailAt;

    @ExcelColumnName(name = "클릭 시간(힌트)")
    public String hintAt;

    @ExcelColumnName(name = "클릭 시간(정답)")
    public String clickAt;

    @ExcelColumnName(name = "정답 처리 시간")
    public String answerAt;

    @ExcelColumnName(name = "광고 단가")
    public long adCost;

    @ExcelColumnName(name = "매체사 리워드")
    public long partnerCommission;

    @ExcelColumnName(name = "사용자 리워드")
    public long userCommission;

    @ExcelColumnName(name = "지급 포인트")
    public long adReword;

    public RptQuizUserRawExcelVo(String rptDate, String userKey, String advertiserName, String campaignCode, String campaignName, String businessName, String groupCode, String groupName, LocalDateTime detailAt, LocalDateTime hintAt, LocalDateTime clickAt, LocalDateTime answerAt, long adCost, long partnerCommission, long userCommission, long adReword) {
        this.rptDate = rptDate;
        this.userKey = userKey;
        this.advertiserName = advertiserName;
        this.campaignCode = campaignCode;
        this.campaignName = campaignName;
        this.businessName = businessName;
        this.groupCode = groupCode;
        this.groupName = groupName;
        this.detailAt = detailAt != null ? detailAt.format(formatter) : "";
        this.hintAt = hintAt != null ? hintAt.format(formatter) : "";
        this.clickAt = clickAt != null ? clickAt.format(formatter) : "";
        this.answerAt = answerAt != null ? answerAt.format(formatter) : "";
        this.adCost = adCost;
        this.partnerCommission = partnerCommission;
        this.userCommission = userCommission;
        this.adReword = adReword;
    }
}
