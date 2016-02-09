package aang521.AangAPI.Function;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.AangUtil;

public class CameraFunc extends AangUtil {

    public CameraFunc(AangScript script) {
        super(script);
    }

    public boolean pitchedUp(){
        return script.getCamera().getPitchAngle() == 67;
    }

    public void pitchUp(){
        script.getCamera().movePitch(67);
    }
}
