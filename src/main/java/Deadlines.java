public class Deadlines extends Task {
    private final String by;

    public Deadlines(String description, String by) {
        super(description, TaskType.DEADLINE);
        this.by = by;
    }

    public String getBy() {
        return by;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + by + ")";
    }
}
