package AangAPI.DataTypes;

import AangAPI.AangUtil;
import AangAPI.DataTypes.Interfaces.Locatable;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.util.GraphicUtilities;

import java.awt.*;

public class Tile {
    public int x, y, z;

    //walking
    public static final int BLOCK_OBJECT = 1 << 8;//1 << 17
    public static final int BLOCK_DECORATION = 1 << 18;

    public static final int BLOCKED = (1<<21);//1<<24
    public static final int WALL_NORTH = (1 << 1);//1 << 10
    public static final int WALL_EAST = (1 << 3);//1 << 12
    public static final int WALL_SOUTH = (1 << 5);//1 << 14
    public static final int WALL_WEST = (1 << 7);//1 << 16
    public static final int WALL_NORTHWEST = 1;//1 << 9
    public static final int WALL_NORTHEAST = (1 << 2);//1 << 11
    public static final int WALL_SOUTHEAST = (1 << 4);//1 << 13
    public static final int WALL_SOUTHWEST = (1 << 6);//1 << 15

    //projectiles

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

    public Point getMapPoint(){
        short[] points = GraphicUtilities.getMinimapScreenCoordinate(AangUtil.script.bot, x, y);
        return new Point(points[0],points[1]);
    }

    public Polygon getPolygon(){
        return new Position(x,y,z).getPolygon(AangUtil.script.bot);
    }

    public void draw(Graphics2D g){
        g.drawPolygon(getPolygon());
    }

    public void fill(Graphics2D g){
        g.fillPolygon(getPolygon());
    }

    public void drawMinimap(Graphics2D g ){
        final Point p = getMapPoint();
        g.drawRect(p.x-1,p.y-1,3,3);
    }

    public void fillMinimap(Graphics2D g ){
        final Point p = getMapPoint();
        g.fillRect(p.x-1,p.y-1,3,3);
    }


    public Point getCenterPoint(){
        Rectangle r = new Position(x,y,z).getPolygon(AangUtil.script.bot).getBounds();
        return new Point((int)r.getCenterX(), (int)r.getCenterY());
    }

    public boolean isOnScreen(){
        return AangUtil.misc.isPointOnScreen(getCenterPoint());
    }

    public boolean isOnMinimap(){
        Point p = getMapPoint();
        return p.x > 570 && p.x < 570 + 145 && p.y > 9 && p.y < 9 + 151 &&
                p.distanceSq(568+13,125+13) > 13*13 && p.distanceSq(545+16,4+16) > 19*19;
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

    public int getGridX(){
        return (x-AangUtil.client.getMapBaseX()<<7) + 64;
    }

    public int getGridY(){
        return (y-AangUtil.client.getMapBaseY()<<7) + 64;
    }

    public boolean isLoaded() {
        return getLocalX() > 0 && getLocalY() > 0 && getLocalX() < 99 && getLocalY() < 99;
    }

    public boolean isValid() {
        return isLoaded();
    }

    public int getClippingFlags() {
        return AangUtil.client.getClippingPlanes()[z].getTileFlags()[getLocalX()][getLocalY()];
    }

    public String toString(){
        return "( " + x + ", " + y + ", " + z + " )";
    }
}
