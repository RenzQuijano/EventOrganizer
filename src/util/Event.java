package util;

public class Event implements Comparable<Event>{

    private String name;

    private DateTime start;

    private DateTime end;

    private String host;

    private int numOfInvitee;

    private String location;

    public Event(String name, DateTime start, DateTime end, String host, int numOfInvitee, String location) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.host = host;
        this.numOfInvitee = numOfInvitee;
        this.location = location;
    }

    @Override
    public int compareTo(Event other) {
        if(this.start.compareTo(other.start) != 0 ) {
            return this.start.compareTo(other.start);
        }
        if(this.end.compareTo(other.end) != 0) {
            return other.end.compareTo(this.end); //switched these around to get the right sort that is asked in assignment
        }
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return String.format("Event: %s, from %s to %s @ %s, number of invitees %d, hosted by %s", name, start, end, location, numOfInvitee, host);
    }

    //getters are used for command "happening on"
    public DateTime getStart() {
        return start;
    }

    public DateTime getEnd() {
        return end;
    }
    //this getter is used for "hosted by" command
    public String getHost() {
        return host;
    }
}
