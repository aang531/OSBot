package aang521.AangWine;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.DataTypes.Path;
import aang521.AangAPI.DataTypes.RSObject;
import aang521.AangAPI.DataTypes.Tile;
import aang521.AangAPI.DataTypes.TileArea;
import aang521.AangAPI.Function.MagicFunc;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;

@ScriptManifest(name = "AangWine", author = "aang521", version = 1.0, info = "Telegrabs zammy wine.", logo = "")
public class AangWine extends AangScript {

    private static final int wineID = 245, lawRune = 563, airStaff = 1381, bankID = 24101, waterRune = 555;

    private int winePrice = 0, lawPrice = 0, waterRunePrice = 0;

    private RSObject table;

    private boolean running = false;
    private Thread mainThread;

    private int itemsInInv;
    private int winesGained = 0;
    private int tooLateTimes = 0;

    private long startTime;

    private Path toAltar = new Path( this, new Tile[] { new Tile(this, 2946,3368), new Tile(this,2946,3373), new Tile(this,2949,3375),
            new Tile(this,2956,3382), new Tile(this,2961,3384), new Tile(this,2961,3388), new Tile(this,2964,3393),
            new Tile(this,2964,3398), new Tile(this,2964,3401), new Tile(this,2967,3407), new Tile(this,2966,3411),
            new Tile(this,2962,3415), new Tile(this,2960,3418), new Tile(this,2958,3419), new Tile(this,2958,3424),
            new Tile(this,2956,3428), new Tile(this,2953,3431), new Tile(this,2952,3438), new Tile(this,2950,3441),
            new Tile(this,2949,3444), new Tile(this,2947,3451), new Tile(this,2946,3456), new Tile(this,2945,3463),
            new Tile(this,2945,3469), new Tile(this,2945,3476), new Tile(this,2945,3482), new Tile(this,2942,3490),
            new Tile(this,2942,3498), new Tile(this,2941,3503), new Tile(this,2941,3511), new Tile(this,2942,3516),
            new Tile(this,2931,3515) } );

    private final TileArea bankArea = new TileArea( this, 2943, 3368, 6, 5 );
    private final TileArea altarArea = new TileArea( this, 2930, 3513, 4, 4 );

    private final Tile wineTile = new Tile(this,2930,3515);

    private final Path teleportToBank = new Path( this, new Tile[] { new Tile(this,2963,3379), new Tile(this,2959,3379), new Tile(this,2955,3379),
            new Tile(this,2951,3378), new Tile(this,2947,3375), new Tile(this,2946,3369) } );

    private long lastTimeClick = 0;

    private void startGrabThread(){
        if( mainThread != null ) {
            running = false;
            mainThread.interrupt();
            try {
                mainThread.join();
            } catch (InterruptedException ignored) {
            }
        }
        running = true;
        mainThread = new Thread() {
            public void run() {
                while(running && !isInterrupted() && bot.getScriptExecutor().isRunning()) {
                    if( bot.getScriptExecutor().isPaused() ){
                        running = false;
                        return;
                    }
                    if (!magic.isSpellSelected() || !magic.getSelectedSpellName().equals("Telekinetic Grab"))
                        magic.selectSpell(MagicFunc.Spell.TELEKINETIC_GRAB);
                    Rectangle r = new Position(wineTile.x,wineTile.y,wineTile.z).getPolygon(bot,100).getBounds();
                    mouse.move((int)r.getCenterX(),(int)r.getCenterY());
                    if( System.currentTimeMillis() - lastTimeClick > 2000 && itemsInInv == 28 ) {
                        running = false;
                        break;
                    }
                    if (menu.getTooltip().equals("Cast Telekinetic Grab -> Wine of zamorak")) {
                        if (System.currentTimeMillis() - lastTimeClick > 4000) {
                            mouse.click(true);
                            lastTimeClick = System.currentTimeMillis();
                            itemsInInv++;
                            winesGained++;
                            try {
                                sleep(22000);
                            } catch (InterruptedException e) {
                                break;
                            }
                        }
                    }
                }
            }
        };
        mainThread.start();
    }

    private void stopGrabThread() {
        running = false;
        if( mainThread != null ) {
            mainThread.interrupt();
            try {
                mainThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean atAltar(){
        return altarArea.contains(localPlayer().getTile());
    }

    public boolean atBank(){
        return bankArea.contains(localPlayer().getTile());
    }

    @Override
    public void scriptStart() {
        if( game.playing() )
            itemsInInv = inventory.getCount();

        Thread tmp = new Thread() {
            public void run() {
                winePrice = misc.getGEPrice(wineID);
                lawPrice = misc.getGEPrice(lawRune);
                waterRunePrice = misc.getGEPrice(waterRune);
            }
        };

        tmp.start();

        startTime = System.currentTimeMillis();
    }

    @Override
    public void stopped() {
        stopGrabThread();

        int runeCost = (tooLateTimes + winesGained)*lawPrice;
        int wineCost = winesGained * winePrice;
        int profit = wineCost-runeCost;

        long timeRunning = System.currentTimeMillis() - startTime;
        long hours = timeRunning / 3600000;
        timeRunning -= hours * 3600000;
        long mins = timeRunning / 60000;
        timeRunning -= mins * 60000;
        long seconds = timeRunning / 1000;

        log("Script stopped after "+hours+":"+mins+":"+seconds);
        log("Profit/h: " + (int)(profit/((System.currentTimeMillis() - startTime)/3600000.0f)));
        log("Gained " + profit);
        log("Collected " + winesGained + " wines, missed " + tooLateTimes + " times");
    }

    @Override
    public int loop() {
        if( atAltar() ) {
            if (inventory.isFull()) {
                stopGrabThread();
                if( !running ) {
                    tabs.openMagicTab();
                    if( System.currentTimeMillis() - lastTimeClick > 4000 ) {
                        if (magic.isSpellSelected())
                            magic.deselectSpell();
                        if( magic.cast(MagicFunc.Spell.FALADOR_TELEPORT) )
                            lastTimeClick = System.currentTimeMillis();
                    }
                }
            }else{
                if( table == null || !table.valid())
                    table = objects.getNearest(595);
                if( !running && !localPlayer().isMoving() ){
                    startGrabThread();
                }
            }
        }else if( atBank() ){
            if (itemsInInv == 28) {
                if(bank.opened()){
                    bank.depositAll(wineID);
                    itemsInInv = inventory.getCount();
                }else {
                    RSObject bank = objects.getNearest(bankID);
                    if( bank != null )
                        bank.click("Bank");
                }
            }else{
                toAltar.traverse();
            }
        }else{
            if (itemsInInv == 28) {
                teleportToBank.traverse();
            }else{
                toAltar.traverse();
            }
        }
        return 200;
    }

    @Override
    public void repaint(Graphics2D g) {

        g.setColor(Color.white);

        int runeCost = (tooLateTimes + winesGained)*lawPrice;
        int wineCost = winesGained * winePrice;
        int profit = wineCost-runeCost;

        long timeRunning = System.currentTimeMillis() - startTime;
        g.drawString("Profit/h: " + misc.formatNumber((int)(profit/(timeRunning/3600000.0f))),5,372);
        long hours = timeRunning / 3600000;
        timeRunning -= hours * 3600000;
        long mins = timeRunning / 60000;
        timeRunning -= mins * 60000;
        long seconds = timeRunning / 1000;

        g.drawString("Time Running "+hours+":"+mins+":"+seconds,5,288);
        g.drawString("Items in inv: " + itemsInInv, 5, 300);
        g.drawString("Times missed: " + tooLateTimes, 5, 312);
        g.drawString("Wines grabbed: " + winesGained, 5, 324);

        g.drawString("Rune Cost: " + runeCost,5,336);
        g.drawString("Price of Wines: " + wineCost,5,348);
        g.drawString("Profit: " + misc.formatNumber(profit),5,360);

        g.drawString(""+menu.getTooltip(),5,100);
     }

    @Override
    public void onMessage(Message messageEvent) {
        if(messageEvent.getMessage().equals("Too late - it's gone!")) {
            itemsInInv--;
            winesGained--;
            tooLateTimes++;
        }
    }
}
