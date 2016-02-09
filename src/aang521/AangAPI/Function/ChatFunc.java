package aang521.AangAPI.Function;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.AangUtil;
import aang521.AangAPI.DataTypes.Component;

import java.util.Arrays;

public class ChatFunc extends AangUtil {

    public ChatFunc( AangScript script) {
        super(script);
    }
    private static final int CHAT_WIDGET_OTHER_TALKING = 231;
    private static final int CHAT_WIDGET_SELF_TALKING = 217;
    private static final int CHAT_WIDGET_OPTIONS = 219;
    private static final int CHAT_COMPONENT_CONTINUE = 2;

    public boolean chatting(){
        return widgets.widget(CHAT_WIDGET_OTHER_TALKING).active()
                || widgets.widget(CHAT_WIDGET_SELF_TALKING).active()
                || widgets.widget(CHAT_WIDGET_OPTIONS).active();
    }

    public boolean isChatOpen(int widgetID, int componentID) {
        return widgets.get(widgetID,componentID).valid();
    }

    public boolean canContinue(){
        return widgets.get(CHAT_WIDGET_OTHER_TALKING, CHAT_COMPONENT_CONTINUE).valid()
                || widgets.get(CHAT_WIDGET_SELF_TALKING, CHAT_COMPONENT_CONTINUE).valid();
    }

    public void continueChat(){
        keyboard.send("{VK_SPACE}");
    }

    public boolean isPendingOption() {
        return widgets.get(CHAT_WIDGET_OPTIONS).active();
    }

    public String getPendingOptionsQuestion() {
        return widgets.get(CHAT_WIDGET_OPTIONS,0,0).getText();
    }

    public Component[] getPendingOptions() {
        Component[] components = widgets.get(CHAT_WIDGET_OPTIONS,0).childs();
        if( components.length <= 2 )
            return null;
        return Arrays.copyOfRange(components,1,components.length-2);
    }

    public void chooseOption(int index){
        keyboard.send("{"+index+"}");
    }

    public boolean pendingInput() {
        Component c = widgets.get(162, 31);
        return c != null && c.valid() && c.visable();
    }
}
