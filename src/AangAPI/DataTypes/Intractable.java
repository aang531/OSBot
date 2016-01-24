package AangAPI.DataTypes;

import AangAPI.AangUtil;
import AangAPI.GameFunc;
import org.osbot.rs07.api.model.Model;

import java.awt.*;

public abstract class Intractable {

    public abstract String getName();
    public abstract Model getModel();
    public abstract void drawModel(Graphics2D g);
    public abstract void drawModelOutline(Graphics2D g);
    public abstract Rectangle getBoundingBox();
    public abstract Point getCenterPoint();
    public abstract boolean valid();
    public abstract Point getRandomPoint();
    public abstract Tile getTile();
    public abstract int getGridX();
    public abstract int getGridY();

    public boolean click(String action){
        return clickInteractableCenterCC(action,getName());
    }

    public boolean useItem(){
        return clickInteractableCenterCC("Use", AangUtil.inventory.getSelectedItemName() + " -> " + this.getName());
    }

    public boolean clickIntractableCC(String action, String name ){
        int tries = 0;
        Point p = this.getRandomPoint();
        while( tries < 5 && this.valid()){
            if( !AangUtil.misc.pointOnScreen(p) ){
                tries++;
                p = this.getRandomPoint();
                continue;
            }
            if( !AangUtil.menu.opened()) {
                AangUtil.mouse.move(p);
                AangUtil.sleep(80);
                final int index = AangUtil.menu.getIndex(action,name);
                if( index == 0 ){
                    AangUtil.mouse.click(true);
                    AangUtil.sleep(80);
                    return AangUtil.game.getCrosshair() == GameFunc.Crosshair.ACTION;
                }else if( index != -1 ){
                    AangUtil.mouse.click(false);
                    AangUtil.sleep(80);
                }
            }else{
                final int index = AangUtil.menu.getIndex(action,name);
                if( index != -1) {
                    AangUtil.menu.clickMenuOption(index);
                    AangUtil.sleep(80);
                    return AangUtil.game.getCrosshair() == GameFunc.Crosshair.ACTION;
                }else {
                    AangUtil.menu.close();
                    AangUtil.sleep(80);
                }
            }
            tries++;
            p = this.getRandomPoint();
        }
        return false;
    }

    public boolean clickInteractableCenterCC(String action, String name ){
        int tries = 0;
        while( tries < 5 && this.valid() && AangUtil.misc.pointOnScreen(this.getCenterPoint())){
            if( !AangUtil.menu.opened()) {
                AangUtil.mouse.move(this.getCenterPoint());
                AangUtil.sleep(80);
                final int index = AangUtil.menu.getIndex(action,name);
                if( index == 0 ){
                    AangUtil.mouse.click(true);
                    AangUtil.sleep(80);
                    return AangUtil.game.getCrosshair() == GameFunc.Crosshair.ACTION;
                }else{
                    AangUtil.mouse.click(false);
                    AangUtil.sleep(80);
                }
            }else{
                final int index = AangUtil.menu.getIndex(action,name);
                if( index != -1) {
                    AangUtil.menu.clickMenuOption(index);
                    AangUtil.sleep(80);
                    return AangUtil.game.getCrosshair() == GameFunc.Crosshair.ACTION;
                }else {
                    AangUtil.menu.close();
                    AangUtil.sleep(80);
                }
            }
            tries++;
        }
        return false;
    }

}
