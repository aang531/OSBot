package AangScriptingUtils;

import AangAPI.AangScript;
import org.osbot.rs07.script.ScriptManifest;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

@ScriptManifest(name = "AangScriptingUtils", author = "Aang521", version = 1.0, info = "Tool for making scripts", logo = "http://files.softicons.com/download/web-icons/flat-style-icons-by-flaticonmaker/png/128x128/tools_black.png")
public class AangScriptingUtils extends AangScript {
    GUI gui;
    Image i;
    @Override
    public void init() {
        gui = new GUI(this);
        gui.display();
        //start();
        bot.setHumanInputEnabled(true);

        try {
            i = ImageIO.read(new URL("http://i.imgur.com/pqBbxco.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void scriptStart(){
        super.setGUI(null);
    }

    @Override
    public void stopped() {

    }

    @Override
    public void repaint(Graphics2D g) {
       // g.drawImage(i,-65,0,null);
        //g.drawImage(i,645-110,0,220,220,null);
        /*RSObject[] objs = mouse.getObjectsOnCursor();
        Npc[] npcs = mouse.getNpcsOnCursor();
        Player[] players = mouse.getPlayersOnCursor();
        GroundItem[] groundItem = mouse.getGroundItemsOnCursor();

        g.drawString("objs count: " + objs.length,5,200);
        g.drawString("npcs count: " + npcs.length,5,212);
        g.drawString("players count: " + players.length,5,224);
        g.drawString("groundItems count: " + groundItem.length,5,236);
        g.setColor(Color.blue);
        for(RSObject o : objs )
            o.drawModel(g);
        g.setColor(Color.green);
        for( Npc npc : npcs )
            npc.drawModel(g);
        g.setColor(Color.red);
        for( Player player : players )
            player.drawModel(g);
        g.setColor(Color.yellow);
        for( GroundItem gi : groundItem )
            gi.drawModel(g);

        Tile t = mouse.getTileOnCursor();
        if(t != null )
            t.draw(g);*/

        /*RSObject[] obj = objects.getAll();
        for(RSObject o : obj ) {
            if (o.isBlockingDecoration()) {
                log("Found clipping1: " + o.getTile());
                g.setColor(Color.red);
                o.fillModel(g);
                continue;
            }
            if( o.getClipping2() == 1){
                g.setColor(Color.green);
                o.fillModel(g);
            }else if( o.getClipping2() == 2 ) {
                g.setColor(Color.blue);
                o.fillModel(g);
            }
        }*/

        //RSObject[] arr = objects.getAll();
        //for(RSObject o: arr)
        //    o.drawModel(g);
    }

    public int loop(){
        return 500;
    }

    public void onResponseCode(int code){
        log("onResponseCode code: "+code);
    }
}
