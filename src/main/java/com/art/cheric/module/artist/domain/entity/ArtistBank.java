package com.art.cheric.module.artist.domain.entity;

import com.art.cheric.global.enums.BankType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Embeddable
@Getter
public class ArtistBank {
    private String bankName;

    private String bankNum;

    @Enumerated(EnumType.STRING)
    private BankType bankType;

    protected ArtistBank() {
    }

    public ArtistBank(String bankName, String bankNum, BankType bankType) {
        this.bankName = bankName;
        this.bankNum = bankNum;
        this.bankType = bankType;
    }
}
