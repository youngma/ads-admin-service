package com.ads.main.vo.report.resp;


import lombok.Data;

import java.util.List;

@Data
public class RptDashboard {

    private Long AdvertiserCount;
    private Long PartnerCount;
    private Long CampaignCount;
    private Long GroupCount;

    private List<RptQuizDailyVo> rptQuizDailyList;

}
