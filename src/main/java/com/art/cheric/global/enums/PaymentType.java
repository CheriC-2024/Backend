package com.art.cheric.global.enums;

import lombok.Getter;

@Getter
public enum PaymentType {
    CARD("카드"),
    VIRTUAL_ACCOUNT ("가상 계좌"),
    TRANSFER ("계좌이체"),
    MOBILE_PHONE ("휴대폰"),
    GIFT_CERTIFICATE  ("문화상품권"),
    BOOK_GIFT_CERTIFICATE  ("도서문화상품권"),
    GAME_GIFT_CERTIFICATE  ("게임문화상품권 "),
    EASY_PAY ("간편 결제");

    private final String value;

    PaymentType(String value){
        this.value = value;
    }
}
