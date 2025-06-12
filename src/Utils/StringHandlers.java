
package Utils;

import GUIs.MenuGUI;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/*hwnglesauveur*/
public class StringHandlers {
    
    public static String genderCorrector(String gender){
        StringBuilder result = new StringBuilder();
        gender = gender.toLowerCase();
        result.append(Character.toString(gender.charAt(0)).toUpperCase()).append(gender.substring(1));
        
        return result.toString();
    }
    
        public static boolean isDateInMonthYear(String fullDateStr, String monthYearStr) {
        try {
            DateTimeFormatter fullFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MM/yyyy");

            LocalDate fullDate = LocalDate.parse(fullDateStr, fullFormatter);

            String extractedMonthYear = fullDate.format(monthYearFormatter);

            return extractedMonthYear.equals(monthYearStr);
        } catch (DateTimeParseException e) {
            MenuGUI.cls();
            System.out.println("Some errors have been occurred!");
            return false;
        }
    }
    
    public static boolean isValidMonthYear(String input) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

            YearMonth.parse(input, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

}
