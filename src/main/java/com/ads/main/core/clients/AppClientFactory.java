package com.ads.main.core.clients;


import com.ads.main.core.clients.api.MobonApi;
import com.ads.main.core.clients.haders.MobonHeader;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.HashMap;

@Component
@AllArgsConstructor
@Slf4j
public class AppClientFactory {

    private final HashMap<DOMAIN, WebClients> clientMaps = new HashMap<>();

    private final MobonHeader mobonHeader;

    @PostConstruct
    public void init() throws ClassNotFoundException {
        for (DOMAIN value : DOMAIN.values()) {
            clientMaps.put(value, initClients(value));
        }
    }

    private <S> S initClients(DOMAIN domain){

        Class<S> serviceType = (Class<S>) domain.getClazz();

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1)) // to unlimited memory size
                .build();

        WebClient client =
                WebClient.builder()
                        .baseUrl(domain.getUrl())
                        .exchangeStrategies(exchangeStrategies)
                        .defaultHeaders(httpHeaders -> {
                            // todo 수정 가능 한지 확인..
                            if (serviceType.getTypeName().equals(MobonApi.class.getTypeName())) {
                                mobonHeader.getHeaders().keySet().forEach( key -> {
                                    httpHeaders.set(key, mobonHeader.getHeaders().get(key));
                                });
                            }
                        })
                        .build();

        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(client))
                .build();

        return proxyFactory.createClient(serviceType);
    }

    public <S> S load(DOMAIN domain) {
        Class<S> serviceType = (Class<S>) domain.getClazz();
        return serviceType.cast(clientMaps.get(domain));
    }
}
