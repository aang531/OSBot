package aang521.AangAPI;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AangGui extends JFrame {

    protected final AangScript script;

    public AangGui( final AangScript script, String title ){
        super(title);

        this.script = script;

        setResizable(false);

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                exit();
                e.getWindow().dispose();
            }
        });
    }

    public AangGui( final AangScript script ){
        this(script,script.getName());
    }

    public void display(){
        pack();
        setVisible(true);
        setLocation( script.getBot().getBotPanel().getLocationOnScreen().x + script.getBot().getBotPanel().getWidth()/2 - getWidth()/2,
                script.getBot().getBotPanel().getLocationOnScreen().y + script.getBot().getBotPanel().getHeight()/2 - getHeight()/2);
    }

    protected void startScript(){
        dispose();
        script.start();
    }

    protected void startScript(boolean hideGui){
        if( hideGui)
            dispose();
        script.start();
    }

    protected void exit(){
        script.stop(false);
    }
}
