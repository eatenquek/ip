package kiki;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import kiki.exception.KikiException;
import kiki.parser.Parser;
import kiki.storage.Storage;
import kiki.task.Deadlines;
import kiki.task.Events;
import kiki.task.Task;
import kiki.task.TaskList;
import kiki.task.ToDos;
import kiki.ui.Ui;

/**
 * Entry point for the chatbot application. Coordinates the {@link Ui},
 * {@link Storage}, {@link Parser}, and {@link TaskList} to respond to user
 * commands.
 */
public class Kiki {
    private final Ui ui = new Ui();
    private final Storage storage = new Storage();
    private final TaskList taskList = new TaskList();

    public static void main(String[] args) {
        new Kiki().run();
    }

    private void run() {
        ui.printWelcome();
        storage.load(taskList, ui);

        while (true) {
            String trimmedInput = ui.readCommand();

            try {
                if (trimmedInput.equalsIgnoreCase("bye")) {
                    ui.printGoodbye();
                    break;
                }

                if (trimmedInput.isEmpty()) {
                    throw new KikiException("Please enter a command.");
                }

                if (trimmedInput.equals("todo") || trimmedInput.startsWith("todo ")) {
                    taskList.ensureCanAdd();
                    String description = Parser.parseTodoDescription(trimmedInput);
                    Task todo = new ToDos(description);
                    taskList.add(todo);
                    storage.save(taskList);
                    ui.printAddedTask(todo, taskList.size());
                    continue;
                }

                if (trimmedInput.equals("deadline") || trimmedInput.startsWith("deadline ")) {
                    taskList.ensureCanAdd();
                    Deadlines deadline = Parser.parseDeadline(trimmedInput);
                    taskList.add(deadline);
                    storage.save(taskList);
                    ui.printAddedTask(deadline, taskList.size());
                    continue;
                }

                if (trimmedInput.equals("event") || trimmedInput.startsWith("event ")) {
                    taskList.ensureCanAdd();
                    Events event = Parser.parseEvent(trimmedInput);
                    taskList.add(event);
                    storage.save(taskList);
                    ui.printAddedTask(event, taskList.size());
                    continue;
                }

                if (trimmedInput.equals("mark") || trimmedInput.startsWith("mark ")) {
                    int taskIndex = Parser.parseTaskIndex(trimmedInput, "mark", taskList.size());
                    Task task = taskList.get(taskIndex);
                    task.markAsDone();
                    storage.save(taskList);
                    ui.printMarked(task);
                    continue;
                }

                if (trimmedInput.equals("unmark") || trimmedInput.startsWith("unmark ")) {
                    int taskIndex = Parser.parseTaskIndex(trimmedInput, "unmark", taskList.size());
                    Task task = taskList.get(taskIndex);
                    task.markAsNotDone();
                    storage.save(taskList);
                    ui.printUnmarked(task);
                    continue;
                }

                if (trimmedInput.equals("delete") || trimmedInput.startsWith("delete ")) {
                    int taskIndex = Parser.parseTaskIndex(trimmedInput, "delete", taskList.size());
                    Task removedTask = taskList.remove(taskIndex);
                    storage.save(taskList);
                    ui.printDeleted(removedTask, taskList.size());
                    continue;
                }

                if (trimmedInput.equalsIgnoreCase("list")) {
                    ui.printList(taskList);
                    continue;
                }

                if (trimmedInput.startsWith("check day ") || trimmedInput.startsWith("check week ")) {
                    boolean isWeek = trimmedInput.startsWith("check week ");
                    String dateText = trimmedInput
                            .substring(isWeek ? "check week ".length() : "check day ".length())
                            .trim();

                    LocalDate anchorDate = Parser.parseCheckDate(dateText);
                    LocalDate rangeStart = anchorDate;
                    LocalDate rangeEnd = anchorDate;

                    if (isWeek) {
                        rangeStart = anchorDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                        rangeEnd = rangeStart.plusDays(6);
                    }

                    printTasksInRange(rangeStart, rangeEnd, isWeek);
                    continue;
                }

                throw new KikiException("I'm sorry, but I don't know what that means :-(");
            } catch (KikiException e) {
                ui.printBox("OOPS!!! " + e.getMessage());
            }
        }

        ui.close();
    }

    /**
     * Returns whether a deadline or event falls within the given date range.
     * Todos have no date and never match.
     */
    private static boolean taskOverlapsRange(Task task, LocalDate rangeStart, LocalDate rangeEnd) {
        if (task instanceof Deadlines deadline) {
            LocalDate dueDate = deadline.getBy().toLocalDate();
            return !dueDate.isBefore(rangeStart) && !dueDate.isAfter(rangeEnd);
        }

        if (task instanceof Events event) {
            LocalDate eventStart = event.getFrom().toLocalDate();
            LocalDate eventEnd = event.getTo().toLocalDate();
            return !eventStart.isAfter(rangeEnd) && !eventEnd.isBefore(rangeStart);
        }

        return false;
    }

    /**
     * Returns the date/time used to sort a deadline or event.
     */
    private static LocalDateTime getSortKey(Task task) {
        if (task instanceof Deadlines deadline) {
            return deadline.getBy();
        }

        if (task instanceof Events event) {
            return event.getFrom();
        }

        return LocalDateTime.MAX;
    }

    /**
     * Finds the deadlines/events in the given date range, sorts them with
     * upcoming ones first (soonest first) followed by past ones, and prints
     * them via {@link Ui}.
     */
    private void printTasksInRange(LocalDate rangeStart, LocalDate rangeEnd, boolean isWeek) {
        List<Task> matches = new ArrayList<>();

        for (int i = 0; i < taskList.size(); i++) {
            if (taskOverlapsRange(taskList.get(i), rangeStart, rangeEnd)) {
                matches.add(taskList.get(i));
            }
        }

        LocalDateTime now = LocalDateTime.now();
        matches.sort(Comparator
                .comparing((Task task) -> getSortKey(task).isBefore(now))
                .thenComparing(Kiki::getSortKey));

        ui.printTasksInRange(rangeStart, rangeEnd, isWeek, matches);
    }
}
