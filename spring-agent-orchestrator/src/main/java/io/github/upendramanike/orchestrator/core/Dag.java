package io.github.upendramanike.orchestrator.core;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Builder
public class Dag {
    private final String id;
    private final List<Node> nodes;
    private final Map<String, Set<String>> dependencies; // nodeId -> set of nodeIds it depends on
}
