package AangAPI;

import AangAPI.DataTypes.Component;

import java.util.Arrays;

public class ChatFunc extends AangUtil{
    private static ChatFunc ourInstance = new ChatFunc();

    public static ChatFunc getInstance() {
        return ourInstance;
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
        return widgets.get(CHAT_WIDGET_OPTIONS,0,0).text();
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
        Component c = widgets.get(162, 32);
        return c != null && c.active();
    }
}
