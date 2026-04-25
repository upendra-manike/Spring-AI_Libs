package io.github.upendramanike.orchestrator.annotation;

import java.lang.annotation.*;
import org.springframework.stereotype.Component;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Agent {
    String role() default "";
    String description() default "";
}
