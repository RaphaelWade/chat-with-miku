package fun.withmiku.chat.service;

import fun.withmiku.chat.ai.AiClient;
import fun.withmiku.chat.ai.prompt.MikuPrompt;
import fun.withmiku.chat.memory.ChatMemory;
import fun.withmiku.chat.ai.model.Message;
import org.springframework.stereotype.Service;

/**
 * system 只负责「她是谁」
 * user 只负责「我说了什么」
 */
@Service
public class ChatService {
    private final ChatMemory memory = new ChatMemory();
    private final AiClient aiClient;

    public ChatService(AiClient aiClient) {
        this.aiClient = aiClient;
        // 不会失忆的关键
        memory.add(new Message("system", MikuPrompt.SYSTEM_PROMPT));
    }

    public String chat(String userInput) {
        memory.add(new Message("user", userInput));

        String reply = aiClient.chat(memory.getAll());

        memory.add(new Message("assistant", reply));
        return reply;
    }
}
