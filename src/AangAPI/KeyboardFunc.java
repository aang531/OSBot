package AangAPI;

public class KeyboardFunc extends AangUtil {
    private static KeyboardFunc ourInstance = new KeyboardFunc();

    public static KeyboardFunc getInstance() {
        return ourInstance;
    }

    private KeyboardFunc() {
    }

    public boolean sendln(String text){
        return script.getKeyboard().typeString(text);
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
