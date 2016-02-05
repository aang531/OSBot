package AangAPI.DataTypes;

import AangAPI.AangUtil;
import AangAPI.DataTypes.Interfaces.Character;
import AangAPI.DataTypes.Interfaces.Locatable;
import org.osbot.rs07.accessor.XNPC;
import org.osbot.rs07.api.def.NPCDefinition;
import org.osbot.rs07.api.model.NPC;

import java.awt.*;

public class Npc extends Character {
    public XNPC osnpc;
    NPCDefinition def;

    public Npc(XNPC osnpc){
        super(osnpc);
        this.osnpc = osnpc;
        final int id = osnpc.getDefinition().getId();
        def = NPCDefinition.forId(id);
        if( def.getRealId() != id && NPCDefinition.forId(def.getRealId()) != null )
            def = NPCDefinition.forId(def.getRealId());
    }

    public Rectangle bounds(){
        return new NPC(osnpc).model.getBoundingBox(osnpc.getGridX(),osnpc.getGridY(), AangUtil.client.getPlane());
    }

    public int getID(){
        return def.getId();
    }

    @Override
    public int getCombatLevel() {
        return def.getLevel();
    }

    @Override
    public String getName() {
        return def.getName();
    }

    @Override
    public boolean valid() {
        return osnpc.getDefinition() != null;
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

    public String[] getActions(){
        return def.getActions();
    }

    public boolean isVisible(){
        return def.isVisible();
    }
}
