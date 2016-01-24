package AangAPI.DataTypes;

import AangAPI.AangUtil;
import org.osbot.core.api.Wrapper;
import org.osbot.rs07.Bot;
import org.osbot.rs07.accessor.XCharacter;
import org.osbot.rs07.accessor.XNPC;
import org.osbot.rs07.accessor.XPlayer;
import org.osbot.rs07.api.model.Model;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.util.GraphicUtilities;

import java.awt.*;

public abstract class Character extends Intractable {
    XCharacter osChar;

    public Character(XCharacter character){
        osChar = character;
    }

    public abstract int getCombatLevel();

    public Tile getTile(){
        return AangUtil.movement.gridToTile(osChar.getGridX(),osChar.getGridY());
    }

    public int getGridX(){
        return osChar.getGridX();
    }

    public int getGridY(){
        return osChar.getGridY();
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
    public void drawModel(Graphics2D g){
        g.fill(GraphicUtilities.getModelArea(AangUtil.script.bot, getGridX(), getGridY(), AangUtil.client.getPlane(), getModel()));
    }

    @Override
    public void drawModelOutline(Graphics2D g){
        g.draw(GraphicUtilities.getModelArea(AangUtil.script.bot, getGridX(), getGridY(), AangUtil.client.getPlane(), getModel()));
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
}
