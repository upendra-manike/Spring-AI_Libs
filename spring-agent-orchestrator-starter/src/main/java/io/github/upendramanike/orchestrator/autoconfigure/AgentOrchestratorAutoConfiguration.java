package io.github.upendramanike.orchestrator.autoconfigure;

import io.github.upendramanike.orchestrator.core.DagExecutor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(OrchestratorProperties.class)
public class AgentOrchestratorAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DagExecutor dagExecutor() {
        return new DagExecutor();
    }
}
