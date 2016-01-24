package AangAPI.DataTypes;

import AangAPI.AangUtil;

public class Path {

    Tile[] tilePath;
    Obstacle[] obstacles;

    int lastIndex = Integer.MAX_VALUE - 3;

    public Path(Tile[] tilePath, Obstacle[] obstacles){
        this.tilePath = tilePath;
        this.obstacles = obstacles;
    }

    public Path(Tile[] tilePath ){
        this.tilePath = tilePath;
        this.obstacles = new Obstacle[]{};
    }

    public boolean traverse(){
        if( !AangUtil.movement.running() && AangUtil.movement.energy() >= AangUtil.movement.nextRunPercent ){
            if( AangUtil.movement.setRunning() )
                AangUtil.movement.nextRunPercent = AangUtil.random(50,80);
        }

        for (Obstacle obstacle : obstacles) {
            if (obstacle.check())
                return true;
        }
        for(int i = Math.min(lastIndex + 2,tilePath.length - 1); i >= 0; i-- ){
            if( AangUtil.movement.tileOnMap(tilePath[i]) && tilePath[i].reachable()){
                lastIndex = i;
                if( (AangUtil.movement.destination().distanceTo(tilePath[i]) > 2 || !AangUtil.localPlayer().inMotion()) && AangUtil.localPlayer().getTile().distanceTo(tilePath[i]) > 0 ) {
                    boolean ret = AangUtil.movement.walkTile(tilePath[i]);
                    AangUtil.sleep(200);
                    return ret;
                }else{
                    return true;
                }
            }
        }
        for(int i = tilePath.length-1; i >= Math.min(lastIndex + 2,tilePath.length - 1) && i >= 0; i-- ){
            if( AangUtil.movement.tileOnMap(tilePath[i]) && tilePath[i].reachable()){
                lastIndex = i;
                if( (AangUtil.movement.destination().distanceTo(tilePath[i]) > 2 || !AangUtil.localPlayer().inMotion()) && AangUtil.localPlayer().getTile().distanceTo(tilePath[i]) > 0 ) {
                    boolean ret = AangUtil.movement.walkTile(tilePath[i]);
                    AangUtil.sleep(200);
                    return ret;
                }else{
                    return true;
                }
            }
        }
        return false;
    }
}
