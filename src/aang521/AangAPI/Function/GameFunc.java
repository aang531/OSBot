package aang521.AangAPI.Function;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.AangUtil;
import org.osbot.rs07.api.Client;

public class GameFunc extends AangUtil {//TODO expand this with all states

    public GameFunc(AangScript script) {
        super(script);
    }

    public boolean playing() {
       return script.getClient().isLoggedIn() && !loading();
    }

    public boolean loading() {
        return script.getClient().getLoginState() == Client.LoginState.LOADING;
    }

    public int getState(){
        return client.getGameState();
    }
}