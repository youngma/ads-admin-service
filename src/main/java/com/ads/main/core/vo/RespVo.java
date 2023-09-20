package com.ads.main.core.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public record RespVo<T> (
        @JsonProperty("message")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("result")  T value
) {
    public RespVo(String message) {
        this(message, null);
    }

    public RespVo(T value) {
        this(null, value);
    }
}

