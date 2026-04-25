package io.github.upendramanike.orchestrator.core;

import io.github.upendramanike.orchestrator.model.ExecutionContext;
import io.github.upendramanike.orchestrator.model.NodeResult;
import io.github.upendramanike.orchestrator.model.NodeState;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
public class DagExecutor {

    public Mono<Map<String, NodeResult>> execute(Dag dag, ExecutionContext context) {
        Map<String, NodeResult> results = new ConcurrentHashMap<>();
        Map<String, Set<String>> deps = new HashMap<>(dag.getDependencies());
        
        return executeNodes(dag.getNodes(), deps, context, results)
                .thenReturn(results);
    }

    private Mono<Void> executeNodes(List<Node> nodes, Map<String, Set<String>> deps, 
                                   ExecutionContext context, Map<String, NodeResult> results) {
        
        List<Node> readyNodes = nodes.stream()
                .filter(node -> !results.containsKey(node.getId()))
                .filter(node -> {
                    Set<String> nodeDeps = deps.getOrDefault(node.getId(), Collections.emptySet());
                    return nodeDeps.isEmpty() || nodeDeps.stream().allMatch(d -> 
                        results.containsKey(d) && results.get(d).getState() == NodeState.SUCCESS
                    );
                })
                .collect(Collectors.toList());

        if (readyNodes.isEmpty()) {
            if (results.size() < nodes.size()) {
                // Check if any failed or if we have a cycle/deadlock
                boolean anyFailed = results.values().stream().anyMatch(r -> r.getState() == NodeState.FAILED);
                if (anyFailed) {
                    return Mono.empty();
                }
                // Potentially a cycle or missing dependency
                return Mono.error(new IllegalStateException("Deadlock or cycle detected in DAG execution"));
            }
            return Mono.empty();
        }

        return Flux.fromIterable(readyNodes)
                .flatMap(node -> Mono.fromCallable(() -> {
                    log.info("Executing node: {}", node.getId());
                    long start = System.currentTimeMillis();
                    try {
                        NodeResult result = node.execute(context);
                        result.setExecutionTimeMs(System.currentTimeMillis() - start);
                        results.put(node.getId(), result);
                        return result;
                    } catch (Exception e) {
                        NodeResult result = NodeResult.builder()
                                .nodeId(node.getId())
                                .state(NodeState.FAILED)
                                .errorMessage(e.getMessage())
                                .executionTimeMs(System.currentTimeMillis() - start)
                                .build();
                        results.put(node.getId(), result);
                        return result;
                    }
                }).subscribeOn(Schedulers.boundedElastic()))
                .then(Mono.defer(() -> executeNodes(nodes, deps, context, results)));
    }
}
