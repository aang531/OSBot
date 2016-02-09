package aang521.AangAPI.DataTypes;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.DataTypes.Interfaces.AangDataType;

public class Path extends AangDataType {

    Tile[] tilePath;
    Obstacle[] obstacles;

    int lastIndex = Integer.MAX_VALUE - 3;

    public Path(AangScript script, Tile[]tilePath, Obstacle[] obstacles){
        super(script);
        this.tilePath = tilePath;
        this.obstacles = obstacles;
    }

    public Path(AangScript script, Tile[] tilePath ){
        super(script);
        this.tilePath = tilePath;
        this.obstacles = new Obstacle[]{};
    }

    public boolean traverse(){
        if( !script.movement.running() && script.movement.energy() >= script.movement.nextRunPercent ){
            if( script.movement.setRunning() )
                script.movement.nextRunPercent = script.random(50,80);
        }

        for (Obstacle obstacle : obstacles) {
            if (obstacle.check())//TODO don't interact if no longer in area
                return true;
        }
        for(int i = Math.min(lastIndex + 2,tilePath.length - 1); i >= 0; i-- ){
            if( tilePath[i].isOnMinimap() && tilePath[i].reachable()){
                lastIndex = i;
                if( (!script.localPlayer().isMoving() || script.movement.destination().distanceTo(tilePath[i]) > 3 ) && script.localPlayer().getTile().distanceTo(tilePath[i]) > 0 ) {
                    boolean ret = script.movement.walkTile(tilePath[i]);
                    script.sleep(200);
                    return ret;
                }else{
                    return true;
                }
            }
        }
        for(int i = tilePath.length-1; i >= Math.min(lastIndex + 2,tilePath.length - 1) && i >= 0; i-- ){
            if( tilePath[i].isOnMinimap() && tilePath[i].reachable()){
                lastIndex = i;
                if( (!script.localPlayer().isMoving() || script.movement.destination().distanceTo(tilePath[i]) > 3 ) && script.localPlayer().getTile().distanceTo(tilePath[i]) > 0 ) {
                    boolean ret = script.movement.walkTile(tilePath[i]);
                    script.sleep(200);
                    return ret;
                }else{
                    return true;
                }
            }
        }
        return false;
    }

    public Tile[] getTiles(){
        return tilePath;
    }

    public Obstacle[] getObstacles(){
        return obstacles;
    }

    public Tile getDest() {
        return tilePath[tilePath.length-1];
    }

    public Tile getStart(){
        return tilePath[0];
    }
}
