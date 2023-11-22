package com.ads.main.core.clients.haders;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Getter
@Slf4j
abstract class AbstractHeaders {

    @PostConstruct
    protected abstract void init();

    protected final HashMap<String, String> headers = new HashMap<>();

}
