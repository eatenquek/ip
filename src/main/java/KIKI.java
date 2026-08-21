import java.util.Scanner;

/**
 * Entry point for the chatbot application.
 */
public class KIKI {
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

        System.out.println("-----------------------------");
        System.out.println("   Currently in Listing Mode!");
        System.out.println("-----------------------------");

        Task[] currList = new Task[100];
        int taskCount = 0;

        while (true) {
            String input = scanner.nextLine();
            String trimmedInput = input.trim();


            if (trimmedInput.equalsIgnoreCase("bye")) {
                System.out.println("   -----------------------------");
                System.out.println("   Goodbye! Hope to see you again soon =)");
                System.out.println("   -----------------------------");
                break;
            }

            if (trimmedInput.startsWith("todo ")){
                String description = trimmedInput.substring("todo ".length()).trim();
                Task todo = new ToDos(description);
                currList[taskCount] = todo;
                taskCount++;

                System.out.println("   -----------------------------");
                System.out.println("   Got it. I've added this task:");
                System.out.println("    " + todo);
                System.out.println("   Now you have " + taskCount + " tasks in your list." );
                System.out.println("   -----------------------------");

                continue;
            }

            if (trimmedInput.startsWith("deadline ")) {
                String deadlineInput = trimmedInput.substring("deadline ".length()).trim();
                String[] deadlineParts = deadlineInput.split(" /by ", 2);

                if (deadlineParts.length < 2) {
                    System.out.println("   -----------------------------");
                    System.out.println("   OOPS!!! Please use: deadline DESCRIPTION /by TIME");
                    System.out.println("   -----------------------------");
                    continue;
                }

                String description = deadlineParts[0].trim();
                String by = deadlineParts[1].trim();
                Task deadline = new Deadlines(description, by);
                currList[taskCount] = deadline;
                taskCount++;

                System.out.println("   -----------------------------");
                System.out.println("   Got it. I've added this task:");
                System.out.println("    " + deadline);
                System.out.println("   Now you have " + taskCount + " tasks in your list." );
                System.out.println("   -----------------------------");

                continue;
            }

            if (trimmedInput.startsWith("event ")) {
                String eventInput = trimmedInput.substring("event ".length()).trim();
                String[] fromParts = eventInput.split(" /from ", 2);

                if (fromParts.length < 2) {
                    System.out.println("   -----------------------------");
                    System.out.println("   OOPS!!! Please use: event DESCRIPTION /from START /to END");
                    System.out.println("   -----------------------------");
                    continue;
                }

                String[] toParts = fromParts[1].split(" /to ", 2);

                if (toParts.length < 2) {
                    System.out.println("   -----------------------------");
                    System.out.println("   OOPS!!! Please use: event DESCRIPTION /from START /to END");
                    System.out.println("   -----------------------------");
                    continue;
                }

                String description = fromParts[0].trim();
                String from = toParts[0].trim();
                String to = toParts[1].trim();
                Task event = new Events(description, from, to);
                currList[taskCount] = event;
                taskCount++;

                System.out.println("   -----------------------------");
                System.out.println("   Got it. I've added this task:");
                System.out.println("    " + event);
                System.out.println("   Now you have " + taskCount + " tasks in your list." );
                System.out.println("   -----------------------------");

                continue;
            }

            if (trimmedInput.startsWith("mark ")) {
                String numberText = trimmedInput.substring("mark ".length()).trim();
                int taskNumber = Integer.parseInt(numberText);
                int taskIndex = taskNumber - 1;

                if (taskIndex < 0 || taskIndex >= taskCount) {
                    System.out.println("   -----------------------------");
                    System.out.println("   OOPS!!! That task number is not in your list.");
                    System.out.println("   -----------------------------");
                    continue;
                }

                currList[taskIndex].markAsDone();
                System.out.println("   -----------------------------");
                System.out.println("    Nice! I've marked this task as done:");
                System.out.println("    " + currList[taskIndex]);
                System.out.println("   -----------------------------");

                continue;
            }


            if (trimmedInput.startsWith("unmark ")) {
                String numberText = trimmedInput.substring("unmark ".length()).trim();
                int taskNumber = Integer.parseInt(numberText);
                int taskIndex = taskNumber - 1;

                if (taskIndex < 0 || taskIndex >= taskCount) {
                    System.out.println("   -----------------------------");
                    System.out.println("   OOPS!!! That task number is not in your list.");
                    System.out.println("   -----------------------------");
                    continue;
                }

                currList[taskIndex].markAsNotDone();
                System.out.println("   -----------------------------");
                System.out.println("    Get to work,  I'll mark this task as not done yet:");
                System.out.println("    " + currList[taskIndex]);
                System.out.println("   -----------------------------");

                continue;
            }

            if (trimmedInput.equalsIgnoreCase("list")) {

                System.out.println("   -----------------------------");
                System.out.println("   Here are the tasks in your list:");

                for (int i = 0; i < taskCount; i++ ) {
                    System.out.println("   "  + (i + 1) + ". " + currList[i]);
                }

                System.out.println("   -----------------------------");
                continue;
            }

            Task task = new Task(input);
            currList[taskCount] = task;
            taskCount++;

            System.out.println("   -----------------------------");
            System.out.println("   added: " + input);
            System.out.println("   -----------------------------");
        }

        scanner.close();
    }
}
