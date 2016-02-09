package aang521.AangAPI.DataTypes;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.DataTypes.Interfaces.AangDataType;

public class Obstacle extends AangDataType {

    public enum ObstacleType{
        OBJECT,
        NPC
    }
    public int id;
    public Tile obstacleTile;
    public TileArea area;
    public String action;
    public ObstacleType type;
    private long lastClickedTime;

    public Obstacle(AangScript script){
        super(script);
        id = -1;
        obstacleTile = null;
        area = new TileArea(script, -1,-1,0,0);
        action = "NULL";
        type = ObstacleType.OBJECT;
    }

    public Obstacle(AangScript script, int id, Tile obstacleTile, TileArea area, String action ) {
        super(script);
        this.id = id;
        this.obstacleTile = obstacleTile;
        this.area = area;
        this.action = action;
        type = ObstacleType.OBJECT;
    }

    public Obstacle(AangScript script, int id, TileArea area, String action ) {
        super(script);
        this.id = id;
        this.area = area;
        this.action = action;
        type = ObstacleType.NPC;
    }

    public boolean check() {
        if( area.contains(script.localPlayer().getTile())){//TODO make is so it just checks if object is on screen and is reachable
            if( type == ObstacleType.OBJECT )
                return checkObject();
            else if( type == ObstacleType.NPC )
                return checkNPC();
        }
        return false;
    }

    private boolean checkObject(){
        RSObject o = script.objects.getAt(obstacleTile, id);
        if( o != null && System.currentTimeMillis() - lastClickedTime > 2000 ) {
            if( !script.misc.isPointInViewport(o.getCenterPoint()))
                return false;
            if(script.inventory.itemSelected() ){
                script.inventory.unselectItem();
            }
            if( o.click(action) ) {
                lastClickedTime = System.currentTimeMillis();
                script.sleep(100);
            }
            return true;
        }
        return false;
    }

    private boolean checkNPC(){
        Npc npc = script.npcs.getNearest(id);
        if( npc != null  ) {
            if( System.currentTimeMillis() - lastClickedTime > 3000 )
                return true;
            if( !script.misc.isPointInViewport(npc.getCenterPoint()))
                return false;
            if(script.inventory.itemSelected() ){
                script.inventory.unselectItem();
            }
            if( npc.click(action) ) {
                lastClickedTime = System.currentTimeMillis();
                script.sleep(100);
            }
            return true;
        }
        return false;
    }
}
