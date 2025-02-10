public class MinToHHMM {

    // minutes from midnight to HHMM
    public static int convertToHHMM(int minutes) {

        int hours = minutes / 60;
        int remainingMinutes = minutes % 60;

        return hours * 100 + remainingMinutes;
    }
}