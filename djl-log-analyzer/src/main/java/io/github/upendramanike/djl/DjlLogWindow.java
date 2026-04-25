package io.github.upendramanike.djl;

import java.util.List;

public final class DjlLogWindow {

    private DjlLogWindow() {
    }

    public static String mergeRecentLines(List<String> lines, int maxLines) {
        if (lines == null || lines.isEmpty()) {
            return "";
        }
        int start = Math.max(0, lines.size() - Math.max(1, maxLines));
        return String.join(System.lineSeparator(), lines.subList(start, lines.size()));
    }
}
