package aang521.AangAPI.Function;


import aang521.AangAPI.AangScript;
import aang521.AangAPI.AangUtil;
import aang521.AangAPI.DataTypes.RSObject;
import aang521.AangAPI.DataTypes.Tile;
import org.osbot.core.api.Wrapper;
import org.osbot.rs07.accessor.*;
import org.osbot.rs07.api.model.RS2Object;

import java.util.ArrayList;
import java.util.List;

public class ObjectsFunc extends AangUtil {

    public ObjectsFunc(AangScript script) {
        super(script);
    }

    public RSObject[] getAll(){
        final XTile[][] tiles = script.getMap().getRegion().getTiles()[client.getPlane()];
        List<RSObject> objects = new ArrayList<>();

        for( int x = 0; x < tiles.length; x++ ) {
            for (int y = 0; y < tiles[x].length; y++) {
                if( tiles[x][y] == null )
                    continue;
                final XInteractableObject[] xobjects = tiles[x][y].getObjects();
                for (int i = 0; i < xobjects.length; i++)
                    if( xobjects[i] != null ) {
                        final RS2Object obj = Wrapper.wrap(xobjects[i]);
                        if( !obj.getName().equals("null") )
                            objects.add(new RSObject(script, obj));
                    }
                final XWallObject w = tiles[x][y].getWallObject();
                if( w != null ) {
                    final RS2Object obj = Wrapper.wrap(w);
                    if( !obj.getName().equals("null") ) {
                        objects.add(new RSObject(script, obj));
                    }
                }
                final XGroundDecoration g = tiles[x][y].getGroundDecoration();
                if( g != null ){
                    final RS2Object obj = Wrapper.wrap(g);
                    if( !obj.getName().equals("null"))
                        objects.add(new RSObject(script, obj));
                }
            }
        }
        return objects.toArray(new RSObject[objects.size()]);
    }

    public RSObject[] getAll(int id){
        final XTile[][] tiles = script.getMap().getRegion().getTiles()[client.getPlane()];
        List<RSObject> objects = new ArrayList<>();

        for( int x = 0; x < tiles.length; x++ ) {
            for (int y = 0; y < tiles[x].length; y++) {
                if( tiles[x][y] == null )
                    continue;
                final XInteractableObject[] xobjects = tiles[x][y].getObjects();
                for (int i = 0; i < xobjects.length; i++)
                    if( xobjects[i] != null ) {
                        final RS2Object obj = Wrapper.wrap(xobjects[i]);
                        if( obj.getId() == id ) {
                            objects.add(new RSObject(script, obj));
                        }
                    }
                final XWallObject w = tiles[x][y].getWallObject();
                if( w != null ) {
                    final RS2Object obj = Wrapper.wrap(w);
                    if( obj.getId() == id ) {
                        objects.add(new RSObject(script, obj));
                    }
                }
                final XGroundDecoration g = tiles[x][y].getGroundDecoration();
                if( g != null ){
                    final RS2Object obj = Wrapper.wrap(g);
                    if( obj.getId() == id ) {
                        objects.add(new RSObject(script, obj));
                    }
                }
            }
        }
        return objects.toArray(new RSObject[objects.size()]);
    }

    public RSObject[] getAll(int[] ids){
        final XTile[][] tiles = script.getMap().getRegion().getTiles()[client.getPlane()];
        List<RSObject> objects = new ArrayList<>();

        for( int x = 0; x < tiles.length; x++ ) {
            for (int y = 0; y < tiles[x].length; y++) {
                if( tiles[x][y] == null )
                    continue;
                final XInteractableObject[] xobjects = tiles[x][y].getObjects();
                for (int i = 0; i < xobjects.length; i++)
                    if( xobjects[i] != null ) {
                        final RS2Object obj = Wrapper.wrap(xobjects[i]);
                        for( int id : ids )
                            if( obj.getId() == id ) {
                                objects.add(new RSObject(script, obj));
                            }
                    }
                final XWallObject w = tiles[x][y].getWallObject();
                if( w != null ) {
                    final RS2Object obj = Wrapper.wrap(w);
                    for( int id : ids )
                        if( obj.getId() == id ) {
                            objects.add(new RSObject(script, obj));
                        }
                }
                final XGroundDecoration g = tiles[x][y].getGroundDecoration();
                if( g != null ){
                    final RS2Object obj = Wrapper.wrap(g);
                    for( int id : ids )
                        if( obj.getId() == id ) {
                            objects.add(new RSObject(script, obj));
                        }
                }
            }
        }
        return objects.toArray(new RSObject[objects.size()]);
    }

    public RSObject getNearest( int id ){
        RSObject[] objects = getAll(id);
        RSObject ret = null;
        int dist = Integer.MAX_VALUE;
        Tile playerTile = localPlayer().getTile();
        for (RSObject object : objects) {
            final int tmp = object.getTile().sqrDistTo(playerTile);
            if (tmp < dist) {
                dist = tmp;
                ret = object;
            }
        }
        return ret;
    }

    public RSObject getNearest( int[] ids ){
        RSObject[] objects = getAll(ids);
        RSObject ret = null;
        int dist = Integer.MAX_VALUE;
        Tile playerTile = localPlayer().getTile();
        for (RSObject object : objects) {
            final int tmp = object.getTile().sqrDistTo(playerTile);
            if (tmp < dist) {
                dist = tmp;
                ret = object;
            }
        }
        return ret;
    }

    public RSObject[] getAt(Tile t){
        XInteractableObject[] osObjects = script.getMap().getRegion().getTiles()[t.z][t.getLocalX()][t.getLocalY()].getObjects();
        XWallObject wallObject = script.getMap().getRegion().getTiles()[t.z][t.getLocalX()][t.getLocalY()].getWallObject();
        XGroundDecoration decoration = script.getMap().getRegion().getTiles()[t.z][t.getLocalX()][t.getLocalY()].getGroundDecoration();
        List<RSObject> ret = new ArrayList<>();
        for( int i = 0; i < osObjects.length; i++ )
            if (osObjects[i] != null){
                final RS2Object obj = Wrapper.wrap(osObjects[i]);
                if( !obj.getName().equals("null"))
                    ret.add(new RSObject(script, obj));
            }
        if( wallObject != null ) {
            final RS2Object obj = Wrapper.wrap(wallObject);
            if( !obj.getName().equals("null"))
                ret.add(new RSObject(script, obj));

        }
        if( decoration != null ){
            final RS2Object obj = Wrapper.wrap(decoration);
            if( !obj.getName().equals("null"))
                ret.add(new RSObject(script, obj));
        }
        return ret.toArray(new RSObject[ret.size()]);
    }

    public RSObject getAt(Tile t, int id ){
        XInteractableObject[] osObjects = script.getMap().getRegion().getTiles()[t.z][t.getLocalX()][t.getLocalY()].getObjects();
        XWallObject wallObject = script.getMap().getRegion().getTiles()[t.z][t.getLocalX()][t.getLocalY()].getWallObject();
        XGroundDecoration decoration = script.getMap().getRegion().getTiles()[t.z][t.getLocalX()][t.getLocalY()].getGroundDecoration();
        List<RSObject> ret = new ArrayList<>();
        for( int i = 0; i < osObjects.length; i++ )
            if (osObjects[i] != null){
                final RS2Object obj = Wrapper.wrap(osObjects[i]);
                if( obj.getId() == id )
                    return new RSObject(script, obj);
            }
        if( wallObject != null ) {
            final RS2Object obj = Wrapper.wrap(wallObject);
            if( obj.getId() == id )
                return new RSObject(script, obj);

        }
        if( decoration != null ){
            final RS2Object obj = Wrapper.wrap(decoration);
            if( obj.getId() == id )
                return new RSObject(script, obj);
        }
        return null;
    }

    public RSObject getAt(Tile t, int[] ids ){
        XInteractableObject[] osObjects = script.getMap().getRegion().getTiles()[t.z][t.getLocalX()][t.getLocalY()].getObjects();
        XWallObject wallObject = script.getMap().getRegion().getTiles()[t.z][t.getLocalX()][t.getLocalY()].getWallObject();
        XGroundDecoration decoration = script.getMap().getRegion().getTiles()[t.z][t.getLocalX()][t.getLocalY()].getGroundDecoration();
        List<RSObject> ret = new ArrayList<>();
        for( int i = 0; i < osObjects.length; i++ )
            if (osObjects[i] != null){
                final RS2Object obj = Wrapper.wrap(osObjects[i]);
                for( int id : ids )
                    if( obj.getId() == id )
                        return new RSObject(script, obj);
            }
        if( wallObject != null ) {
            final RS2Object obj = Wrapper.wrap(wallObject);
            for( int id : ids )
                if( obj.getId() == id )
                    return new RSObject(script, obj);

        }
        if( decoration != null ){
            final RS2Object obj = Wrapper.wrap(decoration);
            for( int id : ids )
                if( obj.getId() == id )
                    return new RSObject(script, obj);
        }
        return null;
    }
}
