package aang521.AangAPI.DataTypes;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.DataTypes.Interfaces.Interactable;
import aang521.AangAPI.DataTypes.Interfaces.Locatable;
import aang521.AangAPI.DataTypes.Interfaces.Modeled;
import org.osbot.core.api.Wrapper;
import org.osbot.rs07.accessor.XGroundItem;
import org.osbot.rs07.accessor.XNodeDeque;
import org.osbot.rs07.api.def.ItemDefinition;
import org.osbot.rs07.api.model.*;
import org.osbot.rs07.api.util.GraphicUtilities;
import org.osbot.rs07.api.util.NodeDequeIterator;

import java.awt.*;
import java.util.*;

public class GroundItem extends Interactable implements Modeled, Locatable {
    public final XGroundItem osGroundItem;
    private final Tile tile;
    private final ItemDefinition def;

    public GroundItem(AangScript script, XGroundItem osGroundItem, Tile tile){
        super(script);
        this.osGroundItem = osGroundItem;
        this.tile = tile;
        def = ItemDefinition.forId(osGroundItem.getId());
    }

    @Override
    public String getName() {
        return def.getName();
    }

    @Override
    public Model getModel() {
        return (( org.osbot.rs07.api.model.GroundItem) Wrapper.wrap(osGroundItem)).getModel();
    }

    @Override
    public void fillModel(Graphics2D g){
        g.fill(GraphicUtilities.getModelArea(script.bot, getGridX(), getGridY(), script.client.getPlane(), getModel()));
    }

    @Override
    public void drawModel(Graphics2D g){
        g.draw(GraphicUtilities.getModelArea(script.bot, getGridX(), getGridY(), script.client.getPlane(), getModel()));
    }

    @Override
    public boolean isOnScreen() {
        return script.misc.isPointInViewport(getCenterPoint());
    }

    @Override
    public Rectangle getBoundingBox(){
        return getModel().getBoundingBox(getGridX(),getGridY(), script.client.getPlane());
    }

    @Override
    public Point getCenterPoint(){
        final Rectangle r = getBoundingBox();
        if( r != null )
            return new Point((int)r.getCenterX(),(int)r.getCenterY());
        else
            return new Point(-1,-1);
    }

    @Override
    public boolean valid() {
        XNodeDeque que = script.client.getGroundItemDeques()[tile.z][tile.getLocalX()][tile.getLocalY()];
        if( que == null )
            return false;
        NodeDequeIterator it = new NodeDequeIterator();
        it.set(que);
        XGroundItem x;
        while((x = (XGroundItem)it.getNext()) != null)
            if( x.equals(osGroundItem) )
                return true;
        return false;
    }

    @Override
    public Point getRandomPoint() {
        return script.misc.getRandomPoint(getModel().getArea(getGridX(), getGridY(), tile.z));
    }

    @Override
    public Tile getTile() {
        return tile;
    }

    @Override
    public int getGridX() {
        return tile.getGridX();
    }

    @Override
    public int getGridY() {
        return tile.getGridY();
    }

    @Override
    public int getX() { return tile.x; }

    @Override
    public int getY() { return tile.y; }

    @Override
    public int getZ() { return tile.x; }

    @Override
    public int getLocalX(){
        return tile.getLocalX();
    }

    @Override
    public int getLocalY(){
        return tile.getLocalY();
    }

    public int getID(){
        return osGroundItem.getId();
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

    public int getAmount(){
        return osGroundItem.getAmount();
    }

    public String[] getActions(){
        java.util.List<String> ret = new ArrayList<>();
        String[] tmp = def.getGroundActions();
        for( String s : tmp )
            if( s!=null )
                ret.add(s);
        return ret.toArray(new String[ret.size()]);
    }

    public boolean pickup() {
        return clickInteractableCenterCC("Take",getName());
    }
}
