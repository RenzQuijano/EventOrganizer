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

        System.out.println("Please enter the list of all events. Enter \"Done\" when finished");

        //input handling
        while(true) {
            String givenEvent = keyboard.nextLine();
            if (givenEvent.equalsIgnoreCase("done")) {
                break;
            }
            String [] eventProperties = givenEvent.split( ", ");
            if (eventProperties.length != 6) {
                System.out.println("Illegal event entry. Please try again");
                continue;
            }

            //EX: Name, 4/18/2004 @ 12:00:00 am, 4/18/2004 @ 12:00:00 pm, Host, 5, Location
            String eventName = eventProperties[0];
            String startDateTimeString = eventProperties[1];
            String endDateTimeString = eventProperties[2];
            String hostName = eventProperties[3];
            try {
                Integer.parseInt(eventProperties[4]);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number for amount of invitees. Ex: 56, 23, 91");
                continue;
            }
            int numOfInvitees = Integer.parseInt(eventProperties[4]);


            String location = eventProperties[5];

            //split up date and time
            //splits into [MM/DD/YYYY] [@] [hh:mm:ss] [am or pm]
            String [] startDateTimeProperties = startDateTimeString.split(" "); //splits into [MM/DD/YYYY] [@] [hh:mm:ss] [am or pm]
            String [] startDateProperties = startDateTimeProperties[0].split("/"); //splits into [MM] [DD] [YYYY]
            String [] startTimeProperties = startDateTimeProperties[2].split(":"); //splits into [hh] [mm] [ss]

            String [] endDateTimeProperties = endDateTimeString.split(" "); //splits into [MM/DD/YYYY] [@] [hh:mm:ss] [am or pm]
            String [] endDateProperties = endDateTimeProperties[0].split("/"); //splits into [MM] [DD] [YYYY]
            String [] endTimeProperties = endDateTimeProperties[2].split(":"); //splits into [hh] [mm] [ss]

            try {
                DateTime startDateTime = getDateTime(startDateProperties, startTimeProperties, startDateTimeProperties);
                DateTime endDateTime = getDateTime(endDateProperties, endTimeProperties, endDateTimeProperties);

                if(!DateTime.isValidDateTime(startDateTime)) {
                    throw new DateTimeInvalidException(startDateTime); //catch exception!!!

                } else if (!DateTime.isValidDateTime(endDateTime)) {
                    throw new DateTimeInvalidException(endDateTime);
                }

                list.add(new Event(eventName, startDateTime, endDateTime, hostName, numOfInvitees, location));


            }
            catch (DateTimeInvalidException e) {
                System.out.println("Error occurred: " + e);
            }
            catch (NumberFormatException e) {
                System.out.println("Error occurred. Please make sure to write date/time's correctly");
            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error: Please make sure to format date/time's correctly");
            }




            //list.add();

        }
        Collections.sort(list);

        //command handling
        while(true) {
            String command = keyboard.nextLine();
            if (command.equalsIgnoreCase("quit")) {
                break;
            }
            if (command.equalsIgnoreCase("print")) {
                for(Event event: list) {
                    System.out.println(event);
                }
            } else if (command.startsWith("happening on ")) {
                String dateTime = command.substring("happening on ".length());
                //convert givenDateTime string to dateTime obj
                for(Event event: list) {
                    //compare every event's start and end dateTime with given date time
                    //if given DateTime is greater than or equal to startDateTime
                    //and given DateTime is less than or equal to endDateTime
                    //print that event
                }

            } else if (command.startsWith("hosted by ")) {
                String givenHost = command.substring("hosted by ".length());
                //make sure given host has no commas
                for(Event event: list) {
                    //compare every event's hostName with givenHostName
                    //if found match, print event
                }

            } else {
                System.out.println("Illegal command. Please try again");
            }
        }
    }

    private static DateTime getDateTime(String[] dateProperties, String[] timeProperties, String[] dateTimeProperties) {
        int month = Integer.parseInt(dateProperties[0]),
                day = Integer.parseInt(dateProperties[1]),
                year = Integer.parseInt(dateProperties[2]);

        int hour = Integer.parseInt(timeProperties[0]),
                minute = Integer.parseInt(timeProperties[1]),
                second = Integer.parseInt(timeProperties[2]);

        if(!dateTimeProperties[3].equalsIgnoreCase("am") || !dateTimeProperties[3].equalsIgnoreCase("pm")) {
            return new DateTime(new Date(day, month, year), hour, minute, second, dateTimeProperties[4].equalsIgnoreCase("am"));
        }

        return new DateTime(new Date(day, month, year), hour, minute, second, dateTimeProperties[3].equalsIgnoreCase("am"));
    }
}