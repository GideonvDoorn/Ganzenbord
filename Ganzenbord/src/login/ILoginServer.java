package login;

public interface ILoginServer {

    User loginUser(String username, String password);
    boolean registerUser(String username, String password);
    boolean logoutUser(String username);


}
