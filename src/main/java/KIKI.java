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
        System.out.println("   Currently in Echo Mode!");
        System.out.println("-----------------------------");

        while (true) {
            String input = scanner.nextLine();


            if (input.trim().equalsIgnoreCase("bye")) {
                System.out.println("   -----------------------------");
                System.out.println("   Goodbye! Hope to see you again soon =)");
                System.out.println("   -----------------------------");
                break;
            }

            System.out.println("   -----------------------------");
            System.out.println("   " + input);
            System.out.println("   -----------------------------");
        }

        scanner.close();
    }
}
