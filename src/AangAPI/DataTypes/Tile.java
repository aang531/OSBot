package AangAPI.DataTypes;

import AangAPI.AangUtil;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.util.GraphicUtilities;

import java.awt.*;

public class Tile {
    public int x, y, z;

    public Tile(int x,int y,int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Tile(Position pos){
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public Point mapPoint(){
        short[] points = GraphicUtilities.getMinimapScreenCoordinate(AangUtil.script.bot, x, y);
        return new Point(points[0],points[1]);
    }

    public Polygon polygon(){
        return new Position(x,y,z).getPolygon(AangUtil.script.bot);
    }

    public Point centerPoint(){
        Rectangle r = new Position(x,y,z).getPolygon(AangUtil.script.bot).getBounds();
        return new Point((int)r.getCenterX(), (int)r.getCenterY());
    }

    public boolean reachable(){
        return AangUtil.script.getMap().canReach(new Position(x,y,z));
    }

    public int sqrDistTo(Tile t){
        final int x = this.x - t.x;
        final int y = this.y - t.y;
        return x*x+y*y;
    }

    public double distanceTo(Tile t){
        return Math.sqrt(sqrDistTo(t));
    }

    public int getLocalX(){
        return x-AangUtil.client.getMapBaseX();
    }

    public int getLocalY(){
        return y-AangUtil.client.getMapBaseY();
    }

    public boolean isLoaded() {
        return true;//TODO
    }
}
