package io.github.upendramanike.orchestrator.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMemoryStore implements MemoryStore {
    private final Map<String, Object> storage = new ConcurrentHashMap<>();

    @Override
    public void put(String key, Object value) {
        storage.put(key, value);
    }

    @Override
    public Object get(String key) {
        return storage.get(key);
    }

    @Override
    public Map<String, Object> getAll() {
        return Map.copyOf(storage);
    }
}
