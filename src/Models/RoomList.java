package Models;

import static GUIs.MenuGUI.cls;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*hwnglesauveur*/
public class RoomList {

    private List<Room> roomList = new ArrayList<>();
    private String filePath;
    
    public RoomList(String filePath) {
        this.filePath = filePath;
    }
    /*--------------------------------------------------------------------------
    ----------------------------------------------------------------------------*/
    public void addRoom(Room inpRoom) {
        roomList.add(inpRoom);
    }

    /*--------------------------------------------------------------------------
    ----------------------------------------------------------------------------*/
    public void displayAvailableRoomList() {
        if (roomList.isEmpty()) {
            System.out.println("Room list is currently empty, not loaded yet.");
            return;
        } else {
            System.out.printf("%-6s | %-18s | %-10s | %-8s | %-8s | %-30s%n",
                    "RoomID", "RoomName", "Type", "Rate", "Capacity", "Furniture");
            System.out.println("--------------------------------------------------------------------------"
                    + "------------------------");
            for (Room idxRoom : roomList) {
                System.out.printf("%-6s | %-18s | %-10s | %-8.1f | %-8d | %-30s%n",
                        idxRoom.getRoomID(), idxRoom.getRoomName(), idxRoom.getRoomType(),
                        idxRoom.getRoomDailyRate(), idxRoom.getRoomCapacity(), idxRoom.getRoomFurnitureDescription());
            }
        }
    }
    
    /*--------------------------------------------------------------------------
    ----------------------------------------------------------------------------*/
    private Room lineToRoom(String line) {
        String[] field = line.trim().split(";");
        try {
            if (field.length == 6) {
                String roomID = field[0];
                String roomName = field[1];
                String roomtype = field[2];
                double dailyRate = Double.parseDouble(field[3]);
                int roomCapacity = Integer.parseInt(field[4]);
                String roomFurniture = field[5];

                Room inpRoom = new Room(roomID, roomName, roomtype, dailyRate, roomCapacity, roomFurniture);

                return inpRoom;
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void importRoomList() {
        BufferedReader br = null;

        try {
            File file = new File(filePath);

            if (!file.exists()) {
                cls();
                System.out.println("File Active_Room.txt isn't exist");
                return;
            }

            br = new BufferedReader(new FileReader(file));
            String line = "";

            while ((line = br.readLine()) != null) {
                if (lineToRoom(line) != null) {
                    this.addRoom(lineToRoom(line));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Active_Room.txt not found");
        } catch (IOException e) {
            System.out.println("Errors while import Active_Room.txt");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                System.out.println("Error while closing file");
            }
        }
    }
    
    /*--------------------------------------------------------------------------
    ----------------------------------------------------------------------------*/

}
