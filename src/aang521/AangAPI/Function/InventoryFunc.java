package aang521.AangAPI.Function;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.AangUtil;
import aang521.AangAPI.DataTypes.Component;
import aang521.AangAPI.DataTypes.Item;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;

import java.util.ArrayList;
import java.util.List;

public class InventoryFunc extends AangUtil {

    private static final int INVENTORY_WIDGET = 149;

    public InventoryFunc(AangScript script) {
        super(script);
    }

    public Component getComponent(){
        return widgets.get(INVENTORY_WIDGET,0);
    }

    public boolean isOpen() {
        return tabs.opened() == Tab.INVENTORY;
    }

    public void open(){
        tabs.openInventory();
    }

    public int getCount(){
        int ret = 0;
        for( int i : getComponent().getItemIDs() )
            if( i != -1 )
                ret++;
        return ret;
    }

    public int getCount(int id){
        int ret = 0;
        for( int i : getComponent().getItemIDs() )
            if( i != -1 && i == id )
                ret++;
        return ret;
    }

    public boolean isFull(){
        return getCount() == 28;
    }

    public boolean contains(int id){
        for( int i : getComponent().getItemIDs() )
        if( i == id )
            return true;
        return false;
    }

    public Item getFirst(int id){
        Component c = getComponent();
        for(int i = 0; i < c.getItemIDs().length; i++ )
            if( c.getItemIDs()[i] == id )
                return new Item(script, c,i);
        return null;
    }

    public Item getLast( int id ){
        Component c = getComponent();
        for(int i = c.getItemIDs().length-1; i >= 0; i++ )
            if( c.getItemIDs()[i] == id )
                return new Item(script, c,i);
        return null;
    }

    public Item get(int id){
        return getFirst(id);
    }

    public Item[] getAll(){
        Component c = getComponent();
        List<Item> ret = new ArrayList<>();
        for(int i = 0; i < c.getItemIDs().length; i++ )
            if( c.getItemIDs()[i] > 0 )
                ret.add(new Item(script, c,i,c.getItemIDs()[i],c.getItemStackSizes()[i]));
        return ret.toArray(new Item[ret.size()]);
    }

    public Item getInSlot(int index){
        Component c = getComponent();
        if( c.getItemIDs()[index] > 0 )
            return new Item(script, c,index,c.getItemIDs()[index],c.getItemStackSizes()[index]);
        return null;
    }

    public Item getAt(int index){
        return getInSlot(index);
    }

    public boolean itemSelected() {
        return client.getSelectedItemState() == 1;
    }

    public String getSelectedItemName() {
        return Script.stripFormatting(client.getSelectedItemName());
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
            if( menu.names()[menu.count()-1].equals(item.getName()))
                return mouse.click(true);
        }
        return false;
    }

    public boolean dragItem( int fromSlot, int toSlot ){
        return false;//TODO implement dragging item to other slot
    }
}
