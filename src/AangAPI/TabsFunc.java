package AangAPI;

import org.osbot.rs07.api.ui.Tab;

public class TabsFunc extends AangUtil{
    private static TabsFunc ourInstance = new TabsFunc();

    public static TabsFunc getInstance() {
        return ourInstance;
    }

    private TabsFunc() {
    }

    public Tab opened(){
        return script.getTabs().getOpen();
    }
}
