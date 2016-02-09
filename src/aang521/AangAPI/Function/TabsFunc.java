package aang521.AangAPI.Function;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.AangUtil;
import aang521.AangAPI.DataTypes.Component;
import org.osbot.rs07.api.ui.Tab;

import java.awt.event.KeyEvent;

public class TabsFunc extends AangUtil {

    public TabsFunc(AangScript script) {
        super(script);
    }

    public Tab opened(){
        return script.getTabs().getOpen();
    }

    public void openInventory(){
        keyboard.send(KeyEvent.VK_ESCAPE);
    }
    public void openAttackTab() {
        keyboard.send(KeyEvent.VK_F1);
    }

    public void openLevelsTab() {
        keyboard.send(KeyEvent.VK_F2);
    }

    public void openQuestTab() {
        keyboard.send(KeyEvent.VK_F3);
    }

    public void openEquipmentTab() {
        keyboard.send(KeyEvent.VK_F4);
    }

    public void openPrayerTab() {
        keyboard.send(KeyEvent.VK_F5);
    }

    public void openMagicTab() {
        keyboard.send(KeyEvent.VK_F6);
    }

    public void openClanTab() {
        keyboard.send(KeyEvent.VK_F7);
    }

    public void openFriendTab() {
        keyboard.send(KeyEvent.VK_F8);
    }

    public void openIgnoreTab() {
        keyboard.send(KeyEvent.VK_F9);
    }

    public void openLogoutTab() {
        Component c = widgets.get(548,31);
        mouse.move(c.getCenterPoint());
        sleep(80);
        mouse.click(true);
    }

    public void openOptionsTab() {
        keyboard.send(KeyEvent.VK_F10);
    }

    public void openEmoteTab() {
        keyboard.send(KeyEvent.VK_F11);
    }

    public void openMusicTab() {
        keyboard.send(KeyEvent.VK_F12);
    }
}
