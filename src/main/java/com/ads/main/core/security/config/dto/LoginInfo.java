package com.ads.main.core.security.config.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.token.Sha512DigestUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;


@Getter
@Setter
@ToString
public class LoginInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long userSeq;
    private String userId;
    private String password;
    private String phoneNumber;
    private String userName;
    public LoginInfo(Long userSeq, String userId, String password, String phoneNumber, String userName) {
        this.userSeq = userSeq;
        this.userId = userId;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
    }
}
