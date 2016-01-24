package AangAPI.DataTypes;

import AangAPI.AangUtil;
import org.osbot.rs07.accessor.XNPC;
import org.osbot.rs07.accessor.XNPCDefinition;
import org.osbot.rs07.api.model.NPC;

import java.awt.*;

public class Npc extends Character {
    public XNPC osnpc;
    XNPCDefinition def;

    public Npc(XNPC osnpc){
        super(osnpc);
        this.osnpc = osnpc;
        def = osnpc.getDefinition();
    }

    public Rectangle bounds(){
        return new NPC(osnpc).model.getBoundingBox(osnpc.getGridX(),osnpc.getGridY(), AangUtil.client.getPlane());
    }

    public int getID(){
        return def.getId();
    }

    @Override
    public int getCombatLevel() {
        return osnpc.getDefinition().getCombatLevel();
    }

    @Override
    public String getName() {
        return def.getName();
    }

    @Override
    public boolean valid() {
        return osnpc.getDefinition() != null;
    }

    @Override
    public Point getRandomPoint() {
        return null;//TODO
    }

    public boolean attack() {
        return clickIntractableCC("Attack",this.getName() + "  (level-" + this.getCombatLevel() + ")");
    }

    @Override
    public boolean click( String action ) {
        return clickIntractableCC(action,this.getName() + (this.getCombatLevel() != 0 ? "  (level-" + this.getCombatLevel() + ")" : ""));
    }

    @Override
    public boolean useItem() {
        return clickIntractableCC("Use",AangUtil.inventory.getSelectedItemName() + " -> " + this.getName() + (this.getCombatLevel() != 0 ? "  (level-" + this.getCombatLevel() + ")" : ""));
    }
}
