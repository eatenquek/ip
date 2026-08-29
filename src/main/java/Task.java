/**
 * Represents a task tracked by Kiki, made up of a description, a done/not-done
 * status, and a {@link TaskType} that determines its display icon.
 */
public class Task {
    private final String description;
    private final TaskType type;
    private boolean isDone;

    public Task(String description, TaskType type) {
        this.description = description;
        this.type = type;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    public boolean isDone() {
        return isDone;
    }

    public String getDescription() {
        return description;
    }

    public TaskType getType() {
        return type;
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }


    @Override
    public String toString() {
        return "[" + type.getSymbol() + "][" + getStatusIcon() + "] " + description;
    }
}
