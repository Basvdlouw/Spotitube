package nl.han.oose.dea.domain;

public class User {

    private String username;
    private String password;
    private String token;
    private String user;

    public User(String username, String password, String token, String user) {
        this.username = username;
        this.password = password;
        this.token = token;
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public String getUser() {
        return user;
    }
}
