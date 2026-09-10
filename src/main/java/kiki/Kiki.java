package kiki;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
    private boolean isLoaded;

    /**
     * Starts the chatbot.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        new Kiki().run();
    }

    private void run() {
        ui.printWelcome();
        loadTasks(ui);

        while (true) {
            String trimmedInput = ui.readCommand();

            if (handleCommand(trimmedInput, ui)) {
                break;
            }
        }

        ui.close();
    }

    /**
     * Returns Kiki's response to a single command for use by the GUI.
     *
     * @param input Raw user input.
     * @return The text Kiki would print for that command.
     */
    public String getResponse(String input) {
        ByteArrayOutputStream responseBuffer = new ByteArrayOutputStream();
        Ui responseUi = new Ui(new ByteArrayInputStream(new byte[0]), new PrintStream(responseBuffer));

        loadTasks(responseUi);
        handleCommand(input.trim(), responseUi);

        return responseBuffer.toString().strip();
    }

    private void loadTasks(Ui outputUi) {
        if (isLoaded) {
            return;
        }

        storage.load(taskList, outputUi);
        isLoaded = true;
    }

    private boolean handleCommand(String trimmedInput, Ui outputUi) {
        try {
            if (trimmedInput.equalsIgnoreCase("bye")) {
                outputUi.printGoodbye();
                return true;
            }

            if (trimmedInput.isEmpty()) {
                throw new KikiException("Please enter a command.");
            }

            if (trimmedInput.equals("todo") || trimmedInput.startsWith("todo ")) {
                addTodo(trimmedInput, outputUi);
                return false;
            }

            if (trimmedInput.equals("deadline") || trimmedInput.startsWith("deadline ")) {
                addDeadline(trimmedInput, outputUi);
                return false;
            }

            if (trimmedInput.equals("event") || trimmedInput.startsWith("event ")) {
                addEvent(trimmedInput, outputUi);
                return false;
            }

            if (trimmedInput.equals("mark") || trimmedInput.startsWith("mark ")) {
                markTask(trimmedInput, outputUi);
                return false;
            }

            if (trimmedInput.equals("unmark") || trimmedInput.startsWith("unmark ")) {
                unmarkTask(trimmedInput, outputUi);
                return false;
            }

            if (trimmedInput.equals("delete") || trimmedInput.startsWith("delete ")) {
                deleteTask(trimmedInput, outputUi);
                return false;
            }

            if (trimmedInput.equalsIgnoreCase("list")) {
                outputUi.printList(taskList);
                return false;
            }

            if (trimmedInput.equals("find") || trimmedInput.startsWith("find ")) {
                String keyword = Parser.parseFindKeyword(trimmedInput);
                printMatchingTasks(keyword, outputUi);
                return false;
            }

            if (trimmedInput.startsWith("check day ") || trimmedInput.startsWith("check week ")) {
                checkTasks(trimmedInput, outputUi);
                return false;
            }

            throw new KikiException("I'm sorry, but I don't know what that means :-(");
        } catch (KikiException e) {
            outputUi.printBox("OOPS!!! " + e.getMessage());
            return false;
        }
    }

    private void addTodo(String trimmedInput, Ui outputUi) throws KikiException {
        taskList.ensureCanAdd();
        String description = Parser.parseTodoDescription(trimmedInput);
        Task todo = new ToDos(description);
        taskList.add(todo);
        storage.save(taskList);
        outputUi.printAddedTask(todo, taskList.size());
    }

    private void addDeadline(String trimmedInput, Ui outputUi) throws KikiException {
        taskList.ensureCanAdd();
        Deadlines deadline = Parser.parseDeadline(trimmedInput);
        taskList.add(deadline);
        storage.save(taskList);
        outputUi.printAddedTask(deadline, taskList.size());
    }

    private void addEvent(String trimmedInput, Ui outputUi) throws KikiException {
        taskList.ensureCanAdd();
        Events event = Parser.parseEvent(trimmedInput);
        taskList.add(event);
        storage.save(taskList);
        outputUi.printAddedTask(event, taskList.size());
    }

    private void markTask(String trimmedInput, Ui outputUi) throws KikiException {
        int taskIndex = Parser.parseTaskIndex(trimmedInput, "mark", taskList.size());
        Task task = taskList.get(taskIndex);
        task.markAsDone();
        storage.save(taskList);
        outputUi.printMarked(task);
    }

    private void unmarkTask(String trimmedInput, Ui outputUi) throws KikiException {
        int taskIndex = Parser.parseTaskIndex(trimmedInput, "unmark", taskList.size());
        Task task = taskList.get(taskIndex);
        task.markAsNotDone();
        storage.save(taskList);
        outputUi.printUnmarked(task);
    }

    private void deleteTask(String trimmedInput, Ui outputUi) throws KikiException {
        int taskIndex = Parser.parseTaskIndex(trimmedInput, "delete", taskList.size());
        Task removedTask = taskList.remove(taskIndex);
        storage.save(taskList);
        outputUi.printDeleted(removedTask, taskList.size());
    }

    private void checkTasks(String trimmedInput, Ui outputUi) throws KikiException {
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

        printTasksInRange(rangeStart, rangeEnd, isWeek, outputUi);
    }

    /**
     * Returns whether a task's description contains the given keyword,
     * ignoring case.
     */
    static boolean descriptionContainsKeyword(Task task, String keyword) {
        return task.getDescription().toLowerCase().contains(keyword.toLowerCase());
    }

    /**
     * Returns whether a deadline or event falls within the given date range.
     * Todos have no date and never match.
     */
    static boolean taskOverlapsRange(Task task, LocalDate rangeStart, LocalDate rangeEnd) {
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
    static LocalDateTime getSortKey(Task task) {
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
    private void printTasksInRange(LocalDate rangeStart, LocalDate rangeEnd, boolean isWeek, Ui outputUi) {
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

        outputUi.printTasksInRange(rangeStart, rangeEnd, isWeek, matches);
    }

    /**
     * Finds the tasks whose description contains the given keyword, and
     * prints them via {@link Ui}.
     */
    private void printMatchingTasks(String keyword, Ui outputUi) {
        List<Task> matches = new ArrayList<>();

        for (int i = 0; i < taskList.size(); i++) {
            if (descriptionContainsKeyword(taskList.get(i), keyword)) {
                matches.add(taskList.get(i));
            }
        }

        outputUi.printMatchingTasks(matches);
    }
}
