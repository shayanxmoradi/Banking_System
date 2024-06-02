package menu.util;

public class Message {
    private static final String menuName = "\nCurrent postions : %s  ";
    private static final String invalidInputMessage = "Your input values are invalid";
    private static final String inputMessage = "pls enter %S";
    private static final String existMessage = "%S is already exist";
    private static final String successfulMessage = "%s successful";
    private static final String notFoundMessage = "%s not found";
    private static final String failedMessage = "%s failed";



    public static String getMenuName(String menu) {
        return String.format(menuName, menu);
    }
    public static String getInvalidInputMessage() {
        return invalidInputMessage;
    }




    public static String getInputMessage(String input) {
        return String.format(inputMessage, input);
    }

    public static String getExistMessage(String input) {
        return String.format(existMessage, input);
    }



    public static String getSuccessfulMessage(String input) {
        return String.format(successfulMessage, input);
    }

    public static String getNotFoundMessage(String input) {
        return String.format(notFoundMessage, input);
    }

    public static String getFailedMessage(String input) {
        return String.format(failedMessage, input);
    }
}
