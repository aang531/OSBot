package AangAPI;

import org.osbot.rs07.accessor.XClient;
import org.osbot.rs07.api.Client;

public class GameFunc extends AangUtil {
    private static GameFunc ourInstance = new GameFunc();

    public static GameFunc getInstance() {
        return ourInstance;
    }

    public boolean playing() {
       return script.client.isLoggedIn();
    }

    public boolean loading() {
        return script.client.getLoginState() == Client.LoginState.LOADING; //TODO check this
    }

    public Crosshair getCrosshair() {
        return Crosshair.values()[script.getClient().accessor.getCrossHairColor()];
    }

    public enum Crosshair {
        NONE, DEFAULT, ACTION;
    }
}