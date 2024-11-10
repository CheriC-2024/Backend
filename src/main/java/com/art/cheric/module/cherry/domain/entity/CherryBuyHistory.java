package com.art.cheric.module.cherry.domain.entity;

import com.art.cheric.global.common.BaseTime;
import com.art.cheric.global.enums.CherryCancelableState;
import com.art.cheric.global.enums.PaymentType;
import com.art.cheric.module.user.domain.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class CherryBuyHistory extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    private String paymentKey;

    @NotNull
    private String orderId;

    @NotNull
    private String orderName;

    @NotNull
    private String currency;

    @NotNull
    private int cherryCount;

    @NotNull
    private int totalAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CherryCancelableState cancelableState;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @NotNull
    private Date requestedAt;

    private Date approvedAt;

    public static CherryBuyHistory of(@NotNull User user, @NotNull String paymentKey, @NotNull String orderId,
                                      @NotNull String orderName, @NotNull String currency, @NotNull int cherryCount,
                                      @NotNull int totalAmount, @NotNull CherryCancelableState cancelableState,
                                      @NotNull PaymentType paymentType, @NotNull Date requestedAt,
                                      @NotNull Date approvedAt) {
        return CherryBuyHistory.builder()
                .user(user)
                .paymentKey(paymentKey)
                .orderId(orderId)
                .orderName(orderName)
                .currency(currency)
                .cherryCount(cherryCount)
                .totalAmount(totalAmount)
                .cancelableState(cancelableState)
                .paymentType(paymentType)
                .requestedAt(requestedAt)
                .approvedAt(approvedAt)
                .build();
    }

    public void updateCherryState(CherryCancelableState cancelableState) {
        this.cancelableState = cancelableState;
    }

}
