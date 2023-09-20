package com.ads.main.vo.partner.account;

import com.ads.main.vo.FilesVo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class PartnerAccountRegisterVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;



    /**
     * 광고주 순번
     */
    private Long partnerSeq;


    /**
     * 은행 코드
     */
    private String bankCode;


    /**
     * 계좌 번호
     */
    private String bankAccount;


    /**
     * 예금주
     */
    private String accountHolder;


    /**
     * 계좌 파일
     */
    @NotNull(message = "계좌 사본은 필수 입니다.")
    private FilesVo file;

}
