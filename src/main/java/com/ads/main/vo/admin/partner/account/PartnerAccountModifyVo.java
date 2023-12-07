package com.ads.main.vo.admin.partner.account;

import com.ads.main.vo.admin.FilesVo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class PartnerAccountModifyVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 순번
     */
    @NotNull(message = "seq can not null")
    private Long seq;

    /**
     * 은행 코드
     */
    @NotBlank(message = "은행 코드는 필수 입니다.")
    private String bankCode;

    /**
     * 계좌 번호
     */
    @NotBlank(message = "계좌번호 는 필수 입니다.")
    private String bankAccount;


    /**
     * 예금주
     */
    @NotBlank(message = "예금주명 은 필수 입니다.")
    private String accountHolder;


    /**
     * 계좌 파일
     */
    @NotNull(message = "계좌 사본은 필수 입니다.")
    private FilesVo file;


    /**
     * 사용 여부
     */
    private boolean accountUse;

}
