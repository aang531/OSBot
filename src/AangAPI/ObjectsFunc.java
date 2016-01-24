package AangAPI;


import AangAPI.DataTypes.RSObject;
import AangAPI.DataTypes.Tile;
import org.osbot.core.api.Wrapper;
import org.osbot.rs07.accessor.*;
import org.osbot.rs07.api.model.RS2Object;

import java.util.ArrayList;
import java.util.List;

public class ObjectsFunc extends AangUtil {
    private static ObjectsFunc ourInstance = new ObjectsFunc();

    public static ObjectsFunc getInstance() {
        return ourInstance;
    }

    public RSObject[] getAll(){
        final XTile[][] tiles = script.getMap().getRegion().getTiles()[client.getPlane()];

        List<RSObject> objects = new ArrayList<>();

        for( int x = 0; x < tiles.length; x++ ) {
            for (int y = 0; y < tiles.length; y++) {
                final XInteractableObject[] xobjects = tiles[x][y].getObjects();
                for (int i = 0; i < xobjects.length; i++)
                    if( xobjects[i] != null ) {
                        final RS2Object obj = Wrapper.wrap(xobjects[i]);
                        if( !obj.getName().equals("null") )
                            objects.add(new RSObject(obj));
                    }
                final XWallObject w = tiles[x][y].getWallObject();
                if( w != null ) {
                    final RS2Object obj = Wrapper.wrap(w);
                    if( !obj.getName().equals("null") ) {
                        objects.add(new RSObject(obj));
                    }
                }
                final XGroundDecoration g = tiles[x][y].getGroundDecoration();
                if( g != null ){
                    final RS2Object obj = Wrapper.wrap(g);
                    if( !obj.getName().equals("null"))
                        objects.add(new RSObject(obj));
                }
            }
        }
        return objects.toArray(new RSObject[objects.size()]);
    }

    public RSObject getNearest( int id ){
        RSObject[] objects = getAll();
        RSObject ret = null;
        int dist = Integer.MAX_VALUE;
        Tile playerTile = localPlayer().getTile();
        for (RSObject object : objects) {
            if (object.getID() == id) {
                final int tmp = object.getTile().sqrDistTo(playerTile);
                if (tmp < dist) {
                    dist = tmp;
                    ret = object;
                }
            }
        }
        return ret;
    }

    public RSObject[] getAt(Tile t){
        XInteractableObject[] osObjects = script.getMap().getRegion().getTiles()[t.z][t.getLocalX()][t.getLocalY()].getObjects();
        RSObject[] objects = new RSObject[osObjects.length];
        for( int i = 0; i < osObjects.length; i++ )
            objects[i] = new RSObject(Wrapper.wrap(osObjects[i]));
        return objects;
    }

    public RSObject getAt(Tile t, int id ){
        if( !t.isLoaded() )
            return null;
        final XInteractableObject[] osObjects = script.getMap().getRegion().getTiles()[t.z][t.getLocalX()][t.getLocalY()].getObjects();
        for( XInteractableObject o : osObjects ) {
            if (o != null) {
                if ((o.getIdHash() >> 14 & 32767) == id) {
                    return new RSObject(Wrapper.wrap(o));
                }
            }
        }
        return null;
    }
}
