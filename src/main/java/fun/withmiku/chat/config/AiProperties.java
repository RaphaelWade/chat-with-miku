package fun.withmiku.chat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ai")
@Data
public class AiProperties {
    private String baseUrl;
    private String apiKey;
    private String model;
}
