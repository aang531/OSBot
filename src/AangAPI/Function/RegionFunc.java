package AangAPI.Function;

import AangAPI.AangUtil;
import org.osbot.rs07.accessor.XTile;

public class RegionFunc extends AangUtil {
    private static RegionFunc ourInstance = new RegionFunc();

    public static RegionFunc getInstance() {
        return ourInstance;
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
