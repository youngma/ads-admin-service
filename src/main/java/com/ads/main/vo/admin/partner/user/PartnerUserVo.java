package com.ads.main.vo.admin.partner.user;

import com.ads.main.vo.admin.partner.PartnerVo;
import com.ads.main.vo.admin.user.UserVo;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class PartnerUserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 순번
     */
    private Long seq;


    /**
     * 광고주 순번
     */
    private PartnerVo partner;


    /**
     * 사용자 순번
     */
    private UserVo user;

}
