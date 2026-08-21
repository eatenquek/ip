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
