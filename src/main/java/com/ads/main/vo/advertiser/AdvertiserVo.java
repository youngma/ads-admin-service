package com.ads.main.vo.advertiser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ads.main.vo.FilesVo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Data
public class AdvertiserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 광고주 순번
     */
    private Long advertiserSeq;


    /**
     * 광고주 명
     */
    @NotBlank(message = "회사 명은 필수 입니다.")
    private String businessName;


    /**
     * 사업자 번호
     */
    @NotBlank(message = "사업자 번호는 필수 입니다.")
    @Pattern(regexp = "([0-2])([0-9])([0-9])([0-9])([0-9])([0-9])([1-4])([0-9])([0-9])([0-9])", message = "사업자 번호를 다시 확인 해주세요.")
    private String businessNumber;


    /**
     * 사업자 등록증 파일
     */
    @NotNull(message = "사업자 등록증 파일 필수 입니다.")
    private FilesVo file;

    /**
     * 사업자 전화 번호
     */
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    private String phoneNumber;


    /**
     * 사업자 이메일
     */
    @NotBlank(message = "이메일은 필수 입니다.")
    @Email (message = "이메일 형식의 형식을 확인 해주세요.")
    private String email;


    /**
     * 세금 계산서 발행 이메일
     */
    @NotBlank(message = "세금 계산서 발행 이메일은 필수 입니다.")
    @Email (message = "세금 계산서 발행 이메일 형식을 확인 해주세요.")
    private String taxBillEmail;


    /**
     * 광고주 명
     */
    @NotBlank(message = "광고주 명은 필수 입니다.")
    private String advertiserName;

    /**
     * 광고 연동 코드
     */
    private String ifCode;

    /**
     * 등록일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date insertedAt;


    /**
     * 등록자
     */
    private String insertedId;


    /**
     * 수정일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;


    /**
     * 수정자
     */
    private String updatedId;

}
