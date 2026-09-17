package kiki.gui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DialogBoxTest {

    @Test
    public void isErrorResponse_errorPrefix_returnsTrue() {
        assertTrue(DialogBox.isErrorResponse("OOPS!!! Unknown command."));
    }

    @Test
    public void isErrorResponse_normalResponse_returnsFalse() {
        assertFalse(DialogBox.isErrorResponse("Task added."));
    }
}
