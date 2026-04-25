package io.github.upendramanike.observability.model;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;
import java.util.Map;

@Data
@Builder
public class TraceSpan {
    private String traceId;
    private String spanId;
    private String agentName;
    private String prompt;
    private String response;
    private Long tokenCount;
    private Double latencyMs;
    private Instant timestamp;
    private Map<String, Object> metadata;
}
