package aang521.AangCraftGuildMiner;

import aang521.AangAPI.AangGui;

import javax.swing.*;

public class GUI extends AangGui{
    private JPanel root;
    public JComboBox oreTypeComboBox;
    private JButton startButton;

    final AangCraftGuildMiner script;

    public GUI(final AangCraftGuildMiner script) {
        super(script, script.getName());
        this.script = script;
        setContentPane(root);

        startButton.addActionListener(e -> startScript());
    }
}
