package aang521.AangAPI.Function;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.AangUtil;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.util.Locale;

public class MiscFunc extends AangUtil {

    public MiscFunc(AangScript script) {
        super(script);
    }

    public int getGEPrice(int id){
        try {
            String price;
            URL url = new URL("http://services.runescape.com/m=itemdb_oldschool/viewitem?obj=" + id);
            URLConnection con = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                if( line.contains("Current Guide Price") ) {
                    price = line.substring(line.indexOf("<span title='")+13,line.indexOf("'>")).replace(",","");
                    return Integer.parseInt(price);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isPointInViewport(Point p){
        return p.x > 3 && p.y > 3 && p.x < 516 && p.y < 338;
    }

    public boolean isPointOnScreen(Point p ){
        return p.x >= 0 && p.y >= 0 && p.x <= 765 && p.y <= 504;
    }

    public Point getRandomPoint(Shape region){
        final Rectangle r = region.getBounds();
        int x, y;
        do {
            x = (int)r.getX() + random( (int)r.getWidth() );
            y = (int)r.getY() + random( (int) r.getHeight() );
        } while(!region.contains(x,y));
        return new Point(x,y);
    }

    public String formatNumber( int number ){
        return NumberFormat.getInstance(Locale.US).format(number);
    }
}
