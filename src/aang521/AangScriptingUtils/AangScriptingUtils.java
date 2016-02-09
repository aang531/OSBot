package aang521.AangScriptingUtils;

import aang521.AangAPI.AangScript;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;

@ScriptManifest(name = "AangScriptingUtils", author = "aang521", version = 1.0, info = "Tool for making scripts", logo = "http://files.softicons.com/download/web-icons/flat-style-icons-by-flaticonmaker/png/128x128/tools_black.png")
public class AangScriptingUtils extends AangScript {
    GUI gui;

    /*static final int pointCount = 4440;
    static final int blurCount = 1300;

    Point2D.Float pos[] = new Point2D.Float[pointCount];
    Point2D.Float rots[] = new Point2D.Float[pointCount];
    float pows[] = new float[blurCount];*/

    @Override
    public void init() {
        drawImage = false;
        gui = new GUI(this);
        gui.display();
        bot.setHumanInputEnabled(true);

         /*for( float i = 0; i < pointCount; i++) {
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
            pows[(int)i] = (float)Math.pow(i/((double)blurCount),3);*/
    }

    public void scriptStart(){
        super.setGUI(null);
    }

    @Override
    public void stopped() {
        gui.stopped();
    }

    @Override
    public void repaint(Graphics2D g) {
        /*g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
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
    }

    public int loop(){
        return 500;
    }

    public void onResponseCode(int code){
        log("onResponseCode code: "+code);
    }
}
