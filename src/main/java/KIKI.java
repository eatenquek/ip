import java.util.Scanner;

/**
 * Entry point for the chatbot application.
 */
public class KIKI {
    private static final String LINE = "   -----------------------------";
    private static final int MAX_TASKS = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("""
                ██╗  ██╗██╗██╗  ██╗██╗
                ██║ ██╔╝██║██║ ██╔╝██║
                █████╔╝ ██║█████╔╝ ██║
                ██╔═██╗ ██║██╔═██╗ ██║
                ██║  ██╗██║██║  ██╗██║
                ╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═╝
                """);
        System.out.println("Hello! I'm Kiki");
        System.out.println("How can I be of service today!");

        System.out.println(LINE);
        System.out.println("    Currently in Listing Mode!");
        System.out.println(LINE);

        Task[] currList = new Task[MAX_TASKS];
        int taskCount = 0;

        while (true) {
            String input = scanner.nextLine();
            String trimmedInput = input.trim();

            try {
                if (trimmedInput.equalsIgnoreCase("bye")) {
                    printBox("Goodbye! Hope to see you again soon =)");
                    break;
                }

                if (trimmedInput.isEmpty()) {
                    throw new KikiException("Please enter a command.");
                }

                if (trimmedInput.equals("todo") || trimmedInput.startsWith("todo ")) {
                    ensureCanAddTask(taskCount);
                    String description = trimmedInput.substring("todo".length()).trim();
                    ensureNotEmpty(description, "The description of a todo cannot be empty.");

                    Task todo = new ToDos(description);
                    currList[taskCount] = todo;
                    taskCount++;
                    printAddedTask(todo, taskCount);
                    continue;
                }

                if (trimmedInput.equals("deadline") || trimmedInput.startsWith("deadline ")) {
                    ensureCanAddTask(taskCount);
                    String deadlineInput = trimmedInput.substring("deadline".length()).trim();
                    int byIndex = deadlineInput.indexOf("/by");

                    if (byIndex < 0) {
                        throw new KikiException("Please use: deadline DESCRIPTION /by TIME");
                    }

                    String description = deadlineInput.substring(0, byIndex).trim();
                    String by = deadlineInput.substring(byIndex + "/by".length()).trim();
                    ensureNotEmpty(description, "The description of a deadline cannot be empty.");
                    ensureNotEmpty(by, "The by time of a deadline cannot be empty.");

                    Task deadline = new Deadlines(description, by);
                    currList[taskCount] = deadline;
                    taskCount++;
                    printAddedTask(deadline, taskCount);
                    continue;
                }

                if (trimmedInput.equals("event") || trimmedInput.startsWith("event ")) {
                    ensureCanAddTask(taskCount);
                    String eventInput = trimmedInput.substring("event".length()).trim();
                    int fromIndex = eventInput.indexOf("/from");

                    if (fromIndex < 0) {
                        throw new KikiException("Please use: event DESCRIPTION /from START /to END");
                    }

                    String description = eventInput.substring(0, fromIndex).trim();
                    String fromAndTo = eventInput.substring(fromIndex + "/from".length()).trim();
                    int toIndex = fromAndTo.indexOf("/to");

                    if (toIndex < 0) {
                        throw new KikiException("Please use: event DESCRIPTION /from START /to END");
                    }

                    String from = fromAndTo.substring(0, toIndex).trim();
                    String to = fromAndTo.substring(toIndex + "/to".length()).trim();
                    ensureNotEmpty(description, "The description of an event cannot be empty.");
                    ensureNotEmpty(from, "The start time of an event cannot be empty.");
                    ensureNotEmpty(to, "The end time of an event cannot be empty.");

                    Task event = new Events(description, from, to);
                    currList[taskCount] = event;
                    taskCount++;
                    printAddedTask(event, taskCount);
                    continue;
                }

                if (trimmedInput.equals("mark") || trimmedInput.startsWith("mark ")) {
                    int taskIndex = parseTaskIndex(trimmedInput, "mark", taskCount);

                    currList[taskIndex].markAsDone();
                    System.out.println(LINE);
                    System.out.println("    Nice! I've marked this task as done:");
                    System.out.println("    " + currList[taskIndex]);
                    System.out.println(LINE);
                    continue;
                }

                if (trimmedInput.equals("unmark") || trimmedInput.startsWith("unmark ")) {
                    int taskIndex = parseTaskIndex(trimmedInput, "unmark", taskCount);

                    currList[taskIndex].markAsNotDone();
                    System.out.println(LINE);
                    System.out.println("    Get to work,  I'll mark this task as not done yet:");
                    System.out.println("    " + currList[taskIndex]);
                    System.out.println(LINE);
                    continue;
                }

                if (trimmedInput.equalsIgnoreCase("list")) {
                    System.out.println(LINE);
                    System.out.println("   Here are the tasks in your list:");

                    for (int i = 0; i < taskCount; i++ ) {
                        System.out.println("   "  + (i + 1) + ". " + currList[i]);
                    }

                    System.out.println(LINE);
                    continue;
                }

                throw new KikiException("I'm sorry, but I don't know what that means :-(");
            } catch (KikiException e) {
                printBox("OOPS!!! " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void printBox(String message) {
        System.out.println(LINE);
        System.out.println("   " + message);
        System.out.println(LINE);
    }

    private static void printAddedTask(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println("   Got it. I've added this task:");
        System.out.println("    " + task);
        System.out.println("   Now you have " + taskCount + " tasks in your list." );
        System.out.println(LINE);
    }

    private static void ensureNotEmpty(String value, String errorMessage) throws KikiException {
        if (value.isEmpty()) {
            throw new KikiException(errorMessage);
        }
    }

    private static void ensureCanAddTask(int taskCount) throws KikiException {
        if (taskCount >= MAX_TASKS) {
            throw new KikiException("Your task list is full.");
        }
    }

    private static int parseTaskIndex(String input, String command, int taskCount) throws KikiException {
        String numberText = input.substring(command.length()).trim();
        ensureNotEmpty(numberText, "Please tell me which task number to " + command + ".");

        try {
            int taskNumber = Integer.parseInt(numberText);
            int taskIndex = taskNumber - 1;

            if (taskIndex < 0 || taskIndex >= taskCount) {
                throw new KikiException("That task number is not in your list.");
            }

            return taskIndex;
        } catch (NumberFormatException e) {
            throw new KikiException("Task number must be a whole number.");
        }
    }
}
