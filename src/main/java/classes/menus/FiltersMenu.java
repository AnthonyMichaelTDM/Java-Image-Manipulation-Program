package classes.menus;

import classes.PictureExplorer;

import java.awt.event.*;
import javax.swing.*;

/**
 * Write a description of class FiltersMenu here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class FiltersMenu extends JMenu  implements ActionListener
{
    private PictureExplorer pictureExplorer;
    /** filters menu */
    //filters menu items
    private JMenuItem edgeDetection;
    private JMenuItem brighten;
    private JMenuItem darken;
    private JMenuItem simplifyColors;

    /**
     * Constructor for objects of class FiltersMenu
     */
    public FiltersMenu(PictureExplorer pictureExplorer)
    {
        super("Filters");
        
        this.pictureExplorer = pictureExplorer;
        
        edgeDetection = new JMenuItem("Edge Detection");
        brighten = new JMenuItem("Brighten Picture");
        darken = new JMenuItem("Darken Picture");
        simplifyColors = new JMenuItem("simplify picture colors");
        // add the action listeners
        edgeDetection.addActionListener(this);
        brighten.addActionListener(this);
        darken.addActionListener(this);
        simplifyColors.addActionListener(this);
        // add the menu items to the menu
        this.add(edgeDetection);
        this.add(brighten);
        this.add(darken);
        this.add(simplifyColors);
    }

    
    /**
     * Controls the Filters menu item actions
     * 
     * @param a the ActionEvent
     */
    public void actionPerformed(ActionEvent a)
    {
        int[] currentTool = new int[2];
        //update the current tool to this tool group
        currentTool[0] = 2;
        currentTool[1] = 0;

        if (a.getSource() == edgeDetection)
        {
            currentTool[1] = 1;
        }
        if (a.getSource() == brighten)
        {
            currentTool[1] = 2;
        }
        if (a.getSource() == darken)
        {
            currentTool[1] = 3;
        }
        if (a.getSource() == simplifyColors)
        {
            currentTool[1] = 4;
        }

        pictureExplorer.updateUtilityPanel(currentTool);
    }
}
