package AangAPI.DataTypes;

import AangAPI.AangUtil;
import org.osbot.rs07.accessor.XRS2Widget;
import org.osbot.rs07.api.ui.RS2Widget;

import java.awt.*;

public class Component {
    public RS2Widget osWidget;

    public Component(XRS2Widget osWidget) {
        this.osWidget = new RS2Widget(osWidget,-1);
    }

    public Component(XRS2Widget osWidget, int thirdLevelID) {
        this.osWidget = new RS2Widget(osWidget,thirdLevelID);
    }

    public Point randomPoint(){
        return new Point(getX()+ AangUtil.random(getWidth()), getY() + AangUtil.random(getHeight()));
    }

    public Point centerPoint(){
        return new Point(getX() + (getWidth() >> 1), getY() + (getHeight() >> 1));
    }

    public int getX(){
        return osWidget.getAbsX();
    }

    public int getY(){
        return osWidget.getAbsY();
    }

    public Point getPosition(){
        return new Point(getX(), getY());
    }

    public int getWidth(){
        return osWidget.accessor.getWidth();
    }

    public int getHeight(){
        return osWidget.accessor.getHeight();
    }

    public int getScrollY(){
        return osWidget.accessor.getScrollY();
    }

    public int getScrollMax(){
        return osWidget.accessor.getScrollMax();
    }

    public Rectangle getBounds(){
        return new Rectangle(getX(), getY(),osWidget.accessor.getWidth(), osWidget.accessor.getHeight());
    }

    public int getItemID(){
        return osWidget.accessor.getItemId();
    }

    public int getItemStackSize(){
        return osWidget.accessor.getItemAmt();
    }

    public int[] getItemIDs() {
        int[] ids = osWidget.accessor.getInv().clone();
        for(int i = 0; i < ids.length; i++ )
            ids[i] -= 1;
        return ids;
    }

    public int[] getItemStackSizes() { return osWidget.accessor.getInvStackSizes(); }

    public Item getItem(){
        return new Item(this);
    }

    public boolean valid(){
        return osWidget != null;
    }

    public String getText(){
        return osWidget.accessor.getMessage();
    }

    public Component get(int i){
        return new Component(osWidget.accessor.getChildren()[i],i);
    }

    public int childCount(){
        XRS2Widget[] children = osWidget.accessor.getChildren();
        return children != null ? children.length : 0;
    }

    public Component[] childs(){
        XRS2Widget[] osWidgets = osWidget.accessor.getChildren();
        Component[] ret = new Component[osWidgets.length];
        for (int i = 0; i < osWidgets.length; i++ ) {
            ret[i] = new Component(osWidgets[i]);
        }
        return ret;
    }

    public boolean visable(){
        return AangUtil.widgets.active(getRootID()) && !osWidget.accessor.getHiddenUntilMouseOver();
    }

    public String getSpellName(){
        return osWidget.accessor.getSpellName();
    }

    public String getToolTip(){
        return osWidget.accessor.getTooltip();
    }

    public String[] getActions(){
        return osWidget.accessor.getActions();
    }

    public String[] getOptions(){
        return osWidget.accessor.getOptions();
    }

    public int getType(){
        return osWidget.accessor.getType();
    }

    public int getContentType(){
        return osWidget.accessor.getContentType();
    }

    public int getRootID(){
        return osWidget.accessor.getId() >> 16;
    }

    public int getID(){
        return osWidget.accessor.getId() & 0xffff;
    }

    public int getSpriteIndex1(){
        return osWidget.accessor.getSpriteIndex1();
    }

    public int getSpriteIndex2(){
        return osWidget.accessor.getSpriteIndex2();
    }
}
