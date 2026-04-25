package io.github.upendramanike.orchestrator.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NodeResult {
    private String nodeId;
    private NodeState state;
    private Object output;
    private String errorMessage;
    private long executionTimeMs;
}
