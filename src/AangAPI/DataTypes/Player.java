package AangAPI.DataTypes;

import org.osbot.rs07.accessor.XPlayer;

import java.awt.*;

public class Player extends Character{
    XPlayer osplayer;

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

    @Override
    public Point getRandomPoint() {
        return null;//TODO
    }
}
