package io.github.upendramanike.orchestrator.agent;

import java.util.List;
import lombok.Builder;
import lombok.Data;

public interface PlannerAgent extends Agent {
    DecompositionPlan decompose(String goal);
    
    @Data
    @Builder
    class DecompositionPlan {
        private List<Task> tasks;
        private List<Dependency> dependencies;
    }
    
    @Data
    @Builder
    class Task {
        private String id;
        private String description;
        private String type; // e.g., LLM, API, DB
    }
    
    @Data
    @Builder
    class Dependency {
        private String taskId;
        private String dependsOnTaskId;
    }
}
