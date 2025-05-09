package main;
import util.DateTime;
import util.Date;
import util.DateTimeInvalidException;
import util.Event;
import java.io.IOException;
import java.util.*;

public class EventOrganizer {

    private static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args){

        ArrayList<Event> list = new ArrayList<>();

        System.out.println("Please enter the list of all events. Enter \"Done\" when finished" +
                "\nFormat Example: Name,4/18/2004 @ 12:00:00 am,4/18/2004 @ 12:00:00 pm,Host,5,Location");

        //input handling
        while(true) {
            String givenEvent = keyboard.nextLine();
            if (givenEvent.equalsIgnoreCase("done")) {
                break;
            }
            String [] eventProperties = givenEvent.split( ",");

            if (eventProperties.length != 6) { //if event name, host, or location have commas, throw an exception and handle it so that the user tries again
                System.out.println("Illegal event entry. Please try again");
                continue;
            }


            String eventName = eventProperties[0];
            String startDateTimeString = eventProperties[1];
            String endDateTimeString = eventProperties[2];
            String hostName = eventProperties[3];
            try { //if the number of invitees isn’t an integer, throw an exception and handle it so that the user tries again
                Integer.parseInt(eventProperties[4]);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number for amount of invitees. Ex: 56, 23, 91");
                continue;
            }
            int numOfInvitees = Integer.parseInt(eventProperties[4]);
            String location = eventProperties[5];

            //if else to check if any fields were left empty (number of invitees were checked beforehand)
            if(eventName.isBlank()) {
                System.out.println("Please enter a name for the event.");
                continue;
            } else if(startDateTimeString.isBlank()) {
                System.out.println("Please enter a start date and time using the following format example: 4/18/2004 @ 12:00:00 am");
                continue;
            } else if(endDateTimeString.isBlank()) {
                System.out.println("Please enter an end date and time using the following format example: 4/18/2004 @ 12:00:00 pm");
            } else if (hostName.isBlank()) {
                System.out.println("Please enter a name for the host. If none, put \"None\"");
                continue;
            } else if (location.isBlank()) {
                System.out.println("Please enter a location. If none, put \"None\"");
                continue;
            }

            //split up date and time
            //splits into [MM/DD/YYYY] [@] [hh:mm:ss] [am or pm]
            try {
                dateTimeStringSplitter(startDateTimeString);
                dateTimeStringSplitter(endDateTimeString);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error: Please make sure to format date/time's correctly");
                continue;
            }

            String[][] startDateTimeProperties = dateTimeStringSplitter(startDateTimeString);
            String[][] endDateTimeProperties = dateTimeStringSplitter(endDateTimeString);

            try { //If either start or end date-time aren’t in the given format, throw an exception and handle it so that the user tries again
                //creates DateTime objects
                DateTime startDateTime = getDateTime(startDateTimeProperties[1], startDateTimeProperties[2], startDateTimeProperties[0]);
                DateTime endDateTime = getDateTime(endDateTimeProperties[1], endDateTimeProperties[2], endDateTimeProperties[0]);

                //checks for am/pm and @ symbol, just so that format is strict
                dateTimeFormatCheck(startDateTimeProperties, startDateTime);
                dateTimeFormatCheck(endDateTimeProperties, endDateTime);

                if(!DateTime.isValidDateTime(startDateTime)) {
                    throw new DateTimeInvalidException(startDateTime);

                } else if (!DateTime.isValidDateTime(endDateTime)) {
                    throw new DateTimeInvalidException(endDateTime);
                }

                list.add(new Event(eventName, startDateTime, endDateTime, hostName, numOfInvitees, location));

            }
            catch (DateTimeInvalidException e) {//error for correct date/time
                System.out.println("Error occurred: " + e);
            }
            catch (NumberFormatException e) {
                System.out.println("Error occurred. Please make sure to write date/time's correctly");
            }
            catch (IOException e) {//error for format of date/time
                System.out.println("Error occurred. Please make sure to format date/time's correctly");
            }
        }
        Collections.sort(list);


        System.out.println("Please enter a command" +
                "\nFor a list of commands, type \"commands\"");
        //command handling
        while(true) {
            String command = keyboard.nextLine();
            if (command.equalsIgnoreCase("quit")) { //ends program
                break;
            }

            if (command.equalsIgnoreCase("commands")) { //displays list of commands
                System.out.println("""
                        quit
                        print
                        happening on (date and time)
                        hosted by (host name)""");

            } else if (command.equalsIgnoreCase("print")) {
                //prints the list of all events in the following order
                //The events are sorted from earliest to latest. Those events that start at the same time
                //should be sorted from longest to shortest (length of an event can be determined since
                //both start and end date/times are given). Those events that have the same start and
                //end dates/times are sorted in alphabetical order of their names.
                for(Event event: list) {
                    System.out.println(event + "\n");
                }
            } else if (command.startsWith("happening on ")) {
                String givenDateTime = command.substring("happening on ".length());
                //convert givenDateTime string to dateTime obj safely
                try {
                    dateTimeStringSplitter(givenDateTime);
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Error: Please make sure to format date/time's correctly");
                    continue;
                }

                String [][] dateTimeProperties = dateTimeStringSplitter(givenDateTime);

                try {
                    DateTime dateTime = getDateTime(dateTimeProperties[1], dateTimeProperties[2], dateTimeProperties[0]);

                    dateTimeFormatCheck(dateTimeProperties, dateTime);

                    if(!DateTime.isValidDateTime(dateTime)) {
                        throw new DateTimeInvalidException(dateTime);
                    }
                }
                catch (DateTimeInvalidException e) {
                    System.out.println("Error occurred: " + e);
                    continue;
                }
                catch (NumberFormatException e) {
                    System.out.println("Error occurred. Please make sure to write date/time's correctly");
                    continue;
                }
                catch (IOException e) {//error for format of date/time
                    System.out.println("Error occurred. Please make sure to format date/time's correctly");
                    continue;
                }

                DateTime dateTime = getDateTime(dateTimeProperties[1], dateTimeProperties[2], dateTimeProperties[0]);

                //if no events during givenDateTime, program prints a message saying so
                boolean eventsDuringTime = false;
                for(Event event: list) {
                    //compare every event's start and end dateTime with given date time
                    //if given DateTime is greater than or equal to startDateTime
                    //and given DateTime is less than or equal to endDateTime
                    //print that event
                    if ((dateTime.compareTo(event.getStart()) >= 0) && (dateTime.compareTo(event.getEnd()) <= 0)) {
                        System.out.println(event);
                        eventsDuringTime = true;
                    }
                }

                if (!eventsDuringTime) {
                    System.out.println("No events during given time: " + dateTime);
                }

            } else if (command.startsWith("hosted by ")) {
                String givenHost = command.substring("hosted by ".length());
                //make sure given host has no commas
                if (givenHost.contains(",")) {
                    System.out.println("Please make sure the host's name does not contain any commas");
                    continue;
                }

                //if no events with givenHost, program prints a message saying so
                boolean eventsWithHost = false;
                for(Event event: list) {
                    //compare every event's hostName with givenHostName
                    //if found match, print event
                    if(givenHost.equalsIgnoreCase(event.getHost().toLowerCase())) {
                        System.out.println(event);
                        eventsWithHost = true;
                    }
                }

                if (!eventsWithHost) {
                    System.out.println("There are no events with that host");
                }

            } else {
                System.out.println("Illegal command. Please try again");
            }
        }
    }

    private static void dateTimeFormatCheck(String[][] dateTimeProperties, DateTime DateTime) throws DateTimeInvalidException, IOException {
        if (!dateTimeProperties[0][1].equals("@")) {
            throw new IOException();
        }
        //to make sure user enters am or pm
        if(!dateTimeProperties[0][3].equalsIgnoreCase("am") && !dateTimeProperties[0][3].equalsIgnoreCase("pm")) {
            throw new IOException();
        }
    }

    private static DateTime getDateTime(String[] dateProperties, String[] timeProperties, String[] dateTimeProperties) throws NumberFormatException{
        int month = Integer.parseInt(dateProperties[0]),
                day = Integer.parseInt(dateProperties[1]),
                year = Integer.parseInt(dateProperties[2]);

        int hour = Integer.parseInt(timeProperties[0]),
                minute = Integer.parseInt(timeProperties[1]),
                second = Integer.parseInt(timeProperties[2]);

        return new DateTime((new Date(day, month, year)), hour, minute, second, dateTimeProperties[3].equalsIgnoreCase("am"));
    }

    private static String[][] dateTimeStringSplitter(String givenDateTimeString) throws IndexOutOfBoundsException {
        String [] dateTimeProperties = givenDateTimeString.split(" "); //splits into [MM/DD/YYYY] [@] [hh:mm:ss] [am or pm]
        String [] dateProperties = dateTimeProperties[0].split("/"); //splits into [MM] [DD] [YYYY]
        String [] timeProperties = dateTimeProperties[2].split(":"); //splits into [hh] [mm] [ss]

        if(dateTimeProperties[3].isEmpty()) {
            throw new IndexOutOfBoundsException();
        }

        return new String[][]{dateTimeProperties, dateProperties, timeProperties};
    }
}