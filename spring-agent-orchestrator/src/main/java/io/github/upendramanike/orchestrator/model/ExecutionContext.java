package io.github.upendramanike.orchestrator.model;

import lombok.Builder;
import lombok.Data;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Builder
public class ExecutionContext {
    private final String executionId;
    @Builder.Default
    private final Map<String, Object> data = new ConcurrentHashMap<>();
    @Builder.Default
    private final MemoryStore memory = new InMemoryMemoryStore();
    
    public void setParam(String key, Object value) {
        data.put(key, value);
    }
    
    public Object getParam(String key) {
        return data.get(key);
    }
}
