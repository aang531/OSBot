package aang521.AangScriptingUtils;

import aang521.AangAPI.AangGui;
import aang521.AangAPI.DataTypes.*;
import aang521.AangAPI.DataTypes.Component;
import aang521.AangAPI.DataTypes.GroundItem;
import aang521.AangAPI.DataTypes.Player;
import aang521.AangAPI.Misc.GUI.PlaceholderTextField;
import aang521.AangAPI.Misc.GUI.NumberFilter;
import org.osbot.rs07.canvas.paint.Painter;
import org.osbot.rs07.input.mouse.BotMouseListener;
import org.osbot.rs07.listener.ConfigListener;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUI extends AangGui {

    private JPanel root;
    private JTabbedPane tabs;
    private JCheckBox objectsCheckBox;
    private JTextArea varpbitValues;
    private PlaceholderTextField gotoTextField;
    private JButton clearButton;
    private JButton pauseButton;
    private JList varpbitsList;
    private JTree widgetTree;
    private JTextField widgetsFilter;
    private JButton updateButton;
    private JTextArea selectedWidgetInfo;
    private JList varpbitChangeList;
    private JCheckBox playersCheckBox;
    private JCheckBox NPCsCheckBox;
    private JCheckBox groundItemsCheckBox;
    private JCheckBox playerLocationCheckBox;
    private JCheckBox cursorLocationCheckBox;
    private JCheckBox hoverEnitiesCheckBox;
    private JButton startRecordingButton;
    private JButton resetButton;
    private JButton copyToClipboardButtonPath;
    private JButton addNewObstacleButton;
    private JButton selectEndPointButton;
    private JButton selectStartPointButton;
    private JButton copyToClipboardButtonArea;
    private JList obstacleList;
    private JButton deleteSelectedButtonObtacle;
    private JList pathList;
    private JButton deleteSelectedButtonPath;
    private JCheckBox drawEntitiesCheckBox;
    private JCheckBox fillDrawnEntitiesCheckBox;
    private JButton selectObjectToDebugButton;
    private JComboBox objectDebugSelectionType;
    private JTextArea selectedObjectValues;
    private JButton clearSelectedDebugObject;
    private JCheckBox inventoryCheckBox;
    private JButton addCurrentTileButton;
    private JButton selectObstacleButton;
    private PlaceholderTextField action;
    private JComboBox obstacleTypeComboBox;
    private JButton selectFirstTileAreaButton;
    private JButton selectSecondTileAreaButton;
    private JButton invertPathButton;
    private JButton testPathButton;
    private JCheckBox menuCheckBox;
    private JCheckBox gameStateCheckBox;
    private JCheckBox mapBaseCheckBox;

    private Painter p;
    private ConfigListener configListener;
    private BotMouseListener mouseListener;
    private Thread thread;

    private boolean active = true;

    private static final int GENERAL = 0;
    private static final int PATHMAKER = 1;
    private static final int AREAMAKER = 2;
    private static final int VAPRBITS = 3;
    private static final int WIDGETS = 4;

    private int[] varpbits;
    private boolean varpbitsPaused = false;
    private DefaultMutableTreeNode widgetRootNode;
    private Component selectedComponent = null;
    private Widget selectedWidget = null;

    private boolean selectingDebugObject = false;
    private int toSelectDebugType = -1;
    private int selectedDebugType = -1;
    private RSObject selectedObject;
    private Npc selectedNPC;
    private Player selectedPlayer;
    private Tile selectedTile;
    private GroundItem selectedGroundItem;
    private Item selectedItem;

    private List<Tile> tilePath = new ArrayList<>();
    private List<Obstacle> obstacles = new ArrayList<>();
    private boolean recordPath = false;
    private boolean selectingObstacle = false;
    private boolean selectFirstTileAreaPath = false;
    private boolean selectSecondTileAreaPath = false;
    private boolean selectFirstTileArea = false;
    private boolean selectSecondTileArea = false;

    private TileArea area = new TileArea(script,-1,-1,0,0);

    private Path testPath = null;
    private boolean testingPath = false;

    private static final Font font = new Font("Helvetica",Font.PLAIN,12);

    public GUI(AangScriptingUtils script){
        super(script, "aang521/AangScriptingUtils");
        setContentPane(root);

        varpbitChangeList.setModel(new DefaultListModel<>());

        varpbits = script.client.getConfigs1();
        for( int i = 0; i < varpbits.length; i++ )
            ((DefaultListModel)varpbitsList.getModel()).addElement(i+": " + varpbits[i] + " (0x"+Integer.toHexString(varpbits[i]).toUpperCase()+")");

        configListener = (config, value) -> {
            if( !varpbitsPaused ) {
                DefaultListModel l = (DefaultListModel) varpbitChangeList.getModel();
                l.addElement(config + ": " + varpbits[config] + " (0x" + Integer.toHexString(varpbits[config]).toUpperCase() + ") -> " +
                        value + " (0x" + Integer.toHexString(value).toUpperCase() + ")");
            }
            varpbits[config] = value;
            varpbitsList.setModel(new DefaultListModel<>());
            for( int i = 0; i < varpbits.length; i++ )
                ((DefaultListModel)varpbitsList.getModel()).addElement(i+": " + varpbits[i] + " (0x"+Integer.toHexString(varpbits[i]).toUpperCase()+")");

        };

        mouseListener = new BotMouseListener() {
            @Override
            public boolean blockInput(Point point) {
                return false;
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if( selectingDebugObject ) {
                    selectingDebugObject = false;
                    selectObjectToDebugButton.setEnabled(true);

                    selectedObject = null;
                    selectedNPC = null;
                    selectedPlayer = null;
                    selectedTile = null;
                    selectedGroundItem = null;

                    switch (toSelectDebugType) {
                        case 0:
                            selectedDebugType = toSelectDebugType;
                            RSObject[] objects = script.mouse.getObjectsOnCursor();
                            boolean found = false;
                            if (selectedObject != null)
                                for (int i = 0; i < objects.length; i++) {
                                    if (selectedObject != null && objects[i].osObject.equals(selectedObject.osObject)) {
                                        found = true;
                                        selectedObject = objects[i == objects.length - 1 ? 0 : i + 1];
                                    }
                                }
                            if (!found && objects.length != 0)
                                selectedObject = objects[0];
                            break;

                        case 1:
                            selectedDebugType = toSelectDebugType;
                            Npc[] npcs = script.mouse.getNpcsOnCursor();
                            found = false;
                            if (selectedNPC != null)
                                for (int i = 0; i < npcs.length; i++) {
                                    if (selectedNPC != null && npcs[i].osnpc.equals(selectedNPC.osnpc)) {
                                        found = true;
                                        selectedNPC = npcs[i == npcs.length - 1 ? 0 : i + 1];
                                    }
                                }
                            if (!found && npcs.length != 0)
                                selectedNPC = npcs[0];
                            break;

                        case 2:
                            selectedDebugType = toSelectDebugType;
                            Player[] players = script.mouse.getPlayersOnCursor();
                            found = false;
                            if (selectedPlayer != null)
                                for (int i = 0; i < players.length; i++) {
                                    if (selectedPlayer != null && players[i].osplayer.equals(selectedPlayer.osplayer)) {
                                        found = true;
                                        selectedPlayer = players[i == players.length - 1 ? 0 : i + 1];
                                    }
                                }
                            if (!found && players.length != 0)
                                selectedPlayer = players[0];
                            break;

                        case 3:
                            selectedTile = script.mouse.getTileOnCursor();
                            selectedDebugType = toSelectDebugType;
                            break;

                        case 4:
                            selectedDebugType = toSelectDebugType;
                            GroundItem[] groundItems = script.mouse.getGroundItemsOnCursor();
                            found = false;
                            if (selectedGroundItem != null)
                                for (int i = 0; i < groundItems.length; i++) {
                                    if (selectedGroundItem != null && groundItems[i].osGroundItem.equals(selectedGroundItem.osGroundItem)) {
                                        found = true;
                                        selectedGroundItem = groundItems[i == groundItems.length - 1 ? 0 : i + 1];
                                    }
                                }
                            if (!found && groundItems.length != 0)
                                selectedGroundItem = groundItems[0];
                            break;

                        case 5:
                            selectedDebugType = toSelectDebugType;
                            selectedItem = script.mouse.getInventoryItemOnCursor();
                            break;

                        default:
                            break;
                    }
                }else if( selectingObstacle ){
                    selectingObstacle = false;
                    selectObstacleButton.setEnabled(true);
                    if( obstacleList.getSelectedIndex() == -1 )
                        return;
                    if( obstacleTypeComboBox.getSelectedIndex() == 0 ){
                        RSObject[] objects = script.mouse.getObjectsOnCursor();
                        if( objects.length > 0 ){
                            obstacles.get(obstacleList.getSelectedIndex()).type = Obstacle.ObstacleType.OBJECT;
                            obstacles.get(obstacleList.getSelectedIndex()).obstacleTile = objects[0].getTile();
                            obstacles.get(obstacleList.getSelectedIndex()).id = objects[0].getID();
                            obstacles.get(obstacleList.getSelectedIndex()).action = objects[0].getActions()[0];
                            updateObstacle(obstacleList.getSelectedIndex());
                        }
                    }else if(obstacleTypeComboBox.getSelectedIndex() == 1){
                        Npc[] npcs = script.mouse.getNpcsOnCursor();
                        if( npcs.length > 0 ){
                            obstacles.get(obstacleList.getSelectedIndex()).type = Obstacle.ObstacleType.NPC;
                            obstacles.get(obstacleList.getSelectedIndex()).id = npcs[0].getID();
                            obstacles.get(obstacleList.getSelectedIndex()).action = npcs[0].getActions()[0];
                            updateObstacle(obstacleList.getSelectedIndex());
                        }
                    }
                }else if(selectFirstTileAreaPath){
                    selectFirstTileAreaPath = false;
                    selectFirstTileAreaButton.setEnabled(true);
                    if( obstacleList.getSelectedIndex() == -1 )
                        return;

                    Tile t = script.mouse.getTileOnCursor();
                    if( t != null ) {
                        Obstacle o = obstacles.get(obstacleList.getSelectedIndex());
                        o.area.floor = t.z;
                        if (o.area.width == 0 && o.area.height == 0) {
                            o.area.x = t.x;
                            o.area.y = t.y;
                        } else {
                            final int secondx = o.area.x + o.area.width;
                            final int secondy = o.area.y + o.area.height;
                            o.area.x = t.x;
                            o.area.y = t.y;

                            o.area.width = t.x - secondx;
                            o.area.width *= o.area.width < 0 ? -1 : 1;
                            o.area.height = t.y - secondy;
                            o.area.height *= o.area.height < 0 ? -1 : 1;
                        }
                    }
                }else if (selectSecondTileAreaPath){
                    selectSecondTileAreaPath = false;
                    selectSecondTileAreaButton.setEnabled(true);
                    if( obstacleList.getSelectedIndex() == -1 )
                        return;

                    Tile t = script.mouse.getTileOnCursor();
                    Obstacle o = obstacles.get(obstacleList.getSelectedIndex());

                    if( o.area.x == -1 ){
                        o.area.x = t.x;
                        o.area.y = t.y;
                    }else{
                        final int tmpx = o.area.x;
                        final int tmpy = o.area.y;

                        o.area.x = Math.min(o.area.x,t.x);
                        o.area.y = Math.min(o.area.y,t.y);

                        o.area.width = o.area.x - Math.max(tmpx,t.x);
                        o.area.height = o.area.y - Math.max(tmpy,t.y);
                        o.area.width *= o.area.width < 0 ? -1 : 1;
                        o.area.height *= o.area.height < 0 ? -1 : 1;
                    }
                }else if(selectFirstTileArea){
                    selectFirstTileArea = false;
                    selectStartPointButton.setEnabled(true);

                    Tile t = script.mouse.getTileOnCursor();
                    area.floor = t.z;
                    if( area.width == 0 && area.height == 0 ){
                        area.x = t.x;
                        area.y = t.y;
                    }else {
                        final int secondx = area.x + area.width;
                        final int secondy = area.y + area.height;
                        area.x = t.x;
                        area.y = t.y;

                        area.width = t.x - secondx;
                        area.width *= area.width < 0 ? -1 : 1;
                        area.height = t.y - secondy;
                        area.height *= area.height < 0 ? -1 : 1;
                    }
                }else if( selectSecondTileArea ){
                    selectSecondTileArea = false;
                    selectEndPointButton.setEnabled(true);

                    Tile t = script.mouse.getTileOnCursor();

                    if( area.x == -1 ){
                        area.x = t.x;
                        area.y = t.y;
                    }else{
                        final int tmpx = area.x;
                        final int tmpy = area.y;

                        area.x = Math.min(area.x,t.x);
                        area.y = Math.min(area.y,t.y);

                        area.width = area.x - Math.max(tmpx,t.x);
                        area.height = area.y - Math.max(tmpy,t.y);
                        area.width *= area.width < 0 ? -1 : 1;
                        area.height *= area.height < 0 ? -1 : 1;
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };

        p = g -> {
            g.setClip(script.bot.getCanvas().getBounds());
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1));
            g.setFont(font);
            g.setStroke(new BasicStroke(1));
            g.setColor(Color.white);

            int textYOffset = 30;

            if(cursorLocationCheckBox.isSelected()) {
                g.drawString("Mouse x: " + script.mouse.getPosition().x + " y: " + script.mouse.getPosition().y, 5, textYOffset += 12);
                g.drawLine(0,script.mouse.getPosition().y,800,script.mouse.getPosition().y);
                g.drawLine(script.mouse.getPosition().x,0,script.mouse.getPosition().x,510);
            }
            if(playerLocationCheckBox.isSelected())
                g.drawString("Player pos: "+script.localPlayer().getTile().toString(),5,textYOffset+=12);
            if( mapBaseCheckBox.isSelected() )
                g.drawString("MapBase x: " + script.map.getBaseX() + " y: " + script.map.getBaseY(),5,textYOffset+=12);
            if( gameStateCheckBox.isSelected() )
                g.drawString("GameState: "+ script.game.getState(),5,textYOffset+=12);
            if( menuCheckBox.isSelected() ){
                textYOffset+=12;
                g.drawString("Menu: ",5,textYOffset+=12);
                String[] actions = script.menu.actions();
                String[] names = script.menu.names();
                for(int i = 0; i < script.menu.count(); i++ )
                    g.drawString(i+" "+actions[i] + " " + names[i],5,textYOffset+=12);
            }
            if(hoverEnitiesCheckBox.isSelected()){
                RSObject[] objects = script.mouse.getObjectsOnCursor();
                Npc[] npcs = script.mouse.getNpcsOnCursor();
                Player[] players = script.mouse.getPlayersOnCursor();
                GroundItem[] groundItem = script.mouse.getGroundItemsOnCursor();

                g.setColor(Color.blue);
                for(RSObject o : objects )
                    o.drawModel(g);
                g.setColor(Color.green);
                for( Npc npc : npcs )
                    npc.drawModel(g);
                g.setColor(Color.red);
                for( Player player : players )
                    player.drawModel(g);
                g.setColor(Color.yellow);
                for( GroundItem gi : groundItem )
                    gi.drawModel(g);

                Tile t = script.mouse.getTileOnCursor();
                if(t != null )
                    t.draw(g);
                g.setColor(Color.white);
            }
            if(objectsCheckBox.isSelected()){
                RSObject[] objects = script.objects.getAll();
                for(RSObject o : objects) {
                    if( o.getBoundingBox() != null ) {
                        if (drawEntitiesCheckBox.isSelected())
                            if (fillDrawnEntitiesCheckBox.isSelected())
                                o.fillModel(g);
                            else
                                o.drawModel(g);
                        Point p = o.getCenterPoint();
                        g.drawString("" + o.getID(), p.x, p.y);
                    }
                }
            }
            if(playersCheckBox.isSelected()){
                Player[] players = script.players.getAll();
                for(Player o : players) {
                    if( o.getBoundingBox() != null ) {
                        if (drawEntitiesCheckBox.isSelected())
                            if (fillDrawnEntitiesCheckBox.isSelected())
                                o.fillModel(g);
                            else
                                o.drawModel(g);
                        Point p = o.getCenterPoint();
                        g.drawString(o.getName(), p.x, p.y);
                    }
                }
            }
            if(NPCsCheckBox.isSelected()){
                Npc[] npcs = script.npcs.getAll();
                for(Npc o : npcs) {
                    if( o.getBoundingBox() != null ) {
                        if (drawEntitiesCheckBox.isSelected())
                            if (fillDrawnEntitiesCheckBox.isSelected())
                                o.fillModel(g);
                            else
                                o.drawModel(g);
                        Point p = o.getCenterPoint();
                        g.drawString(o.getName() + " " + o.getID(), p.x, p.y);
                    }
                }
            }
            if(groundItemsCheckBox.isSelected()){
                GroundItem[] groundItems = script.groundItems.getAll();
                for(GroundItem o : groundItems) {
                    if( o.getBoundingBox() != null ) {
                        if (drawEntitiesCheckBox.isSelected())
                            if (fillDrawnEntitiesCheckBox.isSelected())
                                o.fillModel(g);
                            else
                                o.drawModel(g);
                        Point p = o.getCenterPoint();
                        g.drawString(o.getID()+"", p.x, p.y);
                    }
                }
            }
            if(inventoryCheckBox.isSelected()){
                Item[] items = script.inventory.getAll();
                for(Item o : items) {
                    if(o.valid() ) {
                        Point p = o.getCenterPoint();
                        g.drawString(o.getID()+"", p.x, p.y);
                        if (drawEntitiesCheckBox.isSelected())
                            if (fillDrawnEntitiesCheckBox.isSelected())
                                g.fill(o.getBoundingBox());
                            else
                                g.draw(o.getBoundingBox());
                    }
                }
            }
            switch(tabs.getSelectedIndex()){
                case GENERAL:
                    final Color fillColor = new Color(0,255,255,100);
                    final Color outlineColor = new Color(0,255,255);
                    switch(selectedDebugType){
                        case 0:
                            if( selectedObject != null && selectedObject.valid() ) {
                                g.setColor(outlineColor);
                                selectedObject.getTile().draw(g);
                                g.setColor(fillColor);
                                selectedObject.fillModel(g);
                            }
                            break;

                        case 1:
                            if( selectedNPC != null && selectedNPC.valid() ) {
                                g.setColor(outlineColor);
                                selectedNPC.getTile().draw(g);
                                g.setColor(fillColor);
                                selectedNPC.fillModel(g);
                            }
                            break;

                        case 2:
                            if( selectedNPC != null && selectedNPC.valid() ) {
                                g.setColor(outlineColor);
                                selectedNPC.getTile().draw(g);
                                g.setColor(fillColor);
                                selectedNPC.fillModel(g);
                            }
                            break;

                        case 3:
                            g.setColor(outlineColor);
                            if( selectedTile != null )
                                selectedTile.draw(g);
                            break;

                        case 4:
                            if( selectedGroundItem != null && selectedGroundItem.valid() ) {
                                g.setColor(outlineColor);
                                selectedGroundItem.getTile().draw(g);
                                g.setColor(fillColor);
                                selectedGroundItem.fillModel(g);
                            }
                            break;
                        case 5:
                            if( selectedItem != null && selectedItem.valid() ) {
                                g.setColor(fillColor);
                                g.draw(selectedItem.getBoundingBox());
                            }
                            break;
                    }
                    break;
                case PATHMAKER:
                    g.setColor(Color.blue);
                    for (int i = 1; i < tilePath.size(); i++) {
                        Point a = tilePath.get(i).getMapPoint();
                        Point b = tilePath.get(i-1).getMapPoint();
                        if( a.x != -1 && b.x != -1 )
                            g.drawLine(a.x,a.y,b.x,b.y);

                        a = tilePath.get(i).getCenterPoint();
                        b = tilePath.get(i-1).getCenterPoint();
                        if(script.misc.isPointInViewport(a) && script.misc.isPointInViewport(b))
                            g.drawLine(a.x,a.y,b.x,b.y);
                    }
                    for( int i = 0; i < tilePath.size(); i++ ){
                        Tile t = tilePath.get(i);
                        if( pathList.getSelectedIndex() == i )
                            g.setColor(new Color(255,0,0,100));
                        else
                            g.setColor(new Color(0,255,255,100));
                        if( t.isOnScreen() )
                            t.fill(g);
                        if( pathList.getSelectedIndex() == i )
                            g.setColor(new Color(255,0,0));
                        else
                            g.setColor(new Color(0,255,255));
                        if( t.isOnMinimap() )
                            t.fillMinimap(g);
                    }

                    if( obstacleList.getSelectedIndex() != -1 ){
                        Obstacle o = obstacles.get(obstacleList.getSelectedIndex());
                        if( o.type == Obstacle.ObstacleType.OBJECT ){
                            if( o.id != -1 && o.obstacleTile.isValid() ) {
                                RSObject obj = script.objects.getAt(o.obstacleTile, o.id);
                                g.setColor(Color.yellow);
                                if( obj != null )
                                    obj.drawModel(g);
                                o.obstacleTile.draw(g);
                                if( o.area.contains(script.localPlayer().getTile()))
                                    g.setColor(new Color(0,255,0,100));
                                else
                                    g.setColor(new Color(255,255,0,100));
                                o.area.fill(g);
                                g.setColor(Color.blue);
                                new Tile(script,o.area.x,o.area.y,o.area.floor).draw(g);
                                g.setColor(Color.red);
                                new Tile(script,o.area.x+o.area.width,o.area.y+o.area.height,o.area.floor).draw(g);
                            }
                        }
                    }
                    break;

                case AREAMAKER:
                    if( area.contains(script.localPlayer().getTile()))
                        g.setColor(new Color(0,255,0,100));
                    else
                        g.setColor(new Color(255,255,0,100));
                    area.fill(g);
                    g.setColor(Color.blue);
                    new Tile(script,area.x,area.y,area.floor).draw(g);
                    g.setColor(Color.red);
                    new Tile(script,area.x+area.width,area.y+area.height,area.floor).draw(g);
                    break;

                case WIDGETS:
                    g.setColor(Color.yellow);
                    if( selectedComponent != null)
                        g.drawRect(selectedComponent.getX(),selectedComponent.getY(),selectedComponent.getWidth(),selectedComponent.getHeight());
                    break;

                default:
                    break;
            }
        };

        thread = new Thread(){
            public void run(){
                try {
                    while(root.isVisible() && active && !isInterrupted()){
                        if(!script.bot.getPainters().contains(p))
                            script.bot.addPainter(p);
                        if(!script.bot.getConfigListeners().contains(configListener))
                            script.bot.addConfigListener(configListener);
                        if(!script.bot.getMouseListeners().contains(mouseListener))
                            script.bot.addMouseListener(mouseListener);

                        if(tabs.getSelectedIndex() == GENERAL )
                            updateSelectedObjectValues();

                        if( tabs.getSelectedIndex() == PATHMAKER && recordPath )
                            autoGeneratePath();

                        if( testingPath ) {
                            testPath.traverse();
                            if( testPath.getTiles().length == 0 || script.localPlayer().getTile().distanceTo(testPath.getTiles()[testPath.getTiles().length-1]) < 2 ) {
                                testingPath = false;
                                testPathButton.setText("Test Path");
                            }
                        }
                        sleep(200);
                    }
                } catch (InterruptedException ignored) {
                }
                script.bot.removeMouseListener(mouseListener);
                script.bot.removePainter(p);
                script.bot.removeConfigListener(configListener);
            }
        };
        thread.start();

        //GENERAL
        drawEntitiesCheckBox.addChangeListener(e -> fillDrawnEntitiesCheckBox.setEnabled(drawEntitiesCheckBox.isSelected()));
        selectObjectToDebugButton.addActionListener(e1 -> {
            toSelectDebugType = objectDebugSelectionType.getSelectedIndex();
            selectingDebugObject = true;
            selectObjectToDebugButton.setEnabled(false);
        });
        clearSelectedDebugObject.addActionListener(e -> selectedDebugType = -1);

        //PATHMAKER
        startRecordingButton.addActionListener(e -> {
            recordPath = !recordPath;
            startRecordingButton.setText(recordPath?"Pause Recording":"Start Recording");
        });
        pathList.setModel(new DefaultListModel<>());
        obstacleList.setModel(new DefaultListModel<>());
        addCurrentTileButton.addActionListener(e -> {
            DefaultListModel lm = (DefaultListModel)pathList.getModel();
            final int index = pathList.getSelectedIndex();
            final Tile t = script.localPlayer().getTile();
            if( index == -1 ){
                tilePath.add(t);
                lm.addElement(""+t);
            }else{
                tilePath.add(index,t);
                lm.add(index,""+t);
                pathList.setSelectedIndex(index+1);
            }
        });
        resetButton.addActionListener(e -> resetPath());
        deleteSelectedButtonPath.addActionListener(e -> {
            DefaultListModel lm = (DefaultListModel)pathList.getModel();
            final int index = pathList.getSelectedIndex();
            tilePath.remove(index);
            lm.remove(index);
        });
        invertPathButton.addActionListener(e -> invertPath() );
        testPathButton.addActionListener(e -> {
            if( !testingPath ) {
                testingPath = true;
                recordPath = false;
                startRecordingButton.setText("Start Recording");
                testPath = new Path(script, tilePath.toArray(new Tile[tilePath.size()]), obstacles.toArray(new Obstacle[obstacles.size()]));
                testPathButton.setText("Stop testing");
            }else{
                testingPath = false;
                testPathButton.setText("Test Path");
            }
        });

        addNewObstacleButton.addActionListener(e -> {
            obstacles.add(new Obstacle(script));
            DefaultListModel lm = (DefaultListModel) obstacleList.getModel();
            lm.addElement("Name: NULL ID: 0");
            obstacleList.setSelectedIndex(obstacles.size()-1);
        });
        selectObstacleButton.addActionListener(e -> {
            selectingObstacle = !selectingObstacle;
            selectObstacleButton.setEnabled(!selectingObstacle);
        });
        selectFirstTileAreaButton.addActionListener(e2 -> {
            selectFirstTileAreaPath = true;
            selectFirstTileAreaButton.setEnabled(false);
        });
        selectSecondTileAreaButton.addActionListener(e2 -> {
            selectSecondTileAreaPath = true;
            selectSecondTileAreaButton.setEnabled(false);
        });
        action.addActionListener(e -> {
            int index = obstacleList.getSelectedIndex();
            Obstacle o = obstacles.get(index);
            o.action = action.getText();
        });
        action.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                int index = obstacleList.getSelectedIndex();
                Obstacle o = obstacles.get(index);
                o.action = action.getText();
            }
        });
        obstacleTypeComboBox.addActionListener(e ->{
            int index = obstacleList.getSelectedIndex();
            Obstacle o = obstacles.get(index);
            o.type = obstacleTypeComboBox.getSelectedIndex() == 0 ? Obstacle.ObstacleType.OBJECT : Obstacle.ObstacleType.NPC;
        });
        deleteSelectedButtonObtacle.addActionListener(e -> {
            DefaultListModel lm = (DefaultListModel)obstacleList.getModel();
            final int index = obstacleList.getSelectedIndex();
            obstacles.remove(index);
            lm.remove(index);
        });

        copyToClipboardButtonPath.addActionListener(e -> pathToClipBoard());

        //AREA
        selectStartPointButton.addActionListener(e -> {
            selectFirstTileArea = true;
            selectStartPointButton.setEnabled(false);
        });
        selectEndPointButton.addActionListener(e -> {
            selectSecondTileArea = true;
            selectEndPointButton.setEnabled(false);
        });
        copyToClipboardButtonArea.addActionListener(e -> areaToClipBoard());

        //VARPBITS
        clearButton.addActionListener(e -> varpbitChangeList.setModel(new DefaultListModel<>()));
        pauseButton.addActionListener(e -> {
            varpbitsPaused = !varpbitsPaused;
            pauseButton.setText(varpbitsPaused?"Resume":"Pause");});
        gotoTextField.addActionListener(e -> {
            final int index = Integer.parseInt(gotoTextField.getText());
            varpbitsList.setSelectedIndex(index);
            varpbitsList.scrollRectToVisible(varpbitsList.getCellBounds(index,index));
        });
        ((PlainDocument)gotoTextField.getDocument()).setDocumentFilter(new NumberFilter());
        varpbitsList.addListSelectionListener(e -> {
            if( e.getValueIsAdjusting() ) {
                varpbitChangeList.clearSelection();
            }
            setSelectedVarpbit(varpbitsList.getSelectedIndex());
        });
        varpbitChangeList.addListSelectionListener(e -> {
            if( e.getValueIsAdjusting() ) {
                varpbitsList.clearSelection();
            }
            if( varpbitChangeList.getSelectedIndex() != -1 ) {
                String str = (String) varpbitChangeList.getModel().getElementAt(varpbitChangeList.getSelectedIndex());
                setSelectedVarpbit(Integer.parseInt(str.substring(0, str.indexOf(":"))));
            }
        });

        //WIDGETS
        widgetRootNode = new DefaultMutableTreeNode("root");
        updateWidgets();
        widgetTree.addTreeSelectionListener(e -> {
            TreePath path = e.getNewLeadSelectionPath();
            if( path == null ){
                selectedWidget = null;
                selectedComponent = null;
                updateWidgetValues();
                return;
            }
            if(path.getPathCount() <= 2 ) {
                if( path.getPathCount() == 2 ) {
                    int widget = Integer.parseInt(path.getPath()[1].toString());
                    selectedComponent = null;
                    selectedWidget = script.widgets.get(widget);
                    updateWidgetValues();
                }
                return;
            }
            int widget = Integer.parseInt(path.getPath()[1].toString());
            int component = Integer.parseInt((path.getPath()[2]).toString());
            if( script.widgets.active(widget)) {
                selectedComponent = script.widgets.get(widget, component);
                if (path.getPathCount() == 4)
                    selectedComponent = selectedComponent.get(Integer.parseInt(path.getPath()[3].toString()));
            }
            selectedWidget = null;
            updateWidgetValues();
        });
        updateButton.addActionListener(e -> updateWidgets());
    }

    private void updateSelectedObjectValues(){
        String text = "";

        switch(selectedDebugType){
            case 0:
                if( selectedObject!= null && selectedObject.valid() ) {
                    text += "Name: " + selectedObject.getName() + "\n";
                    text += "ID: " + selectedObject.getID() + "\n";
                    text += "Tile: " + selectedObject.getTile() + "\n";
                    text += "Orientation: " + selectedObject.getOrientation() + "\n";
                    text += "Clipping1: " + selectedObject.isBlockingDecoration() + "\n";
                    text += "Clipping2: " + selectedObject.getClipping2() + "\n";
                    text += "BlocksProjectiles: " + selectedObject.blocksProjectiles() + "\n";
                    text += "WalkToData: " + selectedObject.getWalkToData() + "\n";
                    text += "Config: " + selectedObject.getConfig() + "\n";
                    text += "Type: " + selectedObject.getType() + "\n";
                    text += "Actions: " + Arrays.toString(selectedObject.getActions()) + "\n";
                }
                break;

            case 1:
                if( selectedNPC!= null && selectedNPC.valid() ) {
                    text += "Name: " + selectedNPC.getName() + "\n";
                    text += "ID: " + selectedNPC.getID() + "\n";
                    text += "Tile: " + selectedNPC.getTile() + "\n";
                    text += "CombatLevel: " + selectedNPC.getCombatLevel() + "\n";
                    text += "Health: " + selectedNPC.getHealth() + "\n";
                    text += "MaxHealth: " + selectedNPC.getMaxHealth() + "\n";
                    text += "Interacting: " + selectedNPC.getInteracting() + "\n";
                    text += "CombatTime: " + selectedNPC.getCombatTime() + "\n";
                    text += "Animation: " + selectedNPC.getAnimation() + "\n";
                    text += "Moving: " + selectedNPC.isMoving() + "\n";
                    text += "InCombat: " + selectedNPC.inCombat() + "\n";
                    text += "Actions: " + Arrays.toString(selectedNPC.getActions()) + "\n";
                    text += "Visible: " + selectedNPC.isVisible() + "\n";
                    text += "HeadMessage: " + selectedNPC.getHeadMessage() + "\n";
                    text += "Actions: " + Arrays.toString(selectedNPC.getActions()) + "\n";
                }
                break;

            case 2:
                if( selectedPlayer!= null && selectedPlayer.valid() ) {
                    text += "Name: " + selectedPlayer.getName() + "\n";
                    text += "Tile: " + selectedPlayer.getTile() + "\n";
                    text += "CombatLevel: " + selectedPlayer.getCombatLevel() + "\n";
                    text += "Health: " + selectedPlayer.getHealth() + "\n";
                    text += "MaxHealth: " + selectedPlayer.getMaxHealth() + "\n";
                    text += "Interacting: " + selectedPlayer.getInteracting() + "\n";
                    text += "CombatTime: " + selectedPlayer.getCombatTime() + "\n";
                    text += "Animation: " + selectedPlayer.getAnimation() + "\n";
                    text += "Moving: " + selectedPlayer.isMoving() + "\n";
                    text += "InCombat: " + selectedPlayer.inCombat() + "\n";
                    text += "HeadMessage: " + selectedPlayer.getHeadMessage() + "\n";
                    text += "Appearance: " + Arrays.toString(selectedPlayer.getAppearance()) + "\n";
                }
                break;

            case 3:
                if( selectedTile != null) {
                    text += "" + selectedTile.toString() + "\n";
                    text += "Grid x: " + selectedTile.getGridX() + "\n";
                    text += "Grid y: " + selectedTile.getGridY() + "\n";
                    text += "Local x: " + selectedTile.getLocalX() + "\n";
                    text += "Local y: " + selectedTile.getLocalY() + "\n";
                    if (selectedTile.isLoaded()) {
                        text += "Objects On Tile: " + script.objects.getAt(selectedTile).length + "\n";

                        final int flags = selectedTile.getClippingFlags();
                        text += "\n";
                        text += "ClippingFlags: " + flags + "\n";

                        String s = String.format("%32s",Integer.toBinaryString(flags)).replace(' ','0');
                        for( int i = 0, j = 0; i < s.length(); i++ , j++){
                            if( j % 4 == 0 && i != 0) {
                                s = s.substring(0, i) + " " + s.substring(i);
                                i++;
                            }
                        }

                        text += s + "\n";

                        text += "BlockingObject: " + ((flags & Tile.BLOCK_OBJECT) != 0) + "\n";
                        text += "BLOCKED: " + ((flags & Tile.BLOCKED) != 0) + "\n";
                        text += "WALL_NORTH: " + ((flags & Tile.WALL_NORTH) != 0) + "\n";
                        text += "WALL_NORTHEAST: " + ((flags & Tile.WALL_NORTHEAST) != 0) + "\n";
                        text += "WALL_EAST: " + ((flags & Tile.WALL_EAST) != 0) + "\n";
                        text += "WALL_SOUTHEAST: " + ((flags & Tile.WALL_SOUTHEAST) != 0) + "\n";
                        text += "WALL_SOUTH: " + ((flags & Tile.WALL_SOUTH) != 0) + "\n";
                        text += "WALL_SOUTHWEST: " + ((flags & Tile.WALL_SOUTHWEST) != 0) + "\n";
                        text += "WALL_WEST: " + ((flags & Tile.WALL_WEST) != 0) + "\n";
                        text += "WALL_NORTHWEST: " + ((flags & Tile.WALL_NORTHWEST) != 0);
                    }
                }
                break;

            case 4:
                if( selectedGroundItem != null && selectedGroundItem.valid() ){
                    text += "Name: " + selectedGroundItem.getName() + "\n";
                    text += "ID: " + selectedGroundItem.getID() + "\n";
                    text += "NotedID: " + selectedGroundItem.getNotedID() + "\n";
                    text += "UnnotedID: " + selectedGroundItem.getUnnotedID() + "\n";
                    text += "Tile: " + selectedGroundItem.getTile() + "\n";
                    text += "Amount: " + selectedGroundItem.getAmount() + "\n";
                    text += "Actions: " + Arrays.toString(selectedGroundItem.getActions()) + "\n";
                }
                break;

            case 5:
                if( selectedItem != null && selectedItem.valid() ){
                    text += "Name: " + selectedItem.getName() + "\n";
                    text += "ID: " + selectedItem.getID() + "\n";
                    text += "NotedID: " + selectedItem.getNotedID() + "\n";
                    text += "UnnotedID: " + selectedItem.getUnnotedID() + "\n";
                    text += "Amount: " + selectedItem.getStackSize() + "\n";
                    text += "Actions: " + Arrays.toString(selectedItem.getActions()) + "\n";
                }
                break;
        }

        selectedObjectValues.setText(text);
    }

    private void resetPath(){
        tilePath.clear();
        pathList.setModel(new DefaultListModel<>());
    }

    private void invertPath(){
        recordPath = false;
        startRecordingButton.setText(recordPath?"Pause Recording":"Start Recording");
        List<Tile> newPath = new ArrayList<>();
        for( int i = tilePath.size()-1; i >= 0; i-- )
            newPath.add(tilePath.get(i));
        tilePath = newPath;
        DefaultListModel lm = new DefaultListModel();
        for( Tile t : newPath )
            lm.addElement(t.toString());
        pathList.setModel(lm);
    }

    private void pathToClipBoard(){
        String out = "private Path path = new Path( this, new Tile[] {";
        for( int i = 0; i < tilePath.size(); i++ ) {
            if (i == 0)
                out += "new Tile( this," + tilePath.get(i).x + "," + tilePath.get(i).y +  "," + tilePath.get(i).z + ")";
            else
                out += "," + (i % 4 == 3 && i != tilePath.size() - 1 ? "\n\t" : " ") + "new Tile( this," + tilePath.get(i).x + "," + tilePath.get(i).y + "," + tilePath.get(i).z + ")";
        }

        out += "},\n\tnew Obstacle[]{";
        for( int i = 0; i < obstacles.size(); i++ ) {
            final Obstacle o = obstacles.get(i);
            if (i != 0)
                out += ",\n\t";
            else
                out += " ";
            out += "new Obstacle( this," + o.id + ", " +
                    (o.type == Obstacle.ObstacleType.OBJECT ? "new Tile( this," + o.obstacleTile.x + ", " + o.obstacleTile.y + ", "+o.obstacleTile.z : "") + "), " +
                    "new TileArea( this," + o.area.x + ", " + o.area.y + ", " + o.area.width + ", " + o.area.height + (o.area.floor != 0 ? ", " + o.area.floor : "" ) + " ), " +
                    "\"" + o.action + "\" " + ")";
        }
        out += "});";

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection ss = new StringSelection(out);
        clipboard.setContents(ss, ss);
    }

    private void autoGeneratePath(){
        if( tilePath.size() == 0 ){
            DefaultListModel lm = (DefaultListModel)pathList.getModel();
            final Tile t = script.localPlayer().getTile();
            tilePath.add(t);
            lm.addElement(""+t);
        }else {
            Tile lastTile = tilePath.get(tilePath.size() - 1);
            if (lastTile.distanceTo(script.localPlayer().getTile()) > 5) {
                DefaultListModel lm = (DefaultListModel) pathList.getModel();
                final Tile t = script.localPlayer().getTile();
                tilePath.add(t);
                lm.addElement("" + t);
            }
        }
    }

    private void updateObstacle( int index ){
        DefaultListModel lm = (DefaultListModel)obstacleList.getModel();
        String name;

        if( obstacles.get(index).type == Obstacle.ObstacleType.OBJECT ){
            RSObject o = script.objects.getAt(obstacles.get(index).obstacleTile, obstacles.get(index).id);
            name = o != null ? o.getName() : "NULL";
        }else{
            Npc npc = script.npcs.getNearest(obstacles.get(index).id);
            name = npc != null ? npc.getName() : "NULL";
        }
        lm.set(index,"Name: " + name + " ID: "+obstacles.get(index).id);
        action.setText(obstacles.get(index).action);
    }

    private void areaToClipBoard(){
        String out = "private TileArea area = new TileArea( this,"+area.x + ", " + area.y + ", " + area.width+ ", "+ area.height + (area.floor == 0 ?"" : ", " + area.floor ) + " );";

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection ss = new StringSelection(out);
        clipboard.setContents(ss, ss);
    }

    private void setSelectedVarpbit(int index){
        String text = "Setting: " + index;
        text += "\nValue: " + varpbits[index];
        String s = String.format("%32s",Integer.toBinaryString(varpbits[index])).replace(' ','0');
        for( int i = 0, j = 0; i < s.length(); i++ , j++){
            if( j % 4 == 0) {
                s = s.substring(0, i) + " " + s.substring(i);
                i++;
            }
        }
        text += "\nHex: 0x" + Integer.toHexString(varpbits[index]).toUpperCase();
        text += "\nBinary:" + s;
        varpbitValues.setText(text);
    }

    private void updateWidgets(){
        widgetRootNode.removeAllChildren();
        for (int i = 0; i < script.client.getValidWidgets().length; i++) {
            if (script.client.getValidWidgets()[i]) {
                DefaultMutableTreeNode widget = new DefaultMutableTreeNode("" + i);
                boolean foundString = widgetsFilter.getText().equals("");
                for (int j = 0; j < script.widgets.get(i).childCount(); j++) {
                    if (script.widgets.get(i, j).getText().contains(widgetsFilter.getText()))
                        foundString = true;
                    DefaultMutableTreeNode comp = new DefaultMutableTreeNode("" + j);
                    if (script.widgets.get(i, j).childCount() > 0)
                        for (int k = 0; k < script.widgets.get(i, j).childCount(); k++) {
                            Component c = script.widgets.get(i, j, k);
                            if (c.valid() && c.getText().contains(widgetsFilter.getText()))
                                foundString = true;
                            comp.add(new DefaultMutableTreeNode("" + k));
                        }
                    widget.add(comp);
                }
                if (foundString)
                    widgetRootNode.add(widget);
            }
        }
        widgetTree.setModel(new DefaultTreeModel(widgetRootNode));
        widgetTree.clearSelection();
    }

    private void updateWidgetValues(){
        String text = "";
        if( selectedComponent != null ) {
            text += "Text: " + selectedComponent.getText() + "\n";
            text += "ToolTip: " + selectedComponent.getToolTip() + "\n";
            text += "x pos: " + selectedComponent.getX() + "\n";
            text += "y pos: " + selectedComponent.getY() + "\n";
            text += "width: " + selectedComponent.getWidth() + "\n";
            text += "height: " + selectedComponent.getHeight() + "\n";
            text += "ScrollMax: " + selectedComponent.getScrollMax() + "\n";
            text += "ScrollY: " + selectedComponent.getScrollY() + "\n";
            text += "ItemID: " + selectedComponent.getItemID() + "\n";
            text += "ItemAmount: " + selectedComponent.getItemStackSize() + "\n";
            text += "ItemIDs: " + Arrays.toString(selectedComponent.getItemIDs()) + "\n";
            text += "StackSizes: " + Arrays.toString(selectedComponent.getItemStackSizes()) + "\n";
            text += "Visable: " + selectedComponent.visable() + "\n";
            text += "Spell name: " + selectedComponent.getSpellName() + "\n";
            text += "SpriteIndex1: " + selectedComponent.getSpriteIndex1() + "\n";
            text += "SpriteIndex2: " + selectedComponent.getSpriteIndex2() + "\n";
            text += "Type: " + selectedComponent.getType() + "\n";
            text += "ContentType: " + selectedComponent.getContentType() + "\n";
            text += "ActionType: " + selectedComponent.getActionType() + "\n";
            text += "Actions: " + Arrays.toString(selectedComponent.getActions()) + "\n";
            text += "Options: " + Arrays.toString(selectedComponent.getOptions()) + "\n";
            text += "Selected Action Name: " + selectedComponent.getSelectedActionName() + "\n";
            text += "EnabledMediaID: " + selectedComponent.getEnabledMediaId() + "\n";
            text += "DisabledMediaID: " + selectedComponent.getDisabledMediaId() + "\n";
            text += "Alpha: " + selectedComponent.getAlpha() + "\n";
        }else if( selectedWidget != null ){
            for(Widget.WidgetID id : Widget.WidgetID.values() ){
                if( id.id == selectedWidget.getID() ){
                    text += id.name();
                    break;
                }
            }
        }
        selectedWidgetInfo.setText(text);
    }

    @Override
    public void exit(){
        super.exit();
        active = false;
        script.bot.removeConfigListener(configListener);
    }

    public void stopped(){
        tabs.setEnabledAt(3,false);
    }
}
