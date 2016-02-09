package aang521.AangAPI.DataTypes;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.DataTypes.Interfaces.Interactable;
import aang521.AangAPI.DataTypes.Interfaces.Locatable;
import aang521.AangAPI.DataTypes.Interfaces.Modeled;
import org.osbot.rs07.api.def.ObjectDefinition;
import org.osbot.rs07.api.model.Model;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.util.GraphicUtilities;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RSObject extends Interactable implements Locatable, Modeled {
    public final RS2Object osObject;
    private ObjectDefinition def;

    public RSObject(AangScript script, RS2Object osObject){
        super(script);
        this.osObject = osObject;
        def = osObject.getDefinition();
    }

    public int getID(){
        return osObject.getId();
    }

    @Override
    public String getName() {
        return osObject.getName();
    }

    @Override
    public Model getModel(){
        return osObject.getModel();
    }

    @Override
    public void fillModel(Graphics2D g) {
        g.fill(GraphicUtilities.getModelArea(script.bot, getGridX(), getGridY(), script.client.getPlane(), getModel()));
    }

    @Override
    public void drawModel(Graphics2D g) {
        g.draw(GraphicUtilities.getModelArea(script.bot, getGridX(), getGridY(), script.client.getPlane(), getModel()));
    }

    @Override
    public boolean isOnScreen() {
        return script.misc.isPointInViewport(getCenterPoint());
    }

    @Override
    public Rectangle getBoundingBox(){
        return getModel().getBoundingBox(osObject.getGridX(),osObject.getGridY(), osObject.getZ());
    }

    @Override
    public Point getCenterPoint() {
        Rectangle bb = getBoundingBox();
        if(bb == null )
            return null;
        return new Point((int)bb.getCenterX(),(int)bb.getCenterY());
    }

    @Override
    public boolean valid() {
        return osObject.exists();
    }

    @Override
    public Point getRandomPoint() {
        return script.misc.getRandomPoint(getModel().getArea(getGridX(), getGridY(), osObject.getZ()));
    }

    @Override
    public Tile getTile() {
        return new Tile(script, getX(),getY(), osObject.getZ());
    }

    @Override
    public int getGridX() {
        return osObject.getGridX();
    }

    @Override
    public int getGridY() {
        return osObject.getGridY();
    }

    @Override
    public int getLocalX(){
        return osObject.getLocalX();
    }

    @Override
    public int getLocalY(){
        return osObject.getLocalY();
    }

    @Override
    public int getX() { return (osObject.getGridX()>>7)+ script.client.getMapBaseX(); }

    @Override
    public int getY() { return (osObject.getGridY()>>7)+ script.client.getMapBaseY(); }

    @Override
    public int getZ() { return osObject.getZ(); }

    public String[] getActions(){
        List<String> ret = new ArrayList<>();
        String[] tmp = def.getActions();
        for( String s : tmp )
            if( s!=null )
                ret.add(s);
        return ret.toArray(new String[ret.size()]);
    }

    public int getOrientation(){
        return osObject.getOrientation();
    }

    //isBlockingDecoration
    public boolean isBlockingDecoration(){
        return def.isClipping1();
    }

    //0 can step on
    //1 blocks a direction can step on //not always
    //2 blocks tile
    public int getClipping2(){
        return def.getClipping2();
    }

    public boolean blocksProjectiles(){
        return def.isClipping3();
    }

    public int getWalkToData(){
        return def.getWalkToData();
    }

    public int getConfig(){
        return osObject.getConfig();
    }

    public int getType(){
        return osObject.getType();
    }
}
