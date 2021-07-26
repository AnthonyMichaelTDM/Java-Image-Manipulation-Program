package classes.menus;

import classes.PictureExplorer;

import java.awt.event.*;
import javax.swing.*;

/**
 * Write a description of class ColorModificationMenu here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ColorModificationMenu extends JMenu  implements ActionListener
{
    private PictureExplorer pictureExplorer;
    /** color manipulation menu */
    //  colorMenu items
    private JMenuItem removeColor;
    private JMenuItem trimColor;
    private JMenuItem negate;
    private JMenuItem grayscale;
    private JMenuItem replaceColorWithColor;

    /**
     * Constructor for objects of class ColorModificationMenu
     */
    public ColorModificationMenu(PictureExplorer pictureExplorer)
    {
        super("Color Modification");
        
        this.pictureExplorer = pictureExplorer;
        
        removeColor = new JMenuItem("Remove Color Channel");
        trimColor = new JMenuItem("Trim Color Channel");
        negate = new JMenuItem("Negate Color Channel");
        grayscale = new JMenuItem("grayscale");
        replaceColorWithColor = new JMenuItem("Replace Color With Color");
        // add the action listeners
        removeColor.addActionListener(this);
        trimColor.addActionListener(this);
        negate.addActionListener(this);
        grayscale.addActionListener(this);
        replaceColorWithColor.addActionListener(this);
        // add the menu items to the menu
        this.add(removeColor);
        this.add(trimColor);
        this.add(negate);
        this.add(grayscale);
        this.add(replaceColorWithColor);
    }

    /**
     * Controls the Color Modification menu item actions
     * 
     * @param a the ActionEvent
     */
    public void actionPerformed(ActionEvent a)
    {
        int[] currentTool = new int[2];
        //update the current tool to this tool group
        currentTool[0] = 1;
        currentTool[1]=0;

        if (a.getSource() == removeColor) 
        {
            currentTool[1]=1;
        }
        if (a.getSource() == trimColor) 
        {
            currentTool[1]=2;
        }
        if (a.getSource() == negate) 
        {
            currentTool[1]=3;
        }
        if (a.getSource() == grayscale) 
        {
            currentTool[1]=4;
        }
        if (a.getSource() == replaceColorWithColor)
        {
            currentTool[1]=5;
        }

        pictureExplorer.updateUtilityPanel(currentTool);
    }
}
