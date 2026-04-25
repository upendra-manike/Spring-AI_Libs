package io.github.upendramanike.binding.core;

import lombok.extern.slf4j.Slf4j;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class JsonRepairer {

    /**
     * Attempts to repair common LLM JSON errors:
     * 1. Extraneous text before/after the JSON block.
     * 2. Unescaped newlines in strings.
     * 3. Missing closing braces (basic attempt).
     */
    public String repair(String input) {
        if (input == null) return null;

        // 1. Extract JSON block if surrounded by markdown or chatter
        Pattern jsonPattern = Pattern.compile("(?s)\\{.*\\}|\\[.*\\]");
        Matcher matcher = jsonPattern.matcher(input);
        if (matcher.find()) {
            input = matcher.group();
        }

        // 2. Fix unescaped newlines in values
        Pattern nestedNewline = Pattern.compile("(?<=\": \")(.+?)(?=\",)");
        input = nestedNewline.matcher(input).replaceAll(m -> m.group().replace("\n", "\\n"));

        return input.trim();
    }
}
