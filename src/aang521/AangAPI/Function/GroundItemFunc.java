package aang521.AangAPI.Function;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.AangUtil;
import aang521.AangAPI.DataTypes.GroundItem;
import aang521.AangAPI.DataTypes.Tile;
import org.osbot.rs07.accessor.XGroundItem;
import org.osbot.rs07.accessor.XNodeDeque;
import org.osbot.rs07.api.util.NodeDequeIterator;

import java.util.ArrayList;
import java.util.List;

public class GroundItemFunc extends AangUtil {

    public GroundItemFunc(AangScript script) {
        super(script);
    }

    public GroundItem[] getAll(){
        XNodeDeque[][] deque = client.getGroundItemDeques()[client.getPlane()];
        List<GroundItem> list = new ArrayList<>();
        NodeDequeIterator it = new NodeDequeIterator();

        for( int x = 0; x < deque.length; x++ )
            for( int y = 0; y < deque[x].length; y++ ){
                it = new NodeDequeIterator();
                if( deque[x][y] != null ) {
                    it.set(deque[x][y]);
                    XGroundItem gi;
                    while ((gi = (XGroundItem) it.getNext()) != null) {
                        list.add(new GroundItem(script, gi, new Tile(script, x + client.getMapBaseX(), y + client.getMapBaseY(), client.getPlane())));
                    }
                }
            }
        return list.toArray(new GroundItem[list.size()]);
    }

    public GroundItem[] getAt(Tile t){
        NodeDequeIterator it = new NodeDequeIterator();
        XNodeDeque q = client.getGroundItemDeques()[t.z][t.getLocalX()][t.getLocalY()];
        if(q == null)
            return null;
        it.set(q);
        List<GroundItem> list = new ArrayList<>();
        XGroundItem gi;
        while(( gi = (XGroundItem)it.getNext()) != null ){
            list.add(new GroundItem(script, gi,t));
        }
        return list.toArray(new GroundItem[list.size()]);
    }

    public GroundItem getAt(Tile t, int id ){
        NodeDequeIterator it = new NodeDequeIterator();
        it.set(client.getGroundItemDeques()[t.z][t.getLocalX()][t.getLocalY()]);
        XGroundItem gi;
        while(( gi = (XGroundItem)it.getNext()) != null ){
            if( gi.getId() == id )
                return new GroundItem(script, gi,t);
        }
        return null;
    }

    public GroundItem get(int id){
        XNodeDeque[][] deque = client.getGroundItemDeques()[client.getPlane()];
        NodeDequeIterator it = new NodeDequeIterator();

        for( int x = 0; x < deque.length; x++ )
            for( int y = 0; y < deque[x].length; y++ ){
                if( deque[x][y] != null ) {
                    it.set(deque[x][y]);
                    XGroundItem gi;
                    while ((gi = (XGroundItem) it.getNext()) != null) {
                        if (gi.getId() == id)
                            return new GroundItem(script, gi, new Tile(script, x + client.getMapBaseX(), y + client.getMapBaseY(), client.getPlane()));
                    }
                }
            }
        return null;
    }
}
