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

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye") || input.equals("Bye")) {
                System.out.println("Goodbye! Hope to see you again soon =)");
                break;
            }

            scanner.close();
        }

    }
}
