package com.art.cheric.infra.api;

import com.art.cheric.infra.chatgpt.dto.req.ChatGptReq;
import com.art.cheric.infra.chatgpt.dto.res.ChatGptRes;
import com.art.cheric.web_client.ApiWebClientBuilder;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class WebClientConnector {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

    private final ApiWebClientBuilder webClientBuilder;

    @Value("${openai.api.key}")
    private String CHAT_GPT_KEY;

    @Value("${openai.api.url}")
    private String CHAT_GPT_URL;


    public ChatGptRes callChatGpt(ChatGptReq requestDto) {
        return (ChatGptRes) webClientBuilder.request()
                .post(CHAT_GPT_URL, requestDto)
                .connectBlock(Map.of(AUTHORIZATION, BEARER + CHAT_GPT_KEY), ChatGptRes.class)
                .toObjectCall();
    }


}

