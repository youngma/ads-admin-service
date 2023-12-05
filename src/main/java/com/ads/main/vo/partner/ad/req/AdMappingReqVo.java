package com.ads.main.vo.partner.ad.req;

import com.ads.main.core.config.jpa.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;


@Data
@Getter
@Setter
public class AdMappingReqVo  {
    private Long partnerSeq;
    private Long groupSeq;
    HashSet<Long> mappingAds;
}
