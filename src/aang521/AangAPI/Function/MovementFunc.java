package aang521.AangAPI.Function;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.AangUtil;
import aang521.AangAPI.DataTypes.Path;
import aang521.AangAPI.DataTypes.Tile;
import org.osbot.rs07.api.map.Position;

public class MovementFunc extends AangUtil {

    public MovementFunc(AangScript script) {
        super(script);
    }

    public int nextRunPercent = random(50,80);

    public int energy() {
        return client.getRunEnergy();
    }

    public boolean running() {
        return varpbits.get(173) == 1;
    }

    public boolean setRunning() {
        return setRunning(true);
    }

    public boolean setRunning(boolean running) {
        if( running() != running ) {
            mouse.move(widgets.get(160, 22).getCenterPoint());
            sleep(80);
            mouse.click(true);
            sleep(80);
            return running() == running;
        }
        return true;
    }

    public boolean isMoving(){
        return localPlayer().isMoving();
    }

    public Tile destination(){
        if( isMoving() )
            return Tile.makeTileFromLocal(script, client.getDestinationX(),client.getDestinationY(),client.getPlane());
        return null;
    }

    public boolean tileOnScreen(Tile t){
        return misc.isPointInViewport(t.getCenterPoint());
    }

    public boolean traversePath(Path path){
        return path.traverse();
    }

    public boolean walkTile(Tile t){
        if( tileOnScreen(t) && t.distanceTo(localPlayer().getTile()) < 5 && !bank.opened()){//TODO add all blocking widgets to this
            mouse.move(t.getCenterPoint());
            int index = menu.getActionIndex("Walk here");
            if( index == menu.count()-1 ) {
                boolean ret = mouse.click(true);
                sleep(this::isMoving,100,10);
                return ret;
            }
            else{
                mouse.click(false);
                sleep(80);
                index = menu.getActionIndex("Walk here");
                return menu.clickMenuOption(index);
            }
        }else if( t.isOnMinimap()){
            mouse.move(t.getMapPoint());
            return mouse.click(true);
        }else return false;
    }

    public void webwalk(Tile t){
        script.getWalking().webWalk(new Position(t.x,t.y,t.z));
    }

    public Tile gridToTile(int x, int y, int z){
        return new Tile(script, (x>>7)+client.getMapBaseX(), (y>>7)+client.getMapBaseY(),z);
    }

    public Tile gridToTile(int x, int y){
        return new Tile(script, (x>>7)+client.getMapBaseX(), (y>>7)+client.getMapBaseY(),client.getPlane());
    }
}
