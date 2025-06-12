package Models;

/*hwnglesauveur*/
public class Room {
    
    private String roomID;
    private String roomName;
    private String roomType;
    private double roomDailyRate;
    private int roomCapacity;
    private String roomFurnitureDescription;

    public Room() {
    }

    public Room(String roomID, String roomName, String roomType, double roomDailyRate, int roomCapacity, String roomFurnitureDescription) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.roomType = roomType;
        this.roomDailyRate = roomDailyRate;
        this.roomCapacity = roomCapacity;
        this.roomFurnitureDescription = roomFurnitureDescription;
    }
    

    @Override
    public String toString() {
        return roomID + ", " + roomName + ", " + roomType + ", " + roomDailyRate + ", " + 
               roomCapacity + ", " + roomFurnitureDescription;
    }
    
    
    
    /*--------------------------------------------------------------------------
    ----------------------------------------------------------------------------*/
    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getRoomDailyRate() {
        return roomDailyRate;
    }

    public void setRoomDailyRate(double roomDailyRate) {
        this.roomDailyRate = roomDailyRate;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    public String getRoomFurnitureDescription() {
        return roomFurnitureDescription;
    }

    public void setRoomFurnitureDescription(String roomFurnitureDescription) {
        this.roomFurnitureDescription = roomFurnitureDescription;
    }
    
    /*--------------------------------------------------------------------------
    ----------------------------------------------------------------------------*/

}
