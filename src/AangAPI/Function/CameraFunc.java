package AangAPI.Function;

import AangAPI.AangUtil;

public class CameraFunc extends AangUtil {
    private static CameraFunc ourInstance = new CameraFunc();

    public static CameraFunc getInstance() {
        return ourInstance;
    }

    public boolean pitchedUp(){
        return script.getCamera().getPitchAngle() == 67;
    }

    public void pitchUp(){
        script.getCamera().movePitch(67);
    }
}
