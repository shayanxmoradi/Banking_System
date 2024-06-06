package menu;

import menu.login.LoginMenu;
import menu.util.Input;
import menu.util.Message;

import java.sql.SQLException;

public class MainMenu {
    private final Input INPUT;
    private final Message MESSAGE;
    private final SignUpMenu SIGNUP_MENU;
    private final LoginMenu LOGIN_MENU;
    public MainMenu(Input input, Message message, SignUpMenu signUpMenu, LoginMenu loginMenu) {
        this.INPUT = input;
        this.MESSAGE = message;
        this.SIGNUP_MENU = signUpMenu;
        this.LOGIN_MENU = loginMenu;
    }

    public  void show() throws SQLException {
        meinMenu:
        while (true) {

            System.out.println(Message.getMenuName("mainMenu"));
            System.out.println("""
                    Chose an Option
                    1 -> Sign Up Menu
                    2 -> Sign In Menu
                    3 -> Exit Application
                    """);

            switch (INPUT.scanner.next()) {
                case "1" -> SIGNUP_MENU.show();
                case "2" -> LOGIN_MENU.show();
                case "3" -> {
                    INPUT.scanner.close();
                    System.exit(0);
                }
                default -> System.out.println(MESSAGE.getInvalidInputMessage());
            }

        }
    }
}
