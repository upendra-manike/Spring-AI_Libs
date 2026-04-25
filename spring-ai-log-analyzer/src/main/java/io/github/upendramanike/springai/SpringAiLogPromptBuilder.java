package io.github.upendramanike.springai;

public final class SpringAiLogPromptBuilder {

    private SpringAiLogPromptBuilder() {
    }

    public static String build(String logs) {
        return """
                You are an SRE assistant for Spring Boot services.
                Diagnose the incident from these logs and return:
                - issue category
                - failing component
                - remediation checklist

                Logs:
                """ + logs;
    }
}
