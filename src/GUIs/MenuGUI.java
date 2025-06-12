package GUIs;

import java.util.Scanner;

/*hwnglesauveur*/
public class MenuGUI {

    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println("Can't clear screen.");
        }
    }

    public static void cls() {
        for (int i = 0; i < 20; i++) {
            System.out.println();
        }
    }

    public static String menu() {
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        String choice = "";
        
        while (running == true) {
            System.out.println("<>==========ROOM MANAGEMENT==========<>");
            System.out.println("1. Import room list");
            System.out.println("2. Load guests informations");
            System.out.println("3. Display available room list");
            System.out.println("4. Enter guest information");
            System.out.println("5. Update guest information");
            System.out.println("6. Search guest by nation ID");
            System.out.println("7. Delete guest by ID");
            System.out.println("8. List Vacant Rooms");
            System.out.println("9. Monthly revenue report");
            System.out.println("10. Revenue report by room type");
            System.out.println("11. Save guests informations");
            System.out.println("12. Exit");
            System.out.println("Please choose: ");
            
            choice = sc.nextLine();
            try {
                int num = Integer.parseInt(choice);
                
                if (num >= 1 && num <= 12) {
                    return choice;
                } else {
                    cls();
                    System.out.println("Invalid selection, please try again");
                }
            } catch (Exception e) {
                cls();
                System.out.println("Invalid selection, please try again");
            }
        }
        
        return choice;
    }

}