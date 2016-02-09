package aang521.AangAPI.Function;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.AangUtil;
import org.osbot.rs07.accessor.XTile;

public class RegionFunc extends AangUtil {

    public RegionFunc(AangScript script) {
        super(script);
    }

    public XTile[][] getTiles(int plane){
        return client.getCurrentRegion().getTiles()[plane];
    }

    public XTile[][] getTiles(){
        return client.getCurrentRegion().getTiles()[client.getPlane()];
    }

    public int[][] getClippingFlags(int plane){
        return client.getClippingPlanes()[plane].getTileFlags();
    }

    public int[][] getClippingFlags(){
        return client.getClippingPlanes()[client.getPlane()].getTileFlags();
    }
}
