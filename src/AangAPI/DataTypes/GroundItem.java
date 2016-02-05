package AangAPI.DataTypes;

import AangAPI.AangUtil;
import AangAPI.DataTypes.Interfaces.Interactable;
import AangAPI.DataTypes.Interfaces.Locatable;
import AangAPI.DataTypes.Interfaces.Modeled;
import org.osbot.core.api.Wrapper;
import org.osbot.rs07.accessor.XGroundItem;
import org.osbot.rs07.accessor.XNodeDeque;
import org.osbot.rs07.api.def.ItemDefinition;
import org.osbot.rs07.api.model.*;
import org.osbot.rs07.api.util.GraphicUtilities;
import org.osbot.rs07.api.util.NodeDequeIterator;

import java.awt.*;

public class GroundItem extends Interactable implements Modeled, Locatable {
    public final XGroundItem osGroundItem;
    private final Tile tile;
    private final ItemDefinition def;

    public GroundItem(XGroundItem osGroundItem, Tile tile){
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
        g.fill(GraphicUtilities.getModelArea(AangUtil.script.bot, getGridX(), getGridY(), AangUtil.client.getPlane(), getModel()));
    }

    @Override
    public void drawModel(Graphics2D g){
        g.draw(GraphicUtilities.getModelArea(AangUtil.script.bot, getGridX(), getGridY(), AangUtil.client.getPlane(), getModel()));
    }

    @Override
    public boolean isOnScreen() {
        return AangUtil.misc.isPointOnScreen(getCenterPoint());
    }

    @Override
    public Rectangle getBoundingBox(){
        return getModel().getBoundingBox(getGridX(),getGridY(),AangUtil.client.getPlane());
    }

    @Override
    public Point getCenterPoint(){
        final Rectangle r = getBoundingBox();
        return new Point((int)r.getCenterX(),(int)r.getCenterY());
    }

    @Override
    public boolean valid() {
        XNodeDeque que = AangUtil.client.getGroundItemDeques()[tile.z][tile.getLocalX()][tile.getLocalY()];
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
        return AangUtil.misc.getRandomPoint(getModel().getArea(getGridX(), getGridY(), tile.z));
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
        return def.getGroundActions();
    }

    public boolean pickup() {
        return clickInteractableCenterCC("Take",getName());
    }
}
