package io.github.upendramanike.orchestrator.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface MemoryStore {
    void put(String key, Object value);
    Object get(String key);
    Map<String, Object> getAll();
}
