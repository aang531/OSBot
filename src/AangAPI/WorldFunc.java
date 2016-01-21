package AangAPI;

import org.osbot.rs07.accessor.XTile;
import org.osbot.rs07.api.ui.Tab;

import AangAPI.DataTypes.*;

public class WorldFunc extends AangUtil {
    private static WorldFunc ourInstance = new WorldFunc();

    public static WorldFunc getInstance() {
        return ourInstance;
    }

    public final static int WIDGET_LOGOUT = 182;
    public final static int WIDGET_WORLD_HOP = 69;
    private final static int COMPONENT_WORLD_LIST = 7;

    public static final int[] freeWorlds = new int[] {1,8,16,26,35,81,82,83,84,85,93,94};
    public static final int[] memberWorlds = new int[] {2,3,4,5,6,9,10,11,12,13,14,17,18,20,21,22,27,28,29,30,33,34,36,
            38,41,42,43,44,46,49,50,51,53,54,58,59,61,62,65,66,67,68,69,70,73,75,76,77,78,86};
    public static final int[] pvpWorlds = new int[] {25,37};
    public static final int[] deadManWorlds = new int[] {19,45,52,57,60,74};

    public enum WorldType {
        FREE,
        MEMBERS,
        DEADMEN,
        PVP
    }

    public boolean isWorldHop(){
        return widgets.widget(WIDGET_WORLD_HOP).active();
    }

    public void openWorldHop(){
        if( tabs.opened() != Tab.LOGOUT )
            misc.openLogoutTab();
        widgets.clickComponent(WIDGET_LOGOUT,5);
    }

    public void closeWorldHop(){
        if( tabs.opened() != Tab.LOGOUT )
            misc.openLogoutTab();
        widgets.clickComponent(WIDGET_WORLD_HOP,3);
    }

    public void sortOnMembers(){
        if( tabs.opened() != Tab.LOGOUT )
            misc.openLogoutTab();
        widgets.clickComponent(WIDGET_WORLD_HOP,9);
    }

    public boolean sortedOnMembers(){
        return varpbits.get(477) == 2;
    }

    public int getCurrentWorld() {
        return Integer.parseInt(widgets.get(429,1).text().substring(22));
    }

    private int getWorldFromComponent(Component c, int index){
        return Integer.parseInt(c.get((index*6)+2).text());
    }

    public void hop(int world){
        if( tabs.opened() != Tab.LOGOUT )
            misc.openLogoutTab();
        Component c = widgets.get(WIDGET_WORLD_HOP,COMPONENT_WORLD_LIST);
        XTile x = script.getMap().getRegion().getTiles()[0][0][0];
        int worlds = c.childCount()/6;
        for( int i = 0; i < worlds; i++ )
            if(getWorldFromComponent(c,i) == world) {
                final int totalHeight = 16 * worlds + 11;
                final int worldHeight = i * 16 + 11 + 8;
                if (worldHeight < c.position().y || worldHeight > c.position().y + c.height()) {
                    final float percent = (float) worldHeight / (float) totalHeight;
                    final int neededOffset = (int) (percent * (172));

                    widgets.clickComponent(WIDGET_WORLD_HOP, 15, 0, 8, neededOffset);
                }
                widgets.clickComponent(c.get(i*6));
                return;
            }
    }
}
