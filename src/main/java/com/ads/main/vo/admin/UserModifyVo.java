package com.ads.main.vo.admin;

import com.ads.main.core.enums.user.UserStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;


@Data
public class UserModifyVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 사용자 SEQ
     */
    private Long userSeq;


    /**
     * 사용자 명
     */
    @Getter
    @NotNull(message = "userName can not null")
    private String userName;


    /**
     * 사용자 전화 번호
     */
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    private String phoneNumber;

    /**
     * 사용자 상태
     */
    private String userStatus;

    private String userStatusNm;

    public String getUserStatusNm() {
        try {
            return UserStatus.valueOf(this.userStatus).getName();
        } catch (Exception ignored) {
            return "";
        }
    }
}
