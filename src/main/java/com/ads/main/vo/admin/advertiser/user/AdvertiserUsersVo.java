package com.ads.main.vo.admin.advertiser.user;

import com.ads.main.vo.admin.advertiser.AdvertiserVo;
import com.ads.main.vo.admin.user.UserVo;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
public class AdvertiserUsersVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 순번
     */
    private Long seq;


    /**
     * 광고주 순번
     */
    private AdvertiserVo advertiser;


    /**
     * 사용자 순번
     */
    private List<UserVo> users = new ArrayList<>();


    public void addUser(UserVo user) {
        this.users.add(user);
    }
}
