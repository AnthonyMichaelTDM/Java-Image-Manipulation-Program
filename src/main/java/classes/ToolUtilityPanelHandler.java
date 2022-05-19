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

package classes;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import java.awt.image.*;
//import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.text.NumberFormat;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
/**
 * class used by PictureExplorer to generate tool specific utility panels
 * in general,
 * one main thing it takes from picture is the current tool,
 * it returns the utility panel for that tool
 *
 * only creates it's panel, requires classes that use it to handle other things like
 * adding it to the GUI, and removeing it when a new tool is selected, before regenerating it
 * 
 * 
 * @author Anthony Rubick
 */
public class ToolUtilityPanelHandler extends JPanel
{
    //DATA
    final private int SIDE_PANEL_WIDTH = 150;
    final private int TOOL_CONFIG_WIDTH = 140;
    /**
     * default constructor
     */
    public ToolUtilityPanelHandler() {
        defaultToolUtilityPanel();
    }
    
    /**
     * set the utility panel to the default
     */
    private void defaultToolUtilityPanel() 
    {
        clearPanel();
        this.setBackground(Color.gray); //from parent
        this.setPreferredSize(new Dimension(SIDE_PANEL_WIDTH,100)); //from parent
        this.setLayout(new FlowLayout());
        //this.add(BorderLayout.WEST, Box.createHorizontalStrut(10));//add little gap
        //this.add(BorderLayout.EAST, Box.createHorizontalStrut(5));
    }
    
    /**
     * clears the panel
     */
    public void clearPanel() {
        removeAll(); //from parent
    }
    
    /**
     * this is the method accessable to other classes, and it handles selecting and returning
     * the proper panel for the passed tool
     * uses one of the various __ToolUtilityPanel methods to create set
     * the panel associated with the passed tool
     * @param tool the integer array representing the tool currently selected
     */
    public void updateToolUtilityPanel(int[] tool)
    {
        //create the appropriate panel based on the passed tool
        switch (tool[0]) {
            case 1: //color management tool is selected
            colorModificationToolUtilityPanel(tool[1]);
            break;
            case 2: // filters tool is selected
            filtersToolUtilityPanel(tool[1]);
            break;
            default: defaultToolUtilityPanel();
        }
    }

    ///////////////Color Tools///////////////
    /**
     * creates the panel associated with the passed integer
     * @param tool the integer associated with the color tool the panel is being created for
     */
    private void colorModificationToolUtilityPanel(int tool)
    {
        switch (tool) {
            case 1: //Remove Color Channel tool selected
            removeColorToolPanel();
            break;
            case 2: //Trim Color Channel tool selected
            trimColorToolPanel();
            break;
            case 3: //Negate Color Channel tool selected
            negateToolPanel();
            break;
            case 4: //Gray Scale tool selected
            grayscaleToolPanel();
            break;
            case 5: //set color to color tool selected
            replaceColorWithColorToolPanel();
            break;
            default: //a tool not accounted for selected
            defaultToolUtilityPanel();
            break;
        }
    }

    /**
     * method to handle the remove tool panel,
     * as well as ActionEvents having to do with the UI of this panel
     */
    private void removeColorToolPanel() 
    {
        //DATA 
        JLabel titleLable = new JLabel(); // title label
        String titleText;
        Font titleFont;
        JButton confirmButton = new JButton("confirm"); // button to confirm changes
        //
        JPanel toolConfigPanel = new JPanel();
        // radio buttons to select the channels
        JRadioButton redRadioButton = new JRadioButton("remove red");
        JRadioButton greenRadioButton = new JRadioButton("remove green");
        JRadioButton blueRadioButton = new JRadioButton("remove blue");

        //panels
        defaultToolUtilityPanel();

        //configure panel components
        //title
        titleText = String.format("<html><div WIDTH=%d>%s</div></html>", TOOL_CONFIG_WIDTH, "remove one or more color channels");
        titleFont = new Font(titleLable.getFont().getName(),
                titleLable.getFont().getStyle(),18);
        titleLable.setFont(titleFont);
        titleLable.setText(titleText);
        //radiobuttons
        redRadioButton.setSelected(false);
        greenRadioButton.setSelected(false);
        blueRadioButton.setSelected(false);
        
        //build config panel
        toolConfigPanel.setLayout(new GridLayout(3,1));
        toolConfigPanel.setPreferredSize(new Dimension(TOOL_CONFIG_WIDTH,150));
        toolConfigPanel.add(redRadioButton);
        toolConfigPanel.add(greenRadioButton);
        toolConfigPanel.add(blueRadioButton);
        
        //add components to the main panel
        this.add(BorderLayout.NORTH, titleLable);
        this.add(BorderLayout.CENTER, toolConfigPanel);
        this.add(BorderLayout.SOUTH, confirmButton);
        
        //handle confirm button press
        confirmButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    //use the removeColorChannels method from the Picture class
                    Picture removed = new Picture(PictureExplorer.picture.getHeight(), PictureExplorer.picture.getWidth());
                    removed.copyPicture(new SimplePicture(PictureExplorer.picture.getBufferedImage()));
                    removed.removeColorChannels(
                        redRadioButton.isSelected(), 
                        greenRadioButton.isSelected(), 
                        blueRadioButton.isSelected()
                    );
                    PictureExplorer.pictureConfirmation.updateConfPanelImage(removed);
                }
            });

    }

    /**
     * method to handle the trim tool panel,
     * as well as ActionEvents having to do with the UI of this panel
     */
    private void trimColorToolPanel() 
    {
        //DATA 
        JLabel titleLable = new JLabel();//title label
        String titleText;
        Font titleFont;
        JButton confirmButton = new JButton("confirm");//button to confirm changes
        //
        JPanel toolConfigPanel = new JPanel();
        JComboBox<String> colorBox= new JComboBox<String>(new String[]{"red","green","blue"}); //color comboBox
        // min slider
        JLabel minLabel = new JLabel("min value (0-255)");
        JSlider minSlider = new JSlider(0,255,0);
        JLabel minValueLabel = new JLabel();
        // max slider
        JLabel maxLabel = new JLabel("max value (0-255)");
        JSlider maxSlider = new JSlider(0,255,255);
        JLabel maxValueLabel = new JLabel();

        //panels
        defaultToolUtilityPanel();

        // configure panel components
        //title
        titleText = String.format("<html><div WIDTH=%d>%s</div></html>", TOOL_CONFIG_WIDTH, "trim a color channel");
        titleFont = new Font(titleLable.getFont().getName(),
                titleLable.getFont().getStyle(),18);
        titleLable.setFont(titleFont);
        titleLable.setText(titleText);
        //sliders
        minSlider.setMinorTickSpacing(5);
        minSlider.setMajorTickSpacing(10);
        maxSlider.setMinorTickSpacing(5);
        maxSlider.setMajorTickSpacing(10);

        //build config panel
        toolConfigPanel.setLayout(new GridLayout(7,1));
        toolConfigPanel.setPreferredSize(new Dimension(TOOL_CONFIG_WIDTH,200));
        toolConfigPanel.add(colorBox);
        toolConfigPanel.add(minLabel);
        toolConfigPanel.add(minSlider);
        toolConfigPanel.add(minValueLabel);
        toolConfigPanel.add(maxLabel);
        toolConfigPanel.add(maxSlider);
        toolConfigPanel.add(maxValueLabel);

        // add components to main panel
        this.add(BorderLayout.NORTH, titleLable);
        this.add(BorderLayout.CENTER, toolConfigPanel);
        this.add(BorderLayout.SOUTH, confirmButton);

        //handle confirm button press
        confirmButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    //use the trim method from the Picture class
                    Picture trimmed = new Picture(PictureExplorer.picture.getHeight(), PictureExplorer.picture.getWidth());
                    trimmed.copyPicture(new SimplePicture(PictureExplorer.picture.getBufferedImage()));
                    trimmed.trimColor(
                        colorBox.getSelectedIndex(), 
                        (int) minSlider.getValue(), 
                        (int) maxSlider.getValue()
                    );
                    PictureExplorer.pictureConfirmation.updateConfPanelImage(trimmed);
                }
            });
        //handle min slider changing value
        minSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent evt) {
                    //update minValueLabel
                    minValueLabel.setText("min = " + minSlider.getValue());

                    if (minSlider.getValue() >= maxSlider.getValue()) minSlider.setValue(maxSlider.getValue());
                }
            });
        //handle max slider changing value
        maxSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent evt) {
                    //update maxValueLabel
                    maxValueLabel.setText("max = " + maxSlider.getValue());

                    if (maxSlider.getValue() <= minSlider.getValue()) maxSlider.setValue(minSlider.getValue());
                }
            });

    }

    /**
     * method to handle the negate tool panel,
     * as well as ActionEvents having to do with the UI of this panel
     */
    private void negateToolPanel() 
    {
        //DATA 
        JLabel titleLable = new JLabel();//title label
        String titleText;
        Font titleFont;
        JButton confirmButton = new JButton("confirm");//button to confirm changes
        //
        JPanel toolConfigPanel = new JPanel();
        // radio buttons, to select the channels to negate
        JRadioButton negateRedRadioButton = new JRadioButton("negate red");
        JRadioButton negateGreebRadioButton = new JRadioButton("negate green");
        JRadioButton negateBlueRadioButton = new JRadioButton("negate blue");
        

        //panels
        defaultToolUtilityPanel();
        
        //configure panel components
        //title
        titleText = String.format("<html><div WIDTH=%d>%s</div></html>", TOOL_CONFIG_WIDTH, "negate one or more color channels");
        titleFont = new Font(titleLable.getFont().getName(),
                titleLable.getFont().getStyle(),18);
        titleLable.setFont(titleFont);
        titleLable.setText(titleText);
        //radio buttons
        negateRedRadioButton.setSelected(false);
        negateGreebRadioButton.setSelected(false);
        negateBlueRadioButton.setSelected(false);

        //build config panel
        toolConfigPanel.setLayout(new GridLayout(3,1));
        toolConfigPanel.setPreferredSize(new Dimension(TOOL_CONFIG_WIDTH,150));
        toolConfigPanel.add(negateRedRadioButton);
        toolConfigPanel.add(negateGreebRadioButton);
        toolConfigPanel.add(negateBlueRadioButton);

        //add components to main panel
        this.add(BorderLayout.NORTH, titleLable);
        this.add(BorderLayout.CENTER, toolConfigPanel);
        this.add(BorderLayout.SOUTH, confirmButton);

        //handle confirm button press
        confirmButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    //use the negate method from the Picture class
                    Picture negated = new Picture(PictureExplorer.picture.getHeight(), PictureExplorer.picture.getWidth());
                    negated.copyPicture(new SimplePicture(PictureExplorer.picture.getBufferedImage()));
                    negated.negateColorChannels(
                        negateRedRadioButton.isSelected(), 
                        negateGreebRadioButton.isSelected(), 
                        negateBlueRadioButton.isSelected()
                    );
                    PictureExplorer.pictureConfirmation.updateConfPanelImage(negated);
                }
            });

    }

    /**
     * method to handle the grayscale tool panel,
     * as well as ActionEvents having to do with the UI of this panel
     */
    private void grayscaleToolPanel() 
    {
        //DATA 
        JLabel titleLable = new JLabel();//title label
        String titleText;
        Font titleFont;
        JButton confirmButton = new JButton("confirm");//button to confirm changes
        
        //panels
        defaultToolUtilityPanel();

        //configure panel components
        //title
        titleText = String.format("<html><div WIDTH=%d>%s</div></html>", 100, "grayscale the image");
        titleFont = new Font(titleLable.getFont().getName(),
                titleLable.getFont().getStyle(),18);
        titleLable.setFont(titleFont);
        titleLable.setText(titleText);

        //add components to the main panel
        this.add(BorderLayout.NORTH, titleLable);
        this.add(BorderLayout.SOUTH, confirmButton);

        //handle confirm button press
        confirmButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    //use the grayscale method from the Picture class
                    Picture grayscaled = new Picture(PictureExplorer.picture.getHeight(), PictureExplorer.picture.getWidth());
                    grayscaled.copyPicture(new SimplePicture(PictureExplorer.picture.getBufferedImage()));
                    grayscaled.grayscale();
                    PictureExplorer.pictureConfirmation.updateConfPanelImage(grayscaled);
                }
            });

    }

    /**
     * method to handle the replace color with color tool panel,
     * as well as ActionEvents having to do with the UI of this panel
     */
    private void replaceColorWithColorToolPanel() 
    {
        //DATA
        JLabel titleLable = new JLabel();//title label
        String titleText;
        Font titleFont;
        JButton confirmButton = new JButton("confirm");//button to confirm changes
        //
        JPanel toolConfigPanel = new JPanel();
        ///
        //label, button and panel to display the currently selected color to replace
        JPanel colorToReplacePanel = new JPanel();
        JLabel colorToReplaceLabel = new JLabel();
        JButton colorToReplaceButton = new JButton("update color to replace");
        JPanel replaceColorPanel = new JPanel(); //displays the replace color
        ///
        // label, button and panel to display the currently selected color to set
        JPanel colorToSetPanel = new JPanel();
        JLabel colorToSetLabel = new JLabel();
        JButton colorToSetButton = new JButton("select color to set");
        JPanel setColorPanel = new JPanel(); //displays the set color
        ///
        // label, slider, and panel to select the tolerance
        JPanel tolerancePanel = new JPanel(new GridLayout(2,1));
        JSlider toleranceSlider = new JSlider(1,255,15);
        JLabel toleranceLabel = new JLabel("tolerance (0-255):"  + toleranceSlider.getValue());

        //panels
        defaultToolUtilityPanel();

        //configure panel components
        //title
        titleText = String.format("<html><div WIDTH=%d>%s</div></html>", 100, "replace color with color");
        titleFont = new Font(titleLable.getFont().getName(),
                titleLable.getFont().getStyle(),18);
        titleLable.setFont(titleFont);
        titleLable.setText(titleText);
        //label, button and panel to display the currently selected color to replace
        colorToReplaceLabel.setText("color to replace");
        replaceColorPanel.setBackground(PictureExplorer.selectedPixelColor);
        colorToReplacePanel.add(BorderLayout.WEST, colorToReplaceLabel);
        colorToReplacePanel.add(BorderLayout.EAST, replaceColorPanel);
        colorToReplacePanel.add(BorderLayout.SOUTH, colorToReplaceButton);
        //label, button and panel to display the currently selected color to set
        colorToSetLabel.setText("color to set");
        colorToSetPanel.add(BorderLayout.WEST, colorToSetLabel);
        colorToSetPanel.add(BorderLayout.EAST, setColorPanel);
        colorToSetPanel.add(BorderLayout.SOUTH, colorToSetButton);
        //slider for tolerance
        tolerancePanel.setLayout(new GridLayout(2,1));
        tolerancePanel.add(toleranceLabel);
        tolerancePanel.add(toleranceSlider);

        //build config panel 
        toolConfigPanel.setLayout(new GridLayout(3,1));
        toolConfigPanel.setPreferredSize(new Dimension(TOOL_CONFIG_WIDTH,200));
        toolConfigPanel.add(colorToReplacePanel);
        toolConfigPanel.add(colorToSetPanel);
        toolConfigPanel.add(tolerancePanel);

        //add components to the main panel
        this.add(BorderLayout.NORTH, titleLable);
        this.add(BorderLayout.CENTER, toolConfigPanel);
        this.add(BorderLayout.SOUTH, confirmButton);

        //handle confirm button press
        confirmButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    //use the replaceColorWithColor method from the Picture class
                    Picture colorReplaced = new Picture(PictureExplorer.picture.getHeight(), PictureExplorer.picture.getWidth());
                    colorReplaced.copyPicture(new SimplePicture(PictureExplorer.picture.getBufferedImage()));
                    colorReplaced.replaceColorWithColor(
                        replaceColorPanel.getBackground(), 
                        setColorPanel.getBackground(), 
                        toleranceSlider.getValue()
                    );
                    PictureExplorer.pictureConfirmation.updateConfPanelImage(colorReplaced);
                }
            });
        //handle colorToReplaceButton press
        colorToReplaceButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    //update the replaceColorPanel
                    replaceColorPanel.setBackground(PictureExplorer.selectedPixelColor);
                }
            });

        //handle colorToSetButton press
        colorToSetButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    //update the setColorPanel, and enable confirmButton
                    setColorPanel.setBackground(ColorChooser.pickAColor());
                    confirmButton.setEnabled(true);
                }
            });
        //handle tolerance slider changing value
        toleranceSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent evt) {
                    //update maxValueLabel
                    toleranceLabel.setText("tolerance: " + toleranceSlider.getValue());
                }
            });

    }

    /////////////// Filter Tools///////////////
    /**
     * creates the panel associated with the passed integer
     * @param tool the integer associated with the filter tool the panel is being created for
     */
    private void filtersToolUtilityPanel(int tool)
    {
        switch (tool) {
            case 1: //Edge Detection tool selected
            edgeDetectionToolPanel();
            break;
            case 2: //brighten tool selected
            brightenToolPanel();
            break;
            case 3: //darken tool selected
            darkenToolPanel();
            break;
            case 4: //simplify tool selected
            simplifyColorToolPanel();
            break;
            case 5: //k-means color simplification
            kMeansSimplifyToolPanel();
            break;
            default: //a tool not accounted for selected
            defaultToolUtilityPanel();
        }
    }

    /**
     * method to handle the edge detection tool panel,
     * as well as ActionEvents having to do with the UI of this panel
     */
    private void edgeDetectionToolPanel() 
    {
        //DATA
        JLabel titleLable = new JLabel();//title label
        String titleText;
        Font titleFont;
        JButton confirmButton = new JButton("confirm");//button to confirm changes
        //
        JPanel toolConfigPanel = new JPanel();
        ///
        JPanel algorithmSelectionPanel = new JPanel();
        JLabel explainationLabel = new JLabel();
        //algorithm selection comboBox
        JComboBox<String> algorithmSelection = new JComboBox<String>(new String[]{"algorithm 1", "algorithm 2", "algorithm 3"});
        //auto tolerance radiobutton
        JCheckBox autoTolerance = new JCheckBox();
        ////
        JPanel algorithmSpecificPanel = new JPanel();
        //iterations text box
        JLabel iterationsLabel = new JLabel("iterations: ");
        NumberFormatter iterationsFormat  = new NumberFormatter(NumberFormat.getNumberInstance());
        iterationsFormat.setMinimum(0);
        JFormattedTextField iterationsField = new JFormattedTextField(iterationsFormat);
        ////
        //tolerance slider
        JPanel tolerancePanel = new JPanel();
        JLabel toleranceLabel = new JLabel("tolerance (0-255): ");
        JSlider toleranceSlider = new JSlider(1,255,5);
        //checkbox, toggle grayscaling
        JCheckBox grayscaleCheckBox = new JCheckBox("grayscale output?");

        //panels
        defaultToolUtilityPanel();

        //configure panel components
        //title
        titleText = String.format("<html><div WIDTH=%d>%s</div></html>", 100, "detect edges with 1 of 3 algorithms");
        titleFont = new Font(titleLable.getFont().getName(),
                titleLable.getFont().getStyle(),18);
        titleLable.setFont(titleFont);
        titleLable.setText(titleText);
        //build algorithm panel
        algorithmSpecificPanel.setLayout(new GridLayout(2,1));
        iterationsField.setValue(1);
        iterationsField.setColumns(10);
        algorithmSpecificPanel.add(iterationsLabel);
        algorithmSpecificPanel.add(iterationsField);
        algorithmSpecificPanel.setVisible(false);
        autoTolerance.setVisible(false);
        //build algorithm selection panel
        algorithmSelectionPanel.setPreferredSize(new Dimension(TOOL_CONFIG_WIDTH, 175));
        algorithmSelection.setSelectedIndex(0);
        autoTolerance.setText(String.format("<html><div WIDTH=%d>%s</div></html>", 100, "automatically find tolerance?"));
        explainationLabel.setText(String.format("<html><div WIDTH=%d>%s</div></html>", TOOL_CONFIG_WIDTH-5, "algorithm 1 is much slower, but a higher resolution. Algorithm 2 is much faster, but can sometimes appear \"fuzzy\". Algorithm 3 fills area between edges, useful for making light text show up better against a plain background"));
        explainationLabel.setFont(new Font(explainationLabel.getFont().getName(),explainationLabel.getFont().getStyle(),9));
        algorithmSelectionPanel.add(BorderLayout.NORTH, algorithmSelection);
        algorithmSelectionPanel.add(BorderLayout.SOUTH, algorithmSpecificPanel);
        algorithmSelectionPanel.add(BorderLayout.SOUTH, autoTolerance);
        algorithmSelectionPanel.add(BorderLayout.SOUTH, explainationLabel);
        //build tolerance panel 
        tolerancePanel.setLayout(new GridLayout(3,1));
        grayscaleCheckBox.setVisible(false);
        tolerancePanel.add(grayscaleCheckBox);
        tolerancePanel.add(toleranceLabel);
        tolerancePanel.add(toleranceSlider);
        //build tool config panel
        toolConfigPanel.setLayout(new BorderLayout());
        toolConfigPanel.setPreferredSize(new Dimension(TOOL_CONFIG_WIDTH,250));
        toolConfigPanel.add(BorderLayout.NORTH, algorithmSelectionPanel);
        toolConfigPanel.add(BorderLayout.SOUTH, tolerancePanel);

        //add components to the main panel
        this.add(BorderLayout.NORTH, titleLable);
        this.add(BorderLayout.CENTER, toolConfigPanel);
        this.add(BorderLayout.SOUTH, confirmButton);

        //handle the iterations field
        iterationsField.addPropertyChangeListener("value", new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent e) {
                    iterationsLabel.setText("iterations: " + (int) ((Number)iterationsField.getValue()).doubleValue() );
                }           
            });
        //handle the toggle auto tolerance checkbox
        autoTolerance.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent evt) {
                    if (autoTolerance.isSelected()) {
                        toleranceLabel.setText("tolerance: auto");
                        toleranceSlider.setEnabled(false);
                    }
                    else {
                        toleranceLabel.setText("tolerance (0-255): " + toleranceSlider.getValue());
                        toleranceSlider.setEnabled(true);
                    }
                }
            });

        //handle confirm button press
        confirmButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    //use one of the edgeDetection methods from the Picture class
                    Picture edged = new Picture(PictureExplorer.picture.getHeight(), PictureExplorer.picture.getWidth());
                    edged.copyPicture(new SimplePicture(PictureExplorer.picture.getBufferedImage()));
                    switch (algorithmSelection.getSelectedIndex()) 
                    {
                        case 1: 
                        try {
                            edged.edgeDetection2(toleranceSlider.getValue(), (int) ((Number)iterationsField.getValue()).doubleValue());
                        } catch (Exception e) {JOptionPane.showMessageDialog(null, "an error occured, please try again", e.toString(), JOptionPane.ERROR_MESSAGE);} 
                        break;
                        case 2:
                        if (autoTolerance.isSelected()) edged.bolden2();
                        else edged.bolden(toleranceSlider.getValue());
                        break;
                        default:
                        edged.edgeDetection(toleranceSlider.getValue());
                        break;
                    }
                    //grayscale image if box is checked
                    if (grayscaleCheckBox.isSelected()) {
                        edged.grayscale();
                    }
                    //save the new image (ask first)
                    PictureExplorer.pictureConfirmation.updateConfPanelImage(edged);
                }
            });
        //handle the combo box changing selected value
        algorithmSelection.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    //update the panel as needed
                    switch (algorithmSelection.getSelectedIndex()) {
                        case 2: //algorithm 3 selected
                            algorithmSpecificPanel.setVisible(false);
                            explainationLabel.setVisible(false);
                            autoTolerance.setVisible(true);
                            autoTolerance.setSelected(false);
                            grayscaleCheckBox.setVisible(true);
                            break;
                        case 1: //algorithm 2 selected 
                            algorithmSpecificPanel.setVisible(true);
                            explainationLabel.setVisible(false);
                            autoTolerance.setVisible(false);
                            autoTolerance.setSelected(false);
                            grayscaleCheckBox.setVisible(false);
                            break;
                        case 0: //algorithm 1 selected
                            algorithmSpecificPanel.setVisible(false);
                            explainationLabel.setVisible(true);
                            autoTolerance.setVisible(false);
                            autoTolerance.setSelected(false);
                            grayscaleCheckBox.setVisible(false);
                            break;
                        default:
                            break;
                    }
                    repaint();
                }
            });
        //handle tolerance slider changing value
        toleranceSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent evt) {
                    //update maxValueLabel
                    toleranceLabel.setText("tolerance (0-255): " + toleranceSlider.getValue());
                }
            });
        
    }

    /**
     * method to handle the grayscale tool panel,
     * as well as ActionEvents having to do with the UI of this panel
     */
    private void brightenToolPanel() 
    {
        //DATA 
        JLabel titleLable = new JLabel();//title label
        String titleText;
        Font titleFont;
        JButton confirmButton = new JButton("confirm");//button to confirm changes
        
        //panels
        defaultToolUtilityPanel();
        
        //configure panel components
        titleText = String.format("<html><div WIDTH=%d>%s</div></html>", 100, "brighten the image");
        titleFont = new Font(titleLable.getFont().getName(),
                titleLable.getFont().getStyle(),18);
        titleLable.setFont(titleFont);
        titleLable.setText(titleText);

        //add components to the main panel
        this.add(BorderLayout.NORTH, titleLable);
        this.add(BorderLayout.SOUTH, confirmButton);

        //handle confirm button press
        confirmButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    //use the brighten method from the Picture class
                    Picture brightened = new Picture(PictureExplorer.picture.getHeight(), PictureExplorer.picture.getWidth());
                    brightened.copyPicture(new SimplePicture(PictureExplorer.picture.getBufferedImage()));
                    brightened.brighten();
                    PictureExplorer.pictureConfirmation.updateConfPanelImage(brightened);
                }
            });

    }

    /**
     * method to handle the grayscale tool panel,
     * as well as ActionEvents having to do with the UI of this panel
     */
    private void darkenToolPanel() 
    {
        //DATA 
        JLabel titleLable = new JLabel();//title label
        String titleText;
        Font titleFont;
        JButton confirmButton = new JButton("confirm");//button to confirm changes

        //panels
        defaultToolUtilityPanel();
        
        //configure panel components
        titleText = String.format("<html><div WIDTH=%d>%s</div></html>", 100, "darken the image");
        titleFont = new Font(titleLable.getFont().getName(),
                titleLable.getFont().getStyle(),18);
        titleLable.setFont(titleFont);
        titleLable.setText(titleText);

        //add components to the main panel
        this.add(BorderLayout.NORTH, titleLable);
        this.add(BorderLayout.SOUTH, confirmButton);

        //handle confirm button press
        confirmButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    //use the darken method from the Picture class
                    Picture darkened = new Picture(PictureExplorer.picture.getHeight(), PictureExplorer.picture.getWidth());
                    darkened.copyPicture(new SimplePicture(PictureExplorer.picture.getBufferedImage()));
                    darkened.darken();
                    PictureExplorer.pictureConfirmation.updateConfPanelImage(darkened);
                }
            });

    }

    /**
     * method to handle the color simplification tool panel,
     * as well as ActionEvents having to do with the UI of this panel
     */
    private void simplifyColorToolPanel() 
    {
        //DATA
        JLabel titleLable = new JLabel();//title label;
        String titleText;
        Font titleFont;
        JButton confirmButton = new JButton("confirm");//button to confirm changes;
        //
        JPanel toolConfigPanel = new JPanel();
        ///
        //sort method config stuff
        JPanel sortMethodPanel = new JPanel();
        JLabel sortMethodLabel = new JLabel();
        JComboBox<String> sortMethodSelection = new JComboBox<String>(new String[]{"Hue","z-score","integer rgb"});
        JCheckBox removeDuplicatesCheckBox = new JCheckBox("trim duplicates?");
        ///
        //generation method config stuff
        JPanel genMethodPanel = new JPanel();
        JLabel genMethodLabel = new JLabel();
        JComboBox<String> genMethodSelection = new JComboBox<String>(new String[]{"5 num sum", "mean + SD"});
        ///
        //additional filters
        JPanel additionalFiltersPanel = new JPanel();
        JLabel additionalFiltersLabel = new JLabel();
        JCheckBox grayscaleCheckBox = new JCheckBox("grayscale output");
        JCheckBox invertCheckBox = new JCheckBox("invert output");

        //panels
        defaultToolUtilityPanel();
        
        //configure panel components
        //title
        titleText = String.format("<html><div WIDTH=%d>%s</div></html>", TOOL_CONFIG_WIDTH-5, "simplify image to 5 colors:");
        titleFont = new Font(titleLable.getFont().getName(), titleLable.getFont().getStyle(),18);
        titleLable.setFont(titleFont);
        titleLable.setText(titleText);
        //build sort method panel
        sortMethodPanel.setPreferredSize(new Dimension(TOOL_CONFIG_WIDTH, 90));
        sortMethodLabel.setText(String.format("<html><div WIDTH=%d>%s</div></html>", TOOL_CONFIG_WIDTH-5,"select sort method:"));
        sortMethodSelection.setSelectedIndex(0);
        removeDuplicatesCheckBox.setSelected(false);
        sortMethodPanel.add(BorderLayout.NORTH, sortMethodLabel);
        sortMethodPanel.add(BorderLayout.CENTER, sortMethodSelection);
        sortMethodPanel.add(BorderLayout.SOUTH, removeDuplicatesCheckBox);
        //build generation method panel
        genMethodPanel.setLayout(new BorderLayout());
        genMethodLabel.setText(String.format("<html><div WIDTH=%d>%s</div></html>", TOOL_CONFIG_WIDTH-5, " \nselect color gen method:"));
        genMethodSelection.setSelectedIndex(0);
        genMethodPanel.add(BorderLayout.NORTH, genMethodLabel);
        genMethodPanel.add(BorderLayout.CENTER, genMethodSelection);
        //build additional filters panel
        additionalFiltersPanel.setLayout(new BorderLayout());
        additionalFiltersLabel.setText(String.format("<html><div WIDTH=%d>%s</div></html>", TOOL_CONFIG_WIDTH-5, "select additional filters:"));
        grayscaleCheckBox.setSelected(false);
        invertCheckBox.setSelected(false);
        additionalFiltersPanel.add(BorderLayout.NORTH, additionalFiltersLabel);
        additionalFiltersPanel.add(BorderLayout.CENTER, grayscaleCheckBox);
        additionalFiltersPanel.add(BorderLayout.SOUTH, invertCheckBox);
        //build config panel
        toolConfigPanel.setPreferredSize(new Dimension(TOOL_CONFIG_WIDTH,300));
        toolConfigPanel.add(BorderLayout.NORTH, new JLabel(String.format("<html><div WIDTH=%d>%s</div></html>", TOOL_CONFIG_WIDTH-5,"this method simplifies an image to five colors depending on the selected parameters")));
        toolConfigPanel.add(BorderLayout.CENTER, sortMethodPanel);
        toolConfigPanel.add(BorderLayout.CENTER, genMethodPanel);
        toolConfigPanel.add(BorderLayout.SOUTH,additionalFiltersPanel);

        //add components to the main panel
        this.add(BorderLayout.NORTH, titleLable);
        this.add(BorderLayout.CENTER, toolConfigPanel);
        this.add(BorderLayout.SOUTH, confirmButton);

        //handle confirm button press
        confirmButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    //use one of the color simplification methods from the Picture class
                    Picture simplified = new Picture(PictureExplorer.picture.getHeight(), PictureExplorer.picture.getWidth());
                    simplified.copyPicture(new SimplePicture(PictureExplorer.picture.getBufferedImage()));

                    //simplify image with user selected mode
                    simplified.simplifyColors(sortMethodSelection.getSelectedIndex(), removeDuplicatesCheckBox.isSelected(), genMethodSelection.getSelectedIndex(), new Boolean[]{grayscaleCheckBox.isSelected(), invertCheckBox.isSelected()});

                    //save the new image (ask first)
                    PictureExplorer.pictureConfirmation.updateConfPanelImage(simplified);
                }
            });

    }

    /**
     * method to handle the color simplification tool panel,
     * as well as ActionEvents having to do with the UI of this panel
     */
    private void kMeansSimplifyToolPanel() {
        //DATA
        JLabel titleLable = new JLabel(); //title label
        String titleText;
        Font titleFont;
        JButton confirmButton = new JButton("confirm"); //button to confirm changes
        //
        JPanel toolConfigPanel = new JPanel();
        ///
        JPanel checkboxPanel = new JPanel();
        JCheckBox autoKCheckBox = new JCheckBox("automatically find k");
        JCheckBox removeDupesCheckbox = new JCheckBox("remove duplicates?");
        ///
        JPanel kPanel = new JPanel();
        JLabel kLabel = new JLabel("max colors (k) (min 2)");
        NumberFormatter clustersFormat = new NumberFormatter(NumberFormat.getNumberInstance());
        clustersFormat.setMinimum(2);
        JFormattedTextField kField = new JFormattedTextField(clustersFormat);
        ///
        JPanel iPanel = new JPanel();
        JLabel iLabel = new JLabel("max iterations (i)");
        NumberFormatter iterationsFormat = new NumberFormatter(NumberFormat.getNumberInstance());
        iterationsFormat.setMinimum(2);
        JFormattedTextField iField = new JFormattedTextField(iterationsFormat);

        //panels
        defaultToolUtilityPanel();
        
        //config panel components
        //title
        titleText = String.format("<html><div WIDTH=%d>%s</div></html>", TOOL_CONFIG_WIDTH, "Simplify image with the k-means clustering algorithm");
        titleFont = new Font(titleLable.getFont().getName(), titleLable.getFont().getStyle(),18);
        titleLable.setFont(titleFont);
        titleLable.setText(titleText);
        //clusters text box
        kField.setSize(20, 100);
        //max iterations text box
        iField.setSize(20, 100);
        //checkboxes
        removeDupesCheckbox.setSelected(false);
        removeDupesCheckbox.setToolTipText("ignore multiple occurances of the same color");
        autoKCheckBox.setSelected(false);
        autoKCheckBox.setToolTipText("warning, very slow");
        //build k panel
        kPanel.setLayout(new GridLayout(2,1,5,5));
        //kField.setValue(5);
        kField.setColumns(5);
        kPanel.add(kLabel);
        kPanel.add(kField);
        //build i panel
        iPanel.setLayout(new GridLayout(2,1,5,5));
        //iField.setValue(5);
        iField.setColumns(5);
        iPanel.add(iLabel);
        iPanel.add(iField);
        //build checkboxes pannel
        checkboxPanel.setLayout(new GridLayout(2,1,5,5));
        checkboxPanel.add(autoKCheckBox);
        checkboxPanel.add(removeDupesCheckbox);
        //build tool config panel
        toolConfigPanel.setLayout(new BorderLayout(5,5));
        toolConfigPanel.setPreferredSize(new Dimension(TOOL_CONFIG_WIDTH,150));
        toolConfigPanel.add(BorderLayout.NORTH, kPanel);
        toolConfigPanel.add(BorderLayout.CENTER, iPanel);
        toolConfigPanel.add(BorderLayout.SOUTH, checkboxPanel);

        //add components to the main panel
        this.add(BorderLayout.NORTH, titleLable);
        this.add(BorderLayout.CENTER, toolConfigPanel);
        this.add(BorderLayout.SOUTH, confirmButton);

        //handle confirm button press
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //use the kMeansSimplify method from the Picture class
                Picture simplified= new Picture(PictureExplorer.picture.getHeight(), PictureExplorer.picture.getWidth());
                simplified.copyPicture(new SimplePicture(PictureExplorer.picture.getBufferedImage()));
                
                if (autoKCheckBox.isSelected()) {
                    simplified = simplified.scale(0.5, 0.5); //scale down to save time
                    try { 
                        simplified.kMeansSimplifyAutoK(
                            (int) ((Number)kField.getValue()).doubleValue(),
                            (int) ((Number)iField.getValue()).doubleValue(),
                            false
                        );
                    } catch (Exception e) {JOptionPane.showMessageDialog(null, "an error occured, please try again", e.toString(), JOptionPane.ERROR_MESSAGE);}

                    simplified = simplified.scale(2, 2); //scale back up
                } else {
                    simplified = simplified.scale(0.5, 0.5); //scale down to save time
                    try { 
                       simplified.kMeansSimplify(
                           (int) ((Number)kField.getValue()).doubleValue(),
                           (int) ((Number)iField.getValue()).doubleValue(),
                           false
                       );
                    } catch (Exception e) {JOptionPane.showMessageDialog(null, "an error occured, please try again", e.toString(), JOptionPane.ERROR_MESSAGE);}
                    simplified = simplified.scale(2, 2); //scale back up
                }
                //save the new image (ask first)
                PictureExplorer.pictureConfirmation.updateConfPanelImage(simplified);
            }
        });
    }

    
    ///////////////next tool type (idk, image manipulation, fun, some other groups)///////////////
}
