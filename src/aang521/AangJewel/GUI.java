package aang521.AangJewel;

import aang521.AangAPI.AangGui;

import javax.swing.*;

public class GUI extends AangGui{
    public JComboBox jewelType;
    public JComboBox gemType;
    public JButton startButton;
    private JPanel root;
    public JComboBox location;

    final AangJewel aj;

    public GUI(final AangJewel aj) {
        super(aj, "aang521/AangJewel");
        this.aj = aj;
        setContentPane(root);

        startButton.addActionListener(e -> aj.startButtonPress());
    }
}
