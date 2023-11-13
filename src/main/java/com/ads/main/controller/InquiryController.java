package com.ads.main.controller;


import com.ads.main.core.vo.RespVo;
import com.ads.main.service.AdInquiryService;
import com.ads.main.vo.inquiry.req.AdInquiryAnswerVo;
import com.ads.main.vo.inquiry.req.AdInquirySearchVo;
import com.ads.main.vo.inquiry.resp.AdInquiryVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RequestMapping("/admin/v1/inquiry")
@RestController
@Slf4j
@Validated
public class InquiryController {


    private final AdInquiryService inquiryService;

    @GetMapping("/search")
    public RespVo<Page<AdInquiryVo>> selectInquiry(
            @RequestParam("searchType") String searchType,
            @RequestParam("searchText") String searchText,
            @RequestParam("startDate")  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate,
            @RequestParam("inquiryStatus") String inquiryStatus,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {

        AdInquirySearchVo adInquirySearchVo = AdInquirySearchVo.builder()
                .searchType(searchType)
                .searchStartDt(startDate)
                .searchEndDt(endDate)
                .searchText(searchText)
                .build();

        adInquirySearchVo.setStatus(inquiryStatus);

        log.info("# search = {}, {}", inquiryStatus, adInquirySearchVo);

        return new RespVo<>(inquiryService.findAdInquiryList(adInquirySearchVo, PageRequest.of(page - 1, size)));
    }
    @PostMapping("/answer")
    public RespVo<String> saveAnswer(
            @RequestBody @Validated AdInquiryAnswerVo answerVo
    ) {
        return new RespVo<>(inquiryService.saveAnswer(answerVo));
    }

}
