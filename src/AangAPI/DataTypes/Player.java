package AangAPI.DataTypes;

import AangAPI.DataTypes.Interfaces.Character;
import org.osbot.rs07.accessor.XPlayer;

public class Player extends Character {
    public XPlayer osplayer;

    public Player( XPlayer osplayer){
        super(osplayer);
        this.osplayer = osplayer;
    }

    @Override
    public int getCombatLevel() {
        return osplayer.getCombatLevel();
    }

    @Override
    public String getName() {
        return osplayer.getName();
    }

    @Override
    public boolean valid() {
        return osplayer.getDefinition() != null;
    }

    public int[] getAppearance(){
        return osplayer.getDefinition().getAppearance();
    }
}
