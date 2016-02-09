package aang521.AangAPI;

import aang521.AangAPI.DataTypes.Player;
import aang521.AangAPI.Function.*;
import org.osbot.rs07.accessor.XClient;

import java.util.concurrent.Callable;

public class AangUtil {
    public AangUtil(AangScript script){
        this.script = script;
        this.client = script.client;
    }

    public void init(){
        chat = script.chat;
        misc = script.misc;
        objects = script.objects;
        movement = script.movement;
        inventory = script.inventory;
        npcs = script.npcs;
        prayer = script.prayer;
        worlds = script.worlds;
        widgets = script.widgets;
        bank = script.bank;
        groundItems = script.groundItems;
        menu = script.menu;
        game = script.game;
        camera = script.camera;
        skills = script.skills;
        mouse = script.mouse;
        keyboard = script.keyboard;
        varpbits = script.varpbits;
        tabs = script.tabs;
        map = script.map;
        region = script.region;
        players = script.players;
        magic = script.magic;
    }

    final public AangScript script;
    final public XClient client;

    public MiscFunc misc;
    public ObjectsFunc objects;
    public MovementFunc movement;
    public InventoryFunc inventory;
    public NpcsFunc npcs;
    public PrayerFunc prayer;
    public WorldFunc worlds;
    public WidgetFunc widgets;
    public BankFunc bank;
    public GroundItemFunc groundItems;
    public MenuFunc menu;
    public GameFunc game;
    public CameraFunc camera;
    public ChatFunc chat;
    public SkillsFunc skills;
    public MouseFunc mouse;
    public KeyboardFunc keyboard;
    public VarpbitsFunc varpbits;
    public TabsFunc tabs;
    public MapFunc map;
    public RegionFunc region;
    public PlayerFunc players;
    public MagicFunc magic;

    public void sleep( int millis ) {
        script.sleep(millis);
    }

    public boolean sleep(Callable<Boolean> check, int interval, int checkTimes ) {
        return script.sleep(check,interval,checkTimes);
    }

    public Player localPlayer(){
        return new Player(script, script.myPlayer().accessor);
    }

    public void log(String message){
        script.log(message);
    }

    public void warn(String message){
        script.warn(message);
    }

    public static int random(int max){
        return AangScript.random(max);
    }

    public static int random( int min, int max ){
        return AangScript.random(min,max);
    }
}