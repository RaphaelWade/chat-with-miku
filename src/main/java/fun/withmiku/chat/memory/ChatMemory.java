package fun.withmiku.chat.memory;

import fun.withmiku.chat.ai.model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatMemory {
    private static final int MAX_HISTORY = 10;

    // system prompt（目前只有一条）
    private Message systemMessage;

    // 滑动窗口：只存 user / assistant
    private final List<Message> messages = new ArrayList<>();

    public void add(Message message) {
        // 直接把 system prompt 单拎出来
        if ("system".equals(message.role())) {
            this.systemMessage = message;
            return;
        }

        messages.add(message);

        if (messages.size() > MAX_HISTORY) {
            // 如果 MAX_HISTORY 过大，可以用链表优化
            messages.remove(0);
        }
    }

    public List<Message> getAll() {
        List<Message> result = new ArrayList<>();

        // 单独把 system prompt 放进请求体数据中
        if (systemMessage != null) {
            result.add(systemMessage);
        }

        result.addAll(messages);

        return result;
    }
}
