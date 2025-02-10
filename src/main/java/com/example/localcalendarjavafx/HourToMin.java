public class HourToMin {
    public static int convertToMin(int hhmm){
        int hours = hhmm / 100;
        int minutes = hhmm % 100;

        //validation of input
        if( hours <0 || hours > 23 || minutes < 0 || minutes > 59){
            throw new IllegalArgumentException("Invalid time format");
        }

        return hours * 60 + minutes;
    }
}
