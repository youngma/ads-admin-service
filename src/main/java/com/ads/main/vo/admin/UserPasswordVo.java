package com.ads.main.vo.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class UserPasswordVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 사용자 SEQ
     */
    private Long usrSeq;

    @NotNull(message = "userPassword can not null")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String currentUserPassword;


    @NotNull(message = "userPassword can not null")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String nextUserPassword;
}
