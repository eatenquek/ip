public class ToDos extends Task {

    private String marking = "T";

    public ToDos(String description) {
        super(description);
    }


    @Override
    public String toString() {
        return "[" + marking + "][" + getStatusIcon() + "] " + super.getDescription();
    }

}
