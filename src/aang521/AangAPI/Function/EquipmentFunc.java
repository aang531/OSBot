package aang521.AangAPI.Function;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.AangUtil;
import aang521.AangAPI.DataTypes.Item;

public class EquipmentFunc extends AangUtil {
    public static final int EQUIPMENT_WIDGET = 387;

    public static final int HELMET = 6;
    public static final int CAPE = 7;
    public static final int AMULET = 8;
    public static final int WEAPON = 9;
    public static final int CHEST = 10;
    public static final int SHIELD = 11;
    public static final int LEGS = 12;
    public static final int GLOVES = 13;
    public static final int BOOTS = 14;
    public static final int RING = 15;
    public static final int AMMO = 16;

    public EquipmentFunc(AangScript script) {
        super(script);
    }

    public boolean isOpen(){
        return widgets.get(548,49).getSpriteIndex1() != -1;
    }

    public void open(){
        tabs.openEquipmentTab();
    }

    public Item getHelmet(){
        return widgets.get(EQUIPMENT_WIDGET,HELMET,1).getItem();
    }

    public Item getCape(){
        return widgets.get(EQUIPMENT_WIDGET,CAPE,1).getItem();
    }

    public Item getAmulet(){
        return widgets.get(EQUIPMENT_WIDGET,AMULET,1).getItem();
    }

    public Item getWeapon(){
        return widgets.get(EQUIPMENT_WIDGET,WEAPON,1).getItem();
    }

    public Item getChest(){
        return widgets.get(EQUIPMENT_WIDGET,CHEST,1).getItem();
    }

    public Item getShield(){
        return widgets.get(EQUIPMENT_WIDGET,SHIELD,1).getItem();
    }

    public Item getLegs(){
        return widgets.get(EQUIPMENT_WIDGET,LEGS,1).getItem();
    }

    public Item getGloves(){
        return widgets.get(EQUIPMENT_WIDGET,GLOVES,1).getItem();
    }

    public Item getBoots(){
        return widgets.get(EQUIPMENT_WIDGET,BOOTS,1).getItem();
    }

    public Item getRing(){
        return widgets.get(EQUIPMENT_WIDGET,RING,1).getItem();
    }

    public Item getAmmo(){
        return widgets.get(EQUIPMENT_WIDGET,AMMO,1).getItem();
    }

    public Item getItem(int item){
        return widgets.get(EQUIPMENT_WIDGET,item,1).getItem();
    }
}
