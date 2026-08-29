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

Inputs:
todo read book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
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
    [D][ ] return book (by: Sunday)
   Now you have 2 tasks in your list.
   -----------------------------
   -----------------------------
   Got it. I've added this task:
    [E][ ] project meeting (from: Mon 2pm to: 4pm)
   Now you have 3 tasks in your list.
   -----------------------------
   -----------------------------
   Here are the tasks in your list:
   1. [T][ ] read book
   2. [D][ ] return book (by: Sunday)
   3. [E][ ] project meeting (from: Mon 2pm to: 4pm)
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
Verify that Kiki loads existing tasks from the save file when it starts.

Initial Saved Data:
T | 1 | read book
D | 0 | return book | Sunday
E | 0 | project meeting | Mon 2pm | 4pm

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
   2. [D][ ] return book (by: Sunday)
   3. [E][ ] project meeting (from: Mon 2pm to: 4pm)
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
D | 2 | return book | Sunday
E | 0 | project meeting | Mon 2pm | 4pm

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
   2. [E][ ] project meeting (from: Mon 2pm to: 4pm)
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
Verify that malformed deadline and event commands explain which part is missing.

Inputs:
deadline /by Sunday
deadline return book /by
event meeting /from now
event /from now /to later
event meeting /from /to later
event meeting /from now /to
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
   Goodbye! Hope to see you again soon =)
   -----------------------------

### Test Case: Delete task from middle of list

Aim:
Verify that deleting a task from the middle removes the correct task and shifts later tasks up.

Inputs:
todo read book
deadline return book /by June 6th
event project meeting /from Aug 6th 2pm /to 4pm
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
    [D][ ] return book (by: June 6th)
   Now you have 2 tasks in your list.
   -----------------------------
   -----------------------------
   Got it. I've added this task:
    [E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
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
    [D][X] return book (by: June 6th)
   -----------------------------
   -----------------------------
    Nice! I've marked this task as done:
    [T][X] join sports club
   -----------------------------
   -----------------------------
   Here are the tasks in your list:
   1. [T][X] read book
   2. [D][X] return book (by: June 6th)
   3. [E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
   4. [T][X] join sports club
   5. [T][ ] borrow book
   -----------------------------
   -----------------------------
   Noted. I've removed this task:
     [E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
   Now you have 4 tasks in the list.
   -----------------------------
   -----------------------------
   Here are the tasks in your list:
   1. [T][X] read book
   2. [D][X] return book (by: June 6th)
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
