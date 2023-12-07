package com.ads.main.schedule.quiz;

import com.ads.main.core.clients.AppClientFactory;
import com.ads.main.core.clients.DOMAIN;
import com.ads.main.core.clients.api.MobonApi;
import com.ads.main.core.utils.PageMapper;
import com.ads.main.repository.querydsl.QAdvertiserCampaignMasterRepository;
import com.ads.main.vo.inf.mobi.Data;
import com.ads.main.vo.inf.mobi.MobiAds;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class MobionAdsCheck {

    private final ObjectMapper objectMapper;
    private final AppClientFactory appClientFactory;

    private final QAdvertiserCampaignMasterRepository qAdvertiserCampaignMasterRepository;

    @PostConstruct
    private void initPageMappers() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }
    @Scheduled(cron = "1 * * * * ?")
    @Transactional
    public void check_889361() throws JsonProcessingException {

        MobonApi mobonApi = appClientFactory.load(DOMAIN.MOBON_API);
        String content =  mobonApi.search("889361", "100","0","99", "Y", "false");
        MobiAds mobiAds = objectMapper.readValue(content, MobiAds.class);

        List<String> keys = mobiAds
                .getClient()
                .stream()
                .flatMap(t -> t.getData().stream())
                .map(Data::getIncreaseViewKey)
                .toList();

        qAdvertiserCampaignMasterRepository.adCampaignClosed("889361", keys);

    }

}
