package aang521.AangCraftGuildMiner;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.DataTypes.*;
import aang521.AangAPI.Function.SkillsFunc;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;
import java.util.Arrays;

@ScriptManifest(name = "AangCraftGuildMiner", author = "aang521", version = 1.0, info = "Mines ores at crafting guild.", logo = "")
public class AangCraftGuildMiner extends AangScript {

    private static final int chestID = 100058;
    private static final int bankID = 24101;
    private RSObject bankObject;
    private Tile chestTile = new Tile(this,2936,3280,0);

    private TileArea bankAreaP2P= new TileArea( this,2932, 3279, 4, 3 );
    private TileArea bankAreaF2P = new TileArea( this,3009, 3355, 8, 3 );
    private TileArea bankArea;
    private TileArea mineArea = new TileArea( this,2937, 3276, 6, 15 );

    private Path toBank;
    private Path toMine;

    private Path toMineP2P = new Path( this, new Tile[] {new Tile( this,2934,3281,0), new Tile( this,2940,3282,0)});
    private Path toBankP2P = new Path( this, new Tile[] {new Tile( this,2940,3282,0), new Tile( this,2934,3281,0)});

    private Path toBankF2P = new Path( this, new Tile[] {new Tile( this,2933,3288,0), new Tile( this,2933,3291,0),
            new Tile( this,2938,3295,0), new Tile( this,2943,3296,0), new Tile( this,2948,3299,0), new Tile( this,2953,3298,0),
            new Tile( this,2959,3298,0), new Tile( this,2966,3298,0), new Tile( this,2972,3298,0), new Tile( this,2977,3301,0),
            new Tile( this,2982,3303,0), new Tile( this,2987,3307,0), new Tile( this,2992,3311,0), new Tile( this,2997,3314,0),
            new Tile( this,3001,3318,0), new Tile( this,3005,3322,0), new Tile( this,3005,3328,0), new Tile( this,3006,3334,0),
            new Tile( this,3006,3340,0), new Tile( this,3006,3346,0), new Tile( this,3006,3352,0), new Tile( this,3007,3358,0),
            new Tile( this,3012,3359,0), new Tile( this,3012,3355,0)},
            new Obstacle[]{ new Obstacle( this,7135, new Tile( this,2933, 3289, 0), new TileArea( this,2931, 3285, 5, 4 ), "Open" )});
    private Path toMineF2P = new Path( this, new Tile[] {new Tile( this,3012,3355,0), new Tile( this,3012,3359,0), new Tile( this,3007,3358,0),
            new Tile( this,3006,3352,0), new Tile( this,3006,3346,0), new Tile( this,3006,3340,0), new Tile( this,3006,3334,0),
            new Tile( this,3005,3328,0), new Tile( this,3005,3322,0), new Tile( this,3001,3318,0), new Tile( this,2997,3314,0),
            new Tile( this,2992,3311,0), new Tile( this,2987,3307,0), new Tile( this,2982,3303,0), new Tile( this,2977,3301,0),
            new Tile( this,2972,3298,0), new Tile( this,2966,3298,0), new Tile( this,2959,3298,0), new Tile( this,2953,3298,0),
            new Tile( this,2948,3299,0), new Tile( this,2943,3296,0), new Tile( this,2938,3295,0), new Tile( this,2933,3291,0),
            new Tile( this,2936,3286,0), new Tile( this,2940,3282,0)},
            new Obstacle[]{ new Obstacle( this,7135, new Tile( this,2933, 3289, 0), new TileArea( this,2932, 3289, 2, 3 ), "Open" )});

    private static int[] pickaxeIDs = new int[]{1265,1267,1269,12297,1273,1271,1275,11920,11920};//TODO check if dragon pick(or) is different id
    private int pickaxe = -1;

    private int[] oreIDs;
    private RSObject currentOre;
    private int orePrice;

    private static final int[] goldOres = new int[]{7491,7458};
    private static final int[] silverOres = new int[]{7490,7457};
    private static final int[] clayOres = new int[]{7487,7454};
    private static final int[][] allOreIDS = new int[][]{clayOres,silverOres,goldOres};

    private boolean testForNewPick = false;
    private int highestPickTier = 0;
    private boolean didStartChecks = false;
    private boolean equipNewPick = true;

    private int miningLevelsGained = 0;

    private long startTime;
    private int startExp;
    private int oresMined = 0;

    private String state = "NULL";

    private boolean p2p;

    private GUI gui;

    @Override
    public void init(){
        gui = new GUI(this);
        setGUI(gui);
    }

    @Override
    public void scriptStart() {
        startTime = System.currentTimeMillis();
        if( worlds.isCurrentMember() ){
            p2p = true;
            toBank = toBankP2P;
            toMine = toMineP2P;
            bankArea = bankAreaP2P;
        }else{
            p2p = false;
            toBank = toBankF2P;
            toMine = toMineF2P;
            bankArea = bankAreaF2P;
        }
        oreIDs = allOreIDS[gui.oreTypeComboBox.getSelectedIndex()];
        new Thread(){
            public void run(){
                switch(gui.oreTypeComboBox.getSelectedIndex()){
                    case 0 :
                        orePrice = misc.getGEPrice(434);
                        break;
                    case 1:
                        orePrice = misc.getGEPrice(442);
                        break;
                    case 2:
                        orePrice = misc.getGEPrice(444);
                        break;
                }
            }
        }.run();
    }

    public boolean atBank(){
        return bankArea.contains(localPlayer());
    }

    public boolean atMine(){
        return mineArea.contains(localPlayer());
    }

    public boolean canEquipPick(int tier ){
        int[] reqs = new int[]{1,1,5,10,20,30,40,60,60};//does not include infernal pick
        return skills.getLevel(SkillsFunc.ATTACK) >= reqs[tier];
    }

    public boolean canUse(int tier ){
        int[] reqs = new int[]{1,1,6,11,21,31,41,61,61};//does not include infernal pick
        return skills.getLevel(SkillsFunc.MINING) >= reqs[tier];
    }

    public int getTier(int id ){
        for( int i = 0; i < pickaxeIDs.length; i++ ){
            if( id == pickaxeIDs[i] )
                return i;
        }
        return 0;
    }

    public boolean mining(){
        return currentOre != null && currentOre.valid() && (localPlayer().getAnimation() != -1 || localPlayer().isMoving());
    }

    public void startChecks(){
        startExp = skills.getExp(SkillsFunc.MINING);
        for( int i = pickaxeIDs.length-1; i >= 0; i-- )
            if( canUse(i) ) {
                highestPickTier = i;
                break;
            }

        Item equipedPick = equipment.getWeapon();
        int currentPickTier = -1;
        boolean pickEquiped = false;
        if( equipedPick != null ){
            pickaxe = equipedPick.getID();
            boolean found = false;
            for( int i = 0; i < pickaxeIDs.length; i++ ){
                if( pickaxeIDs[i] == pickaxe ){
                    found = true;
                    currentPickTier = i;
                    pickEquiped = true;
                    break;
                }
            }
            if( !found )
                pickaxe = -1;
        }
        Item[] items = inventory.getAll();
        for( Item item : items )
            for( int i = 0; i <= highestPickTier; i++ ){
                if( item.getID() == pickaxeIDs[i] && i > currentPickTier ){
                    currentPickTier = i;
                    pickEquiped = false;
                    break;
                }
            }
        if(!pickEquiped && currentPickTier != -1 ){
            if( canEquipPick(currentPickTier) ){
                if( !inventory.isOpen())
                    inventory.open();
                inventory.get(pickaxeIDs[currentPickTier]).click("Wield");
            }
        }

        if( currentPickTier == -1 )
            pickaxe = -1;
        else
            pickaxe = pickaxeIDs[currentPickTier];
        didStartChecks = true;
    }

    public void withdrawNewPick(){
        int currentPickTier = -1;
        Item weap = equipment.getWeapon();
        if( weap != null ){
            for( int i = 0; i < pickaxeIDs.length; i++ ){
                if( pickaxeIDs[i] == weap.getID() ){
                    currentPickTier = i;
                    break;
                }
            }
        }
        Item[] items = inventory.getAll();
        for( Item item : items )
            for( int i = 0; i < pickaxeIDs.length; i++ ){
                if( item.getID() == pickaxeIDs[i] && i > currentPickTier ){
                    currentPickTier = i;
                    break;
                }
            }

        for( int i = highestPickTier; i > currentPickTier && i >= 0; i-- ){
            if( bank.contains(pickaxeIDs[i]) ){
                bank.withdraw(pickaxeIDs[i],1);
                if( inventory.contains(pickaxeIDs[i]))
                    equipNewPick = true;
                if( pickaxe != -1 )
                    bank.depositAll(pickaxe);
                pickaxe = pickaxeIDs[i];
                break;
            }
        }

        testForNewPick = false;
    }

    public void equipNewPick(){
        if( bank.opened() && canEquipPick(getTier(pickaxe)) )
            bank.close();
        Item newPick = pickaxe == -1 ? null : inventory.get(pickaxe);
        if( newPick != null && newPick.valid() && canEquipPick(getTier(pickaxe)))
            newPick.click("Wield");
        equipNewPick = false;
        if( atBank() && inventory.contains(pickaxe)? inventory.getCount() > 1 : inventory.getCount() > 0){
            if( !bank.opened() ) {
                if (p2p) {
                    if (bankObject == null || !bankObject.valid())
                        bankObject = objects.getAt(chestTile, chestID);
                    if (bankObject != null && bankObject.valid()) {
                        if (bankObject.click("Use"))
                            sleep(bank::opened, 100, 10);
                    }
                } else {
                    if (bankObject == null || !bankObject.valid())
                        bankObject = objects.getNearest(bankID);
                    if (bankObject != null && bankObject.valid()) {
                        if (bankObject.click("Bank"))
                            sleep(bank::opened, 100, 10);
                    }
                }
            }
            if( bank.opened() ){
                bank.depositAllExcept(pickaxe);
            }
        }
    }

    @Override
    public int loop() throws InterruptedException {
        if( game.playing() ) {
            if( !didStartChecks ){
                state = "startup";
                startChecks();
            }

            if (inventory.isFull()) {
                if (atBank()) {
                    if (bank.opened()) {
                        testForNewPick = true;
                        if( bank.depositAllExcept(pickaxe) )
                            sleep(() -> inventory.contains(pickaxe) ? inventory.getCount() == 1 : inventory.getCount() == 0);
                    } else {
                        if( p2p ) {
                            if (bankObject == null || !bankObject.valid())
                                bankObject = objects.getAt(chestTile, chestID);
                            if (bankObject != null && bankObject.valid()) {
                                if (bankObject.click("Use"))
                                    sleep(bank::opened, 100, 10);
                            }
                        }else{
                            if (bankObject == null || !bankObject.valid())
                                bankObject = objects.getNearest(bankID);
                            if (bankObject != null && bankObject.valid()) {
                                if (bankObject.click("Bank"))
                                    sleep(bank::opened, 100, 10);
                            }
                        }
                    }
                } else {
                    if (!toBank.traverse())
                        movement.webwalk(toBank.getDest());
                }
            } else {
                if (testForNewPick && bank.opened()) {
                    state = "withdrawing new pick";
                    withdrawNewPick();
                } else if( equipNewPick ){
                    state = "equiping new pick";
                    equipNewPick();
                }else if (atMine()) {
                    if( !mining() ){
                        state = "finding new ore";
                        currentOre = objects.getNearest(oreIDs);
                        if( currentOre != null && currentOre.valid() ){
                            if( currentOre.isOnScreen() ){
                                if( currentOre.click("Mine") )
                                    sleep( this::mining, 200, 20 );
                            }else
                                movement.walkTile(currentOre.getTile());
                        }else{
                            //TODO walk to ore which will spawn first
                        }
                    }else{
                        RSObject[] ores = objects.getAll(oreIDs);
                        int dist = Integer.MAX_VALUE;
                        RSObject nextOre = null;
                        for( RSObject ore : ores ){
                            if( ore.osObject != currentOre.osObject ){
                                int thisDist = ore.sqrDistTo(localPlayer());
                                if( thisDist < dist ){
                                    dist = thisDist;
                                    nextOre = ore;
                                }
                            }
                        }
                        if( nextOre != null && nextOre.isOnScreen() )
                            if( !nextOre.getBoundingBox().contains(mouse.getPosition()))
                                mouse.move(nextOre.getRandomPoint());
                        state = "mining";
                    }
                } else {
                    state = "walking to mine";
                    if (!toMine.traverse())//TODO draw path for debug and fix that shit.
                        movement.webwalk(toMine.getDest());
                }
            }
        }
        return 200;
    }

    @Override
    public void repaint(Graphics2D g) {
        g.drawString("Mining: "+mining(), 5, 100);
        g.drawString("State: "+state,5,112);

        long timeRunning = System.currentTimeMillis() - startTime;
        final float hoursf = timeRunning / 3600000.0f;
        final int profit = orePrice * oresMined;

        long hours = timeRunning / 3600000;
        timeRunning -= hours * 3600000;
        long mins = timeRunning / 60000;
        timeRunning -= mins * 60000;
        long seconds = timeRunning / 1000;

        final int expGained = skills.getExp(SkillsFunc.MINING)-startExp;

        g.setColor(Color.black);
        g.fillRect(0, 253, 150, 84 + 2);

        g.setColor(Color.white);
        g.drawRect(0, 253, 150, 84 + 2);

        g.drawString("Time Running " + hours + ":" + mins + ":" + seconds, 5, 264);
        g.drawString("Profit: " + misc.formatNumber(profit), 5, 276);
        g.drawString("Profit/h: " + misc.formatNumber((int) (profit / hoursf)), 5, 288);
        g.drawString("Ores mined: " + misc.formatNumber(oresMined), 5, 300);
        g.drawString("Ores/h: " + misc.formatNumber((int) (oresMined / hoursf)), 5, 312);
        g.drawString("Exp gained: " + misc.formatNumber(expGained), 5, 324);
        g.drawString("Exp/h: " + misc.formatNumber((int) (expGained / hoursf)), 5, 336);
    }

    @Override
    public void onMessage( Message m ){
        if(m.getType() == Message.MessageType.GAME) {
            if (m.getMessage().equals("Congratulations, you just advanced a Mining level.")) {
                if (skills.getLevel(SkillsFunc.MINING) % 10 == 1 || skills.getLevel(SkillsFunc.MINING) == 6) {
                    testForNewPick = true;
                    for( int i = pickaxeIDs.length-1; i >= 0; i-- )
                        if( canUse(i) ) {
                            highestPickTier = i;
                            break;
                        }
                    log("Leveld to new pick tier: "+highestPickTier);
                }
                miningLevelsGained++;
            }else if( m.getMessage().contains("You manage to mine some"))
                oresMined++;
        }
    }
}
