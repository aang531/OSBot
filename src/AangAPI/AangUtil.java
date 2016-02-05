package AangAPI;

import AangAPI.DataTypes.Player;
import AangAPI.Function.*;
import org.osbot.rs07.accessor.XClient;

import java.util.concurrent.Callable;

public class AangUtil {
    public static void init(AangScript script)
    {
        AangUtil.script = script;
    }

    public static AangScript script;
    public static XClient client;

    public static final MiscFunc misc = MiscFunc.getInstance();
    public static final ObjectsFunc objects = ObjectsFunc.getInstance();
    public static final MovementFunc movement = MovementFunc.getInstance();
    public static final InventoryFunc inventory = InventoryFunc.getInstance();
    public static final NpcsFunc npcs = NpcsFunc.getInstance();
    public static final PrayerFunc prayer = PrayerFunc.getInstance();
    public static final WorldFunc worlds = WorldFunc.getInstance();
    public static final WidgetFunc widgets = WidgetFunc.getInstance();
    public static final BankFunc bank = BankFunc.getInstance();
    public static final GroundItemFunc groundItems = GroundItemFunc.getInstance();
    public static final MenuFunc menu = MenuFunc.getInstance();
    public static final GameFunc game = GameFunc.getInstance();
    public static final CameraFunc camera = CameraFunc.getInstance();
    public static final ChatFunc chat = ChatFunc.getInstance();
    public static final SkillsFunc skills = SkillsFunc.getInstance();
    public static final MouseFunc mouse = MouseFunc.getInstance();
    public static final KeyboardFunc keyboard = KeyboardFunc.getInstance();
    public static final VarpbitsFunc varpbits = VarpbitsFunc.getInstance();
    public static final TabsFunc tabs = TabsFunc.getInstance();
    public static final MapFunc map = MapFunc.getInstance();
    public static final RegionFunc region = RegionFunc.getInstance();
    public static final PlayerFunc players = PlayerFunc.getInstance();

    public static void sleep( int millis ) {
        try {
            AangScript.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean sleep(Callable<Boolean> check, int interval, int checkTimes ) {
        return AangScript.sleep(check,interval,checkTimes);
    }

    public static Player localPlayer(){
        return new Player(script.myPlayer().accessor);
    }

    public static void log(String message){
        script.log(message);
    }

    public static void warn(String message){
        script.warn(message);
    }

    public static int random(int max){
        return AangScript.random(max);
    }

    public static int random( int min, int max ){
        return AangScript.random(min,max);
    }
}