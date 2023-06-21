package eextr0.sttracker.Time;

import java.time.Duration;
import java.time.LocalDateTime;

public class DateTimeFormatter {
    public String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
