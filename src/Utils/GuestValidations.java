package Utils;

import Models.GuestList;
import Models.Room;
import Models.RoomList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*hwnglesauveur*/
public class GuestValidations {

    public static boolean isValidGuestID(GuestList guestList, String guestID) {
        if (guestID.length() == 12 && guestList.findById(guestID) == null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidGuestName(String guestName) {
        if (Character.isAlphabetic(guestName.charAt(0)) && guestName.length() >= 2 && guestName.length() <= 25) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidDate(String guestBirthDay) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        try {
            sdf.parse(guestBirthDay);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isValidGender(String guestGender) {
        if (guestGender.toLowerCase().equals("male") || guestGender.toLowerCase().equals("female")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidPhoneNumber(String guestPhoneNumber) {
        if (guestPhoneNumber.length() == 11 && guestPhoneNumber.matches("^84\\d{9}$")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidRoomID(RoomList rooms, String guestRoomID) {
        boolean isInRoomList = false;
        outer: for (Room idxRoom : rooms) {
            if (idxRoom.getRoomID().equals(guestRoomID)) {
                isInRoomList = true;
                break outer;
            }
        }

        if (guestRoomID.matches("^[A-Z]\\d{1,4}$") && isInRoomList == true) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidStartDate(String guestStartDate) {
        if (guestStartDate == null || guestStartDate.trim().isEmpty()) {
            return false;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);

            Date inputDate = sdf.parse(guestStartDate);

            String todayStr = sdf.format(new Date());
            Date today = sdf.parse(todayStr);

            return !inputDate.before(today); // input >= today
        } catch (ParseException e) {
            return false;
        }
    }
    
    public static boolean isValidStartDateForDeleting(String guestStartDate) {
        if (guestStartDate == null || guestStartDate.trim().isEmpty()) {
            return false;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);

            Date inputDate = sdf.parse(guestStartDate);

            String todayStr = sdf.format(new Date());
            Date today = sdf.parse(todayStr);

            return inputDate.after(today); // input > today
        } catch (ParseException e) {
            return false;
        }
    }

}
