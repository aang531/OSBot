package aang521.AangAPI.Function;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.AangUtil;
import aang521.AangAPI.DataTypes.Player;
import aang521.AangAPI.DataTypes.Tile;
import org.osbot.rs07.accessor.XPlayer;

import java.util.ArrayList;
import java.util.List;

public class PlayerFunc extends AangUtil {

    public PlayerFunc(AangScript script) {
        super(script);
    }

    public Player[] getAll(){
        XPlayer[] players = client.getLocalPlayers();
        List<Player> ret = new ArrayList<>();
        for (XPlayer player : players) {
            if( player != null )
                ret.add(new Player(script, player));
        }
        return ret.toArray(new Player[ret.size()]);
    }

    public Player get(String name){
        XPlayer[] players = client.getLocalPlayers();
        for( XPlayer p : players )
            if( p != null && p.getName().equals(name))
                return new Player(script, p);
        return null;
    }

    public Player[] getAt(Tile t){
        XPlayer[] players = client.getLocalPlayers();
        List<Player> ret = new ArrayList<>();
        for( XPlayer p : players )
            if( p != null )
                if( p.getGridX() == t.getGridX() && p.getGridY() == t.getGridY())
                    ret.add(new Player(script, p));
        return ret.toArray(new Player[ret.size()]);
    }
}
