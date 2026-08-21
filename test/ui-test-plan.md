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
