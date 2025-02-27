package com.ads.main.vo.admin.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class RptSinkTimeVo implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 스케쥴 명
     */
    @NotNull(message = "scheduleName can not null")
    private String scheduleName;


    /**
     * 마지막 적재 시간
     */
    @NotNull(message = "lastSink can not null")
    private String lastSink;


    /**
     * 최종 업데이트 시간
     */
    @NotNull(message = "lastUpdateTim can not null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateTim;

}
