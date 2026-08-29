import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Entry point for the chatbot application.
 */
public class KIKI {
    private static final String LINE = "   -----------------------------";
    private static final int MAX_TASKS = 100;
    private static final Path SAVE_FILE_PATH = Path.of("data", "kiki.txt");

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
                    saveTasks(currList, taskCount);
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
                    saveTasks(currList, taskCount);
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
                    saveTasks(currList, taskCount);
                    printAddedTask(event, taskCount);
                    continue;
                }

                if (trimmedInput.equals("mark") || trimmedInput.startsWith("mark ")) {
                    int taskIndex = parseTaskIndex(trimmedInput, "mark", taskCount);

                    currList[taskIndex].markAsDone();
                    saveTasks(currList, taskCount);
                    System.out.println(LINE);
                    System.out.println("    Nice! I've marked this task as done:");
                    System.out.println("    " + currList[taskIndex]);
                    System.out.println(LINE);
                    continue;
                }

                if (trimmedInput.equals("unmark") || trimmedInput.startsWith("unmark ")) {
                    int taskIndex = parseTaskIndex(trimmedInput, "unmark", taskCount);

                    currList[taskIndex].markAsNotDone();
                    saveTasks(currList, taskCount);
                    System.out.println(LINE);
                    System.out.println("    Get to work,  I'll mark this task as not done yet:");
                    System.out.println("    " + currList[taskIndex]);
                    System.out.println(LINE);
                    continue;
                }

                if (trimmedInput.equals("delete") || trimmedInput.startsWith("delete ")) {
                    int taskIndex = parseTaskIndex(trimmedInput, "delete", taskCount);
                    Task removedTask = currList[taskIndex];

                    for (int i = taskIndex; i < taskCount - 1; i++) {
                        currList[i] = currList[i + 1];
                    }

                    currList[taskCount - 1] = null;
                    taskCount--;
                    saveTasks(currList, taskCount);

                    System.out.println(LINE);
                    System.out.println("   Noted. I've removed this task:");
                    System.out.println("     " + removedTask);
                    System.out.println("   Now you have " + taskCount + " tasks in the list.");
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

    private static void saveTasks(Task[] tasks, int taskCount) throws KikiException {
        Path parentPath = SAVE_FILE_PATH.getParent();

        try {
            if (parentPath != null) {
                Files.createDirectories(parentPath);
            }

            if (Files.isDirectory(SAVE_FILE_PATH)) {
                throw new KikiException("Unable to save tasks because the save path is a folder.");
            }
        } catch (IOException e) {
            throw new KikiException("Unable to prepare the save folder.");
        }

        try (BufferedWriter writer = Files.newBufferedWriter(SAVE_FILE_PATH)) {
            for (int i = 0; i < taskCount; i++) {
                writer.write(formatForStorage(tasks[i]));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new KikiException("Unable to save tasks to disk.");
        }
    }

    private static String formatForStorage(Task task) {
        String doneStatus = task.isDone() ? "1" : "0";

        if (task instanceof Deadlines deadline) {
            return "D | " + doneStatus + " | " + deadline.getDescription()
                    + " | " + deadline.getBy();
        }

        if (task instanceof Events event) {
            return "E | " + doneStatus + " | " + event.getDescription() + " | " + event.getFrom()
                    + " | " + event.getTo();
        }

        return "T | " + doneStatus + " | " + task.getDescription();
    }

    private static int loadTasks(Task[] tasks) {
        if (!Files.exists(SAVE_FILE_PATH)) {
            return 0;
        }

        if (Files.isDirectory(SAVE_FILE_PATH)) {
            printBox("OOPS!!! Unable to load tasks because the save path is a folder.");
            return 0;
        }

        int taskCount = 0;

        try (BufferedReader reader = Files.newBufferedReader(SAVE_FILE_PATH)) {
            String line = reader.readLine();

            while (line != null) {
                String trimmedLine = line.trim();

                if (trimmedLine.isEmpty()) {
                    line = reader.readLine();
                    continue;
                }

                if (taskCount >= MAX_TASKS) {
                    printBox("OOPS!!! Save file has more than " + MAX_TASKS
                            + " tasks. Extra tasks were ignored.");
                    break;
                }

                try {
                    tasks[taskCount] = parseSavedTask(trimmedLine);
                    taskCount++;
                } catch (KikiException e) {
                    printBox("OOPS!!! Skipped a corrupted saved task: " + e.getMessage());
                }

                line = reader.readLine();
            }
        } catch (IOException e) {
            printBox("OOPS!!! Unable to load tasks from disk.");
        }

        return taskCount;
    }

    private static Task parseSavedTask(String line) throws KikiException {
        String[] parts = line.split(" \\| ", -1);
        String taskType = parts[0];
        int expectedParts = getExpectedPartCount(taskType);

        if (parts.length != expectedParts) {
            throw new KikiException("invalid saved task format.");
        }

        boolean isDone = parseSavedDoneStatus(parts[1]);
        String description = parts[2].trim();
        Task task;

        ensureNotEmpty(description, "saved task description is empty.");

        if (taskType.equals("D")) {
            String by = parts[3].trim();
            ensureNotEmpty(by, "saved deadline time is empty.");
            task = new Deadlines(description, by);
        } else if (taskType.equals("E")) {
            String from = parts[3].trim();
            String to = parts[4].trim();
            ensureNotEmpty(from, "saved event start time is empty.");
            ensureNotEmpty(to, "saved event end time is empty.");
            task = new Events(description, from, to);
        } else {
            task = new ToDos(description);
        }

        if (isDone) {
            task.markAsDone();
        }

        return task;
    }

    private static int getExpectedPartCount(String taskType) throws KikiException {
        if (taskType.equals("T")) {
            return 3;
        } else if (taskType.equals("D")) {
            return 4;
        } else if (taskType.equals("E")) {
            return 5;
        } else {
            throw new KikiException("unknown saved task type.");
        }
    }

    private static boolean parseSavedDoneStatus(String doneStatus) throws KikiException {
        if (doneStatus.equals("1")) {
            return true;
        } else if (doneStatus.equals("0")) {
            return false;
        } else {
            throw new KikiException("saved task status must be 0 or 1.");
        }
    }
}
