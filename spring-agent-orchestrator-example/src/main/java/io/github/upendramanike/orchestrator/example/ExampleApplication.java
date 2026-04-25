package io.github.upendramanike.orchestrator.example;

import io.github.upendramanike.orchestrator.core.Dag;
import io.github.upendramanike.orchestrator.core.DagExecutor;
import io.github.upendramanike.orchestrator.core.Node;
import io.github.upendramanike.orchestrator.model.ExecutionContext;
import io.github.upendramanike.orchestrator.model.NodeResult;
import io.github.upendramanike.orchestrator.model.NodeState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
@Slf4j
public class ExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(DagExecutor executor) {
        return args -> {
            log.info("Starting Example Agent Orchestration...");

            // Define dummy nodes
            Node planner = new MockNode("planner", "Planning the report...");
            Node fetcher = new MockNode("fetcher", "Fetching data from API...");
            Node summarizer = new MockNode("summarizer", "Summarizing data...");
            Node reviewer = new MockNode("reviewer", "Reviewing final report...");

            // Define dependencies
            Map<String, Set<String>> dependencies = new HashMap<>();
            dependencies.put("fetcher", Set.of("planner"));
            dependencies.put("summarizer", Set.of("fetcher"));
            dependencies.put("reviewer", Set.of("summarizer"));

            Dag dag = Dag.builder()
                    .id("report-gen")
                    .nodes(List.of(planner, fetcher, summarizer, reviewer))
                    .dependencies(dependencies)
                    .build();

            ExecutionContext context = ExecutionContext.builder()
                    .executionId(UUID.randomUUID().toString())
                    .build();

            executor.execute(dag, context)
                    .subscribe(results -> {
                        log.info("Workflow complete. Results:");
                        results.forEach((id, result) -> 
                            log.info("Node {}: {} ({}ms)", id, result.getState(), result.getExecutionTimeMs())
                        );
                    });
        };
    }

    static class MockNode implements Node {
        private final String id;
        private final String message;

        MockNode(String id, String message) {
            this.id = id;
            this.message = message;
        }

        @Override
        public String getId() { return id; }

        @Override
        public NodeResult execute(ExecutionContext context) {
            log.info("Node {}: {}", id, message);
            try { Thread.sleep(500); } catch (InterruptedException e) {}
            return NodeResult.builder()
                    .nodeId(id)
                    .state(NodeState.SUCCESS)
                    .output("Result of " + id)
                    .build();
        }
    }
}
