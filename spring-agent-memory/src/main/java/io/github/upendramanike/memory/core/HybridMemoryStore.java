package io.github.upendramanike.memory.core;

import io.github.upendramanike.memory.model.MemorySegment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HybridMemoryStore {
    Mono<Void> store(MemorySegment segment);
    Flux<MemorySegment> fetchContext(String sessionId, int limit);
    Mono<Void> evolve(String sessionId); // Logic to move items from Hot to Warm/Cold
}
