package com.ads.main.vo.report.resp;


import com.ads.main.vo.report.RptSinkTimeVo;
import lombok.Data;

import java.util.List;

@Data
public class RptDashboard {

    private Long AdvertiserCount;
    private Long PartnerCount;
    private Long CampaignCount;
    private Long GroupCount;

    private List<RptQuizDailyVo> rptQuizDailyList;

    private RptSinkTimeVo lastUpdate;
}
