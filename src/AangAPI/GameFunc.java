package AangAPI;

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

    public Crosshair getCrosshair() {
        return Crosshair.values()[script.getClient().accessor.getCrossHairColor()];
    }

    public enum Crosshair {
        NONE, DEFAULT, ACTION;
    }
}