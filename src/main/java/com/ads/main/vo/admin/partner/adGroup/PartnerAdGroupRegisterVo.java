package com.ads.main.vo.admin.partner.adGroup;

import com.ads.main.vo.admin.FilesVo;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;


@Data
public class PartnerAdGroupRegisterVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 매체사 순번
     */
    @NotNull(message = "매체사 순번은 필수 입니다.")
    private Long partnerSeq;


    /**
     * 서비스 타입
     */
    @NotNull(message = "서비스 타입은 필수 입니다.")
    private String adType;


    /**
     * 광고 그룹 명
     */
    @NotNull(message = "광고 그룹 명은 필수 입니다.")
    private String groupName;

    private String groupCode;

    /**
     * 서비스 로고 파일
     */
    @NotNull(message = "서비스 로고 파일은 필수 입니다.")
    private FilesVo logoFile;


    /**
     * 포인트 로고 파일
     */
    @NotNull(message = "포인트 로고 파일은 필수 입니다.")
    private FilesVo pointIconFile;


    /**
     * 포인트 명
     */
    @NotNull(message = "포인트 명은 필수 입니다.")
    private String pointName;


    /**
     * 서비스 콜백 URL
     */
    @NotNull(message = "콜백 URL 은 필수 입니다.")
    private String callBackUrl;


    /**
     * 수수로 비율(매체사)
     */
    @Min(value = 0, message = "수수로 비율 0보다 커다 합니다.")
    @Max(value = 100, message = "수수로 비율 100보다 작아야 합니다.")
    private Integer commissionRate;

    /**
     * 수수로 비율(사용자)
     */
    @Min(value = 0, message = "수수로 비율 0보다 커다 합니다.")
    @Max(value = 100, message = "수수로 비율 100보다 작아야 합니다.")
    private Integer userCommissionRate;

    /**
     * 포인트 교환 비율
     */
    @Min(value = 0, message = "포인트 교환 비율 0보다 커다 합니다.")
    @Max(value = 100, message = "포인트 교환 비율 100보다 작아야 합니다.")
    private Integer rewordRate;


    private HashSet<Long> mappingAds;
}
