package util;

public class Date implements Comparable<Date>{
    private int day;
    private int month;
    private int year;
    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", month, day, year);
    }
    public int getDay() {
        return day;
    }
    public int getMonth() {
        return month;
    }
    public int getYear() {
        return year;
    }
    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
    @Override
    public int compareTo(Date other) {
        if(this.year != other.year)
            return this.year - other.year;//new Integer(year).compareTo(other.year);
        if(this.month != other.month)
            return this.month - other.month;
        return this.day - other.day;
    }
    public static boolean isValidDate(Date date){
        int day = date.day, month = date.month, year = date.year;
        if(year < 1 || day < 1 || month < 1 || month > 12)
            return false;
        switch (month){
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return day <= 31;
            case 4: case 6: case 9: case 11:
                return day <= 30;
            default://February
                if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0)//leap year
                    return day <=29;
                else
                    return day <= 28;
        }
    }
}
