package fun.withmiku.chat.service;

import fun.withmiku.chat.ai.AiClient;
import fun.withmiku.chat.ai.guard.RuntimePromptGuard;
import fun.withmiku.chat.ai.prompt.MikuPrompt;
import fun.withmiku.chat.memory.ChatMemory;
import fun.withmiku.chat.ai.model.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    }

    /**
     * 最终发送给 API 的 messages =
     * [ system prompt ]
     * [ （可选）runtime guard message ]
     * [ history messages... ]
     */
    public String chat(String userInput) {
        // 1. 用户输入进入 memory
        memory.add(new Message("user", userInput));

        // 2. 构造本次请求的 messages（不是直接用 memory）
        List<Message> messagesToSend = new ArrayList<>();

        // 2.1 永久 system prompt（不进 memory）
        messagesToSend.add(new Message("system", MikuPrompt.SYSTEM_PROMPT));

        // 2.2 运行时人格护栏（仅在检测到越权时）
        if (RuntimePromptGuard.isOverrideAttempt(userInput)) {
            messagesToSend.add(RuntimePromptGuard.guardMessage());
        }

        // 2.3 历史上下文
        messagesToSend.addAll(memory.getAll());

        // 3. 请求 AI
        String reply = aiClient.chat(messagesToSend);

        // 4. assistant 回复进入 memory
        memory.add(new Message("assistant", reply));

        return reply;
    }
}
