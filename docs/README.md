# Veigar User Guide

## Quick start

1. Ensure you have Java `17` or above installed in your Computer. Mac users: Ensure you have the precise JDK version prescribed here.
2. Download the latest `.jar` file from [here](https://github.com/OnlyTheEnd/ip/releases/tag/A-Release).
3. Copy the file to the folder you want to use as the home folder for your TaskList.
4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar veigar.jar` command to run the application. 
A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.

[<img src="Ui.png">]

5. Refer to the Features below for command details


## Features

### Viewing all tasks: `list`

Shows a list of all tasks in your task tracker.

Format: `list`

---

### Adding a todo task: `todo`

Adds a todo task to your task list.

Format: `todo DESCRIPTION`

Example:
* `todo Buy groceries`

---

### Adding an event task: `event`

Adds an event task with a start and end time to your task list.
End date cannot be earlier than from date.

Format: `event DESCRIPTION /from START /to END`

Examples:
* `event Project meeting /from 31/12/2023 1617 /to 31/12/2023 2359`
* `event Conference /from 1 Feb 2026, 5:00PM /to 3 Feb 2026, 5:00PM`

---

### Adding a deadline task: `deadline`

Adds a task with a deadline to your task list.

Format: `deadline DESCRIPTION /by DATE`

Example:
* `deadline Submit report /by Friday`

---

### Marking a task as done: `mark`

Marks the specified task as completed.

Format: `mark INDEX`

* Marks the task at the specified `INDEX` as done.
* The index refers to the index number shown in the displayed task list.
* The index **must be a positive integer** 1, 2, 3, … and within size of task list.

Example:
* `mark 2` marks the 2nd task in the list as done.

---

### Unmarking a task: `unmark`

Marks the specified task as not done.

Format: `unmark INDEX`

* Marks the task at the specified `INDEX` as not done.
* The index refers to the index number shown in the displayed task list.
* The index **must be a positive integer** 1, 2, 3, … and within size of task list.

Example:
* `unmark 2` marks the 2nd task in the list as not done.

---

### Deleting a task: `delete`

Deletes the specified task from your task list.

Format: `delete INDEX`

* Deletes the task at the specified `INDEX`.
* The index refers to the index number shown in the displayed task list.
* The index **must be a positive integer** 1, 2, 3, … and within size of task list.

Example:
* `delete 3` deletes the 3rd task in the list.

---

### Finding tasks by keyword: `find`

Finds tasks whose descriptions contain the given keyword.

Format: `find KEYWORD`

Example:
* `find meeting` returns all tasks containing "meeting" in their description.

---

### Showing tasks by date: `show`

Shows all tasks occurring on a specific date.

Format: `show DATE`

Example:
* `show 1 Feb 2026` displays all tasks scheduled for February 1, 2026.

---

### Exiting the program: `bye`

Exits the application.

Format: `bye`

---

## Editing the data file
AddressBook data are saved automatically as a 
`JSON file [JAR file location]/data/tasks.json`.

Advanced users are welcome to update data directly by editing that data file.

## Acknowledgement:
GSON google library for JSON serializing


| Action | Format | Examples |
|--------|--------|----------|
| Viewing all tasks | `list` | `list` |
| Adding a todo task | `todo DESCRIPTION` | `todo Buy groceries` |
| Adding an event task | `event DESCRIPTION /from START /to END` | `event Project meeting /from 31/12/2023 1617 /to 31/12/2023 2359`<br>`event Conference /from 1 Feb 2026, 5:00PM /to 3 Feb 2026, 5:00PM` |
| Adding a deadline task | `deadline DESCRIPTION /by DATE` | `deadline Submit report /by Friday` |
| Marking a task as done | `mark INDEX` | `mark 2` |
| Unmarking a task | `unmark INDEX` | `unmark 2` |
| Deleting a task | `delete INDEX` | `delete 3` |
| Finding tasks by keyword | `find KEYWORD` | `find meeting` |
| Showing tasks by date | `show DATE` | `show 1 Feb 2026` |
| Exiting the program | `bye` | `bye` |

## Valid Date Formats


1. Date and Time with slashes ex. 31/12/2023 2359.
2. Date and Time with letters ex. 2 Feb 2023, 5:00PM.
3. Day and Time -> Next Occurrence of Day at set time ex. Monday 4PM.
4. Date with letters ex. 26 Feb 2026
5. Date with slashes -> Date and time set to 0000 ex.31/12/2023.
6. Day -> Next Occurrence of Day at current time.ex. Monday.

## [AI guidelines](Ai.md)

[Credits for this project](references.md)