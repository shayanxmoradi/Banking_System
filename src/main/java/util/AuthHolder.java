package util;

public class AuthHolder {
    public Long totkenUserId;
    public String tokenUsername;

    public void reset() {
        totkenUserId = null;
        tokenUsername = null;
    }

    public Long getTotkenUserId() {
        return totkenUserId;
    }

    public void setTotkenUserId(Long totkenUserId) {
        this.totkenUserId = totkenUserId;
    }

    public String getTokenUsername() {
        return tokenUsername;
    }

    public void setTokenUsername(String tokenUsername) {
        this.tokenUsername = tokenUsername;
    }


}
