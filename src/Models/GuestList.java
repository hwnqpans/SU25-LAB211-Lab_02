package Models;

import Utils.GuestValidations;
import Utils.StringHandlers;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*hwnglesauveur*/
public class GuestList implements Serializable {

    private Node head;
    private String filePath;

    public GuestList() {
        this.head = null;
    }
    
    public boolean isEmpty() {
        return head == null;
    }

    public void clear() {
        this.head = null;
    }
    
    /*--------------------------------------------------------------------------
    ----------------------------------------------------------------------------*/
    public void addGuest(Guest guest) {
        Node newNode = new Node(guest);

        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    /*--------------------------------------------------------------------------
    ----------------------------------------------------------------------------*/
    public boolean removeById(String id) {
        if (head == null) {
            return false;
        }

        if (head.info.getGuestID().equalsIgnoreCase(id)) {
            head = head.next;
            return true;
        }

        Node prev = head;
        Node curr = head.next;

        while (curr != null) {
            if (curr.info.getGuestID().equalsIgnoreCase(id)) {
                prev.next = curr.next;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }

        return false;
    }

    /*--------------------------------------------------------------------------
    ----------------------------------------------------------------------------*/
    public Guest findById(String id) {
        Node temp = head;
        while (temp != null) {
            if (temp.info.getGuestID().equalsIgnoreCase(id)) {
                return temp.info;
            }
            temp = temp.next;
        }
        return null;
    }

    /*--------------------------------------------------------------------------
    ----------------------------------------------------------------------------*/
    public void traverse() {
        Node curr = head;
        while (curr != null) {
            System.out.println(curr.info.toString());
            curr = curr.next;
        }
    }

    /*--------------------------------------------------------------------------
    ----------------------------------------------------------------------------*/
    public List<Guest> findGuestsByMonthYear(String inputMonthYear) {
        List<Guest> result = new ArrayList<>();
        Node current = head;

        while (current != null) {
            try {
                String dateStr = current.info.getGuestStartDate();
                if (Utils.StringHandlers.isDateInMonthYear(dateStr, inputMonthYear) == true) {
                    result.add(current.info);
                }
            } catch (DateTimeParseException e) {
                System.out.println("Error while finding month year in GuestList");
            }

            current = current.next;
        }

        return result;
    }

    /*--------------------------------------------------------------------------
    ----------------------------------------------------------------------------*/
    public List<Guest> getAllGuests() {
        List<Guest> result = new ArrayList<>();
        Node current = head;

        while (current != null) {
            result.add(current.info);
            current = current.next;
        }

        return result;
    }

    /*--------------------------------------------------------------------------
    ----------------------------------------------------------------------------*/
    public void enterGuest(RoomList rooms) {
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
            if (GuestValidations.isValidGuestID(this, guestID)
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

                for (Guest g : this.getAllGuests()) {
                    if (g.getGuestRoomID().equalsIgnoreCase(guestRoomID)) {
                        LocalDate existingStart = LocalDate.parse(g.getGuestStartDate(), formatter);
                        LocalDate existingEnd = existingStart.plusDays(Integer.parseInt(g.getGuestRentalDays()) - 1);

                        boolean isOverlap = !(endDate.isBefore(existingStart) || startDate.isAfter(existingEnd));
                        if (isOverlap) {
                            System.out.println("This room has already been booked during this period.");
                            return;
                        }
                    }
                }

            Guest inpGuest = new Guest(guestID, guestName, guestBirthDay, guestGender,
                                       guestPhoneNumber, guestRoomID, guestRentalDays, guestStartDate, guestCotenant);
            } else {
                return;
            }
        } catch (NumberFormatException | DateTimeParseException e) {
            System.out.println("Invalid input format.");
        }
    }
    

    /*--------------------------------------------------------------------------
    ----------------------------------------------------------------------------*/
    public void readGuestFile() {
        
    }
    
    public void saveToFile() {
        FileOutputStream fis = null;
        ObjectOutputStream oos = null;

        try {
            File file = new File(filePath);

            fis = new FileOutputStream(file);
            oos = new ObjectOutputStream(fis);

            oos.writeObject(this);

            System.out.println("Saved guests information !");

        } catch (FileNotFoundException e) {
            System.err.println("Cannot access or file doesnt exist");
            System.err.println(e.getMessage());

        } catch (IOException e) {
            System.err.println("Cannot save file");
            System.err.println(e.getMessage());

        } catch (Exception e) {
            System.err.println("Some errors have been occerred");
            System.err.println(e.getMessage());
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

}
