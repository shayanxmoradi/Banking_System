package menu.login;

import entity.Account;
import entity.User;
import menu.util.Input;
import menu.util.Message;
import service.user.UserService;
import util.ApplicationContext;
import util.AuthHolder;

import java.sql.SQLException;

public class AccountMenu {
    private final Input INPUT;
    private final Message MESSAGE;
    private final UserService USER_SERVICE;


    public AccountMenu(Input input, Message message, UserService userService) {
        this.INPUT = input;
        this.MESSAGE = message;
        this.USER_SERVICE = userService;

    }
    public  void show() {
        accountMenu:
        while (true) {
            System.out.println("""
                            1 -> Create Bank Account
                            2 -> Show all my Accounts
                            3 -> Close Bank Account *WARNING*
                            4 -> Previous Menu
                    """);

            switch (INPUT.scanner.next()) {
                case "1": {
                    System.out.println(Message.getInputMessage("Your account name"));
                    String accountName = Input.scanner.next();
                    System.out.println(Message.getInputMessage("Chose between aviable Banks"));
                    //todo show banks here and set bank handle it!
                    Long bankId = 2L;
                    String bankName = "Volks Bank";
                    System.out.println(Message.getInputMessage("How much money do you put in first place?"));
                    double initAmount = Input.scanner.nextFloat();
                    Account account = new Account(accountName, bankId, bankName, initAmount);
                    USER_SERVICE.createAccount(account);
                    String createdMessage = """
                            Account successfully created
                            your Account Number is = %s
                            your Account paya Number is = %s
                                                      
                            """;

                    System.out.println(createdMessage.formatted(account.getAccountNummber(), account.getPayaNummber()));
                    break;
                }
                case "2": {
                    //todo get all acounts list.

                    break;
                }
                case "3": {
                    //todo handle CLOSE BANK ACCOUNT
                    break;
                }
                case "4": {
                    break accountMenu;
                }
                default:
                    System.out.println(Message.getInvalidInputMessage());
            }

        }
    }
}
