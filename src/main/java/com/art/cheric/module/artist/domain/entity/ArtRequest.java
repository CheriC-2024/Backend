package com.art.cheric.module.artist.domain.entity;

import com.art.cheric.global.common.BaseTime;
import com.art.cheric.module.user.domain.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
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
public class ArtRequest extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_user_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_user_id", nullable = false)
    private User recipient;

    @NotNull
    private String artName;

    @NotNull
    private String message;

    public static ArtRequest of(@NotNull User sender, @NotNull User recipient, @NotNull String artName,
                                @NotNull String message) {
        return ArtRequest.builder()
                .sender(sender)
                .recipient(recipient)
                .artName(artName)
                .message(message)
                .build();
    }
}
