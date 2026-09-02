package kiki.task;

/**
 * Represents a task tracked by Kiki, made up of a description, a done/not-done
 * status, and a {@link TaskType} that determines its display icon.
 */
public class Task {
    private final String description;
    private final TaskType type;
    private boolean isDone;

    /**
     * Creates a new, not-done task.
     *
     * @param description Description of the task.
     * @param type Category of the task, used to pick its display icon.
     */
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

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsNotDone() {
        isDone = false;
    }


    @Override
    public String toString() {
        return "[" + type.getSymbol() + "][" + getStatusIcon() + "] " + description;
    }
}
