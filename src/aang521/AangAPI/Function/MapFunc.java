package aang521.AangAPI.Function;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.AangUtil;

public class MapFunc extends AangUtil {
    public MapFunc(AangScript script ){
        super(script);
    }

    public boolean isMultiCombat(){
        return widgets.get(548,18).visable();
    }

    public int getWildernessLevel(){
        return widgets.active(90) ? Integer.parseInt(widgets.get(90,26).getText().replace("Level: ", "").trim()) : 0;
    }

    public int getBaseX(){
        return client.getMapBaseX();
    }

    public int getBaseY(){
        return client.getMapBaseY();
    }

    public int gridXtoWorldX(int x){
        return (x >> 7)+getBaseX();
    }

    public int gridYtoWorldY(int y){
        return (y >> 7)+getBaseY();
    }

    public int worldXtoGridX(int x){
        return (x-client.getMapBaseX()<<7) + 64;
    }

    public int worldYtoGridY(int y){
        return (y-client.getMapBaseY()<<7) + 64;
    }

    public int getPlane(){
        return client.getPlane();
    }
}
