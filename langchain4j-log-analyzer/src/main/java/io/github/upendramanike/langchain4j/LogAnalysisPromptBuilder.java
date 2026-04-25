package io.github.upendramanike.langchain4j;

public final class LogAnalysisPromptBuilder {

    private LogAnalysisPromptBuilder() {
    }

    public static String build(String logs) {
        return """
                Analyze the following Spring Boot logs and provide:
                1) Error summary
                2) Probable root cause
                3) Suggested fix steps

                Logs:
                """ + logs;
    }
}
