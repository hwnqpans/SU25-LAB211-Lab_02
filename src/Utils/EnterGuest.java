package Utils;

import Models.Guest;
import Models.GuestList;
import Models.Room;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/*hwnglesauveur*/
public class EnterGuest {

    public static Guest enterGuest(GuestList guestList, List<Room> rooms) {
        Scanner sc = new Scanner(System.in);

        String guestID;
        String guestName;
        String guestBirthDay;
        String guestGender;
        String guestPhoneNumber;
        String guestRoomID;
        String guestRentalDays;
        String guestStartDate;
        String guestCotenant = "null";

        System.out.print("Enter guest's national ID number: ");
        guestID = sc.nextLine();
        System.out.print("Enter guest's full name: ");
        guestName = sc.nextLine();
        System.out.print("Enter guest's birth date: ");
        guestBirthDay = sc.nextLine();
        System.out.print("Enter guest's gender (male/female): ");
        guestGender = sc.nextLine();
        guestGender = StringHandlers.genderCorrector(guestGender);
        System.out.print("Enter guest's phone number (Must begin with 84 and follow by 9 digits): ");
        guestPhoneNumber = sc.nextLine();
        System.out.print("Enter guest's desired room ID: ");
        guestRoomID = sc.nextLine().toUpperCase();
        System.out.print("Enter guest's number of rental days: ");
        guestRentalDays = sc.nextLine();
        System.out.print("Enter start date (Must be in future): ");
        guestStartDate = sc.nextLine();
        System.out.print("Enter name of co-tenant (Optional, you can press enter to skip): ");
        guestCotenant = sc.nextLine();

        try {
            if (GuestValidations.isValidGuestID(guestList, guestID)
                    && GuestValidations.isValidGuestName(guestName)
                    && GuestValidations.isValidDate(guestBirthDay)
                    && GuestValidations.isValidGender(guestGender)
                    && GuestValidations.isValidPhoneNumber(guestPhoneNumber)
                    && GuestValidations.isValidRoomID(rooms, guestRoomID)
                    && Integer.parseInt(guestRentalDays) >= 1
                    && GuestValidations.isValidStartDate(guestStartDate)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate startDate = LocalDate.parse(guestStartDate, formatter);
                LocalDate endDate = startDate.plusDays(Integer.parseInt(guestRentalDays) - 1);

                for (Guest g : guestList.getAllGuests()) {
                    if (g.getGuestRoomID().equalsIgnoreCase(guestRoomID)) {
                        LocalDate existingStart = LocalDate.parse(g.getGuestStartDate(), formatter);
                        LocalDate existingEnd = existingStart.plusDays(Integer.parseInt(g.getGuestRentalDays()) - 1);

                        boolean isOverlap = !(endDate.isBefore(existingStart) || startDate.isAfter(existingEnd));
                        if (isOverlap) {
                            System.out.println("This room has already been booked during this period.");
                            return null;
                        }
                    }
                }

                return new Guest(
                        guestID, guestName, guestBirthDay, guestGender, guestPhoneNumber,
                        guestRoomID, guestRentalDays, guestStartDate, guestCotenant
                );
            } else {
                return null;
            }
        } catch (NumberFormatException | DateTimeParseException e) {
            System.out.println("Invalid input format.");
            return null;
        } catch (Exception e) {
            System.out.println("An error occurred.");
            return null;
        }
    }

}
