package io.github.upendramanike.orchestrator.core;

import io.github.upendramanike.orchestrator.model.ExecutionContext;
import io.github.upendramanike.orchestrator.model.NodeResult;

public interface Node {
    String getId();
    NodeResult execute(ExecutionContext context);
}
