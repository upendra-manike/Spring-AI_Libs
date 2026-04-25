package io.github.upendramanike.orchestrator.example;

import io.github.upendramanike.binding.core.JsonRepairer;
import io.github.upendramanike.guardrails.core.PiiMasker;
import io.github.upendramanike.observability.model.TraceSpan;
import io.github.upendramanike.orchestrator.annotation.Agent;
import io.github.upendramanike.orchestrator.core.Dag;
import io.github.upendramanike.orchestrator.core.Node;
import io.github.upendramanike.orchestrator.core.DagExecutor;
import io.github.upendramanike.orchestrator.model.ExecutionContext;
import io.github.upendramanike.orchestrator.model.NodeResult;
import io.github.upendramanike.router.core.ModelRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class ExampleApplication {
    private static final Logger log = LoggerFactory.getLogger(ExampleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(DagExecutor dagExecutor) {
        return args -> {
            log.info("--- Starting Full Ecosystem AI Workflow ---");

            // 1. Setup ecosystem components
            PiiMasker piiMasker = new PiiMasker();
            JsonRepairer jsonRepairer = new JsonRepairer();
            
            String rawInput = "Analyze report for user upendra.manike@gmail.com. Output JSON: { 'status': 'ok' "; // Malformed JSON
            
            // 2. Apply Guardrails
            String safeInput = piiMasker.mask(rawInput);
            log.info("Safe Input (Masked): {}", safeInput);

            // 3. Execution with Observability
            String traceId = UUID.randomUUID().toString();
            ExecutionContext context = ExecutionContext.builder()
                    .executionId(UUID.randomUUID().toString())
                    .build();
            context.setParam("input", safeInput);

            // Build a simple DAG
            Node node1 = new Node() {
                @Override
                public String getId() { return "node1"; }
                @Override
                public NodeResult execute(ExecutionContext ctx) {
                    String input = (String) ctx.getParam("input");
                    return NodeResult.builder()
                            .nodeId("node1")
                            .state(io.github.upendramanike.orchestrator.model.NodeState.SUCCESS)
                            .output("{ \"processed\": true, \"raw\": \"" + input + "\" }")
                            .build();
                }
            };
            
            Dag dag = Dag.builder()
                    .id("example-dag")
                    .nodes(new java.util.ArrayList<>(List.of(node1)))
                    .dependencies(new java.util.HashMap<>())
                    .build();

            dagExecutor.execute(dag, context)
                    .map(resultsMap -> {
                        // 4. Apply Output Binding & Repair
                        NodeResult lastResult = resultsMap.get("node1");
                        String repairedJson = jsonRepairer.repair((String) lastResult.getOutput());
                        
                        // 5. Create Trace Span
                        TraceSpan span = TraceSpan.builder()
                                .traceId(traceId)
                                .agentName("ExampleAgent")
                                .prompt(safeInput)
                                .response(repairedJson)
                                .latencyMs(250.0)
                                .timestamp(Instant.now())
                                .build();
                                
                        log.info("Workflow complete. Trace recorded: {}", span.getTraceId());
                        log.info("Repaired Output: {}", repairedJson);
                        return repairedJson;
                    })
                    .subscribe();
        };
    }

    @Agent("ExampleAgent")
    public static class MockAgent {
        // Keeping this for future starter-based auto-discovery
    }
}
