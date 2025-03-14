package util;

public class DateTimeInvalidException extends Exception{

    private DateTime invalidDateTime;

    public DateTimeInvalidException (DateTime invalidDateTime) {
        this.invalidDateTime = invalidDateTime;
    }

    @Override
    public String toString(){
        return "Illegal Date/Time " + invalidDateTime + ".";
    }
}
