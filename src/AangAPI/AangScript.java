package AangAPI;

import AangAPI.DataTypes.Player;
import org.osbot.rs07.accessor.XClient;
import org.osbot.rs07.script.Script;

import java.awt.*;
import java.util.concurrent.Callable;

public abstract class AangScript extends Script {
    public final ChatFunc chat;
    public final MiscFunc misc;
    public final ObjectsFunc objects;
    public final MovementFunc movement;
    public final InventoryFunc inventory;
    public final NpcsFunc npcs;
    public final PrayerFunc prayer;
    public final WorldFunc worlds;
    public final WidgetFunc widgets;
    public final BankFunc bank;
    public final GroundItemFunc groundItems;
    public final MenuFunc menu;
    public final GameFunc game;
    public final CameraFunc camera;
    public final SkillsFunc skills;
    public final MouseFunc mouse;
    public final KeyboardFunc keyboard;
    public final VarpbitsFunc varpbits;
    public final TabsFunc tabs;
    public final MapFunc map;

    public XClient client = null;

    boolean started = false;

    public AangScript() {
        super();

        chat = ChatFunc.getInstance();
        misc = MiscFunc.getInstance();
        objects = ObjectsFunc.getInstance();
        movement = MovementFunc.getInstance();
        inventory = InventoryFunc.getInstance();
        npcs = NpcsFunc.getInstance();
        prayer = PrayerFunc.getInstance();
        worlds = WorldFunc.getInstance();
        widgets = WidgetFunc.getInstance();
        bank = BankFunc.getInstance();
        groundItems = GroundItemFunc.getInstance();
        menu = MenuFunc.getInstance();
        game = GameFunc.getInstance();
        camera = CameraFunc.getInstance();
        skills = SkillsFunc.getInstance();
        mouse = MouseFunc.getInstance();
        keyboard = KeyboardFunc.getInstance();
        varpbits = VarpbitsFunc.getInstance();
        tabs = TabsFunc.getInstance();
        map = MapFunc.getInstance();

        AangUtil.init(this);
    }

    public Player localPlayer(){
        return new Player(super.myPlayer().accessor);
    }

    public static boolean sleep(Callable<Boolean> check, int interval, int checkTimes ) {
        try {
            int checkedTimes = 0;
            boolean checked = true;
            while (checkedTimes < checkTimes && !(checked = check.call())) {
                sleep(interval);
                checkedTimes++;
            }
            return checked;
        }catch( Exception e ){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onStart(){
        client = getClient().accessor;
        AangUtil.client = client;
        started = true;
        init();
    }

    public abstract void init();

    @Override
    public void onPaint(Graphics2D g) {
        g.setClip(bot.getCanvas().getBounds());
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1));
        g.setColor(Color.green);
        g.drawLine(mouse.getPosition().x,0,mouse.getPosition().x,504);
        g.drawLine(0,mouse.getPosition().y,765,mouse.getPosition().y);
        if( started )
            repaint(g);
    }

    public abstract void repaint(Graphics2D g);
}
