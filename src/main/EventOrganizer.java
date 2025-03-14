package main;
import util.DateTime;
import java.util.*;


public class EventOrganizer {

    private static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Please enter the list of all events. Enter \"Done\" when finished");

        while(true) {
            String givenEvent = keyboard.nextLine();
            String [] eventProperties = givenEvent.split( ",");
            String eventName = eventProperties[0];
            String startDateTimeString = eventProperties[1];
            String endDateTimeString = eventProperties[2];
            String hostName = eventProperties[3];
            String numOfInviteesString = eventProperties[4];
            String location = eventProperties[5];

            //exception handling here for inputs



        }

    }
}