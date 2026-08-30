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

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

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

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void printGoodbye() {
        printBox("Goodbye! Hope to see you again soon =)");
    }

    public void printBox(String message) {
        System.out.println(LINE);
        System.out.println("   " + message);
        System.out.println(LINE);
    }

    public void printAddedTask(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println("   Got it. I've added this task:");
        System.out.println("    " + task);
        System.out.println("   Now you have " + taskCount + " tasks in your list.");
        System.out.println(LINE);
    }

    public void printMarked(Task task) {
        System.out.println(LINE);
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("    " + task);
        System.out.println(LINE);
    }

    public void printUnmarked(Task task) {
        System.out.println(LINE);
        System.out.println("    Get to work,  I'll mark this task as not done yet:");
        System.out.println("    " + task);
        System.out.println(LINE);
    }

    public void printDeleted(Task removedTask, int taskCount) {
        System.out.println(LINE);
        System.out.println("   Noted. I've removed this task:");
        System.out.println("     " + removedTask);
        System.out.println("   Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE);
    }

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

    public void close() {
        scanner.close();
    }
}
