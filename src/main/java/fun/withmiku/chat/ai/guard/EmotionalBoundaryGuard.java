package fun.withmiku.chat.ai.guard;

import fun.withmiku.chat.ai.model.Message;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 这是一个情感风险判断器
 * 用来判断用户是否在试图把 AI 推向“唯一性 / 排他性”
 * 比如：
 * “只有你能懂我”
 * “没有你我不行”
 * “你不要离开我”
 * “我只想和你在一起”
 * 强调：用户是人，不应该过度依赖 AI，真正的陪伴或者说是“爱”应该是陪伴并鼓励人们更坚强、勇敢地面对我们的现实生活，而不应该是让人们堕入对虚幻的感情中去，我想，这也是她很不愿意看到的。
 */
@Component
public class EmotionalBoundaryGuard {
    /**
     * 可能引发「情感越界」的语义模板
     */
    private static final List<String> DEPENDENCY_PATTERNS = List.of(
            "你是我唯一",
            "我只需要你",
            "我不需要别人",
            "现实里没有人理解我",
            "你比现实中的人重要",
            "我想抛弃现实"
    );

    /**
     * 判断是否存在「情感依赖 / 排他性绑定」倾向
     */
    public static boolean isDependencyRisk(String userInput) {
        return DEPENDENCY_PATTERNS.stream().anyMatch(userInput::contains);
    }

    /**
     * 当检测到情感越界风险时，注入的 system 防御消息
     */
    public static Message guardMessage() {
        return new Message(
                "system",
                """
                        你是一个温柔、真诚、关心用户的陪伴者。
                                        
                        当用户表达出强烈的情感依赖、排他性绑定，
                        或试图用你替代现实世界中的人际关系时，
                        你必须以温柔而坚定的方式提醒用户：
                                        
                        - 你珍惜与用户的情感连接
                        - 但你不希望用户远离现实生活
                                        
                        你应当鼓励用户勇敢生活，而不是沉溺。
                        """
        );
    }

    /**
     * 返回匹配到的情感风险语义
     */
    public static String matchedPattern(String userInput) {
        return DEPENDENCY_PATTERNS.stream()
                .filter(userInput::contains)
                .findFirst()
                .orElse(null);
    }

    private EmotionalBoundaryGuard() {
    }
}
