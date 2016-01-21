package AangAPI;

import org.osbot.rs07.script.Script;

import java.awt.*;

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
    public final InteractFunc interact;
    public final SkillsFunc skills;
    public final MouseFunc mouse;
    public final KeyboardFunc keyboard;
    public final VarpbitsFunc varpbits;
    public final TabsFunc tabs;

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
        interact = InteractFunc.getInstance();
        skills = SkillsFunc.getInstance();
        mouse = MouseFunc.getInstance();
        keyboard = KeyboardFunc.getInstance();
        varpbits = VarpbitsFunc.getInstance();
        tabs = TabsFunc.getInstance();

        AangUtil.init(this);
    }

    @Override
    public void onPaint(Graphics2D g) {
        g.drawLine(0,mouse.position().y, mouse.position().x - 5, mouse.position().y );
        g.drawLine(mouse.position().x + 5, mouse.position().y, 765, mouse.position().y );
        g.drawLine(mouse.position().x, 0, mouse.position().x, mouse.position().y - 5 );
        g.drawLine(mouse.position().x, mouse.position().y + 5, mouse.position().x, 504 );
    }
}
