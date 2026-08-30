package kiki.task;

import kiki.exception.KikiException;

/**
 * Manages the in-memory collection of {@link Task}s, backed by a fixed-size
 * array. Exposes the simple operations Kiki needs to add, remove, fetch,
 * and count tasks.
 */
public class TaskList {
    private static final int MAX_TASKS = 100;

    private final Task[] tasks;
    private int taskCount;

    public TaskList() {
        this.tasks = new Task[MAX_TASKS];
        this.taskCount = 0;
    }

    /**
     * Throws if the list is already at capacity, so callers can check
     * before doing any work to build the task that would be added.
     */
    public void ensureCanAdd() throws KikiException {
        if (isFull()) {
            throw new KikiException("Your task list is full.");
        }
    }

    public void add(Task task) {
        tasks[taskCount] = task;
        taskCount++;
    }

    public Task remove(int index) {
        Task removed = tasks[index];

        for (int i = index; i < taskCount - 1; i++) {
            tasks[i] = tasks[i + 1];
        }

        tasks[taskCount - 1] = null;
        taskCount--;

        return removed;
    }

    public Task get(int index) {
        return tasks[index];
    }

    public int size() {
        return taskCount;
    }

    public boolean isFull() {
        return taskCount >= MAX_TASKS;
    }

    public int getCapacity() {
        return MAX_TASKS;
    }
}
