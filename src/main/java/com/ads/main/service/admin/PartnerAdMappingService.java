package com.ads.main.service.admin;


import com.ads.main.core.utils.PageMapper;
import com.ads.main.entity.PartnerAdGroupEntity;
import com.ads.main.entity.PartnerAdMappingEntity;
import com.ads.main.entity.mapper.PartnerAdMappingConvert;
import com.ads.main.repository.jpa.PartnerAdMappingRepository;
import com.ads.main.vo.admin.partner.ad.PartnerAdMappingVo;
import com.ads.main.vo.admin.partner.ad.req.AdMappingReqVo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.ads.main.core.enums.exception.PartnerException.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PartnerAdMappingService {


    private final PartnerAdGroupService partnerAdGroupService;
    private final PartnerAdMappingRepository partnerAdMappingRepository;

    private final PartnerAdMappingConvert partnerAdMappingConvert;
    private PageMapper<PartnerAdMappingVo, PartnerAdMappingEntity> pageMapper;

    @PostConstruct
    private void initPageMappers() {
        pageMapper =  new PageMapper<>(partnerAdMappingConvert);
    }

   public List<PartnerAdMappingVo> fineMappingAds(Long groupSeq) {
        List<PartnerAdMappingEntity> partnerAdMappingEntities = partnerAdMappingRepository.findAllByPartnerAdGroupEntity_GroupSeq(groupSeq);
        return partnerAdMappingConvert.toDtoList(partnerAdMappingEntities);
    }

    public List<Long> findCampaignSeqByGroupSeq(Long partnerSeq, Long groupSeq) {

        Optional<PartnerAdGroupEntity> partnerAdGroupEntityOptional = partnerAdGroupService.findPartnerAdGroupEntityByPartnerSeqAndGroupSeq(partnerSeq, groupSeq);

        if (partnerAdGroupEntityOptional.isEmpty()) {
            throw PARTNER_AD_GROUP_NOT_FOUND.throwErrors();
        }

        return fineMappingAds(groupSeq).stream().map(PartnerAdMappingVo::getCampaignSeq).toList();
    }


    @Transactional
    public List<PartnerAdMappingVo> saveMappingAds(AdMappingReqVo mappingReqVo) {

        Optional<PartnerAdGroupEntity> partnerAdGroupEntityOptional = partnerAdGroupService.findPartnerAdGroupEntityByPartnerSeqAndGroupSeq(mappingReqVo.getPartnerSeq(), mappingReqVo.getGroupSeq());

        if (partnerAdGroupEntityOptional.isEmpty()) {
            throw PARTNER_AD_GROUP_NOT_FOUND.throwErrors();
        }

        ArrayList<PartnerAdMappingEntity> partnerAdMappingEntities = partnerAdMappingRepository.findAllByPartnerAdGroupEntity_GroupSeq(mappingReqVo.getGroupSeq());

        HashSet<Long> deleteSet = new HashSet<>();

        for (PartnerAdMappingEntity mappingEntity : partnerAdMappingEntities) {
            if (!mappingReqVo.getMappingAds().contains(mappingEntity.getCampaignSeq())) {
                deleteSet.add(mappingEntity.getCampaignSeq());
            } else {
                mappingReqVo.getMappingAds().remove(mappingEntity.getCampaignSeq());
            }
        }

        partnerAdMappingRepository.deleteAllByPartnerAdGroupEntity_GroupSeqAndCampaignSeqIn(mappingReqVo.getGroupSeq(), deleteSet.stream().toList());
        ArrayList<PartnerAdMappingEntity> newCampaigns = new ArrayList<>();
        mappingReqVo.getMappingAds().forEach(campaign -> {
            PartnerAdMappingEntity partnerAdMappingEntity = new PartnerAdMappingEntity();
            partnerAdMappingEntity.setPartnerAdGroupEntity(partnerAdGroupEntityOptional.get());
            partnerAdMappingEntity.setCampaignSeq(campaign);
            newCampaigns.add(partnerAdMappingEntity);
        });

        partnerAdMappingRepository.saveAllAndFlush(newCampaigns);

        return partnerAdMappingConvert.toDtoList(partnerAdMappingEntities);
    }

}
