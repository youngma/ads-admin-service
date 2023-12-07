package com.ads.main.vo.admin.partner.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ads.main.core.enums.common.Bank;
import com.ads.main.vo.admin.FilesVo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Data
public class PartnerAccountVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 순번
     */
    @NotNull(message = "seq can not null")
    private Long seq;


    /**
     * 광고주 순번
     */
    private Long partnerSeq;


    /**
     * 은행 코드
     */
    private String bankCode;
    private String bankCodeName;

    public String getBankCodeName() {
        return Bank.of(bankCode).getFullName();
    }

    /**
     * 계좌 번호
     */
    private String bankAccount;


    /**
     * 예금주
     */
    private String accountHolder;


    /**
     * 사용 여부
     */
    private Boolean accountUse;


    /**
     * 등록 일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date insertedAt;


    /**
     * 등록자
     */
    private String insertedId;


    /**
     * 수정 일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date updatedAt;


    /**
     * 수정자
     */
    private String updatedId;


    /**
     * 계좌 파일
     */
    @NotNull(message = "계좌 사본은 필수 입니다.")
    private FilesVo file;


}
