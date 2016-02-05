package AangAPI.DataTypes.Interfaces;

import AangAPI.DataTypes.Tile;

public interface Locatable {
    Tile getTile();
    int getGridX();
    int getGridY();
    int getX();
    int getY();
    int getZ();
    int getLocalX();
    int getLocalY();
}
