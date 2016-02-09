package aang521.AangAPI.Function;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.AangUtil;
import aang521.AangAPI.DataTypes.Npc;
import aang521.AangAPI.DataTypes.Tile;
import org.osbot.rs07.accessor.XNPC;

import java.util.ArrayList;
import java.util.List;

public class NpcsFunc extends AangUtil {

    public NpcsFunc(AangScript script) {
        super(script);
    }

    public Npc[] getAll(){
        script.getNpcs().getAll();
        final XNPC[] xnpcs = client.getLocalNpcs();
        List<Npc> npcs = new ArrayList<>();
        for( int i = 0; i < xnpcs.length; i++ )
            if( xnpcs[i] != null )
                npcs.add(new Npc(script, xnpcs[i]));
        return npcs.toArray(new Npc[npcs.size()]);
    }

    public Npc[] getAt(Tile t){
        XNPC[] players = client.getLocalNpcs();
        List<Npc> ret = new ArrayList<>();
        for( XNPC p : players )
            if( p != null )
                if( p.getGridX() == t.getGridX() && p.getGridY() == t.getGridX())
                    ret.add(new Npc(script, p));
        return ret.toArray(new Npc[ret.size()]);
    }

    public Npc getNearest(int id ){
        final XNPC[] xnpcs = client.getLocalNpcs();
        int dist = Integer.MAX_VALUE;
        Npc ret = null;
        Npc tmpnpc;
        final Tile playerTile = localPlayer().getTile();
        for( XNPC npc : xnpcs )
            if( npc!=null&&npc.getDefinition()!=null)
                if((tmpnpc = new Npc(script, npc)).getID() == id ){
                    final int tmpDist = tmpnpc.getTile().sqrDistTo(playerTile);
                    if( tmpDist < dist ){
                        dist = tmpDist;
                        ret = tmpnpc;
                    }
                }
        return ret;
    }
}
