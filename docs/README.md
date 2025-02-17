# Owen User Guide
Owen is your friendly chatbot who manages your tasks for you! 
Additionally, Owen only takes in typed commands, perfect for those who love typing fast!
![Screenshot of Owen in action](/docs/Ui.png)

// Update the title above to match the actual product name

// Product screenshot goes here

// Product intro goes here

## Installation:

1. Download the latest jar from [Owen's releases](https://github.com/lesterlimjj/ip/releases/) to a folder of your choice.
2. Open a terminal(e.g. Command Prompt) and navigate to the folder where the jar is located.
3. Run the following command:
```bash
java -jar Owen-<version>.jar
```
## Owen's Great Features:
Owen is apple to perform the following commands:
- add todo
- add deadline
- add event
- list
- mark task as done
- unmark task as not done
- delete task
- tag a task
- find task by description
- bye

## Add Todo Command
Adds a simple todo with a description to your tasklist.

Expected Command Format:

```text
todo (description of task)
```

Example Todo Command:
```text
todo eat lunch
```

For the response, Owen will reply you with the details of the Todo as well as the number of tasks you have.

```
The following todo has been added:
[T][] eat lunch
You now have 1 tasks in the list.
```

## Add Deadline Command
Adds a deadline with a description and end date to your tasklist.

Expected Command Format:

```text
deadline (description of task) /by (end date)
```

<b>Dates need to be either M/d/yyyy HHmm or d/M/yyyy HHmm.</b>

Example Deadline Command:
```text
deadline assignment 1 /by 10/3/2020 2359
```

For the response, Owen will reply you with the details of the deadline as well as the number of tasks you have.


```
The following deadline has been added:
[D][] assignment 1 (by: Mar 10 2020 11:59pm)
You now have 2 tasks in the list.
```

## Add Event Command
Adds a event with a description, start date and end date to your tasklist.

Expected Command Format:

```text
event (description of task) /from (start date) /to (end date)
```

<b>Dates need to be either M/d/yyyy HHmm or d/M/yyyy HHmm.</b>

Example Event Command:
```text
event project meeting /from 2/12/2020 1800 /to 2/12/2020 2000
```

For the response, Owen will reply you with the details of the event as well as the number of tasks you have.

```
The following event has been added:
[E][] project meeting (from: Dec 02 2020 6:00pm to: Dec 02 2020 8:00pm)
You now have 3 tasks in the list.
```

## List Command
Gets the details of all provided tasks.

Expected Command Format:

```text
list
```


For the response, Owen will reply you with the details of all the tasks you have given it.

```
Friend, here is your list of tasks:
1. [T][] eat lunch
2. [D][] assignment 1 (by: 10/3/2020 2359)
3. [E][] project meeting (from: Dec 02 2020 6:00pm to: Dec 02 2020 8:00pm)
```

## Mark Command
Mark a task to be done based on an index in the list

Expected Command Format:

```text
mark (index of task to be done)
```

Example mark Command:
```text
mark 1
```


For the response, Owen will reply you with status of the updated task.

```
The following is now done:
[T][X] eat lunch
```

## Unmark Command
unmark a task to be not done based on an index in the list

Expected Command Format:

```text
unmark (index of task to be not done)
```

Example unmark Command:
```text
unmark 1
```

For the response, Owen will reply you with status of the updated task.

```
The following is now not done:
[T][] eat lunch
```

## Delete Command
Deletes a task from your tasklist based on an index in the list.

Expected Command Format:

```text
delete (index of task to be deleted)
```

Example unmark Command:
```text
delete 1
```

For the response, Owen will reply you with the details of the deleted task as well 
as the number of remaining tasks in the list:

```
The following is now deleted:
1. [T][] eat lunch
You now have 2 tasks in the list.
```

## Tag Task Command
Tag an existing task based on an index in the list.

Expected Command Format:

```text
tag (index of task to be tag) (provided tag)
```

Example unmark Command:
```text
tag 1 yummy
```

For the response, Owen will reply you with the details of the deleted task:

```
The following is now tagged:
[T][] eat lunch #yummy
```

## Find Command
Find a task in the list by its description.

Expected Command Format:

```text
find (description to search for)
```

Example unmark Command:
```text
find lunch
```

For the response, Owen will reply you with the details of the deleted task:

```
Friend, here are the results of your search:
1. [T][] eat lunch
```

## Bye Command
Exit the application.

Expected Command Format:

```text
bye
```

For the response, Owen will reply you with a sincere goodbye message:

```
I am sure we will see each other soon. Goodbye.
```