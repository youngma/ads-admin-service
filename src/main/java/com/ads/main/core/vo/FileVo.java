package com.ads.main.core.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class FileVo {
    private String originFileName;
    private String newFileName;
    private String target;

    @JsonIgnore
    private String uploadFile;
}
