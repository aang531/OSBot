package aang521.AangAPI.DataTypes;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.DataTypes.Interfaces.Character;
import org.osbot.rs07.accessor.XNPC;
import org.osbot.rs07.api.def.NPCDefinition;
import org.osbot.rs07.api.model.NPC;

import java.awt.*;
import java.util.*;

public class Npc extends Character {
    public XNPC osnpc;
    NPCDefinition def;

    public Npc(AangScript script, XNPC osnpc){
        super(script, osnpc);
        this.osnpc = osnpc;
        final int id = osnpc.getDefinition().getId();
        def = NPCDefinition.forId(id);
        if( def.getRealId() != id && NPCDefinition.forId(def.getRealId()) != null )
            def = NPCDefinition.forId(def.getRealId());
    }

    public Rectangle bounds(){
        return new NPC(osnpc).model.getBoundingBox(osnpc.getGridX(),osnpc.getGridY(), script.client.getPlane());
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
        return clickIntractableCC("Use", script.inventory.getSelectedItemName() + " -> " + this.getName() + (this.getCombatLevel() != 0 ? "  (level-" + this.getCombatLevel() + ")" : ""));
    }

    public String[] getActions(){
        java.util.List<String> ret = new ArrayList<>();
        String[] tmp = def.getActions();
        for( String s : tmp )
            if( s!=null )
                ret.add(s);
        return ret.toArray(new String[ret.size()]);
    }

    public boolean isVisible(){
        return def.isVisible();
    }
}
