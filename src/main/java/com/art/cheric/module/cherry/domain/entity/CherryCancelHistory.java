package com.art.cheric.module.cherry.domain.entity;

import com.art.cheric.global.common.BaseTime;
import com.art.cheric.module.user.domain.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class CherryCancelHistory extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cherry_buy_history_id", nullable = false)
    private CherryBuyHistory cherryBuyHistory;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    private int cancelAmount;

    @NotNull
    private int taxFreeAmount;

    @NotNull
    private String cancelStatus;

    @NotNull
    private String transactionKey;

    @NotNull
    private Date canceledAt;

    public static CherryCancelHistory of(@NotNull User user, @NotNull int cancelAmount, @NotNull int taxFreeAmount,
                                         @NotNull String cancelStatus, @NotNull String transactionKey,
                                         @NotNull Date canceledAt) {
        return CherryCancelHistory.builder()
                .user(user)
                .cancelAmount(cancelAmount)
                .taxFreeAmount(taxFreeAmount)
                .cancelStatus(cancelStatus)
                .transactionKey(transactionKey)
                .canceledAt(canceledAt)
                .build();
    }

}
