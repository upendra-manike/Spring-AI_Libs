package io.github.upendramanike.router.core;

import lombok.Builder;
import lombok.Data;
import java.util.List;

public interface ModelRouter {
    String route(RouterRequest request);

    @Data
    @Builder
    class RouterRequest {
        private String prompt;
        private List<String> availableModels;
        private Strategy strategy;

        public enum Strategy {
            COST_OPTIMIZED,
            LATENCY_OPTIMIZED,
            QUALITY_OPTIMIZED
        }
    }
}
