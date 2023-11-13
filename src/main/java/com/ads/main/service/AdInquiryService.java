package com.ads.main.service;

import com.ads.main.core.enums.exception.InquiryException;
import com.ads.main.entity.AdInquiryEntity;
import com.ads.main.repository.jpa.AdInquiryRepository;
import com.ads.main.repository.querydsl.QAdInquiryRepository;
import com.ads.main.vo.inquiry.req.AdInquiryAnswerVo;
import com.ads.main.vo.inquiry.req.AdInquirySearchVo;
import com.ads.main.vo.inquiry.resp.AdInquiryVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdInquiryService {

    private final AdInquiryRepository adInquiryRepository;
    private final QAdInquiryRepository qAdInquiryRepository;

    @Transactional(readOnly = true)
    public Page<AdInquiryVo> findAdInquiryList(AdInquirySearchVo searchVo, Pageable pageable) {
        return qAdInquiryRepository.searchAdInquiryList(searchVo, pageable);
    }

    public String saveAnswer(AdInquiryAnswerVo answerVo) {

        AdInquiryEntity adInquiryEntity = adInquiryRepository.findFirstBySeq(Long.valueOf(answerVo.getSeq())).orElseThrow(InquiryException.INQUIRY_NOT_FOUND::throwErrors);

        adInquiryEntity.setAnswer(answerVo.getAnswer());
        adInquiryRepository.save(adInquiryEntity);

        return "등록 되었습니다.";

    }

}

