package com.ads.main.vo.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ads.main.core.enums.user.UserStatus;
import com.ads.main.core.security.config.dto.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class UserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 사용자 SEQ
     */
    private Long userSeq;


    /**
     * 사용자 아이디
     */
    @NotNull(message = "사용자 아이디는 필수 입니다.")
    protected String userId;


    /**
     * 사용자 명
     */
    @Getter
    @NotNull(message = "사용자 명은 필수 입니다.")
    protected String userName;


    /**
     * 사용자 권한(파트너, 관리자, 매체)
     */
    protected Role userRole;


    /**
     * 사용자 비밀번호
     */
    @NotNull(message = "비밀번호는 필수 입니다.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected String userPassword;

    /**
     * 사용자 전화 번호
     */
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    protected String phoneNumber;

    /**
     * 등록일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime insertedAt;


    /**
     * 등록자
     */
    protected String insertedId;


    /**
     * 수정일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime updatedAt;


    /**
     * 수정자
     */
    protected String updatedId;


    /**
     * ㅇㅠ
     */
    protected String userStatus;

    protected String userStatusName;

    public String getUserStatusName() {
        return UserStatus.of(userStatus).getName();
    }

}
