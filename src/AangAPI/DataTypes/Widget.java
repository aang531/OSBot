package AangAPI.DataTypes;

import AangAPI.AangUtil;
import AangAPI.WidgetFunc;

public class Widget {
    private int id;

    public Widget( int id ){
        this.id = id;
    }

    public boolean active(){
        return AangUtil.widgets.active(this.id);
    }

    public Component get(int component){
        return AangUtil.widgets.get(id,component);
    }

    public Component get(int component, int component2){
        return AangUtil.widgets.get(id,component,component2);
    }
}
