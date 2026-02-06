package fun.withmiku.chat.dto;

import fun.withmiku.chat.ai.model.Message;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChatRequest {
    private String model;
    private List<Message> messages;

    public static ChatRequest from(List<Message> messages, String model) {
        return new ChatRequest(model, messages);
    }
}
