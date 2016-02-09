package aang521.WebWalker;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.DataTypes.Tile;
import org.osbot.rs07.api.map.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

//@ScriptManifest(name = "WebWalker", author = "aang521", version = 1.0, info = "Select spot on map to walk to", logo = "")
public class WebWalker extends AangScript {

    private Image map;
    private int xoffset = 0;
    private int yoffset = 0;
    //12656 13948
    @Override
    public void init(){
        try {
            map = ImageIO.read(new URL("http://cdn.runescape.com/assets/img/external/oldschool/2016/osrs_world_map_jan21_2016.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void scriptStart() {
        Tile t = localPlayer().getTile();
        xoffset = -t.x*4 + 7334;
        yoffset = t.y*4 - 16018;
    }

    @Override
    public int loop() throws InterruptedException {
        return 200;
    }

    @Override
    public void repaint(Graphics2D g) {
        if( !moving ) {
            Tile t = localPlayer().getTile();

            final int width = 7589;
            final int height = 5092;
            g.drawImage(map,xoffset + 360,yoffset + 210,width,height,null);

            g.setStroke(new BasicStroke(3));
            int x = -(-t.x*4 + 7334) + 360;
            int y = -(-t.y*4 + 11878) + 210;
            g.setColor(Color.black);
            g.drawLine(x-3 + xoffset,y-5 + yoffset,x+5 + xoffset,y+5 + yoffset);
            g.drawLine(x-3 + xoffset,y+3 + yoffset,x+5 + xoffset,y-3 + yoffset);
            g.setColor(Color.green);
            g.drawLine(x-4 + xoffset,y-4 + yoffset,x+4 + xoffset,y+4 + yoffset);
            g.drawLine(x-4 + xoffset,y+4 + yoffset,x+4 + xoffset,y-4 + yoffset);

            g.setColor(Color.red);
            g.drawString("x: "+((-xoffset/4) -1330 + 3164)+" y: "+((yoffset/4) + 517 + 3487),5,100);
            g.drawString("player pos: " + t,5,120);
            g.drawString("x: "+xoffset+" y: "+yoffset,5,140);
            int mx = -(((-(lastPoint.x - xoffset - 360)-7344))/4);
            int my = -((((lastPoint.y - yoffset - 210)-16018))/4);
            g.drawString("x: "+mx+" y: "+my,5,160);
        }
    }

    private boolean dragging = false;
    private Point lastPoint;
    private boolean moving = false;

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        dragging = true;
        if( !moving && started && e.getButton() == 1 ){
            moving = true;
            int mx = -(((-(lastPoint.x - xoffset - 360)-7344))/4);
            int my = -((((lastPoint.y - yoffset - 210)-16018))/4);
            walking.webWalk(new Position(mx,my,0));
        }
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        dragging = false;
    }

    @Override
    public boolean blockInput( Point p ){
        if( dragging ) {
            xoffset += p.x - lastPoint.x;
            yoffset += p.y - lastPoint.y;
        }
        lastPoint = p;
        return true;
    }
}
