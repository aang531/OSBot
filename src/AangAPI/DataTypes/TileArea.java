package AangAPI.DataTypes;

import java.awt.*;

public class TileArea {

    public int x, y, width, height,floor;

    public TileArea( int x, int y, int width, int height ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        floor = 0;
    }

    public TileArea( int x, int y, int width, int height, int floor ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.floor = floor;
    }

    public TileArea( Tile a, Tile b ){
        x = Math.min(a.x,b.x);
        y = Math.min(a.y,b.y);
        width = a.x-b.x;
        width *= width < 0 ? -1 : 1;
        height = a.y-b.y;
        height *= height < 0 ? -1 : 1;
        floor = a.z;
    }

    public TileArea( Tile t, int width, int height){
        floor = t.z;
        x = t.x;
        y = t.y;
        this.width = width;
        this.height = height;
    }

    public boolean contains(Tile t) {
        return floor == t.z && t.x >= x && t.x <= x + width && t.y >= y && t.y <= y + height;
    }

    public void draw(Graphics2D g){
        for( int x = 0; x <= width; x++ )
            for( int y = 0; y <= height; y++ )
                new Tile(this.x+x,this.y+y,floor).draw(g);
    }

    public void fill(Graphics2D g ){
        /*Polygon p1 = new Tile(x,y,floor).getPolygon();
        Polygon p2 = new Tile(x+width,y,floor).getPolygon();
        Polygon p3 = new Tile(x+width,y+height,floor).getPolygon();
        Polygon p4 = new Tile(x,y+height,floor).getPolygon();
        final Polygon p = new Polygon();
        p.addPoint(p1.xpoints[2],p1.ypoints[2]);
        p.addPoint(p2.xpoints[1],p2.ypoints[1]);
        p.addPoint(p3.xpoints[0],p3.ypoints[0]);
        p.addPoint(p4.xpoints[3],p4.ypoints[3]);
        g.fillPolygon(p);*/
        for( int x = 0; x <= width; x++ )
            for( int y = 0; y <= height; y++ )
                new Tile(this.x+x,this.y+y,floor).fill(g);
    }
}
