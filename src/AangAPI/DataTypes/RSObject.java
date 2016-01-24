package AangAPI.DataTypes;

import AangAPI.AangUtil;
import org.osbot.rs07.api.def.ObjectDefinition;
import org.osbot.rs07.api.model.Model;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.util.GraphicUtilities;

import java.awt.*;

public class RSObject extends Intractable {
    public final RS2Object osObject;
    private ObjectDefinition def;

    public RSObject(RS2Object osObject){
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
    public void drawModel(Graphics2D g) {
        g.fill(GraphicUtilities.getModelArea(AangUtil.script.bot, getGridX(), getGridY(), AangUtil.client.getPlane(), getModel()));
    }

    @Override
    public void drawModelOutline(Graphics2D g) {
        g.draw(GraphicUtilities.getModelArea(AangUtil.script.bot, getGridX(), getGridY(), AangUtil.client.getPlane(), getModel()));
    }

    @Override
    public Rectangle getBoundingBox(){
        return getModel().getBoundingBox(osObject.getGridX(),osObject.getGridY(), osObject.getZ());
    }

    @Override
    public Point getCenterPoint() {
        Rectangle bb = getBoundingBox();
        return new Point((int)bb.getCenterX(),(int)bb.getCenterY());
    }

    @Override
    public boolean valid() {
        return osObject.getDefinition() != null;
    }

    @Override
    public Point getRandomPoint() {
        return null;//TODO
    }

    @Override
    public Tile getTile() {
        return new Tile(osObject.getGridX(), osObject.getGridY(), osObject.getZ());
    }

    @Override
    public int getGridX() {
        return osObject.getGridX();
    }

    @Override
    public int getGridY() {
        return osObject.getGridY();
    }
}
