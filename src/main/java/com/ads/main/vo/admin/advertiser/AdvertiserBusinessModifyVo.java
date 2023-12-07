package com.ads.main.vo.admin.advertiser;

import com.ads.main.vo.admin.FilesVo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class AdvertiserBusinessModifyVo implements Serializable {

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
     * 사업자 번호
     */
    @NotBlank(message = "사업자 번호는 필수 입니다.")
    @Pattern(regexp = "([0-2])([0-9])([0-9])([0-9])([0-9])([0-9])([0-4])([0-9])([0-9])([0-9])", message = "사업자 번호를 다시 확인해주세요.")
    private String businessNumber;


    /**
     * 사업자 등록증 파일
     */
    @NotNull(message = "사업자 등록증 파일 필수 입니다.")
    private FilesVo file;

    private String ifCode;
}
