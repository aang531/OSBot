package AangAPI.DataTypes;

import AangAPI.AangUtil;

public class Obstacle {

    public enum ObstacleType{
        OBJECT,
        NPC
    }
    public int id;
    public Tile obstacleTile;
    public TileArea area;
    public String action;
    public ObstacleType type;

    public Obstacle(){
        id = -1;
        obstacleTile = null;
        area = new TileArea(-1,-1,0,0);
        action = "NULL";
        type = ObstacleType.OBJECT;
    }

    public Obstacle(int id, Tile obstacleTile, TileArea area, String action ) {
        this.id = id;
        this.obstacleTile = obstacleTile;
        this.area = area;
        this.action = action;
        type = ObstacleType.OBJECT;
    }

    public Obstacle(int id, TileArea area, String action ) {
        this.id = id;
        this.area = area;
        this.action = action;
        type = ObstacleType.NPC;
    }

    public boolean check() {
        if( area.contains(AangUtil.localPlayer().getTile())){
            if( type == ObstacleType.OBJECT )
                return checkObject();
            else if( type == ObstacleType.NPC )
                return checkNPC();
        }
        return false;
    }

    private boolean checkObject(){
        RSObject o = AangUtil.objects.getAt(obstacleTile, id);
        if( o != null ) {
            if( !AangUtil.misc.pointOnScreen(o.getCenterPoint()))
                return false;
            if(AangUtil.inventory.itemSelected() ){
                AangUtil.inventory.unselectItem();
            }
            o.click(action);
            AangUtil.sleep(200);
            return true;
        }
        return false;
    }

    private boolean checkNPC(){
        Npc npc = AangUtil.npcs.getNearest(id);
        if( npc != null ) {
            if( !AangUtil.misc.pointOnScreen(npc.getCenterPoint()))
                return false;
            if(AangUtil.inventory.itemSelected() ){
                AangUtil.inventory.unselectItem();
            }
            npc.click(action);
            AangUtil.sleep(200);
            return true;
        }
        return false;
    }
}
