package aang521.AangAPI.DataTypes.Interfaces;

import aang521.AangAPI.DataTypes.Npc;
import aang521.AangAPI.DataTypes.Player;
import aang521.AangAPI.DataTypes.Tile;
import aang521.AangAPI.AangScript;
import org.osbot.core.api.Wrapper;
import org.osbot.rs07.accessor.XCharacter;
import org.osbot.rs07.accessor.XNPC;
import org.osbot.rs07.accessor.XPlayer;
import org.osbot.rs07.api.model.Model;

import java.awt.*;
import java.awt.geom.Area;

public abstract class Character extends Interactable implements Locatable, Modeled {
    XCharacter osChar;

    public Character(AangScript script, XCharacter character ){
        super(script);
        osChar = character;
    }

    public abstract int getCombatLevel();

    @Override
    public Tile getTile(){
        return script.movement.gridToTile(osChar.getGridX(),osChar.getGridY());
    }

    @Override
    public int getX() { return (osChar.getGridX()>>7)+ script.client.getMapBaseX(); }

    @Override
    public int getY() { return (osChar.getGridY()>>7)+ script.client.getMapBaseY(); }

    @Override
    public int getZ() { return script.client.getPlane(); }

    @Override
    public int getGridX(){
        return osChar.getGridX();
    }

    @Override
    public int getGridY(){
        return osChar.getGridY();
    }

    @Override
    public int getLocalX(){
        return (osChar.getGridX()>>7);
    }

    @Override
    public int getLocalY(){
        return (osChar.getGridY()>>7);
    }

    public int getHealth(){
        return osChar.getHealth();
    }

    public int getMaxHealth(){
        return osChar.getMaxHealth();
    }

    public boolean isMoving(){
        return osChar.getWalkingQueueSize() > 0;
    }

    @Override
    public Model getModel(){
        org.osbot.rs07.api.model.Character c = (org.osbot.rs07.api.model.Character)Wrapper.wrap(osChar);
        return c.getModel();
    }

    @Override
    public void fillModel(Graphics2D g){
        Model m = getModel();
        if( m != null ) {
            Area a = m.getArea(getGridX(), getGridY(), script.client.getPlane());
            if( a != null )
                g.fill(a);
        }
    }

    @Override
    public void drawModel(Graphics2D g){
        Model m = getModel();
        if( m != null ) {
            Area a = m.getArea(getGridX(), getGridY(), script.client.getPlane());
            if( a != null )
                g.draw(a);
        }
    }

    @Override
    public Rectangle getBoundingBox(){
        return getModel().getBoundingBox(osChar.getGridX(),osChar.getGridY(), script.client.getPlane());
    }

    @Override
    public Point getCenterPoint(){
        final Rectangle r = getBoundingBox();
        return new Point((int)r.getCenterX(),(int)r.getCenterY());
    }

    @Override
    public Point getRandomPoint(){
        return script.misc.getRandomPoint(getModel().getArea(getGridX(), getGridY(), script.client.getPlane()));
    }

    @Override
    public boolean isOnScreen() {
        return script.misc.isPointInViewport(getCenterPoint());
    }

    public boolean inCombat(){
        return osChar.getCombatTime() > script.client.getCurrentTime();
    }

    public boolean canAttack(){
        return !inCombat() || script.map.isMultiCombat() || script.localPlayer().isInteracting(this) || isInteracting(script.localPlayer());
    }

    public boolean isInteracting(Character c){
        return getInteracting().equals(c);
    }

    public Character getInteracting(){
        final int interactingID = osChar.getCharacterFacingUid();
        if(interactingID == -1 )
            return null;
        if( interactingID < 0x8000 ) {
            final XNPC npc = script.client.getLocalNpcs()[interactingID];
            if( npc != null && npc.getDefinition() != null ) {
                return new Npc(script, npc);
            }else {
                return null;
            }
        }else{
            final XPlayer player = script.client.getLocalPlayers()[interactingID - 0x8000];
            if( player != null && player.getDefinition() != null ) {
                return new Player(script, player);
            }
        }
        return null;
    }

    public int getCombatTime(){
        return osChar.getCombatTime();
    }

    public int getAnimation(){
        return osChar.getAnimation();
    }

    public String getHeadMessage(){
        return osChar.getHeadMessage();
    }
}
