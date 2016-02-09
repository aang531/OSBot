package aang521.AangAPI.DataTypes.Interfaces;

import aang521.AangAPI.DataTypes.Tile;

public interface Locatable {
    Tile getTile();
    int getGridX();
    int getGridY();
    int getX();
    int getY();
    int getZ();
    int getLocalX();
    int getLocalY();

    default int sqrDistTo(Locatable l){
        final int x = this.getX() - l.getX();
        final int y = this.getY() - l.getY();
        return x*x+y*y;
    }

    default double distanceTo(Locatable l){
        return Math.sqrt(sqrDistTo(l));
    }

}
