package AangAPI.DataTypes;

import AangAPI.AangUtil;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.ui.RS2Widget;

import java.awt.*;

public class Component {
    RS2Widget osWidget;

    public Component(RS2Widget osWidget) {
        this.osWidget = osWidget;
    }

    public Point randomPoint(){
        return new Point(position().x+ AangUtil.random(width()),position().y + AangUtil.random(height()));
    }

    public Point centerPoint(){
        return new Point(position().x + (width() >> 1), position().y + (height() >> 1));
    }

    public Point position(){
        return osWidget.getPosition();
    }

    public int width(){
        return osWidget.getWidth();
    }

    public int height(){
        return osWidget.getHeight();
    }

    public Rectangle bounds(){
        return osWidget.getBounds();
    }

    public int itemID(){
        return osWidget.getItemId();
    }

    public int[] itemIDs() {
        return osWidget.getInv();
    }

    public Item[] items(){
        return osWidget.getItems();
    }

    public Item item(){
        return osWidget.getItems()[0];
    }

    public boolean valid(){
        return osWidget != null;
    }

    public boolean active() {
        return osWidget.isVisible();//TODO might be wrong
    }

    public String text(){
        return osWidget.getMessage();
    }

    public Component get(int i){
        return new Component(osWidget.getChildWidget(i));
    }

    public int childCount(){
        return osWidget.getChildWidgets().length;
    }

    public Component[] childs(){
        RS2Widget[] osWidgets = osWidget.getChildWidgets();
        Component[] ret = new Component[osWidgets.length];
        for (int i = 0; i < osWidgets.length; i++ ) {
            ret[i] = new Component(osWidgets[i]);
        }
        return ret;
    }
}
