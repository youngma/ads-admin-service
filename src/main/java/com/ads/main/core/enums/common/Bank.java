package com.ads.main.core.enums.common;


import com.ads.main.core.vo.CodeVo;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Getter
@Slf4j
public enum Bank {

    KYONGNAMBANK("경남은행","039", "39", "경남", "KYONGNAMBANK"),
    GWANGJUBANK("광주은행","034", "34", "광주", "GWANGJUBANK"),
    LOCALNONGHYEOP("단위농협(지역농축협)","012", "12", "단위농협", "LOCALNONGHYEOP"),
    BUSANBANK("부산은행","032", "32", "부산", "BUSANBANK"),
    SAEMAUL("새마을금고","045", "45", "새마을", "SAEMAUL"),
    SANLIM("산림조합","064", "64", "산림", "SANLIM"),
    SHINHAN("신한은행","088", "88", "신한", "SHINHAN"),
    SHINHYEOP("신협","048", "48", "신협", "SHINHYEOP"),
    CITI("씨티은행","027", "27", "씨티", "CITI"),
    WOORI("우리은행","020", "20", "우리", "WOORI"),
    POST("우체국예금보험","071", "71", "우체국", "POST"),
    SAVINGBANK("저축은행중앙회","050", "50", "저축은행중앙회", "SAVINGBANK"),
    JEONBUKBANK("전북은행","037", "37", "전북", "JEONBUKBANK"),
    JEJUBANK("제주은행","035", "35", "제주", "JEJUBANK"),
    KAKAOBANK("카카오뱅크","090", "90", "카카오", "KAKAOBANK"),
    KBANK("케이뱅크","089", "89", "케이", "KBANK"),
    TOSSBANK("토스뱅크","092", "92", "케이", "TOSSBANK"),
    HANA("하나은행","081", "81", "하나", "HANA"),
    HSBC("홍콩상하이은행","054", "54", "-", "HANA"),
    IBK("홍콩상하이은행","003", "03", "기업", "IBK"),
    KOOKMIN("KB국민은행","004", "06", "국민", "KOOKMIN"),
    DAEGUBANK("DGB대구은행","031", "31", "대구", "DAEGUBANK"),
    KDBBANK("KDB산업은행","002", "02", "산업", "KDBBANK"),
    NONGHYEOP("NH농협은행","011", "11", "농협", "NONGHYEOP"),
    SC("SC제일은행","023", "23", "SC제일", "SC"),
    SUHYEOP("Sh수협은행","007", "07", "수협", "SUHYEOP"),
    ;


    private final String fullName;
    private final String code_3;
    private final String code_2;

    private final String kr;
    private final String en;


    Bank(String fullName, String code_3, String code_2, String kr, String en ) {
        this.fullName = fullName;
        this.code_3 = code_3;
        this.code_2 = code_2;
        this.kr = kr;
        this.en = en;
    }

    public static Bank of(final String code_3) {
        return Arrays.stream(Bank.values())
                .filter(value -> value.getCode_3().equals(code_3))
                .findAny()
                .orElseGet(() -> null);
    }

    public static List<CodeVo> getCodes() {
        return Arrays.stream(Bank.values())
                .sorted(Comparator.comparing(Bank::getCode_3))
                .map(t -> new CodeVo(t.getCode_3(), t.getKr()))
                .toList();
    }
}
