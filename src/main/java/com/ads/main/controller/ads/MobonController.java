package com.ads.main.controller.ads;

import com.ads.main.core.clients.AppClientFactory;
import com.ads.main.core.clients.DOMAIN;
import com.ads.main.core.clients.api.MobonApi;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/admin/v1/mobon")
@RestController
@Slf4j
@Validated
public class MobonController {

    private final AppClientFactory appClientFactory;

    @GetMapping("/search")
    public String textToImage(
            @RequestParam(value = "s") @NotNull(message = "지면 코드는 필수 값입니다.") String code
            ) {
        MobonApi mobonApi = appClientFactory.load(DOMAIN.MOBON_API);
        return mobonApi.search(code, "100","0","99", "Y", "false");
    }
}
