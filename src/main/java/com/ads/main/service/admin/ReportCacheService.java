package com.ads.main.service.admin;

import com.ads.main.core.enums.advertiser.AdGroupStatus;
import com.ads.main.core.enums.campaign.CampaignStatus;
import com.ads.main.entity.mapper.RptQuizDailyConverter;
import com.ads.main.repository.jpa.*;
import com.ads.main.vo.admin.report.resp.RptDashboard;
import com.ads.main.vo.admin.report.resp.RptQuizDailyVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportCacheService {


    private final AdCampaignMasterRepository adCampaignMasterRepository;
    private final AdvertiserRepository advertiserRepository;
    private final PartnerAdGroupRepository partnerAdGroupRepository;
    private final PartnerRepository partnerRepository;
    private final RptQuizDailyRepository rptQuizDailyRepository;

    private final RptQuizDailyConverter rptQuizDailyConverter;


    @Cacheable(
            cacheNames = "dashboard-statistics"
            , key = "'count_' + #date"
            , unless = "#result == null"
    )
    public RptDashboard findDashboard(String date) {

        RptDashboard dashboard = new RptDashboard();

        Long advertiserCount = advertiserRepository.countBy();
        Long partnerCount = partnerRepository.countBy();
        Long campaignCount = adCampaignMasterRepository.countByAdEndDateAfterAndAdStartDateBeforeAndCampaignStatusAndExposureStatus(
                LocalDateTime.now(), LocalDateTime.now(), CampaignStatus.Approval, true
        );
        Long adgroupCount = partnerAdGroupRepository.countByGroupStatus(AdGroupStatus.Approval);

        dashboard.setAdvertiserCount(advertiserCount);
        dashboard.setPartnerCount(partnerCount);
        dashboard.setCampaignCount(campaignCount);
        dashboard.setGroupCount(adgroupCount);

        return dashboard;
    }

    @Cacheable(
            cacheNames = "dashboard-statistics"
            , key = "'list_' + #start+#end"
            , unless = "#result == null"
    )
    public List<RptQuizDailyVo> findDashboardList(String start, String end) {
        return rptQuizDailyConverter.toDtoList(rptQuizDailyRepository.findAllByRptDateBetweenOrderByRptDateDesc(start, end));
    }
}



