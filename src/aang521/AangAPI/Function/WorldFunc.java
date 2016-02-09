package aang521.AangAPI.Function;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.AangUtil;
import org.osbot.rs07.api.ui.Tab;

import aang521.AangAPI.DataTypes.*;

public class WorldFunc extends AangUtil {

    public final static int WIDGET_LOGOUT = 182;
    public final static int WIDGET_WORLD_HOP = 69;
    private final static int COMPONENT_WORLD_LIST = 7;

    public static final int[] freeWorlds = new int[] {1,8,16,26,35,81,82,83,84,85,93,94};
    public static final int[] memberWorlds = new int[] {2,3,4,5,6,9,10,11,12,13,14,17,18,20,21,22,27,28,29,30,33,34,36,
            38,41,42,43,44,46,49,50,51,53,54,58,59,61,62,65,66,67,68,69,70,73,75,76,77,78,86};
    public static final int[] pvpWorlds = new int[] {25,37};
    public static final int[] deadManWorlds = new int[] {19,45,52,57,60,74};

    public WorldFunc(AangScript script) {
        super(script);
    }

    public enum WorldType {
        FREE,
        MEMBERS,
        DEADMEN,
        PVP
    }

    public boolean isWorldHop(){
        return widgets.active(WIDGET_WORLD_HOP);
    }

    public void openWorldHop(){
        if( tabs.opened() != Tab.LOGOUT )
            tabs.openLogoutTab();
        widgets.clickComponent(WIDGET_LOGOUT,5);
    }

    public void closeWorldHop(){
        if( tabs.opened() != Tab.LOGOUT )
            tabs.openLogoutTab();
        widgets.clickComponent(WIDGET_WORLD_HOP,3);
    }

    public void sortOnMembers(){
        if( tabs.opened() != Tab.LOGOUT )
            tabs.openLogoutTab();
        widgets.clickComponent(WIDGET_WORLD_HOP,9);
    }

    public boolean sortedOnMembers(){
        return varpbits.get(477) == 2;
    }

    public int getCurrentWorld() {
        return client.getCurrentWorld()-300;
    }

    public boolean isCurrentMember(){
        final int currentWorld = getCurrentWorld();
        for( int world : freeWorlds )
            if( world == currentWorld )
                return false;
        return true;
    }

    private int getWorldFromComponent(Component c, int index){
        return Integer.parseInt(c.get((index*6)+2).getText());
    }

    public void hop(int world){
        if( tabs.opened() != Tab.LOGOUT )
            tabs.openLogoutTab();
        Component c = widgets.get(WIDGET_WORLD_HOP,COMPONENT_WORLD_LIST);
        int worlds = c.childCount()/6;
        for( int i = 0; i < worlds; i++ )
            if(getWorldFromComponent(c,i) == world) {
                final int totalHeight = 16 * worlds + 11;
                final int worldHeight = i * 16 + 11 + 8;
                if (c.get(i*6).getY() < c.getY() || c.get(i*6).getY() + c.get(i*6).getHeight() > c.getY() + c.getHeight()) {
                    final float percent = (float) worldHeight / (float) totalHeight;
                    final int neededOffset = (int) (percent * (172));

                    widgets.clickComponent(WIDGET_WORLD_HOP, 15, 0, 8, neededOffset);
                    sleep(80);
                }
                widgets.clickComponent(c.get(i*6));
                return;
            }
    }
}
