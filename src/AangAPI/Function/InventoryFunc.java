package AangAPI.Function;

import AangAPI.AangUtil;
import AangAPI.DataTypes.Component;
import AangAPI.DataTypes.Item;
import org.osbot.rs07.api.ui.Tab;

import java.util.ArrayList;
import java.util.List;

public class InventoryFunc extends AangUtil {
    private static InventoryFunc ourInstance = new InventoryFunc();

    public static InventoryFunc getInstance() {
        return ourInstance;
    }

    private static final int INVENTORY_WIDGET = 149;

    private Component component(){
        return widgets.get(INVENTORY_WIDGET,0);
    }

    public boolean opened() {
        return tabs.opened() == Tab.INVENTORY;
    }

    public int count(){
        int ret = 0;
        for( int i : component().getItemIDs() )
            if( i != -1 )
                ret++;
        return ret;
    }

    public int count(int id){
        int ret = 0;
        for( int i : component().getItemIDs() )
            if( i != -1 && i == id )
                ret++;
        return ret;
    }

    public boolean full(){
        return count() == 28;
    }

    public boolean contains(int id){
        for( int i : component().getItemIDs() )
        if( i == id )
            return true;
        return false;
    }

    public Item getFirst(int id){
        Component c = component();
        for(int i = 0; i < c.getItemIDs().length; i++ )
            if( c.getItemIDs()[i] == id )
                return new Item(c,i);
        return null;
    }

    public AangAPI.DataTypes.Item[] getAll(){
        Component c = component();
        List<Item> ret = new ArrayList<>();
        for(int i = 0; i < c.getItemIDs().length; i++ )
            if( c.getItemIDs()[i] > 0 )
                ret.add(new Item(c,i,c.getItemIDs()[i],c.getItemStackSizes()[i]));
        return ret.toArray(new Item[ret.size()]);
    }

    public boolean itemSelected() {
        return client.getSelectedItemState() == 1;
    }

    public String getSelectedItemName() {
        return client.getSelectedItemName();
    }

    public void unselectItem() {
        mouse.move(530,150);
        sleep(50);
        mouse.click(true);
    }

    public boolean clickItem( Item item, String action ){
        while( item != null ) {
            int index = menu.getIndex(action, item.getName());
            if( !menu.opened()) {
                mouse.move(item.getCenterPoint());
                sleep(50);
                index = menu.getIndex(action, item.getName());
                if( index == 0)
                    return mouse.click(true);
                else
                    mouse.click(false);
            }else{
                if( index != -1 )
                    return menu.clickMenuOption(index);
                else
                    menu.close();
                return false;
            }
        }
        return false;
    }

    public boolean selectItem(Item item){
        return clickItem(item,"Use");
    }

    public boolean clickItem( final Item item) {
        if( item != null ) {
            mouse.move(item.getCenterPoint());
            sleep(50);
            if( menu.names()[0].equals(item.getName()))
                return mouse.click(true);
        }
        return false;
    }
}
