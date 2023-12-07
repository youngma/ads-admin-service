package com.ads.main.vo.inf.mobi;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Client {

    private String target;
    private int length;
    private boolean noExposureYN;
    private String mobonLogo;
    private String mobonInfo;
    private String bntype;
    private List<Data> data;

}
