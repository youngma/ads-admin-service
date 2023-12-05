package com.ads.main.core.clients.api;

import com.ads.main.core.clients.WebClients;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface MobonApi extends WebClients {
    @GetExchange(value = "/servlet/adNonSDKMobileBanner")
    String search(
            @RequestParam String s,
            @RequestParam String cntad,
            @RequestParam String cntsr,
            @RequestParam String bntype,
            @RequestParam String sslRedirect,
            @RequestParam String increaseViewCnt
    );
}
