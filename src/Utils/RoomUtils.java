package Utils;

import GUIs.MenuGUI;
import Models.Guest;
import Models.GuestList;
import Models.Room;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/*hwnglesauveur*/
public class RoomUtils {

    public static boolean isExistBefore(List<Room> rooms, String roomID) {
        if (rooms.isEmpty()) {
            return false;
        }

        for (Room idxRoom : rooms) {
            if (idxRoom.getRoomID().equals(roomID)) {
                return true;
            }
        }

        return false;
    }

    public static String calculateDate(String date, int duration) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date parsedDate = sdf.parse(date);

            Calendar cal = Calendar.getInstance();
            cal.setTime(parsedDate);
            cal.add(Calendar.DATE, duration);

            return sdf.format(cal.getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    public static void listVacantRoom(List<Room> rooms, GuestList guests) {
        MenuGUI.cls();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate desiredStartDate = LocalDate.now();
        int rentalDays = 1;
        LocalDate desiredEndDate = desiredStartDate.plusDays(rentalDays - 1);

        List<Room> vacantRooms = new ArrayList<>();

        for (Room room : rooms) {
            boolean isVacant = true;

            for (Guest guest : guests.getAllGuests()) {
                if (guest.getGuestRoomID().equalsIgnoreCase(room.getRoomID())) {
                    LocalDate guestStart = LocalDate.parse(guest.getGuestStartDate(), formatter);
                    LocalDate guestEnd = guestStart.plusDays(Integer.parseInt(guest.getGuestRentalDays()) - 1);

                    boolean overlap = !(desiredEndDate.isBefore(guestStart) || desiredStartDate.isAfter(guestEnd));
                    if (overlap) {
                        isVacant = false;
                        break;
                    }
                }
            }

            if (isVacant) {
                vacantRooms.add(room);
            }
        }

        if (!vacantRooms.isEmpty()) {
            System.out.println("\nAvailable Room List");
            System.out.println("RoomID | RoomName             | Type      | Rate   | Capacity | Furniture");
            System.out.println("-------+----------------+-----------+--------+----------+------------------------");

            for (Room room : vacantRooms) {
                System.out.printf(
                        "%-6s | %-22s | %-9s | %-6.1f | %-8d | %s\n",
                        room.getRoomID(),
                        room.getRoomName(),
                        room.getRoomType(),
                        room.getRoomDailyRate(),
                        room.getRoomCapacity(),
                        room.getRoomFurnitureDescription()
                );
            }
        } else {
            System.out.println("\n\u001B[31mAll rooms have currently been rented out; no rooms are available\u001B[0m");
        }
    }

    public static void monthlyRevenueReport(GuestList guests, List<Room> rooms) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        String inpDate = null;

        if (rooms.isEmpty()) {
            MenuGUI.cls();
            System.out.println("Room list is empty");
            return;
        }

        MenuGUI.cls();
        while (running == true) {
            try {
                System.out.println("Enter date for report (MM/yyyy): ");
                inpDate = sc.nextLine();
                if (Utils.StringHandlers.isValidMonthYear(inpDate) == true) {
                    running = false;
                } else {
                    MenuGUI.cls();
                    System.out.println("Invalid date!");
                }
            } catch (Exception e) {
                MenuGUI.cls();
                System.out.println("The entered date isn't valid");
            }
        }

        List<Guest> extractedGuests = guests.findGuestsByMonthYear(inpDate);
        Map<String, Double> roomAmounts = new HashMap<>();

        if (extractedGuests.isEmpty()) {
            MenuGUI.cls();
            System.out.println("There is no data on guests who have rented room");
            return;
        }

        for (Guest guest : extractedGuests) {
            String roomID = guest.getGuestRoomID();
            int rentalDays = Integer.parseInt(guest.getGuestRentalDays());

            Room correspondingRoom = null;
            for (Room room : rooms) {
                if (room.getRoomID().equals(roomID)) {
                    correspondingRoom = room;
                    break;
                }
            }

            if (correspondingRoom != null) {
                double dailyRate = correspondingRoom.getRoomDailyRate();
                double totalAmount = dailyRate * rentalDays;

                roomAmounts.put(roomID, roomAmounts.getOrDefault(roomID, 0.0) + totalAmount);
            }
        }

        MenuGUI.cls();
        System.out.println("Monthly Revenue Report - '" + inpDate + "'");
        System.out.println("---------------------------------------------------------------------");
        System.out.printf("%-8s | %-15s | %-10s | %-10s | %-10s%n",
                "RoomID", "Room Name", "Room type", "DailyRate", "Amount");
        System.out.println("---------------------------------------------------------------------");

        double totalRevenue = 0.0;

        List<String> sortedRoomIDs = new ArrayList<>(roomAmounts.keySet());
        Collections.sort(sortedRoomIDs);

        for (String roomID : sortedRoomIDs) {
            Room room = null;
            for (Room r : rooms) {
                if (r.getRoomID().equals(roomID)) {
                    room = r;
                    break;
                }
            }

            if (room != null) {
                double amount = roomAmounts.get(roomID);
                totalRevenue += amount;

                System.out.printf("%-8s | %-15s | %-10s | %-10.2f | %-10.2f%n",
                        room.getRoomID(),
                        room.getRoomName(),
                        room.getRoomType(),
                        room.getRoomDailyRate(),
                        amount);
            }
        }

        System.out.println("---------------------------------------------------------------------");
        System.out.printf("Total Revenue: %.2f%n", totalRevenue);
    }

    public static void revenueReportByRoomType(GuestList guests, List<Room> rooms) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        String targetRoomType = null;

        if (rooms.isEmpty()) {
            MenuGUI.cls();
            System.out.println("Room list is empty");
            return;
        }

        if (guests.isEmpty()) {
            MenuGUI.cls();
            System.out.println("Guest list is empty");
            return;
        }

        /*-----------------------------------------------------------------*/
        Set<String> availableRoomTypes = new HashSet<>();
        for (Room room : rooms) {
            availableRoomTypes.add(room.getRoomType());
        }

        MenuGUI.cls();
        System.out.println("Available room types: " + availableRoomTypes);

        while (running) {
            try {
                System.out.println("Enter target room type: ");
                targetRoomType = sc.nextLine().trim();

                if (targetRoomType.isEmpty()) {
                    MenuGUI.cls();
                    System.out.println("Room type cannot be empty!");
                    System.out.println("Available room types: " + availableRoomTypes);
                    continue;
                }

                boolean isValidRoomType = false;
                for (String roomType : availableRoomTypes) {
                    if (roomType.equalsIgnoreCase(targetRoomType)) {
                        targetRoomType = roomType;
                        isValidRoomType = true;
                        break;
                    }
                }

                if (isValidRoomType) {
                    running = false;
                } else {
                    MenuGUI.cls();
                    System.out.println("Invalid room type!");
                    System.out.println("Available room types: " + availableRoomTypes);
                }
            } catch (Exception e) {
                MenuGUI.cls();
                System.out.println("Invalid input!");
                System.out.println("Available room types: " + availableRoomTypes);
            }
        }

        Map<String, Double> roomTypeRevenue = new HashMap<>();

        List<Guest> allGuests = guests.getAllGuests();

        for (Guest guest : allGuests) {
            String roomID = guest.getGuestRoomID();
            int rentalDays = Integer.parseInt(guest.getGuestRentalDays());

            Room correspondingRoom = null;
            for (Room room : rooms) {
                if (room.getRoomID().equals(roomID)) {
                    correspondingRoom = room;
                    break;
                }
            }

            if (correspondingRoom != null) {
                String roomType = correspondingRoom.getRoomType();

                if (roomType.equalsIgnoreCase(targetRoomType)) {
                    double dailyRate = correspondingRoom.getRoomDailyRate();
                    double totalAmount = dailyRate * rentalDays;

                    roomTypeRevenue.put(roomType, roomTypeRevenue.getOrDefault(roomType, 0.0) + totalAmount);
                }
            }
        }

        if (roomTypeRevenue.isEmpty()) {
            MenuGUI.cls();
            System.out.println("No revenue data found for room type: " + targetRoomType);
            return;
        }

        MenuGUI.cls();
        System.out.println("Revenue Report by Room Type");
        System.out.println("---------------------------");
        System.out.printf("%-12s | %-12s%n", "Room type", "Amount");
        System.out.println("---------------------------");

        double totalRevenue = 0.0;

        for (Map.Entry<String, Double> entry : roomTypeRevenue.entrySet()) {
            String roomType = entry.getKey();
            double amount = entry.getValue();
            totalRevenue += amount;

            System.out.printf("%-12s | %,12.3f%n", roomType, amount);
        }

        System.out.println("---------------------------");
        System.out.printf("Total Revenue: %,12.3f%n", totalRevenue);
    }

}
