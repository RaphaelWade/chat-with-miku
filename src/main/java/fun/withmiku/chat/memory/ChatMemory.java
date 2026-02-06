package fun.withmiku.chat.memory;

import fun.withmiku.chat.ai.model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatMemory {
    private static final int MAX_HISTORY = 10;

    private final List<Message> messages = new ArrayList<>();

    public void add(Message message) {
        messages.add(message);
        if (messages.size() > MAX_HISTORY) {
            messages.remove(0);
        }
    }

    public List<Message> getAll() {
        return messages;
    }
}
