package menu;

import menu.login.LoginMenu;
import menu.util.Input;
import menu.util.Message;

import java.sql.SQLException;

public class MainMenu {
    public static void show() throws SQLException {
        meinMenu:
        while (true) {
            System.out.println(Message.getMenuName("mainMenu"));
            System.out.println("""
                    Chose an Option
                    1 -> Sign Up Menu
                    2 -> Sign In Menu
                    3 -> Exit Application
                    """);

            switch (Input.scanner.next()) {
                case "1" -> SignUpMenu.show();
                case "2" -> LoginMenu.show();
                case "3" -> {
                    Input.scanner.close();
                    System.exit(0);
                }
                default -> System.out.println(Message.getInvalidInputMessage());
            }

        }
    }
}
