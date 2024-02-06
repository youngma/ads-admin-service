package com.ads.main.vo.admin.partner.adGroup;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ads.main.core.enums.advertiser.AdGroupStatus;
import com.ads.main.core.enums.campaign.CampaignType;
import com.ads.main.vo.admin.FilesVo;
import com.ads.main.vo.admin.partner.PartnerVo;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerAdGroupVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 광고 그룹 순번
     */
    private Long groupSeq;


    /**
     * 매체사 순번
     */
    private PartnerVo partner;


    /**
     * 서비스 타입
     */
    private String adType;
    private String adTypeName;

    public String getAdTypeName() {
        return CampaignType.of(this.adType).getName();
    }

    /**
     * 광고 그룹 코드
     */
    private String groupCode;

    /**
     * 광고 그룹 명
     */
    private String groupName;


    /**
     * 서비스 로고 파일
     */
    private FilesVo logoFile;


    /**
     * 포인트 로고 파일
     */
    private FilesVo pointIconFile;


    /**
     * 포인트 명
     */
    private String pointName;


    /**
     * 서비스 콜백 URL
     */
    private String callBackUrl;


    /**
     * 수수료 비율(매체사)
     */
    private Integer commissionRate;


    /**
     * 수수료 비율(사용자)
     */
    private Integer userCommissionRate;

    /**
     * 포인트 교환 비율
     */
    private Double rewordRate;


    /**
     * 서비스 상태
     */
    private String groupStatus;
    private String groupStatusName;


    public String getGroupStatusName() {
        return AdGroupStatus.of(this.groupStatus).getName();
    }


    /**
     * 등록 일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date insertedAt;


    /**
     * 등록자
     */
    private String insertedId;


    /**
     * 수정 일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;


    /**
     * 수정자
     */
    private String updatedId;


    /**
     * 요청 시간
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestAt;

    /**
     * 승인 처리 시간
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime approvalAt;

    /**
     * 보류 처리 시간
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime holdAt;

    /**
     * 거절 처리 시간
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rejectAt;

    /**
     * 보류 메시지
     */
    private String holdMessage;

    /**
     * 거절 메시지
     */
    private String rejectMessage;

}
