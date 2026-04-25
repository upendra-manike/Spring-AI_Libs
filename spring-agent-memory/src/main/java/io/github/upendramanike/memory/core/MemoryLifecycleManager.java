package io.github.upendramanike.memory.core;

import io.github.upendramanike.memory.model.MemorySegment;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class MemoryLifecycleManager {
    private final HybridMemoryStore store;
    private final ContextCompressor compressor;

    public Mono<Void> processArchive(String sessionId) {
        return store.fetchContext(sessionId, 50)
                .collectList()
                .flatMap(compressor::compress)
                .flatMap(store::store);
    }
}
