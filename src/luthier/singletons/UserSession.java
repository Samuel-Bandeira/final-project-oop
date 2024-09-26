package luthier.singletons;
import luthier.entities.UserAbstract;

public class UserSession {
    private static UserSession instance;
    private UserAbstract loggedUser;

    private UserSession() { }

    public static UserSession getInstance() {
        if (instance == null) {
        	instance = new UserSession();
        }
        return instance;
    }

    public UserAbstract getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(UserAbstract user) {
        this.loggedUser = user;
    }
}
