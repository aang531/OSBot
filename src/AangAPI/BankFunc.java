package AangAPI;

import AangAPI.DataTypes.Component;

import java.util.concurrent.Callable;

public class BankFunc extends AangUtil {
    private static BankFunc ourInstance = new BankFunc();

    public static BankFunc getInstance() {
        return ourInstance;
    }

    public static final int WIDGET_BANK = 12;
    public static final int COMPONENT_TABS = 10;
    public static final int COMPONENT_ITEMS = 12;
    public static final int COMPONENT_ITEM = 22;
    public static final int COMPONENT_NOTE = 23;
    public static final int COMPONENT_DEPOSIT_ALL = 27;
    public static final int COMPONENT_DEPOSIT_ARMOR = 29;

    public boolean opened(){
        return widgets.get(WIDGET_BANK).active();
    }

    public boolean depositAll(){
        return widgets.clickComponent(WIDGET_BANK,COMPONENT_DEPOSIT_ALL);
    }

    public boolean depositAll(int id ){
        return inventory.clickItem(inventory.getFirst(id),"Deposit-All");
    }

    public boolean depositArmor(){
        return widgets.clickComponent(WIDGET_BANK,COMPONENT_DEPOSIT_ARMOR);
    }

    public boolean withdraw(int id, int amount){
        final Component c = getItemComponent(id);
        if( c == null )
            return false;
        final Component itemWindow = widgets.get(WIDGET_BANK,COMPONENT_ITEMS);
        if(c.position().y < itemWindow.position().y || c.position().y + c.height() > itemWindow.position().y + itemWindow.height() ) {
            mouse.move(itemWindow.centerPoint());
            while (c.position().y < itemWindow.position().y || c.position().y + c.height() > itemWindow.position().y + itemWindow.height()) {
                if (c.position().y < itemWindow.position().y)
                    mouse.scrollDown();
                else
                    mouse.scrollUp();
            }
        }

        if( amount == 1 || amount == 5 || amount == 10)
            return widgets.clickComponentItem(c,"Withdraw-"+amount);

        mouse.move(c.centerPoint());
        sleep(80);
        mouse.click(false);
        sleep(80);
        final int index = menu.getActionIndex("Withdraw-"+amount);
        if( index == -1 ) {
            if( !widgets.clickComponentItem(c, "Withdraw-X") )
                return false;
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ChatFunc.getInstance().pendingInput();
                }
            },100);
            if( ChatFunc.getInstance().pendingInput() ){
                keyboard.sendln(""+amount);
                sleep(80);
                return true;
            }
        } else
            return menu.clickMenuOption(index);

        return false;
    }

    public boolean withdrawAll(int id){
        Component c = getItemComponent(id);
        return widgets.clickComponentItem(c,"Withdraw-All");
    }

    private Component getItemComponent(int id){
        Component c = widgets.get(WIDGET_BANK,COMPONENT_ITEMS);
        for( int i = 0; i < c.childCount(); i++ ){
            if( c.childs()[i].itemID() == id ) {
                return c.get(i);
            }
        }
        return null;
    }
}
