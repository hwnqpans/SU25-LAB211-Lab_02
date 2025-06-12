package Utils;

import static GUIs.MenuGUI.cls;
import Models.Guest;
import Models.GuestList;
import Models.Room;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/*hwnglesauveur*/
public class GuestUtils {

    public static void deleteGuestByID(GuestList guests, List<Room> rooms) {
        String guestID;
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running == true) {
            cls();
            System.out.println("Enter the guest's ID for removing: ");
            guestID = sc.nextLine();
            Guest inpGuest = guests.findById(guestID);
            if (inpGuest == null) {
                cls();
                System.out.println("Booking details for ID " + guestID + " could not be found");
                return;
            } else {
                if (GuestValidations.isValidStartDateForDeleting(inpGuest.getGuestStartDate()) == false) {
                    cls();
                    System.out.println("The room booking for this guest cannot be cancelled");
                    return;
                } else {
                    cls();
                    GUIs.OthersGUI.displayGuestInfoByIDwoInput(rooms, inpGuest);
                    System.out.print("Do you relly want to cancel this guest room booking? [y/n]: ");
                    sc = new Scanner(System.in);
                    String choice = sc.nextLine();
                    if (choice.equals("y") || choice.equals("Y")) {
                        cls();
                        guests.removeById(guestID);
                        System.out.println("The booking associated with ID " + guestID + " has been successfully cancelled");
                    } else {
                        cls();
                        System.out.println("Cool, cancelled booking incompleted");
                    }
                    System.out.println("Do you wanna delete more guest? [y/n]");
                    sc = new Scanner(System.in);
                    choice = sc.nextLine();
                    if (choice.equals("y") || choice.equals("Y")) {
                        continue;
                    } else {
                        cls();
                        return;
                    }
                }
            }
        }
    }

    public static void updateGuestInformation(GuestList guests, List<Room> rooms) {
        Scanner sc = new Scanner(System.in);

        cls();
        System.out.print("Enter guest's ID for updating: ");
        String guestID = sc.nextLine().trim();

        if (guestID.length() != 12) {
            System.out.println("Invalid guest ID format! ID must be 12 characters long.");
            System.out.println("Press Enter to continue...");
            sc.nextLine();
            return;
        }

        Guest inpGuest = guests.findById(guestID);
        if (inpGuest == null) {
            System.out.println("No guest found with ID: " + guestID);
            System.out.println("Press Enter to continue...");
            sc.nextLine();
            return;
        }

        cls();
        System.out.println("Current Guest Information:");
        System.out.println("═══════════════════════════");
        System.out.println("ID: " + inpGuest.getGuestID());
        System.out.println("Name: " + inpGuest.getGuestName());
        System.out.println("Birthday: " + inpGuest.getGuestBirthDay());
        System.out.println("Gender: " + inpGuest.getGuestGender());
        System.out.println("Phone: " + inpGuest.getGuestPhoneNumber());
        System.out.println("Room ID: " + inpGuest.getGuestRoomID());
        System.out.println("Rental Days: " + inpGuest.getGuestRentalDays());
        System.out.println("Start Date: " + inpGuest.getGuestStartDate());
        System.out.println("Co-tenant: " + inpGuest.getGuestCotenant());
        System.out.println("═══════════════════════════");
        System.out.println();

        boolean running = true;
        String choice = "";

        while (running) {
            System.out.println("What would you like to update?");
            System.out.println("1. Update guest's name");
            System.out.println("2. Update guest's birthday");
            System.out.println("3. Update guest's gender");
            System.out.println("4. Update guest's phone number");
            System.out.println("5. Update guest's desired room ID");
            System.out.println("6. Update guest's rental days");
            System.out.println("7. Update guest's start date");
            System.out.println("8. Update guest's co-tenant");
            System.out.println("0. Cancel update");
            System.out.print("Enter your choice: ");

            choice = sc.nextLine().trim();

            try {
                int num = Integer.parseInt(choice);
                if (num >= 0 && num <= 8) {
                    running = false;
                } else {
                    cls();
                    System.out.println("Invalid selection! Please enter a number between 0-8.");
                    System.out.println();
                }
            } catch (NumberFormatException e) {
                cls();
                System.out.println("Invalid input! Please enter a valid number.");
                System.out.println();
            }
        }

        if (choice.equals("0")) {
            System.out.println("Update cancelled.");
            System.out.println("Press Enter to continue...");
            sc.nextLine();
            return;
        }

        switch (choice) {
            case "1":
                updateGuestName(inpGuest, sc);
                break;

            case "2":
                updateGuestBirthday(inpGuest, sc);
                break;

            case "3":
                updateGuestGender(inpGuest, sc);
                break;

            case "4":
                updateGuestPhoneNumber(inpGuest, sc);
                break;

            case "5":
                updateGuestRoomID(inpGuest, guests, rooms, sc);
                break;

            case "6":
                updateGuestRentalDays(inpGuest, guests, rooms, sc);
                break;

            case "7":
                updateGuestStartDate(inpGuest, guests, rooms, sc);
                break;

            case "8":
                updateGuestCotenant(inpGuest, sc);
                break;

            default:
                System.out.println("Unexpected error occurred.");
                break;
        }

        System.out.println("Press Enter to continue...");
        sc.nextLine();
    }

    private static void updateGuestName(Guest guest, Scanner sc) {
        cls();
        System.out.println("Current name: " + guest.getGuestName());

        boolean validInput = false;
        while (!validInput) {
            System.out.print("Enter new guest's name (or 'cancel' to go back): ");
            String guestName = sc.nextLine().trim();

            if (guestName.equalsIgnoreCase("cancel")) {
                System.out.println("Name update cancelled.");
                return;
            }

            if (guestName.isEmpty()) {
                System.out.println("Name cannot be empty! Please try again.");
                continue;
            }

            if (GuestValidations.isValidGuestName(guestName)) {
                guest.setGuestName(guestName);
                System.out.println("Name updated successfully!");
                validInput = true;
            } else {
                System.out.println("Invalid name format! Please check the requirements and try again.");
            }
        }
    }

    private static void updateGuestBirthday(Guest guest, Scanner sc) {
        cls();
        System.out.println("Current birthday: " + guest.getGuestBirthDay());

        boolean validInput = false;
        while (!validInput) {
            System.out.print("Enter new birth day (dd/mm/yyyy) or 'cancel' to go back: ");
            String guestBirthDay = sc.nextLine().trim();

            if (guestBirthDay.equalsIgnoreCase("cancel")) {
                System.out.println("Birthday update cancelled.");
                return;
            }

            if (guestBirthDay.isEmpty()) {
                System.out.println("Birthday cannot be empty! Please try again.");
                continue;
            }

            if (GuestValidations.isValidDate(guestBirthDay)) {
                String oldBirthday = guest.getGuestBirthDay();
                guest.setGuestBirthDay(guestBirthDay);
                System.out.println("Birthday updated successfully from '" + oldBirthday + "' to '" + guestBirthDay + "'!");
                validInput = true;
            } else {
                System.out.println("Invalid date format! Please use dd/mm/yyyy format and ensure the date is valid.");
            }
        }
    }

    private static void updateGuestGender(Guest guest, Scanner sc) {
        cls();
        System.out.println("Current gender: " + guest.getGuestGender());

        boolean validInput = false;
        while (!validInput) {
            System.out.print("Enter new gender (male/female) or 'cancel' to go back: ");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("cancel")) {
                System.out.println("Gender update cancelled.");
                return;
            }

            if (input.isEmpty()) {
                System.out.println("Gender cannot be empty! Please enter 'male' or 'female'.");
                continue;
            }

            String guestGender = StringHandlers.genderCorrector(input);

            if (GuestValidations.isValidGender(guestGender)) {
                String oldGender = guest.getGuestGender();
                guest.setGuestGender(guestGender);
                System.out.println("Gender updated successfully from '" + oldGender + "' to '" + guestGender + "'!");
                validInput = true;
            } else {
                System.out.println("Invalid gender! Please enter 'male' or 'female'.");
            }
        }
    }

    private static void updateGuestPhoneNumber(Guest guest, Scanner sc) {
        cls();
        System.out.println("Current phone: " + guest.getGuestPhoneNumber());

        boolean validInput = false;
        while (!validInput) {
            System.out.print("Enter new phone number (start with 84 and follow by 9 digits) or 'cancel' to go back: ");
            String guestPhoneNumber = sc.nextLine().trim();

            if (guestPhoneNumber.equalsIgnoreCase("cancel")) {
                System.out.println("Phone number update cancelled.");
                return;
            }

            if (guestPhoneNumber.isEmpty()) {
                System.out.println("Phone number cannot be empty! Please try again.");
                continue;
            }

            if (GuestValidations.isValidPhoneNumber(guestPhoneNumber)) {
                String oldPhone = guest.getGuestPhoneNumber();
                guest.setGuestPhoneNumber(guestPhoneNumber);
                System.out.println("Phone number updated successfully from '" + oldPhone + "' to '" + guestPhoneNumber + "'!");
                validInput = true;
            } else {
                System.out.println("Invalid phone number format! Must start with 84 followed by 9 digits.");
            }
        }
    }

    private static void updateGuestRoomID(Guest guest, GuestList guests, List<Room> rooms, Scanner sc) {
        cls();
        System.out.println("Current room ID: " + guest.getGuestRoomID());

        boolean validInput = false;
        while (!validInput) {
            System.out.print("Enter new desired room ID or 'cancel' to go back: ");
            String newRoomID = sc.nextLine().trim().toUpperCase();

            if (newRoomID.equalsIgnoreCase("CANCEL")) {
                System.out.println("Room ID update cancelled.");
                return;
            }

            if (newRoomID.isEmpty()) {
                System.out.println("Room ID cannot be empty! Please try again.");
                continue;
            }

            if (!GuestValidations.isValidRoomID(rooms, newRoomID)) {
                System.out.println("Room ID '" + newRoomID + "' does not exist!");
                showAvailableRooms(rooms);
                continue;
            }

            // Check for booking conflicts with new room
            if (hasBookingConflict(guests, newRoomID, guest.getGuestStartDate(),
                    guest.getGuestRentalDays(), guest.getGuestID())) {
                System.out.println("Room booking conflict detected!");
                System.out.println("Room '" + newRoomID + "' is already booked during your requested period.");
                System.out.print("Do you want to see available rooms for your dates? (y/n): ");

                String response = sc.nextLine().trim().toLowerCase();
                if (response.startsWith("y")) {
                    showAvailableRoomsForPeriod(rooms, guests, guest.getGuestStartDate(),
                            guest.getGuestRentalDays(), guest.getGuestID());
                }
                continue;
            }

            String oldRoomID = guest.getGuestRoomID();
            guest.setGuestRoomID(newRoomID);
            System.out.println("Room ID updated successfully from '" + oldRoomID + "' to '" + newRoomID + "'!");
            validInput = true;
        }
    }

    private static void updateGuestRentalDays(Guest guest, GuestList guests, List<Room> rooms, Scanner sc) {
        cls();
        System.out.println("Current rental days: " + guest.getGuestRentalDays());

        boolean validInput = false;
        while (!validInput) {
            System.out.print("Enter new rental days or 'cancel' to go back: ");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("cancel")) {
                System.out.println("Rental days update cancelled.");
                return;
            }

            if (input.isEmpty()) {
                System.out.println("Rental days cannot be empty! Please try again.");
                continue;
            }

            try {
                int days = Integer.parseInt(input);
                if (days < 1) {
                    System.out.println("Rental days must be at least 1! Please try again.");
                    continue;
                }

                // Check for booking conflicts with new rental period
                if (hasBookingConflict(guests, guest.getGuestRoomID(), guest.getGuestStartDate(),
                        input, guest.getGuestID())) {
                    System.out.println("Booking conflict detected!");
                    System.out.println("Room '" + guest.getGuestRoomID() + "' is already booked by another guest during the extended period.");
                    System.out.println("Current booking allows maximum " + calculateMaxAvailableDays(guests, guest) + " days from your start date.");
                    continue;
                }

                String oldDays = guest.getGuestRentalDays();
                guest.setGuestRentalDays(input);
                System.out.println("Rental days updated successfully from " + oldDays + " to " + input + " days!");
                validInput = true;

            } catch (NumberFormatException e) {
                System.out.println("Invalid number format! Please enter a valid number.");
            }
        }
    }

    private static void updateGuestStartDate(Guest guest, GuestList guests, List<Room> rooms, Scanner sc) {
        cls();
        System.out.println("Current start date: " + guest.getGuestStartDate());

        boolean validInput = false;
        while (!validInput) {
            System.out.print("Enter new start date (dd/mm/yyyy) or 'cancel' to go back: ");
            String guestStartDate = sc.nextLine().trim();

            if (guestStartDate.equalsIgnoreCase("cancel")) {
                System.out.println("Start date update cancelled.");
                return;
            }

            if (guestStartDate.isEmpty()) {
                System.out.println("Start date cannot be empty! Please try again.");
                continue;
            }

            if (!GuestValidations.isValidStartDate(guestStartDate)) {
                System.out.println("Invalid start date! Date must be in the future and in dd/mm/yyyy format.");
                continue;
            }

            // Check for booking conflicts with new start date
            if (hasBookingConflict(guests, guest.getGuestRoomID(), guestStartDate,
                    guest.getGuestRentalDays(), guest.getGuestID())) {
                System.out.println("Booking conflict detected!");
                System.out.println("Room '" + guest.getGuestRoomID() + "' is already booked by another guest during this period.");
                suggestAvailableDates(guests, guest.getGuestRoomID(), guest.getGuestRentalDays(), guest.getGuestID());
                continue;
            }

            String oldStartDate = guest.getGuestStartDate();
            guest.setGuestStartDate(guestStartDate);
            System.out.println("Start date updated successfully from '" + oldStartDate + "' to '" + guestStartDate + "'!");
            validInput = true;
        }
    }

    private static void updateGuestCotenant(Guest guest, Scanner sc) {
        cls();
        System.out.println("Current co-tenant: " + guest.getGuestCotenant());

        System.out.print("Enter new co-tenant (press Enter for no co-tenant, or 'cancel' to go back): ");
        String input = sc.nextLine().trim();

        if (input.equalsIgnoreCase("cancel")) {
            System.out.println("Co-tenant update cancelled.");
            return;
        }

        String guestCotenant = input.isEmpty() ? "null" : input;
        String oldCotenant = guest.getGuestCotenant();
        guest.setGuestCotenant(guestCotenant);
        System.out.println("Co-tenant updated successfully from '" + oldCotenant + "' to '" + guestCotenant + "'!");
    }

// Helper method to check booking conflicts
    private static boolean hasBookingConflict(GuestList guests, String roomID, String startDate,
            String rentalDays, String excludeGuestID) {
        List<Guest> allGuests = guests.getAllGuests();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date newStart = sdf.parse(startDate);
            int newDays = Integer.parseInt(rentalDays);
            Date newEnd = new Date(newStart.getTime() + (newDays * 24 * 60 * 60 * 1000L));

            for (Guest existingGuest : allGuests) {
                // Skip the guest being updated
                if (existingGuest.getGuestID().equals(excludeGuestID)) {
                    continue;
                }

                // Check if same room
                if (!existingGuest.getGuestRoomID().equals(roomID)) {
                    continue;
                }

                Date existingStart = sdf.parse(existingGuest.getGuestStartDate());
                int existingDays = Integer.parseInt(existingGuest.getGuestRentalDays());
                Date existingEnd = new Date(existingStart.getTime() + (existingDays * 24 * 60 * 60 * 1000L));

                // Check for overlap
                if (newStart.before(existingEnd) && newEnd.after(existingStart)) {
                    return true; // Conflict detected
                }
            }

            return false; // No conflict

        } catch (NumberFormatException | ParseException e) {
            System.out.println("⚠ Error checking booking conflicts: " + e.getMessage());
            return true; // Assume conflict to be safe
        }
    }

// Helper method to show all available rooms
    private static void showAvailableRooms(List<Room> rooms) {
        System.out.println("\nAvailable rooms:");
        System.out.println("----------------------------------------");
        for (Room room : rooms) {
            System.out.printf("%-8s | %-15s | %-10s | $%.2f/day%n",
                    room.getRoomID(), room.getRoomName(),
                    room.getRoomType(), room.getRoomDailyRate());
        }
        System.out.println("----------------------------------------");
    }

// Helper method to show available rooms for specific period
    private static void showAvailableRoomsForPeriod(List<Room> rooms, GuestList guests, String startDate,
            String rentalDays, String excludeGuestID) {
        System.out.println("\nAvailable rooms for period " + startDate + " (" + rentalDays + " days):");
        System.out.println("---------------------------------------------------------------");

        boolean hasAvailable = false;
        for (Room room : rooms) {
            if (!hasBookingConflict(guests, room.getRoomID(), startDate, rentalDays, excludeGuestID)) {
                System.out.printf("%-8s | %-15s | %-10s | $%.2f/day%n",
                        room.getRoomID(), room.getRoomName(),
                        room.getRoomType(), room.getRoomDailyRate());
                hasAvailable = true;
            }
        }

        if (!hasAvailable) {
            System.out.println("No rooms available for the selected period.");
        }
        System.out.println("---------------------------------------------------------------");
    }

// Helper method to calculate maximum available days
    private static int calculateMaxAvailableDays(GuestList guests, Guest currentGuest) {
        // Implementation would calculate the maximum days available from start date
        // before hitting another booking conflict
        return 30; // Placeholder - implement based on your requirements
    }

// Helper method to suggest available dates
    private static void suggestAvailableDates(GuestList guests, String roomID, String rentalDays, String excludeGuestID) {
        System.out.println("\n💡 Tip: Try checking available dates for room " + roomID);
        System.out.println("Consider adjusting your dates to avoid conflicts.");
    }

}
