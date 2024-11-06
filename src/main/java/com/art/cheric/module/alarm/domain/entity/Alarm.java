package com.art.cheric.module.alarm.domain.entity;

import com.art.cheric.global.common.BaseTime;
import com.art.cheric.global.enums.AlarmType;
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
public class Alarm extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private AlarmType alarm;

    public static Alarm of(@NotNull User user, @NotNull String title, @NotNull String content,
                           @NotNull AlarmType alarm) {
        return Alarm.builder()
                .user(user)
                .title(title)
                .content(content)
                .alarm(alarm)
                .build();
    }

}
