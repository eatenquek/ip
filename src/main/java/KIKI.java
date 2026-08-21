import java.util.Scanner;
import java.util.ArrayList;

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

        String[] currList = new String[100];
        int taskCount = 0;

        while (true) {
            String input = scanner.nextLine();


            if (input.trim().equalsIgnoreCase("bye")) {
                System.out.println("   -----------------------------");
                System.out.println("   Goodbye! Hope to see you again soon =)");
                System.out.println("   -----------------------------");
                break;
            }

            if (input.trim().equalsIgnoreCase("list")) {

                System.out.println("   -----------------------------");

                for (int i = 0; i < taskCount; i++ ) {
                    System.out.println("   "  +i + ". " + currList[i]);
                }

                System.out.println("   -----------------------------");
                continue;
            }

            currList[taskCount] = input;
            taskCount++;

            System.out.println("   -----------------------------");
            System.out.println("   added: " + input);
            System.out.println("   -----------------------------");
        }

        scanner.close();
    }
}
