package com.ads.main.schedule.quiz;


import com.ads.main.core.enums.campaign.CampaignType;
import com.ads.main.entity.AdCampaignMasterEntity;
import com.ads.main.entity.QAdCampaignMasterEntity;
import com.ads.main.entity.mapper.AdCampaignMasterConvert;
import com.ads.main.entity.mapper.AdCampaignRedisConvert;
import com.ads.main.repository.querydsl.QAdvertiserCampaignMasterRepository;
import com.ads.main.repository.template.RptQuizRawTemplate;
import com.ads.main.vo.advertiser.campaign.resp.AdCampaignMasterVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class QuizAdSchedule {

    private final QAdvertiserCampaignMasterRepository qAdvertiserCampaignMasterRepository;

    private final AdCampaignRedisConvert adCampaignRedisConvert;
    private final RedissonClient redissonClient;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @PostConstruct
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
    }


    @Scheduled(cron = "1 * * * * ?")
//    @Scheduled(cron = "0 1/1 * * * *")
    public void quizAdSink() {

        log.info("# Quiz Ad Redis Sink Scheduler");

        long count =  qAdvertiserCampaignMasterRepository.findCountByCampaignType(CampaignType.Quiz01);

        int page = 0;
        int size = 20;

        log.info("# Quiz Ad Redis Clear : {}", LocalDateTime.now());

        RMap<String, String> maps = redissonClient.getMap(CampaignType.Quiz01.getCode());
        Set<String> campaignCodeSet =  maps.readAllKeySet();

        log.info("# Quiz Ad Count => {}", count);
        log.info("# Quiz Ad Set => {}", campaignCodeSet);

        while (count > (long) page * size) {
            List<AdCampaignMasterEntity> entities = qAdvertiserCampaignMasterRepository.findAllByCampaignType(CampaignType.All, PageRequest.of(page, size));
            entities.forEach(t -> {
                campaignCodeSet.remove(t.getCampaignCode());
                AdCampaignMasterVo adCampaignMasterVo = adCampaignRedisConvert.toDto(t);
                boolean isNewKey = false;
                try {
                    isNewKey = maps.fastPut(adCampaignMasterVo.getCampaignCode(), objectMapper.writeValueAsString(adCampaignMasterVo));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
//                log.info("# Quiz Add => {} ", isNewKey);
            });
            log.info("# Quiz Ad Sink => {}, {} ", page, size);
            page += 1;
        }


        log.info("# Quiz Ad Remove => {}", campaignCodeSet.size());
        // 종료된 광고 삭제
        campaignCodeSet
                .forEach(maps::remove);
    }

}
