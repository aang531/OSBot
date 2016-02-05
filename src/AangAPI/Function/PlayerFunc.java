package AangAPI.Function;

import AangAPI.AangUtil;
import AangAPI.DataTypes.Player;
import AangAPI.DataTypes.Tile;
import org.osbot.rs07.accessor.XPlayer;

import java.util.ArrayList;
import java.util.List;

public class PlayerFunc extends AangUtil {
    private static PlayerFunc ourInstance = new PlayerFunc();

    public static PlayerFunc getInstance() {
        return ourInstance;
    }

    public Player[] getAll(){
        XPlayer[] players = client.getLocalPlayers();
        List<Player> ret = new ArrayList<>();
        for (XPlayer player : players) {
            if( player != null )
                ret.add(new Player(player));
        }
        return ret.toArray(new Player[ret.size()]);
    }

    public Player get(String name){
        XPlayer[] players = client.getLocalPlayers();
        for( XPlayer p : players )
            if( p != null && p.getName().equals(name))
                return new Player(p);
        return null;
    }

    public Player[] getAt(Tile t){
        XPlayer[] players = client.getLocalPlayers();
        List<Player> ret = new ArrayList<>();
        for( XPlayer p : players )
            if( p != null )
                if( p.getGridX() == t.getGridX() && p.getGridY() == t.getGridY())
                    ret.add(new Player(p));
        return ret.toArray(new Player[ret.size()]);
    }
}
