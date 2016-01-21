package AangAPI;

import AangAPI.DataTypes.Path;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;

import java.awt.*;

public class MovementFunc extends AangUtil {
    private static MovementFunc ourInstance = new MovementFunc();

    public static MovementFunc getInstance() {
        return ourInstance;
    }

    public int nextRunPercent = random(50,80);

    public int energy() {
        return movement.energy();
    }

    public boolean running() {
        return movement.running();
    }

    public boolean setRunning() {
        return setRunning(true);
    }

    public boolean setRunning(boolean running) {
        if( running() != running ) {
            mouse.move(widgets.get(160, 22).centerPoint());
            sleep(80);
            mouse.click(true);
            sleep(80);
            return running() == running;
        }
        return true;
    }

    public boolean tileOnMap(Tile t){
        Point p = t.mapPoint();
        return p.x > 570 && p.x < 570 + 145 && p.y > 9 && p.y < 9 + 151 &&
                p.distanceSq(568+13,125+13) > 13*13 && p.distanceSq(545+16,4+16) > 19*19;
    }

    public boolean tileOnScreen(Tile t){
        return misc.pointOnScreen(t.centerPoint());
    }

    public boolean traversePath(Path path){
        return path.traverse();
    }

    public boolean walkTile(Tile t){
        if( tileOnScreen(t) && !bank.opened()){//TODO add all blocking widgets to this
            mouse.move(t.centerPoint());
            sleep(50);
            int index = menu.getIndex("Walk here","");
            if( index == 0 )
                return mouse.click(true);
            else{
                mouse.click(false);
                sleep(50);
                return menu.clickMenuOption(index);
            }
        }else if( tileOnMap(t)){
            mouse.move(t.mapPoint());
            sleep(50);
            return mouse.click(true);
        }else return false;
    }
}
