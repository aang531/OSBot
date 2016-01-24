package AangAPI;

import java.awt.*;

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
}
