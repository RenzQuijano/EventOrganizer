User’s Input

The user enters a sequence of multiple events via keyboard (standard input) following by
the keyword “Done” which ends the sequence. For each event, the user enters the following
information separated by comma in a single line of input:

• Event name (String with no comma)

• Start Date and Time (MM/DD/YYYY @ hh:mm:ss am/pm)

• End Date and Time (MM/DD/YYYY @ hh:mm:ss am/pm)

• Host (String with no comma)

• Number of Invitees (int)

• Location (String with no comma)

In the case that the user’s input doesn’t have a valid format, you need to use Exception
handling to recognize the error and ask the user to retry. More specifically,

• If event name, host, or location have commas, throw an exception and
handle it so that the user tries again.

• If either start or end date-time aren’t in the given format, throw an excep-
tion and handle it so that the user tries again.

• If the number of invitees isn’t an integer, throw an exception and handle
it so that the user tries again.

Acceptable Commands

After user enters all the events followed by the “Done” keyword, the user can enter one of
the following commands:

• quit: ends the program.

• print: prints the list of all events in the following order (Each event must
be printed in a single line with the same format that was entered by the user): The
events are sorted from earliest to latest. Those events that start at the same time
should be sorted from longest to shortest (length of an event can be determined since
both start and end date/times are given). Those events that have the same start and
end dates/times are sorted in an alphabetical order of their names.

• happening on MM/DD/YYYY @ hh:mm:ss am/pm: prints the list of all
events happening on the given date and time in the order mentioned before.

• hosted by hostname: prints the list of all events hosted by the given host-
name in the order mentioned before.
