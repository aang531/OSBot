package aang521.AangAPI;

import aang521.AangAPI.DataTypes.Player;
import aang521.AangAPI.Function.*;
import org.osbot.rs07.Bot;
import org.osbot.rs07.accessor.XClient;
import org.osbot.rs07.input.mouse.BotMouseListener;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Callable;

public abstract class AangScript extends Script implements BotMouseListener {
    public ChatFunc chat;
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
    public SkillsFunc skills;
    public MouseFunc mouse;
    public KeyboardFunc keyboard;
    public VarpbitsFunc varpbits;
    public TabsFunc tabs;
    public MapFunc map;
    public RegionFunc region;
    public PlayerFunc players;
    public MagicFunc magic;
    public EquipmentFunc equipment;

    public XClient client = null;

    public boolean started = false;

    private AangGui gui = null;

    protected boolean drawImage = true;
    protected Image avatarImage;

    public AangScript() {
        super();
    }

    public Player localPlayer(){
        return new Player(this, super.myPlayer().accessor);
    }

    public boolean sleep(Callable<Boolean> check, int interval, int checkTimes ) {
        try {
            int checkedTimes = 0;
            boolean checked = true;
            while (checkedTimes < checkTimes && !(checked = check.call())) {
                sleep(interval);
                checkedTimes++;
            }
            return checked;
        }catch( Exception ignored ){
            return false;
        }
    }

    public boolean sleep( Callable<Boolean> check ){
        return sleep(check,200,10);
    }

    public void setGUI(AangGui gui){
        this.gui = gui;
    }

    @Override
    public void onStart(){
        try {
            bot.addMouseListener(this);

            bot.setHumanInputEnabled(true);

            try {
                avatarImage = ImageIO.read(new URL("http://i.imgur.com/pqBbxco.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            init();
            if (gui != null)
                gui.display();
            else
                start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void initUtils(){
        client = getClient().accessor;
        chat = new ChatFunc(this);
        misc = new MiscFunc(this);
        objects = new ObjectsFunc(this);
        movement = new MovementFunc(this);
        inventory = new InventoryFunc(this);
        npcs = new NpcsFunc(this);
        prayer = new PrayerFunc(this);
        worlds = new WorldFunc(this);
        widgets = new WidgetFunc(this);
        bank = new BankFunc(this);
        groundItems = new GroundItemFunc(this);
        menu = new MenuFunc(this);
        game = new GameFunc(this);
        camera = new CameraFunc(this);
        skills = new SkillsFunc(this);
        mouse = new MouseFunc(this);
        keyboard = new KeyboardFunc(this);
        varpbits = new VarpbitsFunc(this);
        tabs = new TabsFunc(this);
        map = new MapFunc(this);
        region = new RegionFunc(this);
        players = new PlayerFunc(this);
        magic = new MagicFunc(this);
        equipment = new EquipmentFunc(this);

        chat.init();
        misc.init();
        objects.init();
        movement.init();
        inventory.init();
        npcs.init();
        prayer.init();
        worlds.init();
        widgets.init();
        bank.init();
        groundItems.init();
        menu.init();
        game.init();
        camera.init();
        skills.init();
        mouse.init();
        keyboard.init();
        varpbits.init();
        tabs.init();
        map.init();
        region.init();
        players.init();
        magic.init();
        equipment.init();
    }

    @Override
    public MethodProvider initializeContext(Bot bot) {
        MethodProvider ret = super.initializeContext(bot);
        initUtils();
        return ret;
    }

    @Override
    public MethodProvider exchangeContext(Bot bot) {
        MethodProvider ret = super.exchangeContext(bot);
        initUtils();
        return ret;
    }

    /**
     * called by the gui
     */
    public void start(){
        started = true;
        bot.setHumanInputEnabled(false);
        scriptStart();
    }

    public abstract void scriptStart();

    public void onExit(){
        if(this.gui!=null)
            this.gui.dispose();
        stopped();
    }

    public void stopped(){}

    public void init(){}

    public int onLoop() throws InterruptedException {
        if( !started )
            return 300;
        if( !camera.pitchedUp())
            camera.pitchUp();
        return loop();
    }

    public abstract int loop() throws InterruptedException;

    private static final Font font = new Font("Helvetica",Font.PLAIN,12);

    @Override
    public void onPaint(Graphics2D g) {//TODO don't do this if not visable
        g.setClip(bot.getCanvas().getBounds());
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1));
        g.setFont(font);
        g.setColor(Color.white);

        g.setStroke(new BasicStroke(1));
        if( started ) {
            if( drawImage )
                g.drawImage(avatarImage,640-100,0,200,200,null);
            repaint(g);
        }else if( drawImage )
            g.drawImage(avatarImage,-65,0,null);

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

    public void sleep(int millis ){
        try {
            MethodProvider.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }
}
