package io.github.upendramanike.orchestrator.agent;

import io.github.upendramanike.orchestrator.model.ExecutionContext;
import lombok.Builder;
import lombok.Data;

public interface Agent {
    AgentResult act(AgentInput input, ExecutionContext context);
    
    @Data
    @Builder
    class AgentInput {
        private String prompt;
        private Object data;
    }
    
    @Data
    @Builder
    class AgentResult {
        private String output;
        private Object structuredData;
    }
}
