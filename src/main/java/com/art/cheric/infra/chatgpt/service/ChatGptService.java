package com.art.cheric.infra.chatgpt.service;


import com.art.cheric.global.enums.ChatGptType;
import com.art.cheric.infra.api.WebClientConnector;
import com.art.cheric.infra.chatgpt.dto.req.ChatGptReq;
import com.art.cheric.infra.chatgpt.dto.req.ChatGptReq.Message;
import com.art.cheric.infra.chatgpt.dto.res.ChatGptRes;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatGptService {
    public static final String GPT_4 = "gpt-4";
    public static final String USER = "user";

    private final WebClientConnector connector;

    // gpt
    public String getChatGptResponse(String prompt) {
        // 요청 데이터 생성
        ChatGptReq request = ChatGptReq.of(GPT_4, List.of(Message.of(USER, prompt)));

        // API 호출
        ChatGptRes response = connector.callChatGpt(request);

        // 응답 처리
        return response.choices().get(0).message().content();
    }

    // 프롬프트
    public String buildPrompt(ChatGptType chatGptType, String propertiesString) {
        if (chatGptType == ChatGptType.THEME) {
            return String.format(
                    "전시 기획자로서, 아래 단어들의 분위기를 형용사로 표현해: %s. " +
                            "단, '미술', '작품', '디자인'은 제외하고 다른 단어의 의미를 반영해. " +
                            "한국어로 5개 추천하고, 각 형용사의 이유를 200자 내외로 설명해. " +
                            "결과는 JSON 형식 {result: [, , , , ], reason: [ , , , , ]}으로 반환해.",
                    propertiesString
            );

        } else {
            return String.format(
                    "너는 전시 기획자야. 아래 단어들의 작품 전시를 기획하려해 " +
                            "미술, 작품, 디자인 같은 단어를 제외한 나머지 의미를 담아 " +
                            "전시 제목:부제목 형식으로 5개를 한국어로 작성해.\n" +
                            "각 제목에 대한 이유를 200자 내외로 설명해. " +
                            "응답은 {result: [ , , , , ], reason: [ , , , , ]} 형식의 JSON으로 반환해."
                    , propertiesString
            );

        }
    }
}
