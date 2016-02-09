package aang521.AangAPI.DataTypes.Interfaces;

import aang521.AangAPI.AangUtil;
import aang521.AangAPI.AangScript;
import aang521.AangAPI.Function.MouseFunc;

import java.awt.*;
import java.util.Arrays;

public abstract class Interactable extends AangDataType {

    public abstract String getName();//TODO move this somewhere else
    public abstract Rectangle getBoundingBox();
    public abstract Point getCenterPoint();
    public abstract boolean valid();

    public Interactable( AangScript script ){
        super(script);
    }

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
        return clickInteractableCenterCC("Use", script.inventory.getSelectedItemName() + " -> " + this.getName());
    }

    public boolean clickIntractableCC(String action, String name ){
        int tries = 0;
        Point p = this.getRandomPoint();
        while( tries < 5 && this.valid()){
            if( !script.misc.isPointInViewport(p) ){
                tries++;
                p = this.getRandomPoint();
                continue;
            }
            if( !script.menu.opened()) {
                script.mouse.move(p);
                final int index = script.menu.getIndex(action,name);
                if( index == script.menu.count()-1 ){
                    script.mouse.click(true);
                    script.sleep(80);
                    return script.mouse.getCrosshair() == MouseFunc.Crosshair.ACTION;
                }else if( index != -1 ){
                    script.mouse.click(false);
                    script.sleep(80);
                }
            }else{
                final int index = script.menu.getIndex(action,name);
                if( index != -1) {
                    script.menu.clickMenuOption(index);
                    script.sleep(80);
                    return script.mouse.getCrosshair() == MouseFunc.Crosshair.ACTION;
                }else {
                    script.menu.close();
                    script.sleep(80);
                }
            }
            tries++;
            p = this.getRandomPoint();
        }
        return false;
    }

    public boolean clickInteractableCenterCC(String action, String name ){
        int tries = 0;
        while( tries < 5 && this.valid() && script.misc.isPointInViewport(this.getCenterPoint())){
            if( !script.menu.opened()) {
                if( !this.getBoundingBox().contains(script.mouse.getPosition()))
                    script.mouse.move(this.getCenterPoint());
                final int index = script.menu.getIndex(action,name);
                if( script.menu.isTooltip(action,name) ){//TODO make this for the other click methods aswell
                    script.mouse.click(true);
                    script.sleep(80);
                    return script.mouse.getCrosshair() == MouseFunc.Crosshair.ACTION;
                }else{
                   script.mouse.click(false);
                    script.sleep(80);
                }
            }else{
                final int index = script.menu.getIndex(action,name);
                if( index != -1) {
                    script.menu.clickMenuOption(index);
                    script.sleep(80);
                    return script.mouse.getCrosshair() == MouseFunc.Crosshair.ACTION;
                }else {
                    script.menu.close();
                    script.sleep(80);
                }
            }
            tries++;
        }
        return false;
    }

    public boolean clickInteractableCenterWholeScreen(String action, String name ){
        int tries = 0;
        while( tries < 5 && this.valid() && script.misc.isPointOnScreen(this.getCenterPoint())){
            if( !script.menu.opened()) {
                script.mouse.move(this.getCenterPoint());
                script.sleep(50);
                final int index = script.menu.getIndex(action,name);
                if( index == script.menu.count()-1 ){
                    return script.mouse.click(true);
                }else{
                    script.mouse.click(false);
                    script.sleep(80);
                }
            }else{
                final int index = script.menu.getIndex(action,name);
                if( index != -1) {
                    return script.menu.clickMenuOption(index);
                }else {
                    script.menu.close();
                    script.sleep(80);
                }
            }
            tries++;
        }
        return false;
    }
}
