package aang521.AangAPI.Function;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.AangUtil;
import aang521.AangAPI.DataTypes.Component;
import aang521.AangAPI.DataTypes.Item;

import java.util.ArrayList;
import java.util.List;

public class BankFunc extends AangUtil {

    public static final int WIDGET_BANK = 12;
    public static final int COMPONENT_TABS = 10;
    public static final int COMPONENT_ITEMS = 12;
    public static final int COMPONENT_DEPOSIT_ALL = 27;
    public static final int COMPONENT_DEPOSIT_ARMOR = 29;

    public BankFunc(AangScript script) {
        super(script);
    }

    public boolean opened(){
        return widgets.get(WIDGET_BANK).active();
    }

    public boolean close(){
        Component c = widgets.get(WIDGET_BANK,3,11);
        return c.click(true);
    }

    public boolean notedMode(){
        return widgets.get(WIDGET_BANK,23).getSpriteIndex1() == 813;
    }

    public boolean insertMode(){
        return widgets.get(WIDGET_BANK,18).getSpriteIndex1() == 813;
    }

    public boolean setNotedMode(boolean active ){
        if( active )
            return widgets.get(WIDGET_BANK,23).click(true);
        else
            return widgets.get(WIDGET_BANK,21).click(true);
    }

    public boolean setInsertMode(boolean active ){
        if( active )
            return widgets.get(WIDGET_BANK,18).click(true);
        else
            return widgets.get(WIDGET_BANK,16).click(true);
    }

    public boolean depositAll(){
        return widgets.clickComponent(WIDGET_BANK,COMPONENT_DEPOSIT_ALL);
    }

    public boolean depositAll(int id ){
        return inventory.clickItem(inventory.getFirst(id),"Deposit-All");
    }

    public boolean depositAllExcept(int id){
        if( !inventory.contains(id) )
            depositAll();
        Item[] items = inventory.getAll();
        boolean ret = true;
        List<Integer> doneIDs = new ArrayList<>();
        itemLoop: for( Item i : items ) {
            for (Integer doneID : doneIDs)
                if (doneID == i.getID()){
                    continue itemLoop;
                }
            if (i.getID() != id)
                if (inventory.getCount(i.getID()) == 1) {
                    if (!i.click("Deposit-1"))
                        ret = false;
                    sleep(100);
                } else {
                    if (!i.click("Deposit-All"))
                        ret = false;
                    else
                        doneIDs.add(i.getID());
                    sleep(100);
                }
        }
        return ret;
    }

    public boolean depositArmor(){
        return widgets.clickComponent(WIDGET_BANK,COMPONENT_DEPOSIT_ARMOR);
    }

    public boolean withdraw(int id, int amount){
        final Component c = getItemComponent(id);
        if( c == null )
            return false;
        final Component itemWindow = widgets.get(WIDGET_BANK,COMPONENT_ITEMS);
        if(c.getPosition().y < itemWindow.getPosition().y || c.getPosition().y + c.getHeight() > itemWindow.getPosition().y + itemWindow.getHeight() ) {
            mouse.move(itemWindow.getCenterPoint());
            while (c.getPosition().y < itemWindow.getPosition().y || c.getPosition().y + c.getHeight() > itemWindow.getPosition().y + itemWindow.getHeight()) {
                if (c.getPosition().y < itemWindow.getPosition().y)
                    mouse.scrollDown();
                else
                    mouse.scrollUp();
            }
        }

        if( amount == 1 || amount == 5 || amount == 10)
            return widgets.clickComponentItem(c,"Withdraw-"+amount);

        mouse.move(c.getCenterPoint());
        sleep(80);
        mouse.click(false);
        sleep(80);
        final int index = menu.getActionIndex("Withdraw-"+amount);
        if( index == -1 ) {
            if( !widgets.clickComponentItem(c, "Withdraw-X") )
                return false;
            sleep(chat::pendingInput, 100, 30 );
            if( chat.pendingInput() ){
                keyboard.sendln(""+amount);
                sleep(80);
                return true;
            }
        } else
            return menu.clickMenuOption(index);

        return false;
    }

    public boolean withdrawAll(int id){
        Component c = getItemComponent(id);
        if( c == null )
            return false;
        final Component itemWindow = widgets.get(WIDGET_BANK,COMPONENT_ITEMS);
        if(c.getPosition().y < itemWindow.getPosition().y || c.getPosition().y + c.getHeight() > itemWindow.getPosition().y + itemWindow.getHeight() ) {
            mouse.move(itemWindow.getCenterPoint());
            while (c.getPosition().y < itemWindow.getPosition().y || c.getPosition().y + c.getHeight() > itemWindow.getPosition().y + itemWindow.getHeight()) {
                if (c.getPosition().y < itemWindow.getPosition().y)
                    mouse.scrollDown();
                else
                    mouse.scrollUp();
            }
        }
        return widgets.clickComponentItem(c,"Withdraw-All");
    }

    private Component getItemComponent(int id){
        Component c = widgets.get(WIDGET_BANK,COMPONENT_ITEMS);
        for( int i = 0; i < c.childCount(); i++ ){
            if( c.childs()[i].getItemID() == id ) {
                return c.get(i);
            }
        }
        return null;
    }

    public int getCount(int id){
        Component c = widgets.get(WIDGET_BANK,COMPONENT_ITEMS);
        for( int i = 0; i < c.childCount(); i++ )
            if( c.childs()[i].getItemID() == id )
                return c.get(i).getItemStackSize();
        return 0;
    }

    public boolean contains(int id){
        Component c = widgets.get(WIDGET_BANK,COMPONENT_ITEMS);
        for( int i = 0; i < c.childCount(); i++ )
            if( c.childs()[i].getItemID() == id )
                return true;
        return false;
    }
}
