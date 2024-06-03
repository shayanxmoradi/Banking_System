package util;

public class AuthHolder {
    public static Long totkenUserId;
    public static String   tokenUsername ;

    public static void reset(){
        totkenUserId = null;
        tokenUsername = null;
    }
}
