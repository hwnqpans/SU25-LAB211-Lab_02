package Models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*hwnglesauveur*/
public class Guest implements Serializable {

    private String guestID;
    private String guestName;
    private String guestBirthDay;
    private String guestGender;
    private String guestPhoneNumber;
    private String guestRoomID;
    private String guestRentalDays;
    private String guestStartDate;
    private String guestCotenant;

    public Guest() {
    }

    public Guest(String guestID, String guestName, String guestBirthDay, String guestGender, String guestPhoneNumber, String guestRoomID, String guestRentalDays, String guestStartDate, String guestCotenant) {
        this.guestID = guestID;
        this.guestName = guestName;
        this.guestBirthDay = guestBirthDay;
        this.guestGender = guestGender;
        this.guestPhoneNumber = guestPhoneNumber;
        this.guestRoomID = guestRoomID;
        this.guestRentalDays = guestRentalDays;
        this.guestStartDate = guestStartDate;
        this.guestCotenant = guestCotenant;
    }

//    @Override
//    public String toString() {
//        return guestID + ", " + guestName + ", " + guestBirthDay + ", " + guestGender + ", "
//                + guestPhoneNumber + ", " + guestRoomID + ", " + guestRentalDays + ", "
//                + guestStartDate + ", " + guestCotenant;
//    }

    /*========================================================================*/
    public String getGuestID() {
        return guestID;
    }

    public void setGuestID(String guestID) {
        this.guestID = guestID;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestBirthDay() {
        return guestBirthDay;
    }

    public void setGuestBirthDay(String guestBirthDay) {
        this.guestBirthDay = guestBirthDay;
    }

    public String getGuestGender() {
        return guestGender;
    }

    public void setGuestGender(String guestGender) {
        this.guestGender = guestGender;
    }

    public String getGuestPhoneNumber() {
        return guestPhoneNumber;
    }

    public void setGuestPhoneNumber(String guestPhoneNumber) {
        this.guestPhoneNumber = guestPhoneNumber;
    }

    public String getGuestRoomID() {
        return guestRoomID;
    }

    public void setGuestRoomID(String guestRoomID) {
        this.guestRoomID = guestRoomID;
    }

    public String getGuestRentalDays() {
        return guestRentalDays;
    }

    public void setGuestRentalDays(String guestRentalDays) {
        this.guestRentalDays = guestRentalDays;
    }

    public String getGuestStartDate() {
        return guestStartDate;
    }

    public void setGuestStartDate(String guestStartDate) {
        this.guestStartDate = guestStartDate;
    }

    public String getGuestCotenant() {
        return guestCotenant;
    }

    public void setGuestCotenant(String guestCotenant) {
        this.guestCotenant = guestCotenant;
    }

    public LocalDate getStartDateAsLocalDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(this.guestStartDate, formatter);
    }

    public int getStayingDays() {
        return Integer.parseInt(this.guestRentalDays);
    }

    /*========================================================================*/

    @Override
    public String toString() {
        return "Guest{"
                + "guestID='" + guestID + '\''
                + ", guestName='" + guestName + '\''
                + ", guestBirthDay='" + guestBirthDay + '\''
                + ", guestGender='" + guestGender + '\''
                + ", guestPhoneNumber='" + guestPhoneNumber + '\''
                + ", guestRoomID='" + guestRoomID + '\''
                + ", guestRentalDays='" + guestRentalDays + '\''
                + ", guestStartDate='" + guestStartDate + '\''
                + ", guestCotenant='" + guestCotenant + '\''
                + '}';
    }

}
