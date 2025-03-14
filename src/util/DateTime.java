package util;

public class DateTime implements Comparable<DateTime>{
    private Date date;
    private int hour;
    private int minute;
    private int second;
    private boolean am;

    public DateTime(Date date, int hour, int minute, int second, boolean am) {
        this.date = date;
        this.hour = hour == 12? 0:hour;
        this.minute = minute;
        this.second = second;
        this.am = am;
    }

    @Override
    public String toString() {
        return String.format("%s @ %02d:%02d:%02d %s", date, hour == 0?12:hour,
                minute, second, am? "am": "pm");
    }
    public static boolean isValidDateTime(DateTime dateTime){
        Date date = dateTime.date;
        int hour = dateTime.hour, minute = dateTime.minute, second = dateTime.second;
        return Date.isValidDate(date) &&
                hour >= 0 && minute >= 0 && second >= 0 &&
                hour < 13 && minute < 60 && second < 60;
    }
    @Override
    public int compareTo(DateTime other) {
        if(this.date.compareTo(other.date) != 0)
            return this.date.compareTo(other.date);
        if(this.am != other.am)
            return this.am?-1:1;
        if(this.hour != other.hour)
            return this.hour - other.hour;
        if(this.minute != other.minute)
            return this.minute - other.minute;
        return this.second - other.second;
    }
}
