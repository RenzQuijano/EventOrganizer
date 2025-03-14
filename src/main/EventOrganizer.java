package main;
import util.DateTime;
import util.Event;

import java.util.*;


public class EventOrganizer {

    private static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {

        ArrayList<Event> list = new ArrayList<>();

        System.out.println("Please enter the list of all events. Enter \"Done\" when finished");

        //input handling
        while(true) {
            String givenEvent = keyboard.nextLine();
            if (givenEvent.equalsIgnoreCase("done")) {
                break;
            }
            String [] eventProperties = givenEvent.split( ",");
            if (eventProperties.length != 6) {
                System.out.println("Illegal event entry. Please try again");
                continue;
            }

            String eventName = eventProperties[0];
            String startDateTimeString = eventProperties[1];
            String endDateTimeString = eventProperties[2];
            String hostName = eventProperties[3];
            String numOfInviteesString = eventProperties[4];
            String location = eventProperties[5];

            //split up date and time



            Event event = new Event();
            list.add(event);

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
}