package fun.withmiku.chat.dto;

import fun.withmiku.chat.ai.model.Message;
import lombok.Data;

import java.util.List;

@Data
public class ChatResponse {
    private List<Choice> choices;

    public String firstReply() {
        if (choices == null || choices.isEmpty()) {
            // 兜底回复
            return "……我在这里。";
        }
        return choices.get(0).getMessage().content();
    }

    @Data
    public static class Choice {
        private Message message;
    }
}
