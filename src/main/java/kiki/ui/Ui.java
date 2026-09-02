package kiki.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import kiki.task.Task;
import kiki.task.TaskList;

/**
 * Handles all console input and output for Kiki.
 */
public class Ui {
    private static final String LINE = "   -----------------------------";
    private static final DateTimeFormatter DISPLAY_DATE_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");

    private final Scanner scanner;

    /**
     * Creates a new Ui that reads user input from standard input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prints the startup banner and greeting.
     */
    public void printWelcome() {
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
    }

    /**
     * Reads and trims the next line of user input.
     *
     * @return The trimmed input line.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Prints the farewell message shown when the user exits.
     */
    public void printGoodbye() {
        printBox("Goodbye! Hope to see you again soon =)");
    }

    /**
     * Prints a message surrounded by horizontal divider lines.
     *
     * @param message Message to print.
     */
    public void printBox(String message) {
        System.out.println(LINE);
        System.out.println("   " + message);
        System.out.println(LINE);
    }

    /**
     * Prints confirmation that a task was added.
     *
     * @param task Task that was added.
     * @param taskCount Total number of tasks after adding.
     */
    public void printAddedTask(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println("   Got it. I've added this task:");
        System.out.println("    " + task);
        System.out.println("   Now you have " + taskCount + " tasks in your list.");
        System.out.println(LINE);
    }

    /**
     * Prints confirmation that a task was marked as done.
     *
     * @param task Task that was marked.
     */
    public void printMarked(Task task) {
        System.out.println(LINE);
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("    " + task);
        System.out.println(LINE);
    }

    /**
     * Prints confirmation that a task was marked as not done.
     *
     * @param task Task that was unmarked.
     */
    public void printUnmarked(Task task) {
        System.out.println(LINE);
        System.out.println("    Get to work,  I'll mark this task as not done yet:");
        System.out.println("    " + task);
        System.out.println(LINE);
    }

    /**
     * Prints confirmation that a task was removed.
     *
     * @param removedTask Task that was removed.
     * @param taskCount Total number of tasks after removing.
     */
    public void printDeleted(Task removedTask, int taskCount) {
        System.out.println(LINE);
        System.out.println("   Noted. I've removed this task:");
        System.out.println("     " + removedTask);
        System.out.println("   Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE);
    }

    /**
     * Prints every task in the given list, numbered from 1.
     *
     * @param taskList Task list to print.
     */
    public void printList(TaskList taskList) {
        System.out.println(LINE);
        System.out.println("   Here are the tasks in your list:");

        for (int i = 0; i < taskList.size(); i++) {
            System.out.println("   " + (i + 1) + ". " + taskList.get(i));
        }

        System.out.println(LINE);
    }

    /**
     * Prints the deadlines/events that fall within a checked date range.
     *
     * @param rangeStart First date of the checked range, inclusive.
     * @param rangeEnd Last date of the checked range, inclusive.
     * @param isWeek Whether the range is a week (changes the header wording).
     * @param matches Tasks that fall within the range, in display order.
     */
    public void printTasksInRange(LocalDate rangeStart, LocalDate rangeEnd, boolean isWeek,
            List<Task> matches) {
        System.out.println(LINE);

        if (isWeek) {
            System.out.println("   Here's what's happening from " + rangeStart.format(DISPLAY_DATE_FORMAT)
                    + " to " + rangeEnd.format(DISPLAY_DATE_FORMAT) + ":");
        } else {
            System.out.println("   Here's what's happening on " + rangeStart.format(DISPLAY_DATE_FORMAT) + ":");
        }

        if (matches.isEmpty()) {
            System.out.println("   Nothing scheduled.");
        } else {
            for (int i = 0; i < matches.size(); i++) {
                System.out.println("   " + (i + 1) + ". " + matches.get(i));
            }
        }

        System.out.println(LINE);
    }

    /**
     * Closes the input scanner. Call once, when the program is exiting.
     */
    public void close() {
        scanner.close();
    }
}
