public class Event {
    private String title;
    private String date; // Format: yyyy-mm-dd
    private int startTime; // in minutes from midnight
    private int endTime; // in minutes from midnight // ditto
    private int priority; // Lower values indicate higher priority

    public Event(String title, String date, int startTime, int endTime, int priority) {
        if (startTime >= endTime) {
            throw new IllegalArgumentException("Start time must be less than end time.");
        }
        if (priority < 0) {
            throw new IllegalArgumentException("Priority must be a non-negative integer.");
        }
        this.title = title;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public int getPriority() {
        return priority;
    }

    // Setter methods for startTime and endTime
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Event{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", startTime=" + MinToHHMM.convertToHHMM(startTime) + // Convert minutes to HHMM format
                ", endTime=" + MinToHHMM.convertToHHMM(endTime) + // Convert minutes to HHMM format
                ", priority=" + priority +
                '}';
    }
}