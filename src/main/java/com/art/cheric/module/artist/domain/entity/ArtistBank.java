package com.art.cheric.module.artist.domain.entity;

import com.art.cheric.global.enums.BankType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class ArtistBank {
    private String bankName;

    private String bankNum;

    @Enumerated(EnumType.STRING)
    private BankType bankType;

    public ArtistBank of(String bankName, String bankNum, BankType bankType) {
        return ArtistBank.builder()
                .bankName(bankName)
                .bankNum(bankNum)
                .bankType(bankType)
                .build();
    }
}
