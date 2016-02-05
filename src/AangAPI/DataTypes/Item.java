package AangAPI.DataTypes;

import AangAPI.DataTypes.Interfaces.Interactable;
import org.osbot.rs07.api.def.ItemDefinition;

import java.awt.*;

public class Item extends Interactable {
    Component component;
    int index;
    int id, amount;
    ItemDefinition def;

    public Item( Component component, int index, int id , int amount ){
        this.id = id;
        this.amount = amount;
        this.index = index;
        this.component = component;
        if( id > 0 )
            def = ItemDefinition.forId(id);
        else
            def = null;
    }

    public Item( Component c, int id, int amount ){
        this(c,-1,id,amount);
    }

    public Item(Component c){
        this(c,-1,c.getItemID(),c.getItemStackSize());
    }

    public Item(Component c, int index){
        this(c,index,c.getItemIDs()[index],c.getItemStackSizes()[index]);
    }

    @Override
    public String getName() {
        return def.getName();
    }

    @Override
    public Rectangle getBoundingBox() {
        if( index == -1 )
            return component.getBounds();

        Point base = component.getPosition();
        int x = base.x + (index % 4) * 42;
        int y = base.y + (index / 4) * 36;
        return new Rectangle(x,y,31,31);
    }

    @Override
    public Point getCenterPoint() {
        Point base = component.getPosition();
        int x = base.x + (index % 4) * 42;
        int y = base.y + (index / 4) * 36;
        return new Point(x+16,y+16);
    }

    @Override
    public boolean valid() {
        return def != null && component.valid() && (index != -1 ? component.getItemIDs()[index] == id : component.getItemID() == id);
    }

    public int getStackSize(){
        amount = (index != -1 ? component.getItemStackSizes()[index] : component.getItemStackSize() );
        return amount;
    }

    public int getID(){
        return id;
    }

    public int getNotedID(){
        return def.getNotedId();
    }

    public int getUnnotedID() {
        return def.getUnnotedId();
    }

    public boolean isNoted(){
        return def.getNotedId() != -1;
    }

    public Component getComponent(){
        return component;
    }

    public int getIndex(){
        return index;
    }
}
