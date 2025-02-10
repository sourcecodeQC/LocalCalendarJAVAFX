import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CalendarManager calendarManager = new CalendarManager();
        Scanner scx = new Scanner(System.in);
        String command;

        System.out.println("Local Calendar Manager");
        System.out.println("Available commands: add, list, save, delete, delete all, exit");

        while (true) {
            System.out.print("Enter command: ");
            command = scx.nextLine();

            switch (command.toLowerCase()) {
                case "add":
                    System.out.print("Enter event title: ");
                    String title = scx.nextLine();
                    System.out.print("Enter event date (e.g., 2023-12-01): ");
                    String date = scx.nextLine();
                    System.out.print("Enter start time (HHMM): ");
                    int startHHMM = scx.nextInt();
                    System.out.print("Enter end time (HHMM): ");
                    int endHHMM = scx.nextInt();
                    System.out.print("Enter priority (integer): ");
                    int priority = scx.nextInt();
                    scx.nextLine();
                    calendarManager.addEvent(title, date, startHHMM, endHHMM, priority);
                    break;

                case "list":
                    calendarManager.listEvents();
                    break;

                case "save":
                    System.out.println("Saving ..."); // i did not know how to italize output
                    calendarManager.saveEvents();
                    break;

                case "delete":
                    System.out.print("Enter event title to delete: ");
                    String titleToDelete = scx.nextLine();
                    calendarManager.deleteEvent(titleToDelete);
                    break;

                case "delete all":
                    if (confirmDeleteAll(scx)) {
                        calendarManager.deleteAllEvents();
                    }

                    else {
                        System.out.println("Aborted");
                    }
                    break;

                case "exit":
                    calendarManager.saveEvents(); // Save events before exiting
                    System.out.println("Exiting & Saving.");
                    scx.close();
                    return;

                default:
                    System.out.println("Unknown command. Please try again.");
            }
        }
    }

    private static boolean confirmDeleteAll(Scanner scx) {
        System.out.println("Are you sure you want to delete all events? Y/N?: ");
        String confirmation1 = scx.nextLine();
        if (!confirmation1.equalsIgnoreCase("Y")) return false;

        System.out.println("This action cannot be undone - THERE IS NO ROLLBACK. Y/N?: ");
        String confirmation2 = scx.nextLine();
        if (!confirmation2.equalsIgnoreCase("Y")) return false;

        System.out.println("Final confirmation. Y/N?: ");
        String confirmation3 = scx.nextLine();
        return confirmation3.equalsIgnoreCase("Y");
    }
}