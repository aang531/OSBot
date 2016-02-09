package aang521.AangAPI.DataTypes;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.DataTypes.Interfaces.AangDataType;
import aang521.AangAPI.DataTypes.Interfaces.Locatable;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.util.GraphicUtilities;

import java.awt.*;

public class Tile extends AangDataType implements Locatable {
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

    public Tile(AangScript script, int x,int y,int z){
        super(script);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Tile(AangScript script, Position pos){
        super(script);
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public Tile(AangScript script, int x, int y ){
        super(script);
        this.x = x;
        this.y = y;
        z = 0;
    }

    public static Tile makeTileFromLocal(AangScript script, int localx, int localy, int z ){
        return new Tile(script, script.map.getBaseX()+localx, script.map.getBaseY()+localy,z);
    }

    public Point getMapPoint(){
        short[] points = GraphicUtilities.getMinimapScreenCoordinate(script.bot, x, y);
        return new Point(points[0],points[1]);
    }

    public Polygon getPolygon(){
        return new Position(x,y,z).getPolygon(script.bot,z);//TODO make this own function
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
        Rectangle r = new Position(x,y,z).getPolygon(script.bot).getBounds();
        return new Point((int)r.getCenterX(), (int)r.getCenterY());
    }

    public boolean isOnScreen(){
        return script.misc.isPointInViewport(getCenterPoint());
    }

    public boolean isOnMinimap(){
        Point p = getMapPoint();
        return p.x > 570 && p.x < 570 + 145 && p.y > 9 && p.y < 9 + 151 &&
                p.distanceSq(568+13,125+13) > 15*15 && p.distanceSq(545+16,4+16) > 19*19;
    }

    public boolean reachable(){
        return script.map.getPlane() == z && script.getMap().canReach(new Position(x,y,z));//TODO make own function
    }

    @Override
    public int getLocalX(){
        return x- script.client.getMapBaseX();
    }

    @Override
    public int getLocalY(){
        return y- script.client.getMapBaseY();
    }

    @Override
    public Tile getTile() {
        return this;
    }

    @Override
    public int getGridX(){
        return (x- script.client.getMapBaseX()<<7) + 64;
    }

    @Override
    public int getGridY(){
        return (y- script.client.getMapBaseY()<<7) + 64;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getZ() {
        return z;
    }

    public boolean isLoaded() {
        return getLocalX() > 0 && getLocalY() > 0 && getLocalX() < 99 && getLocalY() < 99;
    }

    public boolean isValid() {
        return isLoaded();
    }

    public int getClippingFlags() {
        return script.client.getClippingPlanes()[z].getTileFlags()[getLocalX()][getLocalY()];
    }

    public String toString(){
        return "( " + x + ", " + y + ", " + z + " )";
    }
}
