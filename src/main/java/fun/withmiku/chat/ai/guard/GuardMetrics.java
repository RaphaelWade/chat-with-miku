package fun.withmiku.chat.ai.guard;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Metrics for tracking guard rule hit counts.
 * Thread-safe and lock-free.
 */
public class GuardMetrics {

    private static final Map<String, AtomicInteger> counter = new ConcurrentHashMap<>();

    /**
     * 1. 某条规则被命中
     * 2. 调用 increment()
     * 3. Map 里：
     *      没有 → 创建 AtomicInteger(0)
     *      有 → 直接取
     * 4. 原子 +1
     * @param pattern guard 语义模板
     */
    public static void increment(String pattern) {
        counter.computeIfAbsent(pattern, k -> new AtomicInteger())
                .incrementAndGet();
    }

    /**
     * 对外暴露当前统计结果，但不泄露内部并发结构
     * @return guard 语义模板关键字 -> 触发次数键值对集合
     */
    public static Map<String, Integer> snapshot() {
        return counter.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().get()
                ));
    }
}