package io.github.upendramanike.openai;

public final class OpenAiLogPromptBuilder {

    private OpenAiLogPromptBuilder() {
    }

    public static String build(String logs) {
        return """
                Review these backend logs and respond in JSON with fields:
                summary, rootCause, confidence, nextActions.

                Logs:
                """ + logs;
    }
}
