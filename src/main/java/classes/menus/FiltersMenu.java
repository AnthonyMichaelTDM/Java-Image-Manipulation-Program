/*
  * Copyright (c) 2021 Anthony Rubick
  *
  * This program is free software: you can redistribute it and/or modify
  * it under the terms of the GNU General Public License as published by
  * the Free Software Foundation, either version 3 of the License, or
  * (at your option) any later version.
  *
  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU General Public License for more details.
  *
  * You should have received a copy of the GNU General Public License
  * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package classes.menus;

import classes.PictureExplorer;

import java.awt.event.*;
import javax.swing.*;

/**
 * handles the "Filters" menu in the top menu bar
 *
 * @author Anthony Michael
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
    private JMenuItem kMeansSimplify;

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
        simplifyColors = new JMenuItem("Simplify Picture Colors");
        kMeansSimplify = new JMenuItem("K-Means Color Simplification");
        
        // add the action listeners
        edgeDetection.addActionListener(this);
        brighten.addActionListener(this);
        darken.addActionListener(this);
        simplifyColors.addActionListener(this);
        kMeansSimplify.addActionListener(this);
        // add the menu items to the menu
        this.add(edgeDetection);
        this.add(brighten);
        this.add(darken);
        this.add(simplifyColors);
        this.add(kMeansSimplify);
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
        if (a.getSource() == kMeansSimplify)
        {
            currentTool[1] = 5;
        }

        pictureExplorer.updateUtilityPanel(currentTool);
    }
}
