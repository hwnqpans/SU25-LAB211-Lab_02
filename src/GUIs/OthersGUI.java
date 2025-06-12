package GUIs;

import Models.Guest;
import Models.GuestList;
import Models.Room;
import java.util.List;
import java.util.Scanner;

/*hwnglesauveur*/
public class OthersGUI {

    public static void displayGuestInfoByID(GuestList guests, List<Room> rooms) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running == true) {
            MenuGUI.cls();
            System.out.println("Enter guest's ID for searching: ");
            String guestID = sc.nextLine();
            String choice;

            Guest guest = guests.findById(guestID);

            if (guest == null) {
                MenuGUI.cls();
                System.out.println("No guest found with the requested ID: " + guestID);
            } else {
                Room inpRoom = new Room();
                for (Room idxRoom : rooms) {
                    if (idxRoom.getRoomID().equals(guest.getGuestRoomID())) {
                        inpRoom = idxRoom;
                    }
                }

                MenuGUI.cls();
                String line = "-----------------------------------------------------------";
                System.out.println(line);
                System.out.println("Guest information [National ID: " + guest.getGuestID() + "]");
                System.out.println(line);
                System.out.println(" Full name     : " + guest.getGuestName());
                System.out.println(" Phone number  : " + guest.getGuestPhoneNumber());
                System.out.println(" Birth day     : " + guest.getGuestBirthDay());
                System.out.println(" Gender        : " + guest.getGuestGender());
                System.out.println(line);
                System.out.println(" Rental room   : " + guest.getGuestRoomID());
                System.out.println(" Checkin       : " + guest.getGuestStartDate());
                System.out.println(" Rental days   : " + guest.getGuestRentalDays());
                System.out.println(" Check out     : " + Utils.RoomUtils.calculateDate(guest.getGuestStartDate(), Integer.parseInt(guest.getGuestRentalDays())));
                System.out.println(line);
                System.out.println("Room information:");
                System.out.println(" + ID          : " + inpRoom.getRoomID());
                System.out.println(" + Room        : " + inpRoom.getRoomName());
                System.out.println(" + Type        : " + inpRoom.getRoomType());
                System.out.printf(" + Daily rate  : %.0f%n", inpRoom.getRoomDailyRate());
                System.out.println(" + Capacity    : " + inpRoom.getRoomCapacity());
                System.out.println(" + Funiture    : " + inpRoom.getRoomFurnitureDescription());
            }

            System.out.println("Do you wanna search more? (y/n)");
            choice = sc.nextLine();

            if (choice.equals("n")) {
                MenuGUI.cls();
                return;
            }
        }
    }

    public static void displayGuestInfoByIDwoInput(List<Room> rooms, Guest guest) {

        Room inpRoom = new Room();
        for (Room idxRoom : rooms) {
            if (idxRoom.getRoomID().equals(guest.getGuestRoomID())) {
                inpRoom = idxRoom;
            }
        }

        MenuGUI.cls();
        String line = "-----------------------------------------------------------";
        System.out.println(line);
        System.out.println("Guest information [National ID: " + guest.getGuestID() + "]");
        System.out.println(line);
        System.out.println(" Full name     : " + guest.getGuestName());
        System.out.println(" Phone number  : " + guest.getGuestPhoneNumber());
        System.out.println(" Birth day     : " + guest.getGuestBirthDay());
        System.out.println(" Gender        : " + guest.getGuestGender());
        System.out.println(line);
        System.out.println(" Rental room   : " + guest.getGuestRoomID());
        System.out.println(" Checkin       : " + guest.getGuestStartDate());
        System.out.println(" Rental days   : " + guest.getGuestRentalDays());
        System.out.println(" Check out     : " + Utils.RoomUtils.calculateDate(guest.getGuestStartDate(), Integer.parseInt(guest.getGuestRentalDays())));
        System.out.println(line);
        System.out.println("Room information:");
        System.out.println(" + ID          : " + inpRoom.getRoomID());
        System.out.println(" + Room        : " + inpRoom.getRoomName());
        System.out.println(" + Type        : " + inpRoom.getRoomType());
        System.out.printf(" + Daily rate  : %.0f%n", inpRoom.getRoomDailyRate());
        System.out.println(" + Capacity    : " + inpRoom.getRoomCapacity());
        System.out.println(" + Funiture    : " + inpRoom.getRoomFurnitureDescription());
        System.out.println(line);
    }

}