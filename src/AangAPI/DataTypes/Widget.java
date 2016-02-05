package AangAPI.DataTypes;

import AangAPI.AangUtil;

import java.awt.*;


public class Widget {
    private int id;

    public enum WidgetID{
        EMOTES(216),
        LOGOUT(182),
        CHAT(162),
        AROUND_MINIMAP_WIDGETS(160),
        INVENTORY(149),
        COMBAT(593),
        CLAN_CHAT(589),
        IGNORE_LIST(432),
        FRIENDS_LIST(429),
        EQUIPMENT(387),
        SKILLS(320),
        QUESTS(274),
        PRAYER(271),
        OPTIONS(261),
        MUSIC(239),
        MAGIC(218),
        WILDERNESS(90),
        GUI(548),
        GE_INTERFACE(465),
        GE_INVENTORY(467),
        BANK(12),
        BANK_INVENTORY(15),
        POLL(345),
        DEPOSIT_BOT(192);

        public int id;

        WidgetID(int id){
            this.id = id;
        }
    }

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

    public int childCount(){
        return AangUtil.client.getWidgets()[id].length;
    }

    public int getID(){
        return id;
    }
}
