# Kiki

Kiki is a command-line task chatbot for keeping track of simple tasks. It can add todos,
deadlines, and events, show your task list, mark tasks as done or not done, delete tasks,
and exit with a goodbye message.

This project is based on the CS2103 iP Java starter template.

## Requirements

- JDK 25
- IntelliJ IDEA, or a terminal that can run `javac` and `java`

On macOS, you can switch to the project Java version with:

```bash
sdk use java 25.0.3.fx-zulu
```

## Running Kiki

### IntelliJ IDEA

1. Open this project folder in IntelliJ.
2. Configure the project SDK to use JDK 25.
3. Open `src/main/java/kiki/Kiki.java`.
4. Run `Kiki.main()`.

### Terminal

From the project root, compile the Java files and run Kiki:

```bash
find src/main/java -name "*.java" > sources.txt
javac -d out @sources.txt
java -cp out kiki.Kiki
```

### Fat JAR (Gradle)

Build a standalone, runnable JAR (with all dependencies bundled in) using the `shadowJar` task:

```bash
./gradlew shadowJar
```

The JAR is created at `build/libs/kiki.jar`. Run it directly with:

```bash
java -jar build/libs/kiki.jar
```

## Commands

Kiki reads one command per line.

| Command | Format | Example |
| --- | --- | --- |
| Add a todo | `todo DESCRIPTION` | `todo read book` |
| Add a deadline | `deadline DESCRIPTION /by TIME` | `deadline return book /by Sunday` |
| Add an event | `event DESCRIPTION /from START /to END` | `event project meeting /from Mon 2pm /to 4pm` |
| List tasks | `list` | `list` |
| Mark a task as done | `mark NUMBER` | `mark 1` |
| Mark a task as not done | `unmark NUMBER` | `unmark 1` |
| Delete a task | `delete NUMBER` | `delete 2` |
| Exit Kiki | `bye` | `bye` |

Task numbers come from the `list` command. For example, `mark 2` marks the second task in the current list.

## Task Display

Kiki shows each task with a task type and completion status:

```text
[T][ ] read book
[D][X] return book (by: Sunday)
[E][ ] project meeting (from: Mon 2pm to: 4pm)
```

- `[T]` means todo.
- `[D]` means deadline.
- `[E]` means event.
- `[ ]` means not done.
- `[X]` means done.

## Example Session

```text
todo read book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
list
mark 1
delete 2
bye
```

After these commands, Kiki adds three tasks, prints the list, marks the first task as done,
removes the second task, and exits.

## Error Handling

Kiki prints friendly error messages when a command is missing required information or uses
an invalid task number. For example:

```text
todo
OOPS!!! The description of a todo cannot be empty.

mark abc
OOPS!!! Task number must be a whole number.

mark 99
OOPS!!! That task number is not in your list.
```

## Current Limitations

- Kiki saves task changes to `data/kiki.txt` and loads that file when the program starts.
- Kiki can hold up to 100 tasks in one run.
- Deadline and event times are stored as plain text.

Saved tasks use this format:

```text
T | 1 | read book
D | 0 | return book | Sunday
E | 0 | project meeting | Mon 2pm | 4pm
```

## UI Test Plan

The working command behavior is documented in `test/ui-test-plan.md`.
