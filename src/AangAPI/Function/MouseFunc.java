package AangAPI.Function;

import AangAPI.AangUtil;
import AangAPI.DataTypes.*;
import org.osbot.rs07.accessor.XNPC;
import org.osbot.rs07.accessor.XPlayer;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MouseFunc extends AangUtil {
    private static MouseFunc ourInstance = new MouseFunc();
    public static MouseFunc getInstance() {
        return ourInstance;
    }

    private MouseFunc(){
    }

    public void move(int x, int y){
        script.getMouse().move(x,y);
    }

    public void move(Point p){
        script.getMouse().move(p.x,p.y);
    }

    public boolean click(boolean leftClick){
        return script.getMouse().click(leftClick);
    }

    public boolean click(){
        return click(true);
    }

    public Point getPosition(){
        return script.getMouse().getPosition();
    }

    public void scrollUp(){
        script.getMouse().scrollUp();
    }

    public void scrollDown(){
        script.getMouse().scrollDown();
    }

    public Crosshair getCrosshair() {
        return Crosshair.values()[client.getCrossHairColor()];
    }

    public enum Crosshair {
        NONE, DEFAULT, ACTION;
    }

    public RSObject[] getObjectsOnCursor(){
        List<RSObject> ret = new ArrayList<>();
        final RSObject[] tmp = objects.getAll();
        for( int i = 0; i < client.getOnCursorCount(); i++ ){
            final int uid = client.getOnCursorUids()[i];
            int x = map.getBaseX() + (uid & 127);
            int y = map.getBaseY() + (uid >> 7 & 127);
            if((uid >> 29 & 3) == 2){
                for( RSObject o : tmp )
                    if( o.osObject.getUID() == uid)
                        ret.add(o);
            }
        }
        return ret.toArray(new RSObject[ret.size()]);
    }

    public Npc[] getNpcsOnCursor(){
        List<Npc> ret = new ArrayList<>();
        for( int i = 0; i < client.getOnCursorCount(); i++ ){
            final int uid = client.getOnCursorUids()[i];
            int localIndex = uid >> 14 & 0x7fff;
            if((uid >> 29 & 3) == 1){
                XNPC tmpnpc;
                if( (tmpnpc = client.getLocalNpcs()[localIndex]) != null ){
                    Collections.addAll(ret, npcs.getAt(new Tile(map.gridXtoWorldX(tmpnpc.getGridX()),map.gridYtoWorldY(tmpnpc.getGridY()),client.getPlane())));
                    boolean found = false;
                    Npc tmp = new Npc(tmpnpc);
                    for( Npc n : ret )
                        if( n.osnpc.equals(tmp.osnpc)){
                            found = true;
                            break;
                        }
                    if( !found )
                        ret.add(tmp);
                }
            }else  if((uid >> 29 & 3) == 0){
                XPlayer tmplayer;
                if( (tmplayer = client.getLocalPlayers()[localIndex]) != null ){
                    Collections.addAll(ret, npcs.getAt(new Tile(map.gridXtoWorldX(tmplayer.getGridX()),map.gridYtoWorldY(tmplayer.getGridY()),client.getPlane())));
                }
            }
        }
        return ret.toArray(new Npc[ret.size()]);
    }

    public Player[] getPlayersOnCursor(){
        List<Player> ret = new ArrayList<>();
        for( int i = 0; i < client.getOnCursorCount(); i++ ){
            final int uid = client.getOnCursorUids()[i];
            int localIndex = uid >> 14 & 0x7fff;
            if((uid >> 29 & 3) == 0){
                XPlayer tmplayer;
                if( (tmplayer = client.getLocalPlayers()[localIndex]) != null ){
                    Collections.addAll(ret, players.getAt(new Tile(map.gridXtoWorldX(tmplayer.getGridX()),map.gridYtoWorldY(tmplayer.getGridY()),client.getPlane())));
                    boolean found = false;
                    Player tmp = new Player(tmplayer);
                    for( Player p : ret )
                        if( p.getName().equals(tmp.getName())){
                            found = true;
                            break;
                        }
                    if( !found )
                        ret.add(tmp);
                }
            }else if((uid >> 29 & 3) == 1){
                XNPC tmpnpc;
                if( (tmpnpc = client.getLocalNpcs()[localIndex]) != null ) {
                    Collections.addAll(ret, players.getAt(new Tile(map.gridXtoWorldX(tmpnpc.getGridX()),map.gridYtoWorldY(tmpnpc.getGridY()),client.getPlane())));
                }
            }
        }
        return ret.toArray(new Player[ret.size()]);
    }

    public GroundItem[] getGroundItemsOnCursor(){
        List<GroundItem> ret = new ArrayList<>();
        for( int i = 0; i < client.getOnCursorCount(); i++ ){
            final int uid = client.getOnCursorUids()[i];
            int x = map.getBaseX() + (uid & 127);
            int y = map.getBaseY() + (uid >> 7 & 127);
            if((uid >> 29 & 3) == 3){
                final GroundItem[] tmp = groundItems.getAt(new Tile(x,y,client.getPlane()));
                if( tmp != null )
                    Collections.addAll(ret, tmp);
            }
        }
        return ret.toArray(new GroundItem[ret.size()]);
    }

    public Tile getTileOnCursor(){
        Area area = script.myPlayer().getArea(25);
        area.setPlane(client.getPlane());
        for (Position pos: area.getPositions()) {
            if(pos.isVisible(script.bot) && pos.getPolygon(script.bot).contains(mouse.getPosition())) {
                return new Tile(pos.getX(),pos.getY(),client.getPlane());
            }
        }
        return null;
    }
}
