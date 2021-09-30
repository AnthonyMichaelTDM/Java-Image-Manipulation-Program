package classes;


import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.*;

/**
 * this class handles the of the undo and redo buttons, 
 * every time the user applies a tool, send a call to this class, 
 * letting it save the changes to its image cache
 * 
 * @author Anthony Rubick
 */
public class UndoRedoPanel extends JPanel
{
    //DATA
    private PictureExplorer pictureExplorer;
    /** undo label */
    private JLabel undoLabel;
    /** undo button */
    private JButton undoButton;
    /** redo label*/
    private JLabel redoLabel;
    /** redo button */
    private JButton redoButton;
    // list of pictures, used as the cache
    private LinkedList<SimplePicture> pictureCache; 
    private int currIndex;
    private int cacheSize;

    /**
     * Constructor for objects of class UndoRedoButtons
     */
    /**
     * Constructor for objects of class UndoRedoHandler
     * @param size the size of the cache
     * @param pictureExplorer reference to the picture explorer this class is being used in
     */
    public UndoRedoPanel(int size, PictureExplorer pictureExplorer)
    {
        //set up gui frontend (buttons)
        super();
        this.pictureExplorer = pictureExplorer;
        initGui();
        
        //set up undo/redo backend
        currIndex = 0;
        if (size < 2) { 
            cacheSize = 2;
        }
        else 
        {
            cacheSize = size;
        }
        initCache();
    }
    
    
    //METHODS
    /**
     * build the panel that holds the undo and redo buttons
     */
    public void initGui() 
    {
        //DATA
        Box hBox = Box.createHorizontalBox();
        this.setLayout(new FlowLayout());
        
        undoLabel = new JLabel("undo");
        redoLabel = new JLabel("redo");
        
        //call a method to set up the undo redo buttons themselves
        setUpUndoAndRedoButtons();
        
        //add the items to the horizontal box, and the box to the panel
        hBox.add(Box.createHorizontalGlue());
        hBox.add(undoButton);
        hBox.add(undoLabel);
        hBox.add(Box.createHorizontalStrut(10));
        hBox.add(redoLabel);
        hBox.add(redoButton);
        this.add(hBox);
    }
    /**
     * create the "cache" which will store the most recent 10 edits to the image
     */
    public void initCache() 
    {
        pictureCache = new LinkedList<SimplePicture>();
    
        //create cacheSize blank pictures to hold the most recent edits
        //pictureCache.add(new SimplePicture(PictureExplorer.picture.getHeight(), PictureExplorer.picture.getWidth()));
        updateCache(PictureExplorer.picture);
    }
    /**
     * updates cache, adding the passed image, and removing images from the cache as neccessary
     * @param newPicture picture to add to cache
     */
    public void updateCache(SimplePicture newPicture) {
        while (currIndex > 0) {
            pictureCache.removeFirst();
            pictureCache.add(new SimplePicture(PictureExplorer.picture.getHeight(), PictureExplorer.picture.getWidth()));
            currIndex --;
        }
        pictureCache.addFirst(new SimplePicture(newPicture));
        if (pictureCache.size() >= cacheSize) {
            pictureCache.removeLast();
        }
        currIndex = 0;
    }
    /**
     * Method to set up the undo and redo buttons
     */
    private void setUpUndoAndRedoButtons()
    {
        // create the image icons for the buttons 
        //issue ... this works when compiled as a jar, but if you try running this in an IDE you'll likely get a null pointer error
        Icon undoIcon = new ImageIcon(this.getClass().getResource("/leftArrow.gif"), 
                "previous index");
        Icon redoIcon = new ImageIcon(this.getClass().getResource("/rightArrow.gif"), 
                "next index");
        // create the arrow buttons
        undoButton = new JButton(undoIcon);
        redoButton = new JButton(redoIcon);

        // set the tool tip text
        redoButton.setToolTipText("Click to redo the latest action");
        undoButton.setToolTipText("Click to undo the latest action");

        // set the sizes of the buttons
        int undoWidth = undoIcon.getIconWidth() + 2;
        int redoWidth = redoIcon.getIconWidth() + 2;
        int undoHeight = undoIcon.getIconHeight() + 2;
        int redoHeight = redoIcon.getIconHeight() + 2;
        Dimension undoDimension = new Dimension(undoWidth, undoHeight);
        Dimension redoDimension = new Dimension(redoWidth, redoHeight);
        undoButton.setPreferredSize(undoDimension);
        redoButton.setPreferredSize(redoDimension);
        
        // handle undo button press
        undoButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    undo();
                }
            });

        // handle next column button press
        redoButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    redo();
                }
            });
    }
    
    
    /**
     * returns the SimplePicture after currIndex
     */
    public void undo() {
        //increment currIndex unless its >= pictureCache.size()
        if (currIndex < pictureCache.size()) 
        {
            currIndex ++;
            //update explored image from cache
            updateFromCache();
        }
    }
    /**
     * returns the SimplePicture before currIndex
     */
    public void redo() {
        //decrement currIndex unless its <= 0
        if (currIndex > 0) 
        { 
            currIndex --;
            //update explored image from cache
            updateFromCache();
        }
    }
    /**
     * updates the explorers image with the image from the cache at the current index
     */
    private void updateFromCache() {
        //ensure currIndex is within bounds
        if (currIndex < 0) currIndex =0;
        if (currIndex >= pictureCache.size()) currIndex = pictureCache.size() - 1;
        
        PictureExplorer.picture.copyPicture(pictureCache.get(currIndex)); //copy picture pixels, but keep other data
        pictureExplorer.updateImage();
    }
}
