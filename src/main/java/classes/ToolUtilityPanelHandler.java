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
 * @author Anthony Michael
 */
public class ToolUtilityPanelHandler extends JPanel
{
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
        this.setPreferredSize(new Dimension(150,100)); //from parent
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
        //panels
        defaultToolUtilityPanel();
        //title label
        JLabel titleLable = new JLabel();
        //radio buttons, to select the channels
        JRadioButton redRadioButton = new JRadioButton("remove red");
        JRadioButton greenRadioButton = new JRadioButton("remove green");
        JRadioButton blueRadioButton = new JRadioButton("remove blue");
        //button to confirm changes
        JButton confirmButton = new JButton("confirm");

        //config panel components
        redRadioButton.setSelected(false);
        greenRadioButton.setSelected(false);
        blueRadioButton.setSelected(false);

        JPanel toolConfigPanel = new JPanel(new GridLayout(3,1));
        toolConfigPanel.setPreferredSize(new Dimension(150,300));
        toolConfigPanel.add(redRadioButton);
        toolConfigPanel.add(greenRadioButton);
        toolConfigPanel.add(blueRadioButton);

        String titleText = String.format("<html><div WIDTH=%d>%s</div></html>", 100, "remove one or more color channels");
        Font titleFont = new Font(titleLable.getFont().getName(),
                titleLable.getFont().getStyle(),18);
        titleLable.setFont(titleFont);
        titleLable.setText(titleText);
        //add components to the panel
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
        //panels
        defaultToolUtilityPanel();

        // panel components

        //title label
        JLabel titleLable = new JLabel();
        //color comboBox
        JComboBox<String> colorBox = new JComboBox<String>(new String[]{"red","green","blue"});
        //confirm button
        JButton confirmButton = new JButton("confirm");
        //min slider
        JLabel minLabel = new JLabel("min value (0-255)");
        JSlider minSlider = new JSlider(0,255,0);
        JLabel minValueLabel = new JLabel();
        //max slider
        JLabel maxLabel = new JLabel("max value (0-255)");
        JSlider maxSlider = new JSlider(0,255,255);
        JLabel maxValueLabel = new JLabel();

        // config panel components
        //title
        String titleText = String.format("<html><div WIDTH=%d>%s</div></html>", 100, "trim a color channel");
        Font titleFont = new Font(titleLable.getFont().getName(),
                titleLable.getFont().getStyle(),18);
        titleLable.setFont(titleFont);
        titleLable.setText(titleText);
        //sliders
        minSlider.setMinorTickSpacing(5);
        minSlider.setMajorTickSpacing(10);
        maxSlider.setMinorTickSpacing(5);
        maxSlider.setMajorTickSpacing(10);
        //slider panel
        JPanel toolConfigPanel = new JPanel(new GridLayout(7,1));
        toolConfigPanel.setPreferredSize(new Dimension(150,300));
        toolConfigPanel.add(colorBox);
        toolConfigPanel.add(minLabel);
        toolConfigPanel.add(minSlider);
        toolConfigPanel.add(minValueLabel);
        toolConfigPanel.add(maxLabel);
        toolConfigPanel.add(maxSlider);
        toolConfigPanel.add(maxValueLabel);

        // add components to panel
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
        //panels
        defaultToolUtilityPanel();
        //title label
        JLabel titleLable = new JLabel();
        //radio buttons, to select the channels
        JRadioButton negateRedRadioButton = new JRadioButton("negate red");
        JRadioButton negateGreebRadioButton = new JRadioButton("negate green");
        JRadioButton negateBlueRadioButton = new JRadioButton("negate blue");
        //button to confirm changes
        JButton confirmButton = new JButton("confirm");

        //config panel components
        negateRedRadioButton.setSelected(false);
        negateGreebRadioButton.setSelected(false);
        negateBlueRadioButton.setSelected(false);

        JPanel toolConfigPanel = new JPanel(new GridLayout(3,1));
        toolConfigPanel.setPreferredSize(new Dimension(150,300));
        toolConfigPanel.add(negateRedRadioButton);
        toolConfigPanel.add(negateGreebRadioButton);
        toolConfigPanel.add(negateBlueRadioButton);

        String titleText = String.format("<html><div WIDTH=%d>%s</div></html>", 100, "negate one or more color channels");
        Font titleFont = new Font(titleLable.getFont().getName(),
                titleLable.getFont().getStyle(),18);
        titleLable.setFont(titleFont);
        titleLable.setText(titleText);
        //add components to the panel
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
        //panels
        defaultToolUtilityPanel();
        //title label
        JLabel titleLable = new JLabel();
        //button to confirm changes
        JButton confirmButton = new JButton("confirm");

        //config panel components
        String titleText = String.format("<html><div WIDTH=%d>%s</div></html>", 100, "greyscale the image");
        Font titleFont = new Font(titleLable.getFont().getName(),
                titleLable.getFont().getStyle(),18);
        titleLable.setFont(titleFont);
        titleLable.setText(titleText);

        //add components to the panel
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
        //panels
        defaultToolUtilityPanel();
        //title label
        JLabel titleLable = new JLabel();
        //label, button and panel to display the currently selected color to replace
        JLabel colorToReplaceLabel = new JLabel();
        JButton colorToReplaceButton = new JButton("update color to replace");
        JPanel replaceColorPanel = new JPanel(); //displays the replace color
        //label, button and panel to display the currently selected color to set
        JLabel colorToSetLabel = new JLabel();
        JButton colorToSetButton = new JButton("select color to set");
        JPanel setColorPanel = new JPanel(); //displays the set color
        //tolerance slider
        JSlider toleranceSlider = new JSlider(1,255,15);
        JLabel toleranceLabel = new JLabel("tolerance (0-255):"  + toleranceSlider.getValue());
        //button to confirm changes
        JButton confirmButton = new JButton("confirm");

        //config panel components
        String titleText = String.format("<html><div WIDTH=%d>%s</div></html>", 100, "replace color with color");
        Font titleFont = new Font(titleLable.getFont().getName(),
                titleLable.getFont().getStyle(),18);
        titleLable.setFont(titleFont);
        titleLable.setText(titleText);

        JPanel colorToReplacePanel = new JPanel();
        colorToReplaceLabel.setText("color to replace");
        replaceColorPanel.setBackground(PictureExplorer.selectedPixelColor);
        colorToReplacePanel.add(BorderLayout.WEST, colorToReplaceLabel);
        colorToReplacePanel.add(BorderLayout.EAST, replaceColorPanel);
        colorToReplacePanel.add(BorderLayout.SOUTH, colorToReplaceButton);

        JPanel colorToSetPanel = new JPanel();
        colorToSetLabel.setText("color to set");
        colorToSetPanel.add(BorderLayout.WEST, colorToSetLabel);
        colorToSetPanel.add(BorderLayout.EAST, setColorPanel);
        colorToSetPanel.add(BorderLayout.SOUTH, colorToSetButton);

        JPanel tolerancePanel = new JPanel(new GridLayout(2,1));
        tolerancePanel.add(toleranceLabel);
        tolerancePanel.add(toleranceSlider);

        JPanel toolConfigPanel = new JPanel(new GridLayout(3,1));
        toolConfigPanel.setPreferredSize(new Dimension(150,200));
        toolConfigPanel.add(colorToReplacePanel);
        toolConfigPanel.add(colorToSetPanel);
        toolConfigPanel.add(tolerancePanel);

        confirmButton.setEnabled(false);
        //add components to the panel
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
    public void filtersToolUtilityPanel(int tool)
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
        //panels
        defaultToolUtilityPanel();
        //title label
        JLabel titleLable = new JLabel();
        //algorithm selection comboBox
        JComboBox<String> algorithmSelection = new JComboBox<String>(new String[]{"algorithm 1", "algorithm 2", "algorithm 3"});
        JLabel explainationLabel = new JLabel(String.format("<html><div WIDTH=%d>%s</div></html>", 150, "algorithm 1 is much slower, but a higher resolution. Algorithm 2 is much faster, but can sometimes appear \"fuzzy\". Algorithm 3 fills area between edges, useful for making light text show up better against a plain background"));
        //tolerance slider
        JSlider toleranceSlider = new JSlider(1,255,5);
        JLabel toleranceLabel = new JLabel("tolerance (0-255):"  + toleranceSlider.getValue());
        //iterations text box
        NumberFormatter iterationsFormat = new NumberFormatter(NumberFormat.getNumberInstance());
        JFormattedTextField iterationsField = new JFormattedTextField(iterationsFormat);
        JLabel iterationsLabel = new JLabel("iterations: '1");
        //auto tolerance radiobutton
        JCheckBox autoTolerance = new JCheckBox(String.format("<html><div WIDTH=%d>%s</div></html>", 100, "automatically find a decent tolerance?"));
        //button to confirm changes
        JButton confirmButton = new JButton("confirm");
        //checkbox, toggle grayscaling
        JCheckBox grayscaleCheckBox = new JCheckBox("grayscale output?");

        //config panel components
        String titleText = String.format("<html><div WIDTH=%d>%s</div></html>", 100, "detect edges with 1 of 3 algorithms");
        Font titleFont = new Font(titleLable.getFont().getName(),
                titleLable.getFont().getStyle(),18);
        titleLable.setFont(titleFont);
        titleLable.setText(titleText);

        explainationLabel.setFont(new Font(explainationLabel.getFont().getName(),explainationLabel.getFont().getStyle(),9));

        JPanel algorithmPanels = new JPanel(new GridLayout(2,1));
        iterationsField.setValue(1);
        iterationsField.setColumns(10);
        algorithmPanels.add(iterationsLabel);
        algorithmPanels.add(iterationsField);
        algorithmPanels.setVisible(false);
        autoTolerance.setVisible(false);

        algorithmSelection.setSelectedIndex(0);
        JPanel algorithmSelectionPanel = new JPanel();
        algorithmSelectionPanel.add(BorderLayout.NORTH, algorithmSelection);
        algorithmSelectionPanel.add(BorderLayout.SOUTH, algorithmPanels);
        algorithmSelectionPanel.add(BorderLayout.SOUTH, autoTolerance);
        algorithmSelectionPanel.add(BorderLayout.SOUTH, explainationLabel);

        grayscaleCheckBox.setVisible(false);
        grayscaleCheckBox.setSelected(false);
        JPanel tolerancePanel = new JPanel(new GridLayout(3,1));
        tolerancePanel.add(grayscaleCheckBox);
        tolerancePanel.add(toleranceLabel);
        tolerancePanel.add(toleranceSlider);

        JPanel toolConfigPanel = new JPanel(new GridLayout(2,1));
        toolConfigPanel.setPreferredSize(new Dimension(150,300));
        toolConfigPanel.add(algorithmSelectionPanel);
        toolConfigPanel.add(tolerancePanel);

        //add components to the panel
        this.add(BorderLayout.NORTH, titleLable);
        this.add(BorderLayout.CENTER, toolConfigPanel);
        this.add(BorderLayout.SOUTH, confirmButton);

        //handle the iterations field
        iterationsField.addPropertyChangeListener("value", new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent e) {
                    iterationsLabel.setText("iterations: '" + (int) ((Number)iterationsField.getValue()).doubleValue() );
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
                        toleranceLabel.setText("tolerance: " + toleranceSlider.getValue());
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
                            edged.edgeDetection2(toleranceSlider.getValue(), Integer.parseInt(iterationsLabel.getText().substring(iterationsLabel.getText().indexOf("'")+1)));
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
                    algorithmPanels.setVisible(false);
                    explainationLabel.setVisible(true);
                    autoTolerance.setVisible(false);
                    autoTolerance.setSelected(false);
                    grayscaleCheckBox.setVisible(false);

                    if (algorithmSelection.getSelectedIndex() == 1) { //add the things for the second edge detection algorithm
                        algorithmPanels.setVisible(true);
                        explainationLabel.setVisible(false);
                        autoTolerance.setVisible(false);
                        autoTolerance.setSelected(false);
                        grayscaleCheckBox.setVisible(false);
                    } else if (algorithmSelection.getSelectedIndex() == 2) {
                        algorithmPanels.setVisible(false);
                        explainationLabel.setVisible(false);
                        autoTolerance.setVisible(true);
                        grayscaleCheckBox.setVisible(true);
                    }
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

    /**
     * method to handle the grayscale tool panel,
     * as well as ActionEvents having to do with the UI of this panel
     */
    private void brightenToolPanel() 
    {
        //panels
        defaultToolUtilityPanel();
        //title label
        JLabel titleLable = new JLabel();
        //button to confirm changes
        JButton confirmButton = new JButton("confirm");

        //config panel components
        String titleText = String.format("<html><div WIDTH=%d>%s</div></html>", 100, "brighten the image");
        Font titleFont = new Font(titleLable.getFont().getName(),
                titleLable.getFont().getStyle(),18);
        titleLable.setFont(titleFont);
        titleLable.setText(titleText);

        //add components to the panel
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
        //panels
        defaultToolUtilityPanel();
        //title label
        JLabel titleLable = new JLabel();
        //button to confirm changes
        JButton confirmButton = new JButton("confirm");

        //config panel components
        String titleText = String.format("<html><div WIDTH=%d>%s</div></html>", 100, "darken the image");
        Font titleFont = new Font(titleLable.getFont().getName(),
                titleLable.getFont().getStyle(),18);
        titleLable.setFont(titleFont);
        titleLable.setText(titleText);

        //add components to the panel
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
     * method to handle the edge detection tool panel,
     * as well as ActionEvents having to do with the UI of this panel
     */
    private void simplifyColorToolPanel() 
    {
        //panels
        defaultToolUtilityPanel();
        //title label
        JLabel titleLable = new JLabel();
        //button to confirm changes
        JButton confirmButton = new JButton("confirm");
        //combo box to select the mode   0 = equidistant from color wheel, 1 = grayscale, 2 = faithful, 3 = faithful+, 4= balance, 5=balance+
        JComboBox<String> modeSelection = new JComboBox<String>(new String[]{"rainbow", "grayscale", "faithful", "faithful+", "balance", "balance+", "SD+mean", "Zed", "Zed+"});

        //config panel components
        String titleText = String.format("<html><div WIDTH=%d>%s</div></html>", 100, "simplify image to 5 colors");
        Font titleFont = new Font(titleLable.getFont().getName(),
                titleLable.getFont().getStyle(),18);
        titleLable.setFont(titleFont);
        titleLable.setText(titleText);

        modeSelection.setSelectedIndex(0);
        JPanel toolConfigPanel = new JPanel();
        toolConfigPanel.setPreferredSize(new Dimension(150,300));
        toolConfigPanel.add(BorderLayout.NORTH, new JLabel(String.format("<html><div WIDTH=%d>%s</div></html>", 100,"this method simplifies an image to five colors depending on the selected algorithm")));
        toolConfigPanel.add(BorderLayout.SOUTH, modeSelection);

        //add components to the panel
        this.add(BorderLayout.NORTH, titleLable);
        this.add(BorderLayout.CENTER, toolConfigPanel);
        this.add(BorderLayout.SOUTH, confirmButton);

        //handle confirm button press
        confirmButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    //use one of the edgeDetection methods from the Picture class
                    Picture simplified = new Picture(PictureExplorer.picture.getHeight(), PictureExplorer.picture.getWidth());
                    simplified.copyPicture(new SimplePicture(PictureExplorer.picture.getBufferedImage()));

                    //simplify image with user selected mode
                    simplified.simplifyColors(modeSelection.getSelectedIndex());

                    //save the new image (ask first)
                    PictureExplorer.pictureConfirmation.updateConfPanelImage(simplified);
                }
            });

    }

    ///////////////next tool type (idk, image manipulation, fun, some other groups)///////////////
}
