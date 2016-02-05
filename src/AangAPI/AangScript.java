package AangAPI;

import AangAPI.DataTypes.Player;
import AangAPI.Function.*;
import org.osbot.rs07.accessor.XClient;
import org.osbot.rs07.input.mouse.BotMouseListener;
import org.osbot.rs07.script.Script;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.concurrent.Callable;

public abstract class AangScript extends Script implements BotMouseListener {
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
    public final RegionFunc region;
    public final PlayerFunc players;

    public XClient client = null;

    private boolean started = false;

    private AangGui gui = null;

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
        region = RegionFunc.getInstance();
        players = PlayerFunc.getInstance();

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

    public void setGUI(AangGui gui){
        this.gui = gui;
        log("setgui: "+this.gui);
    }

    @Override
    public void onStart(){
        client = getClient().accessor;
        AangUtil.client = client;
        bot.addMouseListener(this);

        init();
        if( gui != null )
            gui.display();
        else
            start();
    }

    /**
     * called by the gui
     */
    public void start(){
        started = true;
        scriptStart();
    }

    public abstract void scriptStart();

    public void onExit(){
        log("gui: "+gui);
        if(this.gui!=null)
            this.gui.dispose();
        stopped();
    }

    public abstract void stopped();

    public void init(){}

    public int onLoop(){
        if( !started )
            return 100;
        return loop();
    }

    public abstract int loop();

    @Override
    public void onPaint(Graphics2D g) {
        g.setClip(bot.getCanvas().getBounds());
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1));
        g.setFont(null);

        g.setColor(Color.white);
        if( started )
            repaint(g);

        g.setColor(Color.green);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        final BasicStroke stroke1 = new BasicStroke(1.5f);
        g.setStroke(stroke1);
        g.drawLine(mouse.getPosition().x-2,mouse.getPosition().y-2,mouse.getPosition().x+2,mouse.getPosition().y+2);
        g.drawLine(mouse.getPosition().x-2,mouse.getPosition().y+2,mouse.getPosition().x+2,mouse.getPosition().y-2);

        final int rot = (int)(((System.currentTimeMillis()%0xfff) / ((float)0xfff))*360);

        final BasicStroke stroke2 = new BasicStroke(3);
        g.setStroke(stroke2);
        g.drawArc(mouse.getPosition().x-10,mouse.getPosition().y-10, 20,20,rot,30);
        g.drawArc(mouse.getPosition().x-10,mouse.getPosition().y-10, 20,20,rot+120,30);
        g.drawArc(mouse.getPosition().x-10,mouse.getPosition().y-10, 20,20,rot+240,30);

        final BasicStroke stroke3 = new BasicStroke(1);
        g.setStroke(stroke3);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
        g.setColor(Color.white);
    }

    public abstract void repaint(Graphics2D g);

    @Override
    public boolean blockInput(Point point) {
        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
