package AangAPI.Function;

import AangAPI.AangUtil;
import org.osbot.rs07.api.Menu;
import org.osbot.rs07.api.util.GraphicUtilities;
import org.osbot.rs07.script.Script;

import java.awt.*;

public class MenuFunc extends AangUtil {
    private static MenuFunc ourInstance = new MenuFunc();

    public static MenuFunc getInstance() {
        return ourInstance;
    }

    public boolean clickMenuOption( int index ){
        if( menu.opened() ) {
            Rectangle loc = menu.bounds();
            mouse.move(loc.x + 40, loc.y + loc.height - 7 - index * 15);
            sleep(80);
            return mouse.click(false);
        }
        return false;
    }

    public String[] actions(){
        return script.getClient().accessor.getMenuAction();
    }

    public String[] names(){
        String[] tmp = script.getClient().accessor.getMenuSpecificAction();
        String[] ret = new String[tmp.length];
        for(int i = 0; i < tmp.length; i++ )
            ret[i] = Script.stripFormatting(tmp[i]);
        script.getMenuAPI().getMenu();
        return ret;
    }

    private int count(){
        return script.getClient().accessor.getMenuCount();
    }

    public int getActionIndex(String action){
        final String[] actions = actions();
        final int count = count();
        for( int i = count-1; i > 0; i-- ) {
            if (actions[i].equals(action))
                return i;
        }
        return -1;
    }

    public int getIndex(String action, String name){
        final String[] names = script.getClient().accessor.getMenuSpecificAction();
        final String[] actions = actions();
        final int count = count();
        for( int i = count-1; i > 0; i-- ){
            if( actions[i].equals(action) && Script.stripFormatting(names[i]).equals(name) ){
                return i;
            }
        }
        return -1;
    }

    public Rectangle bounds(){
        return new Rectangle(client.getMenuX(), client.getMenuY(), client.getMenuWidth(), client.getMenuHeight());
    }

    public boolean opened(){
        return client.getMenuOpen();
    }

    public void close(){
        final Rectangle b = bounds();
        b.x -= 10;
        b.y -= 10;
        b.width += 20;
        b.height += 20;
        boolean left = true;
        boolean top = true;
        if( b.x <= 5 )
            left = false;
        if( b.y <= 5 )
            top = false;
        mouse.move(left ? b.x - 3 : b.x + b.width + 3, top ? b.y - 3 : b.y + b.height + 3 );
        sleep(50);
    }
}
