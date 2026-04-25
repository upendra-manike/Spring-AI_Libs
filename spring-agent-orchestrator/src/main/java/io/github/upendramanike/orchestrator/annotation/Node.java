package io.github.upendramanike.orchestrator.annotation;

import java.lang.annotation.*;
import org.springframework.stereotype.Component;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Node {
    String type() default "";
    String description() default "";
}
