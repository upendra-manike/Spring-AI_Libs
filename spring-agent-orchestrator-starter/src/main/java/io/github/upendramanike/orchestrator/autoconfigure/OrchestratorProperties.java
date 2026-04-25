package io.github.upendramanike.orchestrator.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "orchestrator")
public class OrchestratorProperties {
    private List<WorkflowConfig> workflows;

    @Data
    public static class WorkflowConfig {
        private String name;
        private List<String> dag;
    }
}
