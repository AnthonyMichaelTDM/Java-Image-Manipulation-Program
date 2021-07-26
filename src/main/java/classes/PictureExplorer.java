package classes;

import classes.menus.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.text.NumberFormat;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

/**
 * Displays a picture and lets you explore the picture by displaying the row, column, red,
 * green, and blue values of the pixel at the cursor when you click a mouse button or
 * press and hold a mouse button while moving the cursor.  It also lets you zoom in or
 * out.  You can also type in a row and column value to see the color at that location.
 * 
 * Originally created for the Jython Environment for Students (JES). 
 * Modified to work with DrJava by Barbara Ericson
 * Also modified to show row and columns by Barbara Ericson
 * Modified to be a fully gui based Image Manipulation program by Anthony Rubick
 * 
 * @author Anthony Michael
 */
public class PictureExplorer implements MouseMotionListener, ActionListener, MouseListener
{
    /*what I want from this GUI
     * menu bar where the user can select the action to take, as well as some zooming things
     * main panel, which will hold the image
     * side panel (next to main panel), which will hold config options for the actions the user takes
     *      side panel will be handled in a subclass or something
     * bottom menu, where the user can load a image, save their image, undo, and redo actions (later)
     */

    //current picture file extension, and the path to the directory containing the image
    private String pictureExtension;
    private String picturePath; 

    // current indicies
    /** row index */
    private int rowIndex = 0;
    /** column index */
    private int colIndex = 0;

    //stores integer values corresponding to the tool currently selected, 
    //first index is the tool group, second is the actual tool
    //{0,0} represents no tool being selected
    private int[] currentTool = {0,0};

    // main GUI
    /** window to hold GUI */
    private JFrame pictureFrame;
    /** window that allows the user to scroll to see a large picture */
    private JScrollPane scrollPane;

    // GUI components
    //bottom panel
    /** undo label */
    private JLabel undoLabel;
    /** undo button */
    private JButton undoButton;
    /** redo label*/
    private JLabel redoLabel;
    /** redo button */
    private JButton redoButton;
    /** save button */
    private JButton saveButton;
    /** load button */
    private JButton loadButton;
    /** ask for confirmation checkbox */
    private JCheckBox askForConfirmationCheckBox;

    //side panel
    private ToolUtilityPanelHandler toolUtilityPanel;

    //menu bar components
    /**menu bar*/
    private JMenuBar menuBar;
    // things on the menuBar
    /** color manipulation menu */
    private ColorModificationMenu colorMenu;
    /** filters menu */
    private FiltersMenu filtersMenu;
    /** zoom menu */
    private JMenu zoomMenu;
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

    /** confirmation frame */
    static PictureConfirmation pictureConfirmation;

    /** The picture being explored */
    static DigitalPicture picture;

    /** The image icon used to display the picture */
    private ImageIcon scrollImageIcon;

    /** The image display */
    private ImageDisplay imageDisplay;

    /** the zoom factor (amount to zoom) */
    private double zoomFactor;

    /** the color currently selected */
    static Color selectedPixelColor;

    /** the number system to use, 0 means starting at 0, 1 means starting at 1 */
    private int numberBase=0;

    /**
     * Public constructor 
     * @param picture the picture to explore
     */
    public PictureExplorer(DigitalPicture picture)
    {
        // set the fields
        this.picture=picture;
        zoomFactor=1;

        // create the window and set things up
        createWindow();
    }

    /**
     * Changes the number system to start at one
     */
    public void changeToBaseOne()
    {
        numberBase=1;
    }

    /**
     * Set the title of the frame
     *@param title the title to use in the JFrame
     */
    public void setTitle(String title)
    {
        pictureFrame.setTitle(title);
    }

    /**
     * Method to create and initialize the picture frame
     */
    private void createAndInitPictureFrame()
    {
        pictureFrame = new JFrame(); // create the JFrame
        pictureFrame.setResizable(true);  // allow the user to resize it
        pictureFrame.getContentPane().setLayout(new BorderLayout()); // use border layout
        pictureFrame.setTitle(picture.getTitle());
        PictureExplorerFocusTraversalPolicy newPolicy = new PictureExplorerFocusTraversalPolicy();
        pictureFrame.setFocusTraversalPolicy(newPolicy);
        
        //make it so that the program closes when the window is closed 
        pictureFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        pictureFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    if (JOptionPane.showConfirmDialog(pictureFrame, 
                        "Are you sure you want to close this window?", "Close Window?", 
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                        System.exit(0);
                    }
                }
            });

    }

    /**
     * Method to create the menu bar, menus, and menu items
     */
    private void setUpMenuBar()
    {
        //create menu
        menuBar = new JMenuBar();

        /** zoom menu */
        zoomMenu = new JMenu("Zoom");
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
        zoomMenu.add(twentyFive);
        zoomMenu.add(fifty);
        zoomMenu.add(seventyFive);
        zoomMenu.add(hundred);
        zoomMenu.add(hundredFifty);
        zoomMenu.add(twoHundred);
        zoomMenu.add(fiveHundred);
        menuBar.add(zoomMenu);

        /** color modification menu */
        colorMenu = new ColorModificationMenu(this);
        menuBar.add(colorMenu);

        /** filters menu */
        filtersMenu = new FiltersMenu(this);
        menuBar.add(filtersMenu);
        

        // set the menu bar to this menu
        pictureFrame.setJMenuBar(menuBar);
    }

    /**
     * Create and initialize the scrolling image
     */
    private void createAndInitScrollingImage()
    {
        scrollPane = new JScrollPane();

        BufferedImage bimg = picture.getBufferedImage();
        imageDisplay = new ImageDisplay(bimg);
        imageDisplay.addMouseMotionListener(this);
        imageDisplay.addMouseListener(this);
        //imageDisplay.setToolTipText("Click a mouse button on a pixel to see the pixel information");
        scrollPane.setViewportView(imageDisplay);
        pictureFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Creates the JFrame and sets everything up
     */
    private void createWindow()
    {
        // create the picture frame and initialize it
        createAndInitPictureFrame();

        // set up the menu bar
        setUpMenuBar();

        //create the information panel
        createBottomPanel();

        //create the utility panel
        createSidePanel();

        //creates the scrollpane for the picture
        createAndInitScrollingImage();
        
        // show the picture in the frame at the size it needs to be
        pictureFrame.pack();
        pictureFrame.setVisible(true);
        
        //initialize the confirmation panel used by some of the tools
        pictureConfirmation = new PictureConfirmation();
    }

    /**
     * Method to set up the default Side Panel
     */
    private void createSidePanel() 
    {
        toolUtilityPanel = new ToolUtilityPanelHandler();
        pictureFrame.getContentPane().add(BorderLayout.WEST,toolUtilityPanel);
        repaint();
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
                    //in the future, undo the most recent action
                }
            });

        // handle next column button press
        redoButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    //in the future, redo the most recently undo'd action
                }
            });
    }

    /**
     * Create the image option panel, will handle things like redo and undo
     * @param labelFont the font for the labels
     * @return the image option panel
     */
    private JPanel createImageOptionPanel(Font labelFont) {
        // create a Image Option panel
        JPanel imgageOptionPanel = new JPanel();
        imgageOptionPanel.setLayout(new FlowLayout());
        Box hBox = Box.createHorizontalBox();

        // create the labels
        undoLabel = new JLabel("undo");
        redoLabel = new JLabel("redo");

        // set up the next and previous buttons
        setUpUndoAndRedoButtons();

        // set up the font for the labels
        redoLabel.setFont(labelFont);
        undoLabel.setFont(labelFont);

        //set up askForConfirmationCheckBox
        askForConfirmationCheckBox = new JCheckBox("ask for confirmation?");
        askForConfirmationCheckBox.setSelected(true);

        // add the items to the vertical box and the box to the panel
        hBox.add(Box.createHorizontalGlue());
        hBox.add(askForConfirmationCheckBox);
        hBox.add(Box.createHorizontalStrut(10));
        hBox.add(undoButton);
        hBox.add(undoLabel);
        hBox.add(Box.createHorizontalStrut(10));
        hBox.add(redoLabel);
        hBox.add(redoButton);
        imgageOptionPanel.add(hBox);
        hBox.add(Box.createHorizontalGlue());

        return imgageOptionPanel;
    }

    /**
     * Method to set up the save and load buttons
     */
    private void setUpSaveAndLoadButtons()
    {
        // create the arrow buttons
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");

        // set the tool tip text
        saveButton.setToolTipText("Click to redo the latest action");
        loadButton.setToolTipText("Click to undo the latest action");

        // handle save button press
        saveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    //save the current image to a file
                    String path = FileChooser.pickAFile("Save image as", picturePath);

                    if (path != null) {
                        //try to write to path as is, this fails if user provides invalid extension 
                        if (picture.write(path)) {} //try saving
                        //if that doesn't work, try adding pictureExtension, and if that doesn't work, default to .jpg
                        else if (picture.write(path + pictureExtension)) {} //try adding the extension of the original image
                        else {picture.write(path + ".jpg");} //otherwise just use .jpg
                    }
                }
            });

        // handle load button press
        loadButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    //select a picture
                    String fileName = FileChooser.pickAFile("Open image");

                    //set selected picture to the picture being edited, unless there was an error while selecting the picture
                    if (fileName != null) {
                        //load the image
                        picture = new Picture(fileName);

                        try {
                            pictureExtension = fileName.substring(fileName.lastIndexOf("."));
                            picturePath = picture.getFileName().substring(0 , picture.getFileName().lastIndexOf(System.getProperty("file.separator"))); //directory containing the loaded image, by creating a substring containing all characters up to the last file separator (/ on UNIX, \ on windows
                        } catch (Exception e) { pictureExtension = ".jpg"; picturePath = FileChooser.getMediaDirectory();}
                        updateImage();
                    } else {
                        //display error message
                        JOptionPane.showMessageDialog(null, "error loading image, \n you probably just clicked cancel, \n but if not, please try again", "error loading file", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
    }

    /**
     * Create the file option panel, will handle things like save and load
     * @param labelFont the font to use for labels
     */
    private JPanel createFileOptionPanel(Font labelFont)
    {
        // create a Image Option panel
        JPanel fileOptionPanel = new JPanel();
        fileOptionPanel.setLayout(new FlowLayout());
        Box hBox = Box.createHorizontalBox();

        // set up the next and previous buttons
        setUpSaveAndLoadButtons();

        // set up the font for the labels
        saveButton.setFont(labelFont);
        loadButton.setFont(labelFont);

        // add the items to the vertical box and the box to the panel
        hBox.add(Box.createHorizontalGlue());
        hBox.add(saveButton);
        hBox.add(Box.createHorizontalStrut(10));
        hBox.add(loadButton);
        fileOptionPanel.add(hBox);
        hBox.add(Box.createHorizontalGlue());

        return fileOptionPanel;
    }

    /**
     * Creates the Bottom JPanel with the more important settings on it
     * and color information
     */
    private void createBottomPanel()
    {
        // create the info panel and set the layout
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        // create the font
        Font largerFont = new Font(bottomPanel.getFont().getName(),
                bottomPanel.getFont().getStyle(),14);

        // create the pixel location panel
        JPanel fileOptionPanel = createFileOptionPanel(largerFont);

        // create the color information panel
        JPanel imageOptionPanel = createImageOptionPanel(largerFont);

        // add the panels to the info panel
        bottomPanel.add(BorderLayout.WEST,fileOptionPanel);
        bottomPanel.add(BorderLayout.EAST,imageOptionPanel); 

        // add the info panel
        pictureFrame.getContentPane().add(BorderLayout.SOUTH,bottomPanel);
    } 

    /**
     * Method to check that the current position is in the viewing area and if
     * not scroll to center the current position if possible
     */
    public void checkScroll()
    {
        // get the x and y position in pixels
        int xPos = (int) (colIndex * zoomFactor); 
        int yPos = (int) (rowIndex * zoomFactor); 

        // only do this if the image is larger than normal
        if (zoomFactor > 1) {

            // get the rectangle that defines the current view
            JViewport viewport = scrollPane.getViewport();
            Rectangle rect = viewport.getViewRect();
            int rectMinX = (int) rect.getX();
            int rectWidth = (int) rect.getWidth();
            int rectMaxX = rectMinX + rectWidth - 1;
            int rectMinY = (int) rect.getY();
            int rectHeight = (int) rect.getHeight();
            int rectMaxY = rectMinY + rectHeight - 1;

            // get the maximum possible x and y index
            int macolIndexX = (int) (picture.getWidth() * zoomFactor) - rectWidth - 1;
            int macolIndexY = (int) (picture.getHeight() * zoomFactor) - rectHeight - 1;

            // calculate how to position the current position in the middle of the viewing
            // area
            int viewX = xPos - (int) (rectWidth / 2);
            int viewY = yPos - (int) (rectHeight / 2);

            // reposition the viewX and viewY if outside allowed values
            if (viewX < 0)
                viewX = 0;
            else if (viewX > macolIndexX)
                viewX = macolIndexX;
            if (viewY < 0)
                viewY = 0;
            else if (viewY > macolIndexY)
                viewY = macolIndexY;

            // move the viewport upper left point
            viewport.scrollRectToVisible(new Rectangle(viewX,viewY,rectWidth,rectHeight));
        }
    }

    /**
     * Zooms in the on picture by scaling the image.  
     * It is extremely memory intensive.
     * @param factor the amount to zoom by
     */
    public void zoom(double factor)
    {
        // save the current zoom factor
        zoomFactor = factor;

        // calculate the new width and height and get an image that size
        int width = (int) (picture.getWidth()*zoomFactor);
        int height = (int) (picture.getHeight()*zoomFactor);
        BufferedImage bimg = picture.getBufferedImage();

        // set the scroll image icon to the new image
        imageDisplay.setImage(bimg.getScaledInstance(width, height, Image.SCALE_DEFAULT));
        imageDisplay.setCurrentX((int) (colIndex * zoomFactor));
        imageDisplay.setCurrentY((int) (rowIndex * zoomFactor));
        imageDisplay.revalidate();
        checkScroll();  // check if need to reposition scroll
    }

    /*getting removed for now, too many issues
    /**
     * zooms the image to fit the current window size
     *
    public void zoomImageToFit()
    {
    //calculate the needed zoom factor
    double factor = scrollPane.getWidth() / picture.getWidth();
    if (factor > scrollPane.getHeight() / picture.getHeight())
    {
    factor = scrollPane.getHeight() / picture.getHeight();
    }
    //call the zoom method

    zoom(factor);
    }*/

    /**
     * Updates, reloads the image
     * probably pretty memory intensive
     */
    public void updateImage() {
        // calculate the new width and height and get an image that size
        int width = (int) (picture.getWidth());
        int height = (int) (picture.getHeight());
        BufferedImage bimg = picture.getBufferedImage();

        // set the scroll image icon to the new image
        imageDisplay.setImage(bimg.getScaledInstance(width, height, Image.SCALE_DEFAULT));
        imageDisplay.setCurrentX((int) (colIndex));
        imageDisplay.setCurrentY((int) (rowIndex));
        imageDisplay.revalidate();
        checkScroll();

        zoom(zoomFactor);

        //rename the window
        pictureFrame.setTitle(picture.getTitle());
    }

    /**
     * Repaints the image on the scrollpane.  
     */
    public void repaint()
    {
        pictureFrame.repaint();
    }
    //*************************************************//
    //               Tool Utility Panel Management          //
    //*************************************************//

    /**
     * uses one of the various __ToolUtilityPanel methods to create set
     * the panel associated with the passed tool
     * @param tool the integer array representing the tool currently selected
     */
    public void updateUtilityPanel(int[] tool)
    {
        //set that created panel to the utilityPanel
        pictureFrame.getContentPane().remove(toolUtilityPanel);
        toolUtilityPanel.updateToolUtilityPanel(tool);
        pictureFrame.getContentPane().add(BorderLayout.WEST,toolUtilityPanel);
        pictureFrame.pack();
        repaint();
    }

    //****************************************//
    //               Event Listeners          //
    //****************************************//

    /**
     * Called when the mouse is dragged (button held down and moved)
     * @param e the mouse event
     */
    public void mouseDragged(MouseEvent e)
    {

    }

    /**
     * Method to check if the given x and y are in the picture
     * @param column the horizontal value
     * @param row the vertical value
     * @return true if the row and column are in the picture 
     * and false otherwise
     */
    private boolean isLocationInPicture(int column, int row)
    {
        boolean result = false; // the default is false
        if (column >= 0 && column < picture.getWidth() &&
        row >= 0 && row < picture.getHeight())
            result = true;

        return result;
    }

    /**
     * Method to return the pixel information from the passed x and y but
     * also converts x and y from strings
     * @param xString the x value as a string from the user
     * @param yString the y value as a string from the user
     */
    public Color returnPixelInformation(String xString, String yString)
    {
        int x = -1;
        int y = -1;
        try {
            x = Integer.parseInt(xString);
            x = x - numberBase;
            y = Integer.parseInt(yString);
            y = y - numberBase;
        } catch (Exception ex) {
        }

        if (x >= 0 && y >= 0) {
            return returnPixelInformation(x,y);
        } else return new Color(255,255,255);
    }

    /**
     * Method to display pixel information for the passed x and y
     * @param pictureX the x value in the picture
     * @param pictureY the y value in the picture
     */
    private Color returnPixelInformation(int pictureX, int pictureY)
    {
        Color pixelColor;

        // check that this x and y are in range
        if (isLocationInPicture(pictureX, pictureY))
        {
            // save the current x and y index
            colIndex = pictureX;
            rowIndex = pictureY;

            // get the pixel at the x and y
            Pixel pixel = new Pixel(picture,colIndex,rowIndex);

            // set the values based on the pixel
            pixelColor = new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
        } 
        else
        {
            pixelColor = new Color(255,255,255);
        }

        // notify the image display of the current x and y
        imageDisplay.setCurrentX((int) (colIndex * zoomFactor));
        imageDisplay.setCurrentY((int) (rowIndex * zoomFactor));

        return pixelColor;
    }

    /**
     * Method to display pixel information based on a mouse event
     * @param e a mouse event
     */
    private Color returnPixelInformation(MouseEvent e)
    {

        // get the cursor x and y
        int cursorX = e.getX();
        int cursorY = e.getY();

        // get the x and y in the original (not scaled image)
        int pictureX = (int) (cursorX / zoomFactor + numberBase);
        int pictureY = (int) (cursorY / zoomFactor + numberBase);

        // return the information for this x and y
        return returnPixelInformation(pictureX,pictureY);
    }

    /**
     * Method called when the mouse is moved with no buttons down
     * @param e the mouse event
     */
    public void mouseMoved(MouseEvent e)
    {}

    /**
     * Method called when the mouse is clicked
     * @param e the mouse event
     */
    public void mouseClicked(MouseEvent e)
    {
        //if it's a left click, update the selected pixel
        if (SwingUtilities.isLeftMouseButton(e)) {
            selectedPixelColor = returnPixelInformation(e);
        }
        //else, if it's a right click (FUTURE), update the drag selection
        else if (SwingUtilities.isRightMouseButton(e)) {

        }
    }

    /**
     * Method called when the mouse button is pushed down
     * @param e the mouse event
     */ 
    public void mousePressed(MouseEvent e)
    {

    }

    /**
     * Method called when the mouse button is released
     * @param e the mouse event
     */
    public void mouseReleased(MouseEvent e)
    {
    }

    /**
     * Method called when the component is entered (mouse moves over it)
     * @param e the mouse event
     */
    public void mouseEntered(MouseEvent e)
    {
    }

    /**
     * Method called when the mouse moves over the component
     * @param e the mouse event
     */
    public void mouseExited(MouseEvent e)
    {
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
     * Controls the menu bars
     * 
     * @param a the ActionEvent
     */
    public void actionPerformed(ActionEvent a)
    {

        if(a.getActionCommand().equals("Update"))
        {
            this.repaint();
            return;
        }

        //handle zoom menu actions
        for (Component element : zoomMenu.getMenuComponents())
        {
            if (element.equals(a.getSource()))
            {
                zoomActionPerformed(a);
                return;
            }
        }
    }

    /**
     * Controls the zoom menu bar
     *
     * @param a the ActionEvent 
     */
    public void zoomActionPerformed(ActionEvent a)
    {
        /* getting removed for now, too many issues
        if(a.getActionCommand().equals("Auto Zoom"))
        {
        zoomImageToFit();
        enableZoomItems();
        zoomToFit.setEnabled(false);
        }
         */
        if(a.getActionCommand().equals("25%"))
        {
            this.zoom(.25);
            enableZoomItems();
            twentyFive.setEnabled(false);
        }
        if(a.getActionCommand().equals("50%"))
        {
            this.zoom(.50);
            enableZoomItems();
            fifty.setEnabled(false);
        }
        if(a.getActionCommand().equals("75%"))
        {
            this.zoom(.75);
            enableZoomItems();
            seventyFive.setEnabled(false);
        }
        if(a.getActionCommand().equals("100%"))
        {
            this.zoom(1.0);
            enableZoomItems();
            hundred.setEnabled(false);
        }
        if(a.getActionCommand().equals("150%"))
        {
            this.zoom(1.5);
            enableZoomItems();
            hundredFifty.setEnabled(false);
        }
        if(a.getActionCommand().equals("200%"))
        {
            this.zoom(2.0);
            enableZoomItems();
            twoHundred.setEnabled(false);
        }
        if(a.getActionCommand().equals("500%"))
        {
            this.zoom(5.0);
            enableZoomItems();
            fiveHundred.setEnabled(false);
        }
    }
    
    /**
     * Class for establishing the focus for the textfields
     */
    private class PictureExplorerFocusTraversalPolicy
    extends FocusTraversalPolicy {

        /**
         * Method to get the next component for focus
         */
        public Component getComponentAfter(Container focusCycleRoot,
        Component aComponent) {
            if (aComponent.equals(saveButton))
                return saveButton;
            else 
                return saveButton;
        }

        /**
         * Method to get the previous component for focus
         */
        public Component getComponentBefore(Container focusCycleRoot,
        Component aComponent) {
            if (aComponent.equals(saveButton))
                return saveButton;
            else 
                return saveButton;
        }

        public Component getDefaultComponent(Container focusCycleRoot) {
            return saveButton;
        }

        public Component getLastComponent(Container focusCycleRoot) {
            return saveButton;
        }

        public Component getFirstComponent(Container focusCycleRoot) {
            return saveButton;
        }
    }

    /**
     * class for creating a confirmation box that displays 
     * a preview of the image 
     * and asks user for confirmation before setting that temp image to the picture
     */
    public class PictureConfirmation {
        /** picture to display */
        private DigitalPicture tempPicture;
        /** frame used to hold all of this */
        private JFrame tempFrame;
        /** ImageIcon used to display the picture in the label*/
        private ImageIcon tempImageIcon = new ImageIcon();
        /** Label used to display the picture */
        private JLabel imageLabel;
        /** button used to save the tempPicture as the explorers picture */
        private JButton applyButton;

        /** constructor */
        public PictureConfirmation() 
        {
            this.initFrame();
        }

        /**
         * A method to update the picture frame image with the image  
         * in the picture 
         */
        public void updateConfPanelImage(DigitalPicture temp)
        {
            //if the ask for confirmation box is unchecked, update the image, and return
            if (!askForConfirmationCheckBox.isSelected()) {
                picture = new Picture(temp.getBufferedImage());
                tempFrame.setVisible(false);
                updateImage();
                tempFrame.dispose();
                return;
            }
            tempFrame.dispose();
            this.initFrame();
            tempPicture = new SimplePicture(temp.getBufferedImage());
            // only do this if there is a picture
            if (tempPicture != null)
            {
                // set the image for the image icon from the picture
                tempImageIcon.setImage(temp.getImage());

                // set the title of the frame to the title of the picture
                tempFrame.setTitle(picture.getTitle());

                //make the frame visible 
                tempFrame.setVisible(true);
            }
        }

        /**
         * A method to initialize the picture frame
         */
        private void initFrame()
        {
            //init things
            tempFrame = new JFrame();
            applyButton = new JButton("apply");
            tempImageIcon.setImage((new Picture(picture.getBufferedImage())).getImage());
            imageLabel = new JLabel(tempImageIcon);

            // add the labels to the frame
            tempFrame.getContentPane().add(BorderLayout.CENTER, this.imageLabel);
            // add the button to the frame
            applyButton.setPreferredSize(new Dimension(50,20));
            tempFrame.getContentPane().add(BorderLayout.SOUTH, this.applyButton);

            // pack the frame (set the size to as big as it needs to be)
            tempFrame.pack();

            //set frame configs mode
            tempFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // make the frame invisible
            tempFrame.setVisible(false);

            //handle apply button press
            applyButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        picture = new Picture(tempPicture.getBufferedImage());
                        //tempFrame.setVisible(false)
                        updateImage();
                        //memory cleanup
                        tempFrame.dispose();
                        tempImageIcon.setImage(new Picture().getImage());
                        return;
                    }
                });
        }
    }

    /**
     * Test Main.  It will explore the beach 
     */
    public static void main( String args[])
    {
        Picture pix = new Picture("beach.jpg");
        pix.explore();
    }

}
