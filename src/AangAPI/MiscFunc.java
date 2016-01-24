package AangAPI;

import AangAPI.DataTypes.Component;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MiscFunc extends AangUtil {
    private static MiscFunc ourInstance = new MiscFunc();

    public static MiscFunc getInstance() {
        return ourInstance;
    }

    public int getGEPrice(int id){
        try {
            String price;
            URL url = new URL("http://services.runescape.com/m=itemdb_oldschool/Wine_of_zamorak/viewitem?obj=" + id);
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

    public boolean pointOnScreen(Point p){
        return p.x > 3 && p.y > 3 && p.x < 516 && p.y < 338;
    }

    //TODO
    /*public Tile getTileUnderPoint(Point p){
        Tile playerTile = ctx.players.local().tile();
        for (int y = -10; y <= 10; y++)
            for (int x = -10; x <= 10; x++) {
                if (playerTile.derive(x, y).matrix(ctx).bounds().contains(p)) {
                    return playerTile.derive(x, y);
                }
            }
        return null;
    }*/
}
