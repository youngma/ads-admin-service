package com.ads.main.vo.report.req;

import com.ads.main.core.security.config.dto.Role;
import com.querydsl.core.BooleanBuilder;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
@Builder
public class RptSearchVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    private Role role;

    private String searchStartDt;
    private String searchEndDt;


    private long advertiserSeq;
    private String campaignCode;


    private long partnerSeq;
    private String groupCode;


    public BooleanBuilder where() {

        BooleanBuilder builder = new BooleanBuilder();

//        switch (role) {
//            case Role.PARTNER -> {
//
//            }
//
//            case Role.ADVERTISER -> {
//
//            }
//        }

        return builder;
    }


}
