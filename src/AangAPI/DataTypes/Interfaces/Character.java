package AangAPI.DataTypes.Interfaces;

import AangAPI.AangUtil;
import AangAPI.DataTypes.Npc;
import AangAPI.DataTypes.Player;
import AangAPI.DataTypes.Tile;
import org.osbot.core.api.Wrapper;
import org.osbot.rs07.accessor.XCharacter;
import org.osbot.rs07.accessor.XNPC;
import org.osbot.rs07.accessor.XPlayer;
import org.osbot.rs07.api.model.Model;

import java.awt.*;
import java.awt.geom.Area;

public abstract class Character extends Interactable implements Locatable, Modeled {
    XCharacter osChar;

    public Character(XCharacter character){
        osChar = character;
    }

    public abstract int getCombatLevel();

    @Override
    public Tile getTile(){
        return AangUtil.movement.gridToTile(osChar.getGridX(),osChar.getGridY());
    }

    @Override
    public int getX() { return (osChar.getGridX()>>7)+AangUtil.client.getMapBaseX(); }

    @Override
    public int getY() { return (osChar.getGridY()>>7)+AangUtil.client.getMapBaseY(); }

    @Override
    public int getZ() { return AangUtil.client.getPlane(); }

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

    public boolean inMotion(){
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
            Area a = m.getArea(getGridX(), getGridY(), AangUtil.client.getPlane());
            if( a != null )
                g.fill(a);
        }
    }

    @Override
    public void drawModel(Graphics2D g){
        Model m = getModel();
        if( m != null ) {
            Area a = m.getArea(getGridX(), getGridY(), AangUtil.client.getPlane());
            if( a != null )
                g.draw(a);
        }
    }

    @Override
    public Rectangle getBoundingBox(){
        return getModel().getBoundingBox(osChar.getGridX(),osChar.getGridY(),AangUtil.client.getPlane());
    }

    @Override
    public Point getCenterPoint(){
        final Rectangle r = getBoundingBox();
        return new Point((int)r.getCenterX(),(int)r.getCenterY());
    }

    @Override
    public Point getRandomPoint(){
        return AangUtil.misc.getRandomPoint(getModel().getArea(getGridX(), getGridY(), AangUtil.client.getPlane()));
    }

    @Override
    public boolean isOnScreen() {
        return AangUtil.misc.isPointOnScreen(getCenterPoint());
    }

    public boolean inCombat(){
        return osChar.getCombatTime() > AangUtil.client.getCurrentTime();
    }

    public boolean canAttack(){
        return !inCombat() || AangUtil.map.isMultiCombat() || AangUtil.localPlayer().isInteracting(this) || isInteracting(AangUtil.localPlayer());
    }

    public boolean isInteracting(Character c){
        return getInteracting().equals(c);
    }

    public Character getInteracting(){
        final int interactingID = osChar.getCharacterFacingUid();
        if(interactingID == -1 )
            return null;
        if( interactingID < 0x8000 ) {
            final XNPC npc = AangUtil.client.getLocalNpcs()[interactingID];
            if( npc != null && npc.getDefinition() != null ) {
                return new Npc(npc);
            }else {
                return null;
            }
        }else{
            final XPlayer player = AangUtil.client.getLocalPlayers()[interactingID - 0x8000];
            if( player != null && player.getDefinition() != null ) {
                return new Player(player);
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
