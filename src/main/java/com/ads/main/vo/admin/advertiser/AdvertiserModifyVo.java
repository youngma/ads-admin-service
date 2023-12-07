package com.ads.main.vo.admin.advertiser;

import com.ads.main.vo.admin.FilesVo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class AdvertiserModifyVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 광고주 순번
     */
    private Long advertiserSeq;


    /**
     * 광고주 명
     */
    @NotBlank(message = "사업자 명은 필수 입니다.")
    private String businessName;



    /**
     * 사업자 전화 번호
     */
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    private String phoneNumber;


    /**
     * 사업자 이메일
     */
    @NotBlank(message = "이메일 은 필수 입니다.")
    @Email (message = "이메일 형식이 아닙니다.")
    private String email;


    /**
     * 광고주 명
     */
    @NotBlank(message = "광고주 대표명 은 필수 입니다.")
    private String advertiserName;


    /**
     * 세금 계산서 발행 이메일
     */
    @NotBlank(message = "세금 계산서 발행 이메일은 필수 입니다.")
    @Email (message = "세금 계산서 발행 이메일 형식을 확인 해주세요.")
    private String taxBillEmail;


    /**
     * 광고 연동 코드
     */
    private String ifCode;

    /**
     * 사업자 등록증 파일
     */
//    @NotNull(message = "사업자 등록증 파일 필수 입니다.")
    private FilesVo file;
}
