package AangAPI;

import org.osbot.rs07.api.Menu;

import java.awt.*;

public class MenuFunc extends AangUtil {
    private static MenuFunc ourInstance = new MenuFunc();

    public static MenuFunc getInstance() {
        return ourInstance;
    }

    public boolean clickMenuOption( int index ){
        if( menu.opened() ) {
            Point loc = menu.bounds().getLocation();
            mouse.move(loc.x + 40, loc.y + 27+ index * 15);
            sleep(80);
            return mouse.click(true);
        }
        return false;
    }

    public String[] actions(){
        return script.getClient().accessor.getMenuAction();
    }

    public String[] names(){
        return script.getClient().accessor.getMenuSpecificAction();
    }

    private int count(){
        return script.getClient().accessor.getMenuCount();
    }

    public int getActionIndex(String action){
        final String[] actions = actions();
        final int count = count();
        for( int i = 0; i < count; i++ ) {
            if (actions[i].equals(action))
                return count-i-1;
        }
        return -1;
    }

    public int getIndex(String action, String name){
        final String[] names = names();
        final String[] actions = actions();
        final int count = count();
        for( int i = 0; count > i; i++ ){
            if( actions[i].equals(action) && names[i].equals(name) ){
                return i;
            }
        }
        return -1;
    }

    public Rectangle bounds(){
        Menu menu = script.getMenuAPI();
        return new Rectangle(menu.getX(), menu.getY(), menu.getWidth(), menu.getHeight());
    }

    public boolean opened(){
        return script.getMenuAPI().isOpen();
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
