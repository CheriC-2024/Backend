package com.art.cheric.module.artist.domain.entity;

import com.art.cheric.global.enums.BankType;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class ArtistBank {
    private String bankName;

    private String bankNum;

    private BankType bankType;

    public ArtistBank() {
    }

    public ArtistBank(String bankName, String bankNum, BankType bankType) {
        this.bankName = bankName;
        this.bankNum = bankNum;
        this.bankType = bankType;
    }


}
