package com.ads.main.core.clients;

import com.ads.main.core.clients.api.MobonApi;
import lombok.Getter;

public enum DOMAIN {

    MOBON_API("mobon-api", "https://www.mediacategory.com", MobonApi.class),
    ;

    @Getter
    private final String serviceName;

    @Getter
    private final String url;

    @Getter
    private final Class clazz;

    DOMAIN(String serviceName, String url, Class<? extends WebClients> clazz) {
        this.serviceName = serviceName;
        this.url = url;
        this.clazz = clazz;
    }

}
