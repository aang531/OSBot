package aang521.AangAPI.Function;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.AangUtil;
import aang521.AangAPI.DataTypes.Component;
import org.osbot.rs07.script.Script;

public class MagicFunc extends AangUtil {

    public static final int MAGIC_WIDGET = 218;

    public MagicFunc(AangScript script) {
        super(script);
    }

    @SuppressWarnings("unused")
    enum SpellBook{
        NORMAL,
        LUNAR,
        ACIENT,
        NECROMANCY
    }

    @SuppressWarnings("unused")
    public enum Spell {
        HOME_TELEPORT_NORMAL(1, 0, 0, SpellBook.NORMAL),
        WIND_STRIKE(2, 1, 65, SpellBook.NORMAL),
        CONFUSE(3, 3, 66, SpellBook.NORMAL),
        ENCHANT_CROSSBOW_BOLT(4, 4, 0, SpellBook.NORMAL),
        WATER_STRIKE(5, 5, 67, SpellBook.NORMAL),
        LVL_1_ENCHANT(6, 7, 68, SpellBook.NORMAL),
        EARTH_STRIKE(7, 9, 69, SpellBook.NORMAL),
        WEAKEN(8, 11, 70, SpellBook.NORMAL),
        FIRE_STRIKE(9, 13, 71, SpellBook.NORMAL),
        BONES_TO_BANANAS(10, 15, 72, SpellBook.NORMAL),
        WIND_BOLT(11, 17, 73, SpellBook.NORMAL),
        CURSE(12, 19, 74, SpellBook.NORMAL),
        BIND(13, 20, 369, SpellBook.NORMAL),
        LOW_LEVEL_ALCHEMY(14, 21, 75, SpellBook.NORMAL),
        WATER_BOLT(15, 23, 76, SpellBook.NORMAL),
        VARROCK_TELEPORT(16, 25, 77, SpellBook.NORMAL),
        LVL_2_ENCHANT(17, 27, 78, SpellBook.NORMAL),
        EARTH_BOLT(18, 29, 79, SpellBook.NORMAL),
        LUMBRIDGE_TELEPORT(19, 31, 80, SpellBook.NORMAL),
        TELEKINETIC_GRAB(20, 33, 81, SpellBook.NORMAL),
        FIRE_BOLT(21, 35, 82, SpellBook.NORMAL),
        FALADOR_TELEPORT(22, 37, 83, SpellBook.NORMAL),
        CRUMBLE_UNDEAD(23, 39, 84, SpellBook.NORMAL),
        HOUSE_TELEPORT(24, 40, 405, SpellBook.NORMAL),
        WIND_BLAST(25, 41, 85, SpellBook.NORMAL),
        SUPERHEAT_ITEM(26, 43, 86, SpellBook.NORMAL),
        CAMELOT_TELEPORT(27, 45, 87, SpellBook.NORMAL),
        WATER_BLAST(28, 47, 88, SpellBook.NORMAL),
        LVL_3_ENCHANT(29, 49, 89, SpellBook.NORMAL),
        IBAN_BLAST(30, 50, 103, SpellBook.NORMAL),
        SNARE(31, 50, 370, SpellBook.NORMAL),
        MAGIC_DART(32, 50, 374, SpellBook.NORMAL),
        ARDOUGNE_TELEPORT(33, 51, 104, SpellBook.NORMAL),
        EARTH_BLAST(34, 53, 90, SpellBook.NORMAL),
        HIGH_LEVEL_ALCHEMY(35, 55, 91, SpellBook.NORMAL),
        CHARGE_WATER_ORB(36, 56, 92, SpellBook.NORMAL),
        LVL_4_ENCHANT(37, 57, 93, SpellBook.NORMAL),
        WATCHTOWER_TELEPORT(38, 58, 105, SpellBook.NORMAL),
        FIRE_BLAST(39, 59, 94, SpellBook.NORMAL),
        CHARGE_EARTH_ORB(40, 60, 95, SpellBook.NORMAL),
        BONES_TO_PEACHES(41, 60, 404, SpellBook.NORMAL),
        SARADOMIN_STRIKE(42, 60, 111, SpellBook.NORMAL),
        CLAWS_OF_GUTHIX(43, 60, 110, SpellBook.NORMAL),
        FLAMES_OF_ZAMORAK(44, 60, 109, SpellBook.NORMAL),
        TROLLHEIM_TELEPORT(45, 61, 373, SpellBook.NORMAL),
        WIND_WAVE(46, 62, 96, SpellBook.NORMAL),
        CHARGE_FIRE_ORB(47, 63, 97, SpellBook.NORMAL),
        TELEPORT_TO_APE_ATOLL(48, 64, 407, SpellBook.NORMAL),
        WATER_WAVE(49, 65, 98, SpellBook.NORMAL),
        CHARGE_AIR_ORB(50, 66, 99, SpellBook.NORMAL),
        VULNERABILITY(51, 66, 106, SpellBook.NORMAL),
        LVL_5_ENCHANT(52, 68, 100, SpellBook.NORMAL),
        TELEPORT_TO_KOUREND(53, 69, 410, SpellBook.NORMAL),
        EARTH_WAVE(54, 70, 101, SpellBook.NORMAL),
        ENFEEBLE(55, 73, 107, SpellBook.NORMAL),
        TELEOTHER_LUMBRIDGE(56, 74, 399, SpellBook.NORMAL),
        FIRE_WAVE(57, 75, 102, SpellBook.NORMAL),
        ENTANGLE(58, 79, 371, SpellBook.NORMAL),
        STUN(59, 80, 108, SpellBook.NORMAL),
        CHARGE(60, 80, 372, SpellBook.NORMAL),
        TELEOTHER_FALADOR(61, 82, 400, SpellBook.NORMAL),
        TELE_BLOCK(62, 85, 402, SpellBook.NORMAL),
        TELEPORT_TO_BOUNTY_TARGET_NORMAL(63, 85, 409, SpellBook.NORMAL),
        LVL_6_ENCHANT(64, 87, 403, SpellBook.NORMAL),
        TELEOTHER_CAMELOT(65, 90, 401, SpellBook.NORMAL),

        ICE_RUSH(67, 58, 375, SpellBook.ACIENT),
        ICE_BLITZ(68, 82, 377, SpellBook.ACIENT),
        ICE_BURST(69, 70, 376, SpellBook.ACIENT),
        ICE_BARRAGE(70, 94, 378, SpellBook.ACIENT),
        BLOOD_RUSH(71, 56, 383, SpellBook.ACIENT),
        BLOOD_BLITZ(72, 80, 385, SpellBook.ACIENT),
        BLOOD_BURST(73, 68, 384, SpellBook.ACIENT),
        BLOOD_BARRAGE(74, 92, 386, SpellBook.ACIENT),
        SMOKE_RUSH(75, 50, 379, SpellBook.ACIENT),
        SMOKE_BLITZ(76, 74, 381, SpellBook.ACIENT),
        SMOKE_BURST(77, 62, 380, SpellBook.ACIENT),
        SMOKE_BARRAGE(78, 86, 382, SpellBook.ACIENT),
        SHADOW_RUSH(79, 52, 387, SpellBook.ACIENT),
        SHADOW_BLITZ(80, 76, 389, SpellBook.ACIENT),
        SHADOW_BURST(81, 64, 388, SpellBook.ACIENT),
        SHADOW_BARRAGE(82, 88, 390, SpellBook.ACIENT),
        PADEWWA_TELEPORT(83, 54, 391, SpellBook.ACIENT),
        SENNTISTEN_TELEPORT(84, 60, 392, SpellBook.ACIENT),
        KHARYRLL_TELEPORT(85, 66, 393, SpellBook.ACIENT),
        LASSAR_TELEPORT(86, 72, 394, SpellBook.ACIENT),
        DAREEYAK_TELEPORT(87, 78, 395, SpellBook.ACIENT),
        CARRALLANGAR_TELEPORT(88, 84, 396, SpellBook.ACIENT),
        ANNAKARL_TELEPORT(89, 90, 397, SpellBook.ACIENT),
        GHORROCK_TELEPORT(90, 96, 398, SpellBook.ACIENT),
        TELEPORT_TO_BOUNTY_TARGET_ACIENT(91, 85, 409, SpellBook.ACIENT),
        HOME_TELEPORT_ACIENT(92, 0, 0, SpellBook.ACIENT),

        HOME_TELEPORT_LUNAR(94, 0, 0, SpellBook.LUNAR),
        BAKE_PIE(95, 65, 593, SpellBook.LUNAR),
        CURE_PLANT(96, 66, 617, SpellBook.LUNAR),
        MONSTER_EXAMINE(97, 66, 627, SpellBook.LUNAR),
        NPC_CONTACT(98, 67, 618, SpellBook.LUNAR),
        CURE_OTHER(99, 68, 609, SpellBook.LUNAR),
        HUMIDIFY(100, 68, 628, SpellBook.LUNAR),
        MOONCLAN_TELEPORT(101, 69, 594, SpellBook.LUNAR),
        TELE_GROUP_MOONCLAN(102, 70, 619, SpellBook.LUNAR),
        CURE_ME(103, 71, 612, SpellBook.LUNAR),
        HUNTER_KIT(104, 71, 629, SpellBook.LUNAR),
        WATERBIRTH_TELEPORT(105, 72, 595, SpellBook.LUNAR),
        TELE_GROUP_WATERBIRTH(106, 73, 620, SpellBook.LUNAR),
        CURE_GROUP(107, 74, 615, SpellBook.LUNAR),
        STAT_SPY(108, 75, 626, SpellBook.LUNAR),
        BARBARIAN_TELEPORT(109, 75, 597, SpellBook.LUNAR),
        TELE_GROUP_BARBARIAN(110, 76, 621, SpellBook.LUNAR),
        SUPERGLASS_MAKE(111, 77, 598, SpellBook.LUNAR),
        TAN_LEATHER(112, 78, 633, SpellBook.LUNAR),
        KHAZARD_TELEPORT(113, 78, 599, SpellBook.LUNAR),
        TELE_GROUP_KHAZARD(114, 79, 622, SpellBook.LUNAR),
        DREAM(115, 79, 630, SpellBook.LUNAR),
        STRING_JEWELLERY(116, 80, 600, SpellBook.LUNAR),
        STAT_RESTORE_POT_SHARE(117, 81, 604, SpellBook.LUNAR),
        MAGIC_IMBUE(118, 82, 602, SpellBook.LUNAR),
        FERTILE_SOIL(119, 83, 603, SpellBook.LUNAR),
        BOOST_POTION_SHARE(120, 84, 601, SpellBook.LUNAR),
        FISHING_GUILD_TELEPORT(121, 85, 605, SpellBook.LUNAR),
        TELEPORT_TO_BOUNTY_TARGET_LUNAR(122, 85, 409, SpellBook.LUNAR),
        TELE_GROUP_FISHING_GUILD(123, 86, 623, SpellBook.LUNAR),
        PLANK_MAKE(124, 86, 631, SpellBook.LUNAR),
        CATHERBY_TELEPORT(125, 87, 606, SpellBook.LUNAR),
        TELE_GROUP_CATHERBY(126, 88, 624, SpellBook.LUNAR),
        RECHARGE_DRAGONSTONE(127, 89, 634, SpellBook.LUNAR),
        ICE_PLATEAU_TELEPORT(128, 89, 607, SpellBook.LUNAR),
        TELE_GROUP_ICE_PLATEAU(129, 90, 625, SpellBook.LUNAR),
        ENERGY_TRANSFER(130, 91, 608, SpellBook.LUNAR),
        HEAL_OTHER(131, 92, 610, SpellBook.LUNAR),
        VENGEANCE_OTHER(132, 93, 611, SpellBook.LUNAR),
        VENGEANCE(133, 94, 614, SpellBook.LUNAR),
        HEAL_GROUP(134, 95, 616, SpellBook.LUNAR),
        SPELLBOOK_SWAP(135, 96, 632, SpellBook.LUNAR),
        GEOMANCY(136, 65, 613, SpellBook.LUNAR);

        //TODO NECRO BOOK

        int index;
        int levelReq;
        int spriteIndex;
        SpellBook spellBook;

        Spell(int index, int levelReq, int spriteIndex, SpellBook spellBook){
            this.index = index;
            this.levelReq = levelReq;
            this.spellBook = spellBook;
            this.spriteIndex = spriteIndex;
        }
    }

    public boolean isSpellSelected(){
        return client.getSpellSelected();
    }

    public String getSelectedSpellName(){
        return Script.stripFormatting(client.getSelectedSpellName());
    }

    public boolean selectSpell(Spell spell){
        if( !isOpen() )
            open();
        Component c = widgets.get(MAGIC_WIDGET,spell.index);
        return c.click(true);
    }

    public boolean deselectSpell(){
        if( isOpen() )
            selectSpell(getSelectedSpell());
        else
            widgets.get(548,51).click(true);
        return false;
    }

    public boolean cast(Spell spell ){
        if( !isOpen() )
            open();
        Component c = widgets.get(MAGIC_WIDGET,spell.index);
        return c.click(true);
    }

    public boolean canCast(Spell spell){
        return skills.getBoostedLevel(SkillsFunc.MAGIC) >= spell.levelReq;
    }

    public void open(){
        tabs.openMagicTab();
    }

    public boolean isOpen(){
        return widgets.get(548,51).getSpriteIndex1() != -1;
    }

    @Deprecated
    public Spell getSelectedSpell() {
        return null;//TODO
    }
}
