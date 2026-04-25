package io.github.upendramanike.orchestrator.model;

import lombok.Builder;
import lombok.Data;
import java.time.Duration;

@Data
@Builder
public class RetryPolicy {
    @Builder.Default
    private int maxRetries = 3;
    @Builder.Default
    private Duration backoff = Duration.ofSeconds(1);
    @Builder.Default
    private double backoffMultiplier = 2.0;
}
