package AangAPI.Function;

import AangAPI.AangUtil;
import org.osbot.rs07.api.Client;

public class GameFunc extends AangUtil {
    private static GameFunc ourInstance = new GameFunc();

    public static GameFunc getInstance() {
        return ourInstance;
    }

    public boolean playing() {
       return script.getClient().isLoggedIn();
    }

    public boolean loading() {
        return script.getClient().getLoginState() == Client.LoginState.LOADING;
    }
}