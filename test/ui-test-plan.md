# UI Test Plan

Add future CS2103 iP UI tests below. Each test case must contain Aim, Inputs, and Expected Output sections.

### Test Case: Exit command

Aim:
Check that the program starts, accepts bye, and exits with the goodbye message.

Inputs:
bye

Expected Output:
██╗  ██╗██╗██╗  ██╗██╗
██║ ██╔╝██║██║ ██╔╝██║
█████╔╝ ██║█████╔╝ ██║
██╔═██╗ ██║██╔═██╗ ██║
██║  ██╗██║██║  ██╗██║
╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═╝
Hello! I'm Kiki
How can I be of service today!
   -----------------------------
    Currently in Listing Mode!
   -----------------------------
   -----------------------------
   Goodbye! Hope to see you again soon =)
   -----------------------------

### Test Case: Add and list task types

Aim:
Verify that todo, deadline, and event tasks can be added and displayed using the list command.
Deadline/event times use the "yyyy-MM-dd HHmm" input format and are displayed as
"MMM dd yyyy, h:mm a".

Inputs:
todo read book
deadline return book /by 2019-12-01 1800
event project meeting /from 2019-12-02 1400 /to 2019-12-02 1600
list
bye

Expected Output:
██╗  ██╗██╗██╗  ██╗██╗
██║ ██╔╝██║██║ ██╔╝██║
█████╔╝ ██║█████╔╝ ██║
██╔═██╗ ██║██╔═██╗ ██║
██║  ██╗██║██║  ██╗██║
╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═╝
Hello! I'm Kiki
How can I be of service today!
   -----------------------------
    Currently in Listing Mode!
   -----------------------------
   -----------------------------
   Got it. I've added this task:
    [T][ ] read book
   Now you have 1 tasks in your list.
   -----------------------------
   -----------------------------
   Got it. I've added this task:
    [D][ ] return book (by: Dec 01 2019, 6:00 pm)
   Now you have 2 tasks in your list.
   -----------------------------
   -----------------------------
   Got it. I've added this task:
    [E][ ] project meeting (from: Dec 02 2019, 2:00 pm to: Dec 02 2019, 4:00 pm)
   Now you have 3 tasks in your list.
   -----------------------------
   -----------------------------
   Here are the tasks in your list:
   1. [T][ ] read book
   2. [D][ ] return book (by: Dec 01 2019, 6:00 pm)
   3. [E][ ] project meeting (from: Dec 02 2019, 2:00 pm to: Dec 02 2019, 4:00 pm)
   -----------------------------
   -----------------------------
   Goodbye! Hope to see you again soon =)
   -----------------------------

### Test Case: Invalid mark number

Aim:
Verify that marking past the current list size reports an error instead of crashing.

Inputs:
todo read book
mark 2
bye

Expected Output:
██╗  ██╗██╗██╗  ██╗██╗
██║ ██╔╝██║██║ ██╔╝██║
█████╔╝ ██║█████╔╝ ██║
██╔═██╗ ██║██╔═██╗ ██║
██║  ██╗██║██║  ██╗██║
╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═╝
Hello! I'm Kiki
How can I be of service today!
   -----------------------------
    Currently in Listing Mode!
   -----------------------------
   -----------------------------
   Got it. I've added this task:
    [T][ ] read book
   Now you have 1 tasks in your list.
   -----------------------------
   -----------------------------
   OOPS!!! That task number is not in your list.
   -----------------------------
   -----------------------------
   Goodbye! Hope to see you again soon =)
   -----------------------------

### Test Case: Load saved tasks

Aim:
Verify that Kiki loads existing tasks from the save file when it starts. Saved deadline/event
times are stored as ISO-8601 LocalDateTime text (e.g. "2019-12-01T18:00").

Initial Saved Data:
T | 1 | read book
D | 0 | return book | 2019-12-01T18:00
E | 0 | project meeting | 2019-12-02T14:00 | 2019-12-02T16:00

Inputs:
list
bye

Expected Output:
██╗  ██╗██╗██╗  ██╗██╗
██║ ██╔╝██║██║ ██╔╝██║
█████╔╝ ██║█████╔╝ ██║
██╔═██╗ ██║██╔═██╗ ██║
██║  ██╗██║██║  ██╗██║
╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═╝
Hello! I'm Kiki
How can I be of service today!
   -----------------------------
    Currently in Listing Mode!
   -----------------------------
   -----------------------------
   Here are the tasks in your list:
   1. [T][X] read book
   2. [D][ ] return book (by: Dec 01 2019, 6:00 pm)
   3. [E][ ] project meeting (from: Dec 02 2019, 2:00 pm to: Dec 02 2019, 4:00 pm)
   -----------------------------
   -----------------------------
   Goodbye! Hope to see you again soon =)
   -----------------------------

### Test Case: Skip corrupted saved tasks

Aim:
Verify that Kiki skips malformed saved tasks, loads valid saved tasks, and keeps running.

Initial Saved Data:
T | 1 | read book
X | 0 | unknown task
D | 2 | return book | 2019-12-01T18:00
E | 0 | project meeting | 2019-12-02T14:00 | 2019-12-02T16:00

Inputs:
list
bye

Expected Output:
██╗  ██╗██╗██╗  ██╗██╗
██║ ██╔╝██║██║ ██╔╝██║
█████╔╝ ██║█████╔╝ ██║
██╔═██╗ ██║██╔═██╗ ██║
██║  ██╗██║██║  ██╗██║
╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═╝
Hello! I'm Kiki
How can I be of service today!
   -----------------------------
    Currently in Listing Mode!
   -----------------------------
   -----------------------------
   OOPS!!! Skipped a corrupted saved task: unknown saved task type.
   -----------------------------
   -----------------------------
   OOPS!!! Skipped a corrupted saved task: saved task status must be 0 or 1.
   -----------------------------
   -----------------------------
   Here are the tasks in your list:
   1. [T][X] read book
   2. [E][ ] project meeting (from: Dec 02 2019, 2:00 pm to: Dec 02 2019, 4:00 pm)
   -----------------------------
   -----------------------------
   Goodbye! Hope to see you again soon =)
   -----------------------------

### Test Case: Empty todo and unknown command

Aim:
Verify that an empty todo description and an unknown command show friendly error messages.

Inputs:
todo
blah
bye

Expected Output:
██╗  ██╗██╗██╗  ██╗██╗
██║ ██╔╝██║██║ ██╔╝██║
█████╔╝ ██║█████╔╝ ██║
██╔═██╗ ██║██╔═██╗ ██║
██║  ██╗██║██║  ██╗██║
╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═╝
Hello! I'm Kiki
How can I be of service today!
   -----------------------------
    Currently in Listing Mode!
   -----------------------------
   -----------------------------
   OOPS!!! The description of a todo cannot be empty.
   -----------------------------
   -----------------------------
   OOPS!!! I'm sorry, but I don't know what that means :-(
   -----------------------------

### Test Case: Sort tasks

Aim:
Verify that sort lists dated tasks by date/time and places todos after dated tasks.

Inputs:
todo read book
deadline submit report /by 2035-08-21 1700
event morning class /from 2035-08-20 0900 /to 2035-08-20 1000
sort
bye

Expected Output:
██╗  ██╗██╗██╗  ██╗██╗
██║ ██╔╝██║██║ ██╔╝██║
█████╔╝ ██║█████╔╝ ██║
██╔═██╗ ██║██╔═██╗ ██║
██║  ██╗██║██║  ██╗██║
╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═╝
Hello! I'm Kiki
How can I be of service today!
   -----------------------------
    Currently in Listing Mode!
   -----------------------------
   -----------------------------
   Got it. I've added this task:
    [T][ ] read book
   Now you have 1 tasks in your list.
   -----------------------------
   -----------------------------
   Got it. I've added this task:
    [D][ ] submit report (by: Aug 21 2035, 5:00 pm)
   Now you have 2 tasks in your list.
   -----------------------------
   -----------------------------
   Got it. I've added this task:
    [E][ ] morning class (from: Aug 20 2035, 9:00 am to: Aug 20 2035, 10:00 am)
   Now you have 3 tasks in your list.
   -----------------------------
   -----------------------------
   Sorted your tasks by date/time:
   1. [E][ ] morning class (from: Aug 20 2035, 9:00 am to: Aug 20 2035, 10:00 am)
   2. [D][ ] submit report (by: Aug 21 2035, 5:00 pm)
   3. [T][ ] read book
   -----------------------------
   -----------------------------
   Goodbye! Hope to see you again soon =)
   -----------------------------
   -----------------------------
   Goodbye! Hope to see you again soon =)
   -----------------------------

### Test Case: Invalid mark and unmark inputs

Aim:
Verify that mark and unmark handle invalid, out-of-range, and missing task numbers.

Inputs:
todo read book
mark abc
mark 2
unmark
bye

Expected Output:
██╗  ██╗██╗██╗  ██╗██╗
██║ ██╔╝██║██║ ██╔╝██║
█████╔╝ ██║█████╔╝ ██║
██╔═██╗ ██║██╔═██╗ ██║
██║  ██╗██║██║  ██╗██║
╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═╝
Hello! I'm Kiki
How can I be of service today!
   -----------------------------
    Currently in Listing Mode!
   -----------------------------
   -----------------------------
   Got it. I've added this task:
    [T][ ] read book
   Now you have 1 tasks in your list.
   -----------------------------
   -----------------------------
   OOPS!!! Task number must be a whole number.
   -----------------------------
   -----------------------------
   OOPS!!! That task number is not in your list.
   -----------------------------
   -----------------------------
   OOPS!!! Please tell me which task number to unmark.
   -----------------------------
   -----------------------------
   Goodbye! Hope to see you again soon =)
   -----------------------------

### Test Case: Invalid deadline and event inputs

Aim:
Verify that malformed deadline and event commands explain which part is missing, and that a
description/time that is present but not in the "yyyy-MM-dd HHmm" format is rejected with a
usage message instead of being accepted as free text.

Inputs:
deadline /by 2019-12-01 1800
deadline return book /by
deadline return book /by Sunday
event meeting /from now
event /from 2019-12-01 1400 /to 2019-12-01 1600
event meeting /from /to 2019-12-01 1600
event meeting /from 2019-12-01 1400 /to
event meeting /from Sunday /to 2019-12-01 1600
bye

Expected Output:
██╗  ██╗██╗██╗  ██╗██╗
██║ ██╔╝██║██║ ██╔╝██║
█████╔╝ ██║█████╔╝ ██║
██╔═██╗ ██║██╔═██╗ ██║
██║  ██╗██║██║  ██╗██║
╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═╝
Hello! I'm Kiki
How can I be of service today!
   -----------------------------
    Currently in Listing Mode!
   -----------------------------
   -----------------------------
   OOPS!!! The description of a deadline cannot be empty.
   -----------------------------
   -----------------------------
   OOPS!!! The by time of a deadline cannot be empty.
   -----------------------------
   -----------------------------
   OOPS!!! Please use: deadline DESCRIPTION /by yyyy-MM-dd HHmm
   -----------------------------
   -----------------------------
   OOPS!!! Please use: event DESCRIPTION /from START /to END
   -----------------------------
   -----------------------------
   OOPS!!! The description of an event cannot be empty.
   -----------------------------
   -----------------------------
   OOPS!!! The start time of an event cannot be empty.
   -----------------------------
   -----------------------------
   OOPS!!! The end time of an event cannot be empty.
   -----------------------------
   -----------------------------
   OOPS!!! Please use: event DESCRIPTION /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm
   -----------------------------
   -----------------------------
   Goodbye! Hope to see you again soon =)
   -----------------------------

### Test Case: Delete task from middle of list

Aim:
Verify that deleting a task from the middle removes the correct task and shifts later tasks up.

Inputs:
todo read book
deadline return book /by 2024-06-06 1200
event project meeting /from 2024-08-06 1400 /to 2024-08-06 1600
todo join sports club
todo borrow book
mark 1
mark 2
mark 4
list
delete 3
list
bye

Expected Output:
██╗  ██╗██╗██╗  ██╗██╗
██║ ██╔╝██║██║ ██╔╝██║
█████╔╝ ██║█████╔╝ ██║
██╔═██╗ ██║██╔═██╗ ██║
██║  ██╗██║██║  ██╗██║
╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═╝
Hello! I'm Kiki
How can I be of service today!
   -----------------------------
    Currently in Listing Mode!
   -----------------------------
   -----------------------------
   Got it. I've added this task:
    [T][ ] read book
   Now you have 1 tasks in your list.
   -----------------------------
   -----------------------------
   Got it. I've added this task:
    [D][ ] return book (by: Jun 06 2024, 12:00 pm)
   Now you have 2 tasks in your list.
   -----------------------------
   -----------------------------
   Got it. I've added this task:
    [E][ ] project meeting (from: Aug 06 2024, 2:00 pm to: Aug 06 2024, 4:00 pm)
   Now you have 3 tasks in your list.
   -----------------------------
   -----------------------------
   Got it. I've added this task:
    [T][ ] join sports club
   Now you have 4 tasks in your list.
   -----------------------------
   -----------------------------
   Got it. I've added this task:
    [T][ ] borrow book
   Now you have 5 tasks in your list.
   -----------------------------
   -----------------------------
    Nice! I've marked this task as done:
    [T][X] read book
   -----------------------------
   -----------------------------
    Nice! I've marked this task as done:
    [D][X] return book (by: Jun 06 2024, 12:00 pm)
   -----------------------------
   -----------------------------
    Nice! I've marked this task as done:
    [T][X] join sports club
   -----------------------------
   -----------------------------
   Here are the tasks in your list:
   1. [T][X] read book
   2. [D][X] return book (by: Jun 06 2024, 12:00 pm)
   3. [E][ ] project meeting (from: Aug 06 2024, 2:00 pm to: Aug 06 2024, 4:00 pm)
   4. [T][X] join sports club
   5. [T][ ] borrow book
   -----------------------------
   -----------------------------
   Noted. I've removed this task:
     [E][ ] project meeting (from: Aug 06 2024, 2:00 pm to: Aug 06 2024, 4:00 pm)
   Now you have 4 tasks in the list.
   -----------------------------
   -----------------------------
   Here are the tasks in your list:
   1. [T][X] read book
   2. [D][X] return book (by: Jun 06 2024, 12:00 pm)
   3. [T][X] join sports club
   4. [T][ ] borrow book
   -----------------------------
   -----------------------------
   Goodbye! Hope to see you again soon =)
   -----------------------------

### Test Case: Save-triggering task changes

Aim:
Verify that task-changing commands still produce the expected UI output while Kiki saves
the task list in the background.

Inputs:
todo save me
mark 1
unmark 1
delete 1
bye

Expected Output:
██╗  ██╗██╗██╗  ██╗██╗
██║ ██╔╝██║██║ ██╔╝██║
█████╔╝ ██║█████╔╝ ██║
██╔═██╗ ██║██╔═██╗ ██║
██║  ██╗██║██║  ██╗██║
╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═╝
Hello! I'm Kiki
How can I be of service today!
   -----------------------------
    Currently in Listing Mode!
   -----------------------------
   -----------------------------
   Got it. I've added this task:
    [T][ ] save me
   Now you have 1 tasks in your list.
   -----------------------------
   -----------------------------
    Nice! I've marked this task as done:
    [T][X] save me
   -----------------------------
   -----------------------------
    Get to work,  I'll mark this task as not done yet:
    [T][ ] save me
   -----------------------------
   -----------------------------
   Noted. I've removed this task:
     [T][ ] save me
   Now you have 0 tasks in the list.
   -----------------------------
   -----------------------------
   Goodbye! Hope to see you again soon =)
   -----------------------------

### Test Case: Check day and check week filter and sort tasks

Aim:
Verify that "check day DATE" and "check week DATE" only show deadlines/events that fall on
that date (or in that Mon-Sun week), display both date and time, include multi-day events
that merely overlap the range, exclude unrelated tasks, and sort matches soonest-first (todos
are never shown since they have no date).

Inputs:
deadline submit report /by 2035-08-21 1700
event workshop /from 2035-08-19 0900 /to 2035-08-23 1800
deadline unrelated task /by 2035-09-01 0900
check day 21 August 2035
check week 21 August 2035
bye

Expected Output:
██╗  ██╗██╗██╗  ██╗██╗
██║ ██╔╝██║██║ ██╔╝██║
█████╔╝ ██║█████╔╝ ██║
██╔═██╗ ██║██╔═██╗ ██║
██║  ██╗██║██║  ██╗██║
╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═╝
Hello! I'm Kiki
How can I be of service today!
   -----------------------------
    Currently in Listing Mode!
   -----------------------------
   -----------------------------
   Got it. I've added this task:
    [D][ ] submit report (by: Aug 21 2035, 5:00 pm)
   Now you have 1 tasks in your list.
   -----------------------------
   -----------------------------
   Got it. I've added this task:
    [E][ ] workshop (from: Aug 19 2035, 9:00 am to: Aug 23 2035, 6:00 pm)
   Now you have 2 tasks in your list.
   -----------------------------
   -----------------------------
   Got it. I've added this task:
    [D][ ] unrelated task (by: Sept 01 2035, 9:00 am)
   Now you have 3 tasks in your list.
   -----------------------------
   -----------------------------
   Here's what's happening on Aug 21 2035:
   1. [E][ ] workshop (from: Aug 19 2035, 9:00 am to: Aug 23 2035, 6:00 pm)
   2. [D][ ] submit report (by: Aug 21 2035, 5:00 pm)
   -----------------------------
   -----------------------------
   Here's what's happening from Aug 20 2035 to Aug 26 2035:
   1. [E][ ] workshop (from: Aug 19 2035, 9:00 am to: Aug 23 2035, 6:00 pm)
   2. [D][ ] submit report (by: Aug 21 2035, 5:00 pm)
   -----------------------------
   -----------------------------
   Goodbye! Hope to see you again soon =)
   -----------------------------

### Test Case: Check day/week with no matches and invalid dates

Aim:
Verify that "check day"/"check week" report "Nothing scheduled." when no task falls in range,
and reject a date that cannot be parsed. Note that "check day" with no date text at all falls
through to the unknown-command handler, since it does not match the "check day " prefix.

Inputs:
check day
check day banana
check week 21 August 2035
bye

Expected Output:
██╗  ██╗██╗██╗  ██╗██╗
██║ ██╔╝██║██║ ██╔╝██║
█████╔╝ ██║█████╔╝ ██║
██╔═██╗ ██║██╔═██╗ ██║
██║  ██╗██║██║  ██╗██║
╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═╝
Hello! I'm Kiki
How can I be of service today!
   -----------------------------
    Currently in Listing Mode!
   -----------------------------
   -----------------------------
   OOPS!!! I'm sorry, but I don't know what that means :-(
   -----------------------------
   -----------------------------
   OOPS!!! Please use a date like: 21 August
   -----------------------------
   -----------------------------
   Here's what's happening from Aug 20 2035 to Aug 26 2035:
   Nothing scheduled.
   -----------------------------
   -----------------------------
   Goodbye! Hope to see you again soon =)
   -----------------------------

### Test Case: Find matches keyword case-insensitively

Aim:
Verify that "find KEYWORD" matches tasks of any type by a case-insensitive substring of
their description, reports when nothing matches, and rejects an empty keyword.

Inputs:
todo read book
deadline return book /by 2019-12-01 1800
todo join sports club
find book
find xyz
find
bye

Expected Output:
██╗  ██╗██╗██╗  ██╗██╗
██║ ██╔╝██║██║ ██╔╝██║
█████╔╝ ██║█████╔╝ ██║
██╔═██╗ ██║██╔═██╗ ██║
██║  ██╗██║██║  ██╗██║
╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═╝
Hello! I'm Kiki
How can I be of service today!
   -----------------------------
    Currently in Listing Mode!
   -----------------------------
   -----------------------------
   Got it. I've added this task:
    [T][ ] read book
   Now you have 1 tasks in your list.
   -----------------------------
   -----------------------------
   Got it. I've added this task:
    [D][ ] return book (by: Dec 01 2019, 6:00 pm)
   Now you have 2 tasks in your list.
   -----------------------------
   -----------------------------
   Got it. I've added this task:
    [T][ ] join sports club
   Now you have 3 tasks in your list.
   -----------------------------
   -----------------------------
   Here are the matching tasks in your list:
   1. [T][ ] read book
   2. [D][ ] return book (by: Dec 01 2019, 6:00 pm)
   -----------------------------
   -----------------------------
   Here are the matching tasks in your list:
   No matching tasks found.
   -----------------------------
   -----------------------------
   OOPS!!! Please tell me what keyword to search for.
   -----------------------------
   -----------------------------
   Goodbye! Hope to see you again soon =)
   -----------------------------
