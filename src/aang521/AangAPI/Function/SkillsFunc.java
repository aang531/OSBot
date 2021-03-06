package aang521.AangAPI.Function;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.AangUtil;

public class SkillsFunc extends AangUtil {

    public static final int ATTACK = 0;
    public static final int DEFENSE = 1;
    public static final int STRENGTH = 2;
    public static final int HITPOINTS = 3;
    public static final int RANGE = 4;
    public static final int PRAYER = 5;
    public static final int MAGIC = 6;
    public static final int COOKING = 7;
    public static final int WOODCUTTING = 8;
    public static final int FLETCHING = 9;
    public static final int FISHING = 10;
    public static final int FIREMAKING = 11;
    public static final int CRAFTING = 12;
    public static final int SMITHING = 13;
    public static final int MINING = 14;
    public static final int HERBLORE = 15;
    public static final int AGILITY = 16;
    public static final int THIEVING = 17;
    public static final int SLAYER = 18;
    public static final int FARMING = 19;
    public static final int RUNECRAFTING = 20;
    public static final int HUNTER = 21;
    public static final int CONSTRUCTION = 22;

    public SkillsFunc(AangScript script) {
        super(script);
    }

    public int getExp(int skill){
        return client.getLevelExperience()[skill];
    }

    public int getLevel(int skill){
        return client.getLevelStat()[skill];
    }

    public int getBoostedLevel( int skill ){
        return client.getCurrentLevelStat()[skill];
    }
}
