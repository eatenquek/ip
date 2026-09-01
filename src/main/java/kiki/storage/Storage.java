package kiki.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import kiki.exception.KikiException;
import kiki.task.Deadlines;
import kiki.task.Events;
import kiki.task.Task;
import kiki.task.TaskList;
import kiki.task.ToDos;
import kiki.ui.Ui;

/**
 * Handles loading tasks from disk on startup and saving the task list to
 * disk after every change.
 */
public class Storage {
    private static final Path SAVE_FILE_PATH = Path.of("data", "kiki.txt");

    /**
     * Saves the given task list to disk, overwriting any previous contents.
     *
     * @param tasks Task list to save.
     * @throws KikiException If the save folder cannot be prepared or the file
     *         cannot be written.
     */
    public void save(TaskList tasks) throws KikiException {
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
            for (int i = 0; i < tasks.size(); i++) {
                writer.write(formatForStorage(tasks.get(i)));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new KikiException("Unable to save tasks to disk.");
        }
    }

    /**
     * Loads previously saved tasks into {@code tasks}, printing a warning
     * via {@code ui} for any corrupted or excess saved lines.
     */
    public void load(TaskList tasks, Ui ui) {
        if (!Files.exists(SAVE_FILE_PATH)) {
            return;
        }

        if (Files.isDirectory(SAVE_FILE_PATH)) {
            ui.printBox("OOPS!!! Unable to load tasks because the save path is a folder.");
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(SAVE_FILE_PATH)) {
            String line = reader.readLine();

            while (line != null) {
                String trimmedLine = line.trim();

                if (trimmedLine.isEmpty()) {
                    line = reader.readLine();
                    continue;
                }

                if (tasks.isFull()) {
                    ui.printBox("OOPS!!! Save file has more than " + tasks.getCapacity()
                            + " tasks. Extra tasks were ignored.");
                    break;
                }

                try {
                    tasks.add(parseSavedTask(trimmedLine));
                } catch (KikiException e) {
                    ui.printBox("OOPS!!! Skipped a corrupted saved task: " + e.getMessage());
                }

                line = reader.readLine();
            }
        } catch (IOException e) {
            ui.printBox("OOPS!!! Unable to load tasks from disk.");
        }
    }

    /**
     * Formats a task as a single pipe-separated line for the save file.
     *
     * @param task Task to format.
     * @return The formatted line, without a trailing newline.
     */
    private String formatForStorage(Task task) {
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

    /**
     * Parses a single pipe-separated line from the save file back into a task.
     *
     * @param line Trimmed, non-empty line read from the save file.
     * @return The reconstructed task.
     * @throws KikiException If the line has the wrong number of fields, an
     *         unknown task type, a bad done-status, or an unparsable date/time.
     */
    private Task parseSavedTask(String line) throws KikiException {
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
            String byText = parts[3].trim();
            ensureNotEmpty(byText, "saved deadline time is empty.");

            try {
                LocalDateTime by = LocalDateTime.parse(byText);
                task = new Deadlines(description, by);
            } catch (DateTimeParseException e) {
                throw new KikiException("invalid saved deadline date/time.");
            }

        } else if (taskType.equals("E")) {
            String fromText = parts[3].trim();
            String toText = parts[4].trim();
            ensureNotEmpty(fromText, "saved event start time is empty.");
            ensureNotEmpty(toText, "saved event end time is empty.");

            try {
                LocalDateTime from = LocalDateTime.parse(fromText);
                LocalDateTime to = LocalDateTime.parse(toText);
                task = new Events(description, from, to);
            } catch (DateTimeParseException e) {
                throw new KikiException("invalid saved event date/time.");
            }
        } else {
            task = new ToDos(description);
        }

        if (isDone) {
            task.markAsDone();
        }

        return task;
    }

    /**
     * Returns how many pipe-separated fields a saved line of the given task
     * type should have.
     *
     * @param taskType Single-letter saved task type ("T", "D", or "E").
     * @return The expected field count.
     * @throws KikiException If the task type is not recognized.
     */
    private int getExpectedPartCount(String taskType) throws KikiException {
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

    /**
     * Parses a saved done-status field ("0" or "1") into a boolean.
     *
     * @param doneStatus Saved done-status field.
     * @return {@code true} if the field is "1", {@code false} if "0".
     * @throws KikiException If the field is neither "0" nor "1".
     */
    private boolean parseSavedDoneStatus(String doneStatus) throws KikiException {
        if (doneStatus.equals("1")) {
            return true;
        } else if (doneStatus.equals("0")) {
            return false;
        } else {
            throw new KikiException("saved task status must be 0 or 1.");
        }
    }

    private void ensureNotEmpty(String value, String errorMessage) throws KikiException {
        if (value.isEmpty()) {
            throw new KikiException(errorMessage);
        }
    }
}
