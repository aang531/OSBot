package AangAPI.DataTypes.Interfaces;

import AangAPI.AangUtil;
import AangAPI.Function.MouseFunc;

import java.awt.*;

public abstract class Interactable {

    public abstract String getName();//TODO move this somewhere else
    public abstract Rectangle getBoundingBox();
    public abstract Point getCenterPoint();
    public abstract boolean valid();

    public Point getRandomPoint() {
        Rectangle r = getBoundingBox();
        final int x = (int)r.getX() + AangUtil.random( (int)r.getWidth() );
        final int y = (int)r.getY() + AangUtil.random( (int) r.getHeight() );
        return new Point(x,y);
    }

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
            if( !AangUtil.misc.isPointOnScreen(p) ){
                tries++;
                p = this.getRandomPoint();
                continue;
            }
            if( !AangUtil.menu.opened()) {
                AangUtil.mouse.move(p);
                AangUtil.sleep(80);
                final int index = AangUtil.menu.getIndex(action,name);
                if( index == 0 ){
                    AangUtil.mouse.click(false);
                    AangUtil.sleep(80);
                    return AangUtil.mouse.getCrosshair() == MouseFunc.Crosshair.ACTION;
                }else if( index != -1 ){
                    AangUtil.mouse.click(true);
                    AangUtil.sleep(80);
                }
            }else{
                final int index = AangUtil.menu.getIndex(action,name);
                if( index != -1) {
                    AangUtil.menu.clickMenuOption(index);
                    AangUtil.sleep(80);
                    return AangUtil.mouse.getCrosshair() == MouseFunc.Crosshair.ACTION;
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
        while( tries < 5 && this.valid() && AangUtil.misc.isPointOnScreen(this.getCenterPoint())){
            if( !AangUtil.menu.opened()) {
                AangUtil.mouse.move(this.getCenterPoint());
                AangUtil.sleep(80);
                final int index = AangUtil.menu.getIndex(action,name);
                if( index == 0 ){
                    AangUtil.mouse.click(true);
                    AangUtil.sleep(80);
                    return AangUtil.mouse.getCrosshair() == MouseFunc.Crosshair.ACTION;
                }else{
                    AangUtil.mouse.click(false);
                    AangUtil.sleep(80);
                }
            }else{
                final int index = AangUtil.menu.getIndex(action,name);
                if( index != -1) {
                    AangUtil.menu.clickMenuOption(index);
                    AangUtil.sleep(80);
                    return AangUtil.mouse.getCrosshair() == MouseFunc.Crosshair.ACTION;
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
