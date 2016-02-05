package AangJewel;

import AangAPI.AangScript;
import AangAPI.DataTypes.*;
import org.osbot.BotApplication;
import org.osbot.rs07.accessor.XClippingPlane;
import org.osbot.rs07.script.RandomEvent;
import org.osbot.rs07.script.ScriptManifest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.net.MalformedURLException;
import java.net.URL;

@ScriptManifest(name = "AangJewel", author = "Aang521", version = 1.0, info = "", logo = "http://i.imgur.com/GqWzbek.gif")
public class AangJewel extends AangScript{

    static final int pointCount = 4440;
    static final int blurCount = 1300;

    Point2D.Float pos[] = new Point2D.Float[pointCount];
    Point2D.Float rots[] = new Point2D.Float[pointCount];
    float pows[] = new float[blurCount];

    Npc test;

    @Override
    public void scriptStart(){
        bot.getRandomExecutor().unregisterHook(RandomEvent.AUTO_LOGIN);
        bot.setHumanInputEnabled(true);

        test = npcs.getNearest(3087);

        for( float i = 0; i < pointCount; i++) {
            float rot = (i / (pointCount/2))*(float)(Math.PI*2);
            float xpos = (float)(Math.cos(rot+Math.PI/2) * 5);
            float ypos = (float)(Math.sin(rot+Math.PI/2) * 5);

            rot = (i / pointCount)*(float)(Math.PI*2);
            float xrot = (float)(Math.cos(-rot)*10);
            float yrot = (float)(Math.sin(-rot)*10);
            pos[(int)i] = new Point2D.Float(xpos, ypos);
            rots[(int)i] = new Point2D.Float(xrot, yrot);
        }
        for( double i = 0; i < blurCount; i++)
            pows[(int)i] = (float)Math.pow(i/((double)blurCount),3);
    }

    @Override
    public void stopped() {

    }

    @Override
    public int loop() {
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
        //osChar.splatTime()

        //TODO check what this is client.getConfigs2();
        /**getClient().gameClockMs();
        getClient().getCurrentTick();
        getClient().getCurrentWorld();

        g.drawString("Time: "+client.getCurrentTime(),5,80);

        if( test != null ) {
            g.drawString("test valid: " + test.valid(), 5, 100);
            if (test.valid()) {
                test.drawModel(g);
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

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.black);
        g.fillOval(455,355,30,30);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
        int i = (int)((System.currentTimeMillis()*2)%pointCount);
        for( int j = 0, k = i - blurCount < 0 ? pointCount + (i-blurCount) : i - blurCount; j < blurCount; j++, k++ ){
            if( k >= pointCount )
                k = 0;
            g.setColor(Color.getHSBColor(121.0f/360.0f,1,pows[j]));
            g.drawLine((int)(pos[k].x + 470 - rots[k].x),(int) (pos[k].y + 370 - rots[k].y),(int)( pos[k].x + 470 + rots[k].x), (int)(pos[k].y + 370 + rots[k].y));
        }*/

        XClippingPlane plane = getMap().getRegion().getClippingPlanes()[localPlayer().getTile().z];
        int[][] flags = plane.getTileFlags();
        for( int y = 1; y < 99; y++ )
            for( int x = 1; x < 99; x++ )
            {
                if( flags[x][y] == 0 )
                    g.setColor(new Color(0,255,0,100));
                else {
                    g.setColor(new Color(255,0,0,100));
                }
                g.fillPolygon(new Tile(x+client.getMapBaseX(),y+client.getMapBaseY(),client.getPlane()).getPolygon());
                if( flags[x][y] == 0 ) {
                    g.setColor(Color.green);
                }
                else {
                    g.setColor(Color.red);
                }
                g.fillRect(x*3,y*3,3,3);
            }

        //g.drawRect((int)myPlayer().getPosition().getPolygon(bot).getBounds().getCenterX(),
        //        (int)myPlayer().getPosition().getPolygon(bot).getBounds().getCenterY(),2,2);

        //g.drawString(""+myPlayer().getPosition(),5,88);
        //g.drawString(((myPlayer().accessor.getLocalX()>>7)+client.getMapBaseX())+", "+((myPlayer().accessor.getGridY()>>7)+client.getMapBaseY())+
        //        ", "+client.getPlane(), 5, 100);

        //g.drawString("getSelectedItemState "+client.getSelectedItemState(),5,120);
    }
}
