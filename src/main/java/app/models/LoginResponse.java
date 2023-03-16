package app.models;

public class LoginResponse {
    private int customerId;
    private String token;

    private String redirectUrl;

    public LoginResponse(int customerId, String token, String redirect) {
        this.customerId = customerId;
        this.token = token;
        this.redirectUrl = redirect;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
