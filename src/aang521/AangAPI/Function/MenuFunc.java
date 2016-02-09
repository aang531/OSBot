package aang521.AangAPI.Function;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.AangUtil;
import org.osbot.rs07.script.Script;

import java.awt.*;
import java.util.Arrays;

public class MenuFunc extends AangUtil {

    public MenuFunc(AangScript script) {
        super(script);
    }

    public boolean clickMenuOption(int index ){
        if( menu.opened() ) {
            Rectangle loc = menu.bounds();
            mouse.move(loc.x + 40, loc.y + loc.height - 7 - index * 15);
            sleep(80);
            return mouse.click(true);
        }
        return false;
    }

    public String[] actions(){
        return Arrays.copyOf(client.getMenuAction(),count());
        //return client.getMenuAction();
    }

    public String[] names(){
        String[] tmp = client.getMenuSpecificAction();
        String[] ret = new String[tmp.length];
        for(int i = 0; i < count(); i++ )
            ret[i] = Script.stripFormatting(tmp[i]);
        return ret;
    }

    public int count(){
        return client.getMenuCount();
    }

    public int getActionIndex(String action){
        final String[] actions = actions();
        final int count = count();
        for( int i = count-1; i >= 0; i-- ) {
            if (actions[i].equals(action))
                return i;
        }
        return -1;
    }

    public int getIndex(String action, String name){
        final String[] names = names();
        final String[] actions = actions();
        final int count = count();
        for( int i = count-1; i >= 0; i-- ){
            if( actions[i].equals(action) && names[i].equals(name) ){
                return i;
            }
        }
        return -1;
    }

    public String getTooltip(){
        return script.bot.csc.menu.get(0).action + " " + Script.stripFormatting(script.bot.csc.menu.get(0).name);
    }

    public String getTooltipAction(){
        return script.bot.csc.menu.get(0).action;
    }

    public String getTooltipName(){
        return Script.stripFormatting(script.bot.csc.menu.get(0).name);
    }

    public boolean isTooltip(String action, String name){
        return script.bot.csc.menu.get(0).action.equals(action) && Script.stripFormatting(script.bot.csc.menu.get(0).name).equals(name);
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
