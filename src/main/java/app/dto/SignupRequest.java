package app.dto;
/**
 * Model class for a signup request
 */

public class SignupRequest {

    private final String username;
    private final String password;

    public SignupRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}
