package aang521.AangAPI.DataTypes.Interfaces;

import org.osbot.rs07.api.model.Model;

import java.awt.*;

public interface Modeled {
    Model getModel();
    void fillModel(Graphics2D g);
    void drawModel(Graphics2D g);
    boolean isOnScreen();
}
