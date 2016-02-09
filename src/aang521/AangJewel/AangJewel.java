package aang521.AangJewel;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.DataTypes.*;
import aang521.AangAPI.Function.SkillsFunc;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;

@ScriptManifest(name = "AangJewel", author = "aang521", version = 1.0, info = "Crafts jewelery at al kharid.", logo = "")
public class AangJewel extends AangScript {

    private static final int goldID = 2357;
    private static final int amuletMouldID = 1595, ringMouldID = 1592;
    private static final int[] gemIDs = new int[]{1607,1605,1603,1601,1615};
    private static final int[] amuletIDs = new int[]{1675,1677,1679,1681,1683};
    private static final int[] ringIDs = new int[]{1637,1639,1641,1643,1645};
    private static final int furnaceID = 24009;
    private static final int bankID = 6943;
    private final Tile bankTile = new Tile( this,3268,3167);

    private static final int furnaceInterface = 446;
    private int jewelComponent;

    private int goldPrice, gemPrice, jewelPrice, profit;

    private final Path toBank = new Path( this, new Tile[] {new Tile( this,3274,3186), new Tile( this,3277,3186), new Tile( this,3280,3185),
            new Tile( this,3280,3182), new Tile( this,3280,3178), new Tile( this,3276,3172), new Tile( this,3275,3169),
            new Tile( this,3273,3167), new Tile( this,3269,3167)},
            new Obstacle[]{ new Obstacle(this, 7122, new Tile( this,3280, 3185), new TileArea( this, 3274, 3184, 5, 4 ), "Open")});

    private final Path toFurnace = new Path( this, new Tile[] {new Tile( this,3269,3167), new Tile( this,3272,3167), new Tile( this,3273,3169),
            new Tile( this,3275,3174), new Tile( this,3276,3178), new Tile( this,3278,3181), new Tile( this,3280,3185),
            new Tile( this,3278,3185), new Tile( this,3274,3186)},
            new Obstacle[]{ new Obstacle(this, 7122, new Tile( this,3280, 3185), new TileArea( this, 3280, 3181, 3, 6 ), "Open" )} );


    private final TileArea bankArea = new TileArea( this, 3269, 3164, 3, 6 );
    private final TileArea furnaceArea = new TileArea( this, 3274, 3184, 4, 4 );

    private int amuletsMade = 0;
    private long startTime;
    private int startExp;

    private int amuletsInInv = 0;

    private RSObject furnace = null;
    private long lastCraftingTime;

    private int jewelID, gemID, mouldID;

    private String status = "NULL";

    private GUI gui;

    @Override
    public void init(){
        gui = new GUI(this);
        gui.display();
        setGUI(gui);
    }

    @Override
    public void scriptStart(){
        startExp = skills.getExp(SkillsFunc.CRAFTING);
        startTime = System.currentTimeMillis();
        Thread tmp = new Thread(){
            public void run(){
                goldPrice = misc.getGEPrice(goldID);
                gemPrice = misc.getGEPrice(gemID);
                jewelPrice = misc.getGEPrice(jewelID);
                profit = jewelPrice - (gemPrice+goldPrice);
            }
        };
        tmp.start();
        amuletsInInv = inventory.getCount(jewelID);
    }

    public void startButtonPress(){
        final int gemIndex = gui.gemType.getSelectedIndex();
        final int jewelIndex = gui.jewelType.getSelectedIndex();
        gemID = gemIDs[gemIndex];
        if( jewelIndex == 0 ) {
            mouldID = amuletMouldID;
            jewelID = amuletIDs[gemIndex];
            jewelComponent = 33 + gemIndex;
        }else if ( jewelIndex == 1 ) {
            mouldID = ringMouldID;
            jewelID = ringIDs[gemIndex];
            jewelComponent = 8 + gemIndex;
        }else {
            log("error could not find jewelIndex: " + jewelIndex);
            stop(false);
            return;
        }
        start();
        gui.dispose();
    }

    private boolean atBank() {
        return bankArea.contains(localPlayer().getTile());
    }

    private boolean atFurnace() {
        return furnaceArea.contains(localPlayer().getTile());
    }

    private boolean crafting() {
        return lastCraftingTime + 1300 > System.currentTimeMillis();
    }

    @Override
    public int loop() throws InterruptedException {
        if (localPlayer().getAnimation() == 899)
            lastCraftingTime = System.currentTimeMillis();

        int tmp = inventory.getCount(jewelID);
        amuletsMade += Math.max(0, tmp - amuletsInInv);
        amuletsInInv = tmp;

        if (inventory.contains(gemID) && inventory.getCount(gemID) <= inventory.getCount(goldID) && inventory.contains(mouldID) ) {
            if (atFurnace()) {
                if (widgets.widget(furnaceInterface).active() && chat.pendingInput()) {
                    status = "sending amount";
                    keyboard.sendln("13");
                    sleep(600);
                } else if (widgets.widget(furnaceInterface).active()) {
                    status = "Clicking make-x";
                    int count = Math.min(inventory.getCount(gemID), inventory.getCount(goldID));
                    if( count <= 10 )
                        widgets.clickComponentItem(furnaceInterface, jewelComponent, "Make-10");
                    else
                        widgets.clickComponentItem(furnaceInterface, jewelComponent, "Make-X");
                    sleep(600);
                } else if (!crafting()) {
                    if( !inventory.isOpen())
                        inventory.open();
                    if (inventory.itemSelected() && inventory.getSelectedItemName().equals("Gold bar") ) {
                        status = "using gold with furnace";
                        if (furnace == null || !furnace.valid())
                            furnace = objects.getNearest(furnaceID);
                        if (furnace != null && furnace.valid()) {
                            if( furnace.useItem() ){
                                sleep(() -> widgets.widget(furnaceInterface).active(), 100, 10);
                            }
                            sleep(100);
                        }
                    } else {
                        status = "selecting gold";
                        inventory.clickItem(inventory.getFirst(goldID));
                        sleep(100);
                    }
                }else{
                    status = "crafting";
                    sleep(200);
                }
            } else {
                status = "walking to furnace";
                toFurnace.traverse();
            }
        } else {
            if (atBank()) {
                if (bank.opened()) {
                    if( inventory.isFull() )
                        if( inventory.getCount(goldID) > 14 )
                            bank.depositAll(goldID);
                        else if( inventory.getCount(gemID) > 13 )
                            bank.depositAll(gemID);
                    boolean clicked = true;
                    if (!inventory.contains(mouldID)) {
                        status = "withdrawing mould";
                        clicked = bank.withdraw(mouldID,1);
                        sleep(200);
                    }
                    if(!clicked) return 200;
                    if (inventory.contains(jewelID)) {
                        status = "depositing amulets";
                        clicked = bank.depositAll(jewelID);
                        sleep(200);
                    }
                    if(!clicked) return 200;
                    if (!inventory.contains(gemID)) {
                        status = "withdrawing sapphire";
                        clicked = bank.withdraw(gemID, 13);
                        sleep(100);
                    }
                    if(!clicked) return 200;
                    if (inventory.getCount(goldID) < 13) {
                        status = "withdrawing gold";
                        clicked = bank.withdrawAll(goldID);
                        sleep(100);
                    }
                    if(!clicked) return 200;
                    sleep(200);
                    if (!inventory.contains(jewelID) && inventory.contains(gemID) && inventory.contains(goldID) )
                        toFurnace.traverse();
                } else {
                    status = "opening bank";
                    RSObject bank = objects.getAt(bankTile,bankID);
                    if( bank != null && bank.click("Bank") ) {
                        sleep(this.bank::opened, 150, 10);
                    }
                }
            } else {
                status = "walking to bank";
                toBank.traverse();
            }
        }
        return 200;
    }

    @Override
    public void repaint(Graphics2D g) {
        g.setColor(Color.black);
        g.fillRect(0, 240, 150, 96 + 2);

        g.setColor(Color.white);
        g.drawRect(0, 240, 150, 96 + 2);
        int totalProfit = profit * amuletsMade;

        int expGained = skills.getExp(SkillsFunc.CRAFTING) - startExp;

        long timeRunning = System.currentTimeMillis() - startTime;
        final float hoursf = timeRunning / 3600000.0f;
        g.drawString("Profit/h: " + misc.formatNumber((int) (totalProfit / hoursf)), 5, 312);
        g.drawString("Jewels/h: " + misc.formatNumber((int) (amuletsMade / hoursf)), 5, 324);
        g.drawString("Exp/h: " + misc.formatNumber((int) (expGained / hoursf)), 5, 336);
        long hours = timeRunning / 3600000;
        timeRunning -= hours * 3600000;
        long mins = timeRunning / 60000;
        timeRunning -= mins * 60000;
        long seconds = timeRunning / 1000;

        g.drawString("Time Running " + hours + ":" + mins + ":" + seconds, 5, 252);
        g.drawString("Profit: " + misc.formatNumber(totalProfit), 5, 264);
        g.drawString("Profit each: " + profit, 5, 276);
        g.drawString("Jewels made: " + misc.formatNumber(amuletsMade), 5, 288);
        g.drawString("Exp gained: " + misc.formatNumber(expGained), 5, 300);

        g.drawString(status,5,100);
        g.drawString( "Crafting: "+crafting(),5,112);
    }

    @Override
    public void onMessage(Message messageEvent) {
        if(messageEvent.getType() == Message.MessageType.GAME) {
            if (messageEvent.getMessage().equals("Congratulations, you've just advanced a Crafting level."))
                lastCraftingTime = 0;
        }
    }
}
