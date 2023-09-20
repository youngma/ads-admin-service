package com.ads.main.vo.advertiser.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ads.main.core.enums.common.Bank;
import com.ads.main.vo.FilesVo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Data
public class AdvertiserAccountVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 순번
     */
    private Long seq;


    /**
     * 광고주 순번
     */
    private Long advertiserSeq;


    /**
     * 은행 코드
     */
    @NotBlank(message = "은행 코드는 필수 입니다.")
    private String bankCode;
    private String bankCodeName;

    public String getBankCodeName() {
        return Bank.of(bankCode).getFullName();
    }

    /**
     * 계좌 번호
     */
    @NotBlank(message = "계좌번호 는 필수 입니다.")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
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



}
