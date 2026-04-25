package io.github.upendramanike.guardrails.core;

import java.util.regex.Pattern;

public class PiiMasker {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}");
    private static final Pattern PHONE_PATTERN = Pattern.compile("\\d{3}-\\d{3}-\\d{4}"); // US Example

    public String mask(String input) {
        if (input == null) return null;
        String masked = EMAIL_PATTERN.matcher(input).replaceAll("[EMAIL_REDACTED]");
        masked = PHONE_PATTERN.matcher(masked).replaceAll("[PHONE_REDACTED]");
        return masked;
    }
}
