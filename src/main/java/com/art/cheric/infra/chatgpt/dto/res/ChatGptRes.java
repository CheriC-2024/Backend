package com.art.cheric.infra.chatgpt.dto.res;

import java.util.List;

public record ChatGptRes(
        List<Choice> choices
) {
    public record Choice(
            Message message
    ) {
        public record Message(
                String role,
                String content
        ) {
        }
    }
}