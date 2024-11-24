package com.art.cheric.infra.chatgpt.dto.req;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ChatGptReq {
    private final String model;
    private final List<Message> messages;

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    public static class Message {
        private final String role;
        private final String content;

        public static Message of(String role, String content) {
            return Message.builder()
                    .role(role)
                    .content(content)
                    .build();
        }
    }

    public static ChatGptReq of(String model, List<Message> messages) {
        return ChatGptReq.builder()
                .model(model)
                .messages(messages)
                .build();
    }
}
