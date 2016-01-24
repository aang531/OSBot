package AangJewel;

import AangAPI.AangScript;
import AangAPI.DataTypes.Npc;
import AangAPI.DataTypes.RSObject;
import AangAPI.DataTypes.Tile;
import org.osbot.rs07.accessor.XClippingPlane;
import org.osbot.rs07.api.model.Model;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.util.GraphicUtilities;
import org.osbot.rs07.input.mouse.EntityDestination;
import org.osbot.rs07.script.RandomBehaviourHook;
import org.osbot.rs07.script.RandomEvent;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;

@ScriptManifest(name = "AangJewel", author = "Aang521", version = 1.0, info = "", logo = "")
public class AangJewel extends AangScript{

    Npc test;

    @Override
    public void init(){
        bot.getRandomExecutor().unregisterHook(RandomEvent.AUTO_LOGIN);
        bot.setHumanInputEnabled(true);

        test = npcs.getNearest(3087);
    }

    @Override
    public int onLoop() {
        //random(123);
        //TODO research: getClient().accessor
//getObjects().getAll();
        //TODO set mousespeed on 0 for hopping maybe
        if( test == null || !test.valid() )
            test = npcs.getNearest(3087);
        return 10;
    }

    @Override
    public void repaint(Graphics2D g){
 //osChar.getCombatTime()
        //osChar.splatTime()

        g.drawString("Time: "+client.getCurrentTime(),5,80);

        if( test != null ) {
            g.drawString("test valid: " + test.valid(), 5, 100);
            if (test.valid()) {
                test.drawModelOutline(g);
                client.getCurrentRegion().getObjects();
                //getMap().getRegion().getClippingPlanes().
                g.drawString("hp: " + test.osnpc.getHealth(), 5, 120);
                g.drawString("combatTime: " + test.osnpc.getCombatTime(), 5, 140);
                g.drawString("Visible: " + test.osnpc.getDefinition().getVisible(), 5, 160);
                g.drawString("Max hp: "+test.osnpc.getMaxHealth(),5 , 180);
                for( int i = 0; i < test.osnpc.getSplatTime().length; i++ )
                    g.drawString("SplatTime: "+test.osnpc.getSplatTime()[i], 5, 200+i*20);
            }
        }

        /*XClippingPlane plane = getMap().getRegion().getClippingPlanes()[localPlayer().getTile().z];
        int[][] flags = plane.getTileFlags();
        for( int y = 0; y < flags.length; y++ )
            for( int x = 0; x < flags[y].length; x++ )
            {
                if( flags[x][y] == 0 )
                    g.setColor(new Color(0,255,0,100));
                else {
                    g.setColor(new Color(255,0,0,100));
                }
                g.fillPolygon(new Tile(x+client.getMapBaseX(),y+client.getMapBaseY(),client.getPlane()).polygon());
                if( flags[x][y] == 0 ) {
                    g.setColor(Color.green);
                }
                else {
                    g.setColor(Color.red);
                }
                g.fillRect(x*3,y*3,3,3);
            }
*/
        //g.drawRect((int)myPlayer().getPosition().getPolygon(bot).getBounds().getCenterX(),
        //        (int)myPlayer().getPosition().getPolygon(bot).getBounds().getCenterY(),2,2);

        //g.drawString(""+myPlayer().getPosition(),5,88);
        //g.drawString(((myPlayer().accessor.getLocalX()>>7)+client.getMapBaseX())+", "+((myPlayer().accessor.getGridY()>>7)+client.getMapBaseY())+
        //        ", "+client.getPlane(), 5, 100);

        //g.drawString("getSelectedItemState "+client.getSelectedItemState(),5,120);
    }
}
