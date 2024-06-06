import entity.User;
import menu.MainMenu;
import util.ApplicationContext;

import java.sql.SQLException;


public class BankingApplication {
    public static void main(String[] args) throws SQLException {
        ApplicationContext.getInstance().getMenu().show();

    }
}