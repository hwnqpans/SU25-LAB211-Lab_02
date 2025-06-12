package Controllers;

import GUIs.*;
import static GUIs.MenuGUI.cls;
import Models.GuestList;
import Models.RoomList;

/*hwnglesauveur*/
public class Main {
    
    public static String RoomFilePath = "Active_Room_List.txt";
    
    public static void main(String[] args) {
        
        RoomList srcRoom = new RoomList(RoomFilePath);
        GuestList srcGuestList = new GuestList();
        
        String menuChoice;
        
        while (true) {
            menuChoice = MenuGUI.menu();
            
            switch (menuChoice) {
                case Actions.IMPORT_ROOM_LIST:
                    cls();
                    srcRoom.importRoomList();
                    break;
                    
                case Actions.LOAD_GUESTS: //Optinal
                    
                    break;
                    
                case Actions.DISPLAY_AVAILABLE_ROOMS:
                    cls();
                    srcRoom.displayAvailableRoomList();
                    break;
                    
                case Actions.ENTER_GUEST:
                    cls();
                    
                case Actions.UPDATE_GUEST:
                    cls();
                    
                    break;
                    
                case Actions.SEARCH_GUEST:
                    cls();
                    
                    break;
                    
                case Actions.DELETE_GUEST:
                    cls();
                    
                    break;
                    
                case Actions.LIST_VACANT_ROOMS:
                    cls();
                    
                    break;
                    
                case Actions.MONTHLY_REVENUE_REPORT:
                    
                    break;
                    
                case Actions.REVENUE_REPORT_ROOM_TYPE:
                    
                    break;
                    
                case Actions.SAVE_GUESTS_INFORMATION:
                    //Đã làm xong nhưng chưa implement, xem lại function lưu và saved boolean
                    break;
                    
                case Actions.EXIT:
                    cls();
                    System.out.println("Good bye, have a good day!!!");
                    return;
                    
                default:
                    throw new AssertionError();
            }
        }
        
    }

}
