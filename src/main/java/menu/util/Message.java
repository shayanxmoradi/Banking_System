package menu.util;

public class Message {
    private static final String menuName = "\nCurrent postions : %s  ";
    private static final String invalidInputMessage = "Your input values are invalid";


    public static String getMenuName(String menu) {
        return String.format(menuName, menu);
    }
    public static String getInvalidInputMessage() {
        return invalidInputMessage;
    }
}
