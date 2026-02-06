package fun.withmiku.chat.ai;

import fun.withmiku.chat.config.AiProperties;
import fun.withmiku.chat.ai.model.Message;
import fun.withmiku.chat.dto.ChatRequest;
import fun.withmiku.chat.dto.ChatResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class AiClientImpl implements AiClient {
    private final WebClient webClient;
    private final AiProperties properties;

    public AiClientImpl(WebClient.Builder builder, AiProperties properties) {
        this.properties = properties;
        this.webClient = builder
                .baseUrl(properties.getBaseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION,
                        "Bearer " + properties.getApiKey())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
    @Override
    public String chat(List<Message> messages) {
        ChatRequest request = ChatRequest.from(messages, properties.getModel());

        ChatResponse response = webClient.post()
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ChatResponse.class)
                .block();

        return response.firstReply();
    }
}
