package aang521.AangAPI.Function;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.AangUtil;

public class KeyboardFunc extends AangUtil {

    public KeyboardFunc(AangScript script) {
        super(script);
    }

    public boolean sendln(String text){
        return script.getKeyboard().typeString(text,true);
    }

    public boolean send(String text){
        return script.getKeyboard().typeString(text,false);
    }

    public void send(int keycode ){
        script.getKeyboard().pressKey(keycode);
        sleep(20);
        script.getKeyboard().releaseKey(keycode);
    }
}
