package login;

import java.io.Serializable;

public class User implements Serializable {

    private String username;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private String password;



    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
