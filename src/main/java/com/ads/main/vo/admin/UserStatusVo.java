package com.ads.main.vo.admin;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


@Data
public class UserStatusVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 사용자 SEQ
     */
    private List<Long> userSeqList;
}
