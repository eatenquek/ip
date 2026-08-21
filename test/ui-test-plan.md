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
