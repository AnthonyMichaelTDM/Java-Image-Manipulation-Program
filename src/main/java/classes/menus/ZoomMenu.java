package classes.menus;

import classes.PictureExplorer;

import java.awt.event.*;
import javax.swing.*;

/**
 * handles the "Zoom" menu in the top menu bar
 *
 * @author Anthony Michael
 */
public class ZoomMenu extends JMenu  implements ActionListener
{
    private PictureExplorer pictureExplorer;
    /* zoom to fit level */
    //private JMenuItem zoomToFit;// removed, too many issues
    /* 25% zoom level */
    private JMenuItem twentyFive;
    /* 50% zoom level */
    private JMenuItem fifty;
    /* 75% zoom level */
    private JMenuItem seventyFive;
    /* 100% zoom level */
    private JMenuItem hundred;
    /* 150% zoom level */
    private JMenuItem hundredFifty;
    /* 200% zoom level */
    private JMenuItem twoHundred;
    /* 500% zoom level */
    private JMenuItem fiveHundred;

    /**
     * Constructor for objects of class ZoomMenu
     */
    public ZoomMenu(PictureExplorer pictureExplorer)
    {
        super("Zoom");
        this.pictureExplorer = pictureExplorer;

        //zoomToFit = new JMenuItem("Auto Zoom");// removeed, too many issues
        twentyFive = new JMenuItem("25%");
        fifty = new JMenuItem("50%");
        seventyFive = new JMenuItem("75%");
        hundred = new JMenuItem("100%");
        hundred.setEnabled(false);
        hundredFifty = new JMenuItem("150%");
        twoHundred = new JMenuItem("200%");
        fiveHundred = new JMenuItem("500%");
        // add the action listeners
        //zoomToFit.addActionListener(this);// removeed, too many issues
        twentyFive.addActionListener(this);
        fifty.addActionListener(this);
        seventyFive.addActionListener(this);
        hundred.addActionListener(this);
        hundredFifty.addActionListener(this);
        twoHundred.addActionListener(this);
        fiveHundred.addActionListener(this);
        // add the menu items to the menu
        //zoomMenu.add(zoomToFit); // removeed, too many issues
        this.add(twentyFive);
        this.add(fifty);
        this.add(seventyFive);
        this.add(hundred);
        this.add(hundredFifty);
        this.add(twoHundred);
        this.add(fiveHundred);
    }

    /**
     * Method to enable all menu commands
     */
    private void enableZoomItems()
    {
        //zoomToFit.setEnabled(true);// removeed, too many issues
        twentyFive.setEnabled(true);
        fifty.setEnabled(true);
        seventyFive.setEnabled(true);
        hundred.setEnabled(true);
        hundredFifty.setEnabled(true);
        twoHundred.setEnabled(true);
        fiveHundred.setEnabled(true);
    }

    /**
     * Controls the Filters menu item actions
     * 
     * @param a the ActionEvent
     */
    public void actionPerformed(ActionEvent a)
    {
        //zoom the selected amount
        if(a.getActionCommand().equals("25%"))
        {
            pictureExplorer.zoom(.25);
            enableZoomItems();
            twentyFive.setEnabled(false);
        }
        if(a.getActionCommand().equals("50%"))
        {
            pictureExplorer.zoom(.50);
            enableZoomItems();
            fifty.setEnabled(false);
        }
        if(a.getActionCommand().equals("75%"))
        {
            pictureExplorer.zoom(.75);
            enableZoomItems();
            seventyFive.setEnabled(false);
        }
        if(a.getActionCommand().equals("100%"))
        {
            pictureExplorer.zoom(1.0);
            enableZoomItems();
            hundred.setEnabled(false);
        }
        if(a.getActionCommand().equals("150%"))
        {
            pictureExplorer.zoom(1.5);
            enableZoomItems();
            hundredFifty.setEnabled(false);
        }
        if(a.getActionCommand().equals("200%"))
        {
            pictureExplorer.zoom(2.0);
            enableZoomItems();
            twoHundred.setEnabled(false);
        }
        if(a.getActionCommand().equals("500%"))
        {
            pictureExplorer.zoom(5.0);
            enableZoomItems();
            fiveHundred.setEnabled(false);
        }
    }
}
