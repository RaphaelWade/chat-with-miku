package fun.withmiku.chat.ai;

import fun.withmiku.chat.ai.model.Message;

import java.util.List;

public interface AiClient {
    String chat(List<Message> messages);
}
