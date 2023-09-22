package com.ads.main.vo.advertiser.campaign.req;

import com.ads.main.vo.FilesVo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class AdQuizRegisterVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 참여 방법
     */
    @NotBlank(message = "참여 방법 은 필수 입니다.")
    private String useHow;



    /**
     * 퀴즈 문제
     */
    @NotBlank(message = "퀴즈 문제 은 필수 입니다.")
    private String quizTitle;


    /**
     * 퀴즈 정답
     */
    @NotBlank(message = "퀴즈 정답 은 필수 입니다.")
    private String quizAnswer;


    /**
     * 리스트 노출 이미지
     */
    @NotNull(message = "리스트 노출 이미지 필수 입니다.")
    private FilesVo mainImage;

    /**
     * 상세 노출 이미지1
     */
    @NotNull(message = "상세 노출 이미지1 필수 입니다.")
    private FilesVo detailImage1;

    /**
     *상세 노출 이미지2
     */
    @NotNull(message = "상세 노출 이미지2 필수 입니다.")
    private FilesVo detailImage2;

    /**
     * 랜딩 URL(PC)
     */
    @NotBlank(message = "PC 랜딩 URL 은 필수 입니다.")
    private String targetUrlPc;


    /**
     * 랜딩 URL(MOBILE)
     */
    @NotBlank(message = "모바일 랜딩 URL 은 필수 입니다.")
    private String targetUrlMobile;

    /**
     * 상품 코드
     */
    @NotBlank(message = "상품 코드 은 필수 입니다.")
    private String goodsCode;


}
