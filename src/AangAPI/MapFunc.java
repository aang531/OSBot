package AangAPI;

public class MapFunc extends AangUtil{
    private static MapFunc ourInstance = new MapFunc();

    public static MapFunc getInstance() {
        return ourInstance;
    }

    public boolean isMultiCombat(){
        return script.getMap().isMultiway();//TODO make this so it takes widget directly instead of osbot function.
    }

    public int getWildernessLevel(){
        return script.getMap().getWildernessLevel();//TODO make this so it takes widget directly instead of osbot function.
    }
}
