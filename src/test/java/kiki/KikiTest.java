package kiki;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;

import kiki.task.Deadlines;
import kiki.task.Events;
import kiki.task.Task;
import kiki.task.ToDos;

public class KikiTest {

    private static final LocalDate RANGE_START = LocalDate.of(2035, Month.AUGUST, 20);
    private static final LocalDate RANGE_END = LocalDate.of(2035, Month.AUGUST, 26);

    @Test
    public void taskOverlapsRange_deadlineInsideRange_returnsTrue() {
        Task deadline = new Deadlines("submit report",
                LocalDateTime.of(2035, Month.AUGUST, 21, 17, 0));

        assertTrue(Kiki.taskOverlapsRange(deadline, RANGE_START, RANGE_END));
    }

    @Test
    public void taskOverlapsRange_deadlineOutsideRange_returnsFalse() {
        Task deadline = new Deadlines("unrelated task",
                LocalDateTime.of(2035, Month.SEPTEMBER, 1, 9, 0));

        assertFalse(Kiki.taskOverlapsRange(deadline, RANGE_START, RANGE_END));
    }

    @Test
    public void taskOverlapsRange_eventSpanningRangeBoundary_returnsTrue() {
        Task event = new Events("workshop",
                LocalDateTime.of(2035, Month.AUGUST, 19, 9, 0),
                LocalDateTime.of(2035, Month.AUGUST, 23, 18, 0));

        assertTrue(Kiki.taskOverlapsRange(event, RANGE_START, RANGE_END));
    }

    @Test
    public void taskOverlapsRange_eventEntirelyBeforeRange_returnsFalse() {
        Task event = new Events("old workshop",
                LocalDateTime.of(2035, Month.AUGUST, 1, 9, 0),
                LocalDateTime.of(2035, Month.AUGUST, 2, 18, 0));

        assertFalse(Kiki.taskOverlapsRange(event, RANGE_START, RANGE_END));
    }

    @Test
    public void taskOverlapsRange_todo_returnsFalse() {
        Task todo = new ToDos("read book");

        assertFalse(Kiki.taskOverlapsRange(todo, RANGE_START, RANGE_END));
    }

    @Test
    public void getSortKey_deadline_returnsByDateTime() {
        LocalDateTime by = LocalDateTime.of(2035, Month.AUGUST, 21, 17, 0);
        Task deadline = new Deadlines("submit report", by);

        assertEquals(by, Kiki.getSortKey(deadline));
    }

    @Test
    public void getSortKey_event_returnsFromDateTime() {
        LocalDateTime from = LocalDateTime.of(2035, Month.AUGUST, 19, 9, 0);
        Task event = new Events("workshop", from, LocalDateTime.of(2035, Month.AUGUST, 23, 18, 0));

        assertEquals(from, Kiki.getSortKey(event));
    }

    @Test
    public void getSortKey_todo_returnsMaxDateTime() {
        Task todo = new ToDos("read book");

        assertEquals(LocalDateTime.MAX, Kiki.getSortKey(todo));
    }

    @Test
    public void descriptionContainsKeyword_substringMatch_returnsTrue() {
        Task todo = new ToDos("read book");

        assertTrue(Kiki.descriptionContainsKeyword(todo, "book"));
    }

    @Test
    public void descriptionContainsKeyword_differentCase_returnsTrue() {
        Task todo = new ToDos("read book");

        assertTrue(Kiki.descriptionContainsKeyword(todo, "BOOK"));
    }

    @Test
    public void descriptionContainsKeyword_noMatch_returnsFalse() {
        Task todo = new ToDos("read book");

        assertFalse(Kiki.descriptionContainsKeyword(todo, "movie"));
    }
}
