package AangAPI.DataTypes;

import AangAPI.AangUtil;
import org.osbot.rs07.accessor.XRS2Widget;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.ui.RS2Widget;

import java.awt.*;

public class Component {
    public XRS2Widget osWidget;
    private int thirdLevelID = -1;

    public Component(XRS2Widget osWidget) {
        this.osWidget = osWidget;
    }

    public Component(XRS2Widget osWidget, int thirdLevelID) {
        this.osWidget = osWidget;
        this.thirdLevelID = thirdLevelID;
    }

    public Point randomPoint(){
        return new Point(position().x+ AangUtil.random(width()),position().y + AangUtil.random(height()));
    }

    public Point centerPoint(){
        return new Point(position().x + (width() >> 1), position().y + (height() >> 1));
    }

    public Point position(){
        return new Point(osWidget.getX(), osWidget.getY());
    }

    public int width(){
        return osWidget.getWidth();
    }

    public int height(){
        return osWidget.getHeight();
    }

    public Rectangle bounds(){
        return new Rectangle(osWidget.getX(), osWidget.getY(),osWidget.getWidth(), osWidget.getHeight());
    }

    public int itemID(){
        return osWidget.getItemId();
    }

    public int[] itemIDs() {
        return osWidget.getInv();
    }

    public int[] itemStackSizes() { return osWidget.getInvStackSizes(); }

    public Item item(){
        return new Item(new RS2Widget(osWidget,thirdLevelID),osWidget.getItemId(),osWidget.getItemAmt());
    }

    public boolean valid(){
        return osWidget != null;
    }

    public String text(){
        return osWidget.getMessage();
    }

    public Component get(int i){
        return new Component(osWidget.getChildren()[i],i);
    }

    public int childCount(){
        return osWidget.getChildren().length;
    }

    public Component[] childs(){
        XRS2Widget[] osWidgets = osWidget.getChildren();
        Component[] ret = new Component[osWidgets.length];
        for (int i = 0; i < osWidgets.length; i++ ) {
            ret[i] = new Component(osWidgets[i]);
        }
        return ret;
    }
}
