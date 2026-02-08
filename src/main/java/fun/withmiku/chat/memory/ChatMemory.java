package fun.withmiku.chat.memory;

import fun.withmiku.chat.ai.model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatMemory {
    private static final int MAX_HISTORY = 10;

    // 滑动窗口：只存 user / assistant
    private final List<Message> messages = new ArrayList<>();

    public void add(Message message) {
        messages.add(message);

        if (messages.size() > MAX_HISTORY) {
            // 如果 MAX_HISTORY 过大，可以用链表优化
            messages.remove(0);
        }
    }

    public List<Message> getAll() {
        // 防止暴露内部集合
        return List.copyOf(messages);
    }
}
