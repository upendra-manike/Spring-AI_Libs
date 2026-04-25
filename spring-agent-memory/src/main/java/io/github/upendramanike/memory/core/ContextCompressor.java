package io.github.upendramanike.memory.core;

import io.github.upendramanike.memory.model.MemorySegment;
import reactor.core.publisher.Mono;
import java.util.List;

public interface ContextCompressor {
    Mono<MemorySegment> compress(List<MemorySegment> segments);
}
