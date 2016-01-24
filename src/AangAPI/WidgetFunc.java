package AangAPI;

import AangAPI.DataTypes.*;
import AangAPI.DataTypes.Component;
import org.osbot.rs07.api.model.Item;

import java.awt.*;

public class WidgetFunc extends AangUtil {
    private static WidgetFunc ourInstance = new WidgetFunc();

    public static WidgetFunc getInstance() {
        return ourInstance;
    }

    private WidgetFunc() {
    }

    public Component get(int widget, int component){
        return new Component(client.getWidgets()[widget][component]);
    }

    public Component get(int widget, int component, int component2) {
        return new Component(client.getWidgets()[widget][component].getChildren()[component2],component2);
    }

    public Widget get(int widget) {
        return widget(widget);
    }

    public Widget widget(int widget){
        return get(widget);
    }

    public boolean active(int widget){
        return client.getValidWidgets()[widget];
    }

    public boolean clickComponent(Component c){
        mouse.move(c.randomPoint());
        sleep(60);
        mouse.click();
        return true;
    }

    public boolean clickComponent(Component c, int xoffset, int yoffset){
        Point p = new Point(xoffset,yoffset);
        p.translate(c.position().x,c.position().y);
        mouse.move(p);
        sleep(60);
        mouse.click();
        return true;
    }

    public boolean clickComponent(int widget, int component){
        return clickComponent(get(widget,component));
    }

    public boolean clickComponent(int widget, int component, int component2){
        return clickComponent(get(widget,component, component2));
    }

    public boolean clickComponent(int widget, int component, int xoffset, int yoffset){
        return clickComponent(get(widget,component),xoffset,yoffset);
    }

    public boolean clickComponent(int widget, int component, int component2, int xoffset, int yoffset){
        return clickComponent(get(widget,component,component2),xoffset,yoffset);
    }

    public boolean clickComponentItem(int widget, int component, String action ){
        Component c = get(widget, component);
        return clickComponentItem(c,action);
    }

    public boolean clickComponentItem(Component c, String action ){
        while( c != null && c.valid() && misc.pointOnScreen(c.centerPoint())){
            Item i = c.item();
            int index = menu.getIndex(action,i.getName());
            if( !menu.opened()) {
                mouse.move(c.centerPoint());
                sleep(100);
                index = menu.getIndex(action,i.getName());
                if( index == 0 ){
                    return mouse.click(true);
                }else{
                    mouse.click(false);
                }
            }else{
                if( index != -1)
                    return menu.clickMenuOption(index);
                else
                    menu.close();
                return false;
            }
        }
        return false;
    }
}
