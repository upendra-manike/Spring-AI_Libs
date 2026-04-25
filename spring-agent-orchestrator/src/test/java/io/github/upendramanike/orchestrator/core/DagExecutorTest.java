package io.github.upendramanike.orchestrator.core;

import io.github.upendramanike.orchestrator.model.ExecutionContext;
import io.github.upendramanike.orchestrator.model.NodeResult;
import io.github.upendramanike.orchestrator.model.NodeState;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DagExecutorTest {

    @Test
    void testSequentialExecution() {
        DagExecutor executor = new DagExecutor();
        
        Node node1 = new MockNode("node1");
        Node node2 = new MockNode("node2");
        
        Dag dag = Dag.builder()
                .id("test-dag")
                .nodes(List.of(node1, node2))
                .dependencies(Map.of("node2", Set.of("node1")))
                .build();
                
        ExecutionContext context = ExecutionContext.builder()
                .executionId("test-exec")
                .build();
                
        StepVerifier.create(executor.execute(dag, context))
                .assertNext(results -> {
                    assertEquals(2, results.size());
                    assertEquals(NodeState.SUCCESS, results.get("node1").getState());
                    assertEquals(NodeState.SUCCESS, results.get("node2").getState());
                })
                .verifyComplete();
    }

    static class MockNode implements Node {
        private final String id;
        MockNode(String id) { this.id = id; }
        @Override public String getId() { return id; }
        @Override public NodeResult execute(ExecutionContext context) {
            return NodeResult.builder().nodeId(id).state(NodeState.SUCCESS).build();
        }
    }
}
