package io.github.upendramanike.memory.model;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;
import java.util.Map;

@Data
@Builder
public class MemorySegment {
    private String id;
    private String content;
    private Instant timestamp;
    private Double relevanceScore;
    private Map<String, Object> metadata;
    private MemoryTier tier;

    public enum MemoryTier {
        HOT,     // Redis / In-Memory
        WARM,    // SQL / NoSQL
        COLD     // Vector DB
    }
}
