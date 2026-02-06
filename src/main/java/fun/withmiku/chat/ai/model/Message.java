package fun.withmiku.chat.ai.model;

/**
 * 消息结构
 * @param role system / user / assistant
 * @param content message
 */
public record Message(String role, String content) {
}
