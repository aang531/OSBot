package AangAPI;

import javax.swing.*;
import java.awt.*;
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

    public void display(){
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    protected void startScript(){
        script.start();
    }

    protected void exit(){
        script.stop(false);
    }
}
