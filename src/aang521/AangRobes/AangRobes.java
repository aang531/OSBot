package aang521.AangRobes;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.DataTypes.*;
import aang521.AangAPI.Function.WorldFunc;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;

@ScriptManifest(name = "AangRobes", author = "aang521", version = 1.0, info = "Collets Monk robes in monastery and banks them.", logo = "")
public class AangRobes extends AangScript {

    private static final int robeTopID = 544, robeBottomID = 542;
    private int robeTopPrice,robeBottomPrice;

    /*private static final Path toRobes = new Path( new Tile[] {  new Tile( this,3094,3494), new Tile( this,3093,3498), new Tile( this,3089,3501),
            new Tile( this,3083,3504), new Tile( this,3077,3504), new Tile( this,3075,3510), new Tile( this,3075,3515),
            new Tile( this,3072,3520), new Tile( this,3061,3518), new Tile( this,3054,3511), new Tile( this,3054,3505),
            new Tile( this,3053,3500), new Tile( this,3053,3493), new Tile( this,3058,3489), new Tile( this,3058,3484),
            new Tile( this,3058,3484,1), new Tile( this,3058,3487,1)},
            new Obstacle[]{  new Obstacle(2641, new Tile( this, 3057, 3483), new TileArea( 3056, 3482, 3, 5 ), "Climb-up" ),
                    new Obstacle(7122, new Tile( this, 3058, 3485, 1), new TileArea( 3055, 3482, 4, 3, 1 ), "Open" )});
*/
    private final Path toRobes = new Path( this, new Tile[] {new Tile( this,3096,3494,0), new Tile( this,3093,3499,0), new Tile( this,3089,3503,0),
            new Tile( this,3087,3508,0), new Tile( this,3086,3513,0), new Tile( this,3081,3515,0), new Tile( this,3076,3518,0),
            new Tile( this,3071,3520,0), new Tile( this,3065,3520,0), new Tile( this,3061,3515,0), new Tile( this,3056,3513,0),
            new Tile( this,3052,3509,0), new Tile( this,3054,3504,0), new Tile( this,3053,3499,0), new Tile( this,3054,3492,0),
            new Tile( this,3058,3488,0), new Tile( this,3058,3483,0), new Tile( this,3058,3487,1)},
            new Obstacle[]{ new Obstacle(this, 2641, new Tile( this,3057, 3483, 0), new TileArea( this, 3054, 3482, 5, 3 ), "Climb-up" ),
                    new Obstacle(this, 7122, new Tile( this,3058, 3485, 1), new TileArea( this, 3055, 3482, 4, 3, 1 ), "Open" )});

    /*private static final Path toBank = new Path( new Tile[] {new Tile( this,3058,3483,1), new Tile( this,3058,3483), new Tile( this,3058,3488),
            new Tile( this,3053,3494), new Tile( this,3053,3501), new Tile( this,3054,3507), new Tile( this,3056,3510),
            new Tile( this,3060,3517), new Tile( this,3062,3520), new Tile( this,3068,3520), new Tile( this,3072,3516),
            new Tile( this,3073,3508), new Tile( this,3080,3506), new Tile( this,3085,3505), new Tile( this,3092,3500), new Tile( this,3095,3495)},
            new Obstacle[]{ new Obstacle(7122, new Tile( this,3058, 3485, 1), new TileArea( 3056, 3486, 3, 6, 1 ), "Open" ),
                    new Obstacle(16679, new Tile( this,3057, 3483, 1), new TileArea( 3054, 3482, 5, 7, 1 ), "Climb-down" )});*/
    private final Path toBank = new Path( this, new Tile[] {new Tile( this,3058,3487,1), new Tile( this,3058,3484,1), new Tile( this,3058,3483,0),
            new Tile( this,3058,3488,0), new Tile( this,3054,3492,0), new Tile( this,3053,3499,0), new Tile( this,3054,3504,0),
            new Tile( this,3052,3509,0), new Tile( this,3056,3513,0), new Tile( this,3061,3515,0), new Tile( this,3065,3520,0),
            new Tile( this,3071,3520,0), new Tile( this,3076,3518,0), new Tile( this,3081,3515,0), new Tile( this,3086,3513,0),
            new Tile( this,3087,3508,0), new Tile( this,3089,3503,0), new Tile( this,3093,3499,0), new Tile( this,3096,3494,0)},
            new Obstacle[]{ new Obstacle(this, 7122, new Tile( this,3058, 3485, 1), new TileArea( this,3056, 3486, 3, 3, 1 ), "Open" ),
                    new Obstacle(this, 16679, new Tile( this,3057, 3483, 1), new TileArea( this, 3055, 3482, 4, 3, 1 ), "Climb-down" )});

    private final TileArea robeArea = new TileArea(this, 3056, 3486, 3, 5, 1);
    private final TileArea bankArea = new TileArea(this, 3091,3488, 7, 11);

    private static final int bankID = 6943;

    private int robeTopInInv = 0, robeTopGained = 0;
    private int robeBottomInInv = 0, robeBottomGained = 0;
    private long startTime;

    private static final int hopDelay = 23000;
    private long lastHopTime = 0;

    @Override
    public void scriptStart(){
        startTime = System.currentTimeMillis();

        Thread tmp = new Thread() {
            public void run() {
                robeTopPrice = misc.getGEPrice(robeTopID);
                robeBottomPrice = misc.getGEPrice(robeBottomID);
            }
        };
        tmp.start();

        robeTopInInv = inventory.getCount(robeTopID);
        robeBottomInInv = inventory.getCount(robeTopID);

        if( !worlds.isWorldHop() )
            worlds.openWorldHop();
        if(!worlds.sortedOnMembers())
            worlds.sortOnMembers();
    }

    private boolean atBank(){
        return bankArea.contains(localPlayer().getTile());
    }

    private boolean atRobes(){
        return robeArea.contains(localPlayer().getTile());
    }

    @Override
    public int loop() throws InterruptedException {
        if( game.playing() ) {
            int tmp = inventory.getCount(robeTopID);
            robeTopGained += Math.max(0, tmp - robeTopInInv);
            robeTopInInv = tmp;
            tmp = inventory.getCount(robeBottomID);
            robeBottomGained += Math.max(0, tmp - robeBottomInInv);
            robeBottomInInv = tmp;

            if (inventory.isFull()) {
                if (atBank()) {
                    if (bank.opened()) {
                        bank.depositAll();
                    } else {
                        RSObject bank = objects.getNearest(bankID);
                        if (bank != null) {
                            if( bank.click("Bank") )
                                sleep(this.bank::opened,100,10);
                        }
                    }
                } else {
                    toBank.traverse();
                }
            } else {
                if (atRobes()) {
                    GroundItem robeTop = groundItems.get(robeTopID);
                    GroundItem robeBottom = groundItems.get(robeBottomID);
                    if (robeTop != null && robeTop.valid() && localPlayer().getAnimation() == -1) {
                        robeTop.pickup();
                        sleep(400);
                    } else if (robeBottom != null && robeBottom.valid() && localPlayer().getAnimation() == -1) {
                        robeBottom.pickup();
                        sleep(400);
                    } else if (System.currentTimeMillis() > hopDelay + lastHopTime) {
                        if( !worlds.isWorldHop() ) {
                            worlds.openWorldHop();
                            sleep(80);
                        }
                        if (!worlds.sortedOnMembers())
                            worlds.sortOnMembers();

                        int currentWorld = worlds.getCurrentWorld();
                        int currentIndex = -1;
                        for (int i = 0; i < WorldFunc.freeWorlds.length; i++) {//TODO make check to see if member
                            if (WorldFunc.freeWorlds[i] == currentWorld) {
                                currentIndex = i;
                                break;
                            }
                        }
                        if (currentIndex == WorldFunc.freeWorlds.length - 1) {
                            worlds.hop(WorldFunc.freeWorlds[0]);
                            lastHopTime = System.currentTimeMillis();
                            sleep(200);
                        } else {
                            worlds.hop(WorldFunc.freeWorlds[currentIndex + 1]);
                            lastHopTime = System.currentTimeMillis();
                            sleep(200);
                        }
                    }
                } else {
                    toRobes.traverse();
                }
            }
        }
        return 200;
    }

    @Override
    public void repaint(Graphics2D g){
        g.setColor(Color.black);
        g.fillRect(0, 276, 150, 60 + 2);

        g.setColor(Color.white);
        g.drawRect(0, 276, 150, 60 + 2);
        int profit = robeTopPrice * robeTopGained + robeBottomPrice * robeBottomGained;

        long timeRunning = System.currentTimeMillis() - startTime;
        g.drawString("Profit/h: " + misc.formatNumber((int) (profit / (timeRunning / 3600000.0f))), 5, 324);
        g.drawString("Robes/h: " + misc.formatNumber((int)((robeTopGained + robeBottomGained) / (timeRunning / 3600000.0f))), 5, 336);
        long hours = timeRunning / 3600000;
        timeRunning -= hours * 3600000;
        long mins = timeRunning / 60000;
        timeRunning -= mins * 60000;
        long seconds = timeRunning / 1000;

        g.drawString("Time Running " + hours + ":" + mins + ":" + seconds, 5, 288);
        g.drawString("Profit: " + misc.formatNumber(profit), 5, 300);
        g.drawString("Robes gained: " + misc.formatNumber(robeTopGained+robeBottomGained), 5, 312);
    }
}
