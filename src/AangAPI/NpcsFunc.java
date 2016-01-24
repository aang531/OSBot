package AangAPI;

import AangAPI.DataTypes.Npc;
import AangAPI.DataTypes.Tile;
import org.osbot.rs07.accessor.XNPC;

public class NpcsFunc extends AangUtil {
    private static NpcsFunc ourInstance = new NpcsFunc();

    public static NpcsFunc getInstance() {
        return ourInstance;
    }

    public Npc[] getAll(){
        final XNPC[] xnpcs = client.getLocalNpcs();
        final Npc[] npcs = new Npc[xnpcs.length];
        for( int i = 0; i < xnpcs.length; i++ )
            if( xnpcs[i] != null )
            npcs[i] = new Npc(xnpcs[i]);
        return npcs;
    }

    public Npc getNearest(int id ){
        final XNPC[] xnpcs = client.getLocalNpcs();
        int dist = Integer.MAX_VALUE;
        Npc ret = null;
        Npc tmpnpc;
        final Tile playerTile = localPlayer().getTile();
        for( XNPC npc : xnpcs )
            if( npc!=null&&npc.getDefinition()!=null)
                if((tmpnpc = new Npc(npc)).getID() == id ){
                    final int tmpDist = tmpnpc.getTile().sqrDistTo(playerTile);
                    if( tmpDist < dist ){
                        dist = tmpDist;
                        ret = tmpnpc;
                    }
                }
        return ret;
    }
}
