package classes;

import java.awt.*;
//import java.awt.font.*;
//import java.awt.geom.*;
import java.awt.image.BufferedImage;
//import java.text.*;
import java.util.*;
//import java.util.List; // resolves problem with java.awt.List and java.util.List
import java.util.ArrayList;

/**
 * A class that represents a picture. This class inherits from SimplePicture and
 * allows the student to add functionality to the Picture class. based on work
 * by Barbara Ericson ericson@cc.gatech.edu
 * 
 * @author Anthony Michael
 */
public class Picture extends SimplePicture {
    ///////////////////// constructors //////////////////////////////////

    /**
     * Constructor that takes no arguments
     */
    public Picture() {
        /*
         * not needed but use it to show the implicit call to super() child constructors
         * always call a parent constructor
         */
        super();
    } // Picture

    /**
     * Constructor that takes a file name and creates the picture
     * 
     * @param fileName the name of the file to create the picture from
     */
    public Picture(String fileName) {
        // let the parent class handle this fileName
        super(fileName);
    } // Picture

    /**
     * Constructor that takes the width and height
     * 
     * @param height the height of the desired picture
     * @param width  the width of the desired picture
     */
    public Picture(int height, int width) {
        // let the parent class handle this width and height
        super(width, height);
    } // Picture

    /**
     * Constructor that takes a picture and creates a copy of that picture
     * 
     * @param copyPicture the picture to copy
     */
    public Picture(Picture copyPicture) {
        // let the parent class do the copy
        super(copyPicture);
    } // Picture
    /**
     * Constructor that takes a simple picture and creates a copy of that picture
     * 
     * @param copyPicture the SimplePicture to copy
     */
    public Picture(SimplePicture copyPicture) {
        // let the parent class do the copy
        super(copyPicture);
    }

    /**
     * Constructor that takes a buffered image
     * 
     * @param image the buffered image to use
     */
    public Picture(BufferedImage image) {
        super(image);
    } // Picture
    
    
    ///////////////////// utility methods //////////////////////////////////
    
    
    ////////////////////// methods added for the image manipulation GUI
    ////////////////////// //////////////////////
    /**
     * remove, remove one or more color channels by setting the values to 0
     * 
     * @param removeRed   whether or not to remove Red
     * @param removeGreen whether or not to remove Green
     * @param removeBlue  whether or not to remove Blue
     */
    public void removeColorChannels(boolean removeRed, boolean removeGreen, boolean removeBlue) {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                if (removeRed) {
                    pixelObj.setRed(0);
                } // if
                if (removeGreen) {
                    pixelObj.setGreen(0);
                } // if
                if (removeBlue) {
                    pixelObj.setBlue(0);
                } // if
            } // for
        } // for
        pixels = null;
        return;
    } // removeColorChannels

    /**
     * Trim, trims a color channel to be in between the passed min and max values
     * 
     * @param color an integer representing the color to be trimmed (0 = red; 1 =
     *              green; 2 = blue)
     * @param min   the minimum value, any color values below this will be increased
     *              to this value
     * @param max   the maximum value, any color values above this will be decreased
     *              to this value
     */
    public void trimColor(int color, int min, int max) {
        Pixel[][] pixels = this.getPixels2D();
        int i;
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                switch (color) {
                    case 0: // trim red
                        i = pixelObj.getRed();
                        if (i < min)
                            pixelObj.setRed(min);
                        else if (i > max)
                            pixelObj.setRed(max);
                        break;
                    case 1: // trim green
                        i = pixelObj.getGreen();
                        if (i < min)
                            pixelObj.setGreen(min);
                        else if (i > max)
                            pixelObj.setGreen(max);
                        break;
                    case 2: // trim blue
                        i = pixelObj.getBlue();
                        if (i < min)
                            pixelObj.setBlue(min);
                        else if (i > max)
                            pixelObj.setBlue(max);
                        break;
                } // switch-case
            } // for
        } // for
        pixels = null;
        return;
    } // trimColor

    /**
     * Negate, negates one or more color channels by setting the values to 255 -
     * current value
     * 
     * @param negateRed   whether or not to negate Red
     * @param negateGreen whether or not to negate Green
     * @param negateBlue  whether or not to negate Blue
     */
    public void negateColorChannels(boolean negateRed, boolean negateGreen, boolean negateBlue) {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                if (negateRed) {
                    pixelObj.setRed(255 - pixelObj.getRed());
                } // if
                if (negateGreen) {
                    pixelObj.setGreen(255 - pixelObj.getGreen());
                } // if
                if (negateBlue) {
                    pixelObj.setBlue(255 - pixelObj.getBlue());
                } // if
            } // for
        } // for
        pixels = null;
        return;
    } // negateColorChannels

    /**
     * method to put the image in grayscale by setting the red green and blue values
     * to the average of the 3
     */
    public void grayscale() {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                double average = (pixelObj.getRed() + pixelObj.getGreen() + pixelObj.getBlue()) / 3;
                pixelObj.setRed((int) average);
                pixelObj.setGreen((int) average);
                pixelObj.setBlue((int) average);
            } // for
        } // for
        pixels = null;
        return;
    } // grayscale

    /**
     * relaceColorWithColor, parses the picture, setting all pixels of the passed
     * colorToReplace to the passed colorToSet
     * 
     * @param colorToReplace the color to replace
     * @param colorToSet     the color to replace with
     * @param tolerance      (FUTURE) will allow the user to set a tolerance,
     *                       replacing all colors within this distance of
     *                       colorToReplace
     */
    public void replaceColorWithColor(Color colorToReplace, Color colorToSet, int tolerance) {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                if (Pixel.colorDistance(pixelObj.getColor(), colorToReplace) <= tolerance) {
                    pixelObj.setRed(colorToSet.getRed());
                    pixelObj.setGreen(colorToSet.getGreen());
                    pixelObj.setBlue(colorToSet.getBlue());
                } // if
            } // for
        } // for
        pixels = null;
        return;
    } // replaceColorWithColor

    /**
     * brightens an image
     */
    public void brighten() {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                pixelObj.setColor(pixelObj.getColor().brighter());
            } // for
        } // for
        pixels = null;
        return;
    } // brighten

    /**
     * darkens an image
     */
    public void darken() {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                pixelObj.setColor(pixelObj.getColor().darker());
            } // for
        } // for
        pixels = null;
        return;
    } // darken

    /**
     * this method simplifies an image to five colors, how the colors are chosen
     * depend on the algorithm choosen uses the 5 number summary of all the pixels
     * color values (as integers) to balance the image better
     * 
     * @param mode which mode to use, 0 = equidistant from color wheel, 1 = grayscale, 2 = faithful, 3 = faithful+, 4 = balance, 5 = balance+, 6 = SD+mean, 7 = Zed, 8 = Zed+
     */
    /*
     0 = equidistant from color wheel 
     1 = 6, but grayscale
     2 = faithful: numbers are the 5 number summary of allpixel's colors, sorted by their integer representations 
     3 = faithful+: 2, but duplicate values are removed 
     4 = balance: numbers are the 5 number summary of all pixel's colors, sorted by their hues 
     5 = balance+: 4, but duplicate values are removed 
     6 = SD+mean: numbers are based off of the mean and standard deviation of the RGB color channels 
     7 = Zed: numbers are the 5 number summary of all pixel's colors, sorted by their z-scores 
     8 = Zed+: 7, but duplicate values are removed
     */
    public void simplifyColors(int mode) {
        // data
        Pixel currentPixel = null;
        Pixel[][] pixels = this.getPixels2D();
        ArrayList<Integer> pictureColors = new ArrayList<Integer>();
        Color background = pixels[0][0].getColor();
        float[] hsbValues = new float[3];
        // variables for 5 number summaries
        int min = 0, q1 = 0, med = 0, q3 = 0, max = 0;
        Color[] fiveNumSumColors = new Color[5];
        Set<Integer> colorSet;
        // 5 colors
        Color[] colors = new Color[5];

        // if the background colors saturation, or brightness is really low, increase it
        /*
         * if (true) {
         * Color.RGBtoHSB(background.getRed(),background.getGreen(),background.getBlue()
         * , hsbValues); if (Math.abs(hsbValues[1]) < 0.3) { hsbValues[1] = 0.3f; } if
         * (Math.abs(hsbValues[2]) < 0.3) { hsbValues[2] = 0.3f; } background =
         * Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2]); }
         */

        // get an array list of the integer representations of all the pixels color
        // values
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                pictureColors.add(pixelObj.getColor().getRGB());
            } // for
        } // for

        // generate 5 number summary
        // do something different depending on the mode
        switch (mode) {
            /** faithful+ */
            case 3:
                // find 5 num sum, this time summarize the occurances of each color rather than
                // the colors themselves
                // remove duplicates from picture colors using a hashset
                // Create a new LinkedHashSet
                colorSet = new LinkedHashSet<>();
                // Add The elements to set
                colorSet.addAll(pictureColors);
                // Clear the list
                pictureColors.clear();
                // add the elements of set
                // with no duplicates to the list
                pictureColors.addAll(colorSet);
                // sort the array
                Collections.sort(pictureColors);

                // find the five num sum
                // find the median
                med = pictureColors.get((int) (pictureColors.size() / 2.0));
                // find the min and max
                min = pictureColors.get(0);
                max = pictureColors.get(pictureColors.size() - 1);
                // find q1 and q3
                q1 = pictureColors.get((int) (pictureColors.size() / 4.0));
                q3 = pictureColors.get((int) (pictureColors.size() * (3.0 / 5.0)));
            break;

            /** balance+ */
            case 5:
                // find 5 num sum, this time summarize the occurances of each color rather than
                // the colors themselves
                // remove duplicates from picture colors using a hashset
                // *// Create a new LinkedHashSet
                colorSet = new LinkedHashSet<>();
                // Add The elements to set
                colorSet.addAll(pictureColors);
                // Clear the list
                pictureColors.clear();
                // add the elements of set
                // with no duplicates to the list
                pictureColors.addAll(colorSet);

            
            /** balance */
            case 4:
                // sort the array
                Collections.sort(pictureColors, new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        // DATA
                        Color c1 = new Color(o1);
                        int[] c1HSB = getHSV(c1.getRed(), c1.getGreen(), c1.getBlue());
                        Color c2 = new Color(o2);
                        int[] c2HSB = getHSV(c2.getRed(), c2.getGreen(), c2.getBlue());

                        // rank by hue
                        return (int) (c1HSB[0] * 255) - (int) (c2HSB[0] * 255);
                    } // public int compare

                    public int[] getHSV(int r, int g, int b) {
                        float cmax = Math.max(r, Math.max(g, b));
                        float cmin = Math.min(r, Math.min(g, b));
                        float deltaC = cmax - cmin;
                        float h;
                        if (cmax == r) {
                            h = (((g - b) / deltaC) % 6) * 60;
                        } else if (cmax == g) {
                            h = (((b - r) / deltaC) + 2) * 60;
                        } else {
                            h = (((r - g) / deltaC) + 4) * 60;
                        } // if else-if else
                        float s = 0;
                        if (cmax != 0) {
                            s = deltaC / cmax;
                        } // if
                        int[] returnValue = new int[3];
                        // re-scale
                        returnValue[0] = (int) h / 255;
                        returnValue[1] = (int) s / 255;
                        returnValue[2] = (int) cmax / 255;
                        return returnValue;
                    } // public int[] getHSV
                });
                // find the five num sum
                // find the median
                med = pictureColors.get((int) (pictureColors.size() / 2.0));
                // find the min and max
                min = pictureColors.get(0);
                max = pictureColors.get(pictureColors.size() - 1);
                // find q1 and q3
                q1 = pictureColors.get((int) (pictureColors.size() / 4.0));
                q3 = pictureColors.get((int) (pictureColors.size() * (3.0 / 5.0)));
            break;
            
            /** grayscale */
            case 1:
            /** SD+mean */
            case 6:
                // find mean and standard deviation for each color channel, then create colors
                // with that
                float meanRed = 0, meanGreen = 0, meanBlue = 0;
                double sdRed = 0, sdGreen = 0, sdBlue = 0;

                // find mean of each color channel
                for (int color : pictureColors) {
                    Color c = new Color(color);
                    // sum each color channel
                    meanRed += c.getRed();
                    meanGreen += c.getGreen();
                    meanBlue += c.getBlue();
                } // for
                  // divide by total
                meanRed /= pictureColors.size();
                meanGreen /= pictureColors.size();
                meanBlue /= pictureColors.size();

                // find standard deviation of each color channel sqrt( sum( (x-mean)^2 ) / n )
                // sum((x-mean)^2)
                for (int color : pictureColors) {
                    Color c = new Color(color);
                    // (x-mean)^2
                    sdRed += Math.pow(c.getRed() - meanRed, 2);
                    sdGreen += Math.pow(c.getGreen() - meanGreen, 2);
                    sdBlue += Math.pow(c.getBlue() - meanBlue, 2);
                } // for
                  // /n
                sdRed /= pictureColors.size();
                sdGreen /= pictureColors.size();
                sdBlue /= pictureColors.size();
                // sqrt
                sdRed = Math.sqrt(sdRed);
                sdGreen = Math.sqrt(sdGreen);
                sdBlue = Math.sqrt(sdBlue);
                
                // print out means and SDs for debugging, remove this later
                //System.out.println("Means:\nred: " + meanRed + "; green " + meanGreen + "; blue " + meanBlue);
                //System.out.println("Standard Deviations:\nred: " + sdRed + "; green " + sdGreen + "; blue " + sdBlue);
                
                
                // create 5 colors with mean and sd
                // mean
                med = (new Color((int) (meanRed), (int) (meanGreen), (int) (meanBlue))).getRGB();
                // +- 0.5 Standard Deviations
                q1 = (new Color(
                    Math.abs((int) (meanRed - 0.5*sdRed)%255), 
                    Math.abs((int) (meanGreen - 0.5*sdGreen)%255), 
                    Math.abs((int) (meanBlue - 0.5*sdBlue)%255))
                    ).getRGB();
                q3 = (new Color(
                    Math.abs((int) (meanRed + 0.5*sdRed)%255), 
                    Math.abs((int) (meanGreen + 0.5*sdGreen)%255), 
                    Math.abs((int) (meanBlue + 0.5*sdBlue)%255))
                    ).getRGB();
                // +- 1 Standard Deviations
                min = (new Color(
                    Math.abs((int) (meanRed - sdRed)%255), 
                    Math.abs((int) (meanGreen - sdGreen)%255), 
                    Math.abs((int) (meanBlue - sdBlue)%255))
                    ).getRGB();
                max = (new Color(
                    Math.abs((int) (meanRed + sdRed)%255), 
                    Math.abs((int) (meanGreen + sdGreen)%255), 
                    Math.abs((int) (meanBlue + sdBlue)%255))
                    ).getRGB();
                        
            break;
            
            /** Zed+ */
            case 7:
                // remove duplicates from picture colors using a hashset
                // *// Create a new LinkedHashSet
                colorSet = new LinkedHashSet<>();
                // Add The elements to set
                colorSet.addAll(pictureColors);
                // Clear the list
                pictureColors.clear();
                // add the elements of set
                // with no duplicates to the list
                pictureColors.addAll(colorSet);
            /** Zed */
            case 8:
                //DATA
                float sum=0.0f;
                double sumXMinusMeanSquared=0.0;

                //find mean and standard deviation
                //mean
                for (int c : pictureColors) {
                    sum += c;
                }//for
                final float mean = sum/pictureColors.size(); //made final so the lamda function can use it

                //standard deviation sqrt( sum( (x-mean)^2 ) / n )
                // sum((x-mean)^2)
                for (int c : pictureColors) {
                    // (x-mean)^2
                    sumXMinusMeanSquared += Math.pow(c-mean,2);
                }//for
                final double sd = Math.sqrt(sumXMinusMeanSquared/pictureColors.size()); //made final so the lamda function can use it

                //sort by z-score
                Collections.sort(pictureColors, new Comparator<Integer>() {
                    @Override
                    public int compare(Integer c1, Integer c2) {
                        // DATA
                        double z1,z2;

                        //find z scores of each   z=(x-mean)/SD;
                        z1=(c1-mean)/sd;
                        z2=(c2-mean)/sd;
                        
                        //do abs value
                        z1 = Math.abs(z1);
                        z2 = Math.abs(z2);

                        // rank by z-score
                        if (z1 > z2) return 1;
                        else if (z1 < z2) return -1;
                        else return 0;
                    } // public int compare
                });

                //5 number summary
                // find the median
                med = pictureColors.get((int) (pictureColors.size() / 2.0));
                // find the min and max
                min = pictureColors.get(0);
                max = pictureColors.get(pictureColors.size() - 1);
                // find q1 and q3
                q1 = pictureColors.get((int) (pictureColors.size() / 4.0));
                q3 = pictureColors.get((int) (pictureColors.size() * (3.0 / 5.0)));

            break;
        
            /** others */
            default:
                // sort the array
                Collections.sort(pictureColors);
                // find the median
                med = pictureColors.get((int) (pictureColors.size() / 2.0));
                // find the min and max
                min = pictureColors.get(0);
                max = pictureColors.get(pictureColors.size() - 1);
                // find q1 and q3
                q1 = pictureColors.get((int) (pictureColors.size() / 4.0));
                q3 = pictureColors.get((int) (pictureColors.size() * (3.0 / 5.0)));
            break;
        } // switch-case
        fiveNumSumColors[0] = new Color(min);
        fiveNumSumColors[1] = new Color(q1);
        fiveNumSumColors[2] = new Color(med);
        fiveNumSumColors[3] = new Color(q3);
        fiveNumSumColors[4] = new Color(max);

        // generate colors
        // do something different depending on the mode
        switch (mode) {
            /* equidistant */
            case 0:
                // generate the colors
                hsbValues = Color.RGBtoHSB(background.getRed(), background.getGreen(), background.getBlue(), hsbValues);
                //change the brightness
                for (int i = 0; i < colors.length; i++) {
                    colors[i] = Color.getHSBColor((float) (hsbValues[0] + (0.2 * i)) % 1, hsbValues[1], hsbValues[2]);
                } // for
            break;

            /* others */
            default:
                // generate the colors
                colors = fiveNumSumColors;
            break;
        } // switch-case

        // clear the colors ArrayList to save some memory or something
        pictureColors.clear();

        // recolor image
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                currentPixel = pixels[row][col];

                // parse the fiveNumSum colors and compare to background
                int fnsBestIndex = 0;
                for (int e = 1; e < fiveNumSumColors.length; e++) {
                    if (currentPixel.colorDistance(fiveNumSumColors[e]) < currentPixel
                            .colorDistance(fiveNumSumColors[fnsBestIndex])) {
                        fnsBestIndex = e;
                    } // if
                } // for

                currentPixel.setColor(colors[fnsBestIndex]);
            } // for
        } // for

        // special operations
        // some modes have other filters applied after simplification, do that here
        switch (mode) {
            case 1:
                this.grayscale();
            break; // grayscale mode
            default:
            break;
        } // switch-case
        return;
    } // simplifyColors
      ////////////////////// methods ///////////////////////////////////////

    /**
     * Method to return a string with information about this picture.
     * 
     * @return a string with information about the picture such as fileName, height
     *         and width.
     */
    public String toString() {
        String output = "Picture, filename " + getFileName() + " height " + getHeight() + " width " + getWidth();
        return output;

    } // toString

    /**
     * Method that mirrors the picture around a vertical mirror in the center of the
     * picture from left to right
     */
    public void mirrorVertical() {
        Pixel[][] pixels = this.getPixels2D();
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < width / 2; col++) {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][width - 1 - col];
                rightPixel.setColor(leftPixel.getColor());
            } // for
        } // for
        pixels = null;
        return;
    } // mirrorVertical

    /**
     * copy from the passed fromPic to the specified startRow and startCol in the
     * current picture
     * 
     * @param fromPic  the picture to copy from
     * @param startRow the start row to copy to
     * @param startCol the start col to copy to
     */
    public void copy(Picture fromPic, int startRow, int startCol) {
        Pixel fromPixel = null;
        Pixel toPixel = null;
        Pixel[][] toPixels = this.getPixels2D();
        Pixel[][] fromPixels = fromPic.getPixels2D();
        for (int fromRow = 0, toRow = startRow; fromRow < fromPixels.length
                && toRow < toPixels.length; fromRow++, toRow++) {
            for (int fromCol = 0, toCol = startCol; fromCol < fromPixels[0].length
                    && toCol < toPixels[0].length; fromCol++, toCol++) {
                fromPixel = fromPixels[fromRow][fromCol];
                toPixel = toPixels[toRow][toCol];
                toPixel.setColor(fromPixel.getColor());
            } // for
        } // for
        toPixels = null;
        fromPixels = null;
        return;
    } // copy

    /**
     * Method to show large changes in color
     * 
     * @param edgeDist the distance for finding edges
     */
    public void edgeDetection(int edgeDist) {
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        Pixel bottomPixel = null;
        Pixel[][] pixels = this.getPixels2D();
        Color rightColor = null;
        Color bottomColor = null;
        for (int row = 0; row < pixels.length - 1; row++) {
            for (int col = 0; col < pixels[0].length - 1; col++) {
                leftPixel = pixels[row][col];
                bottomPixel = pixels[row + 1][col];
                bottomColor = bottomPixel.getColor();
                rightPixel = pixels[row][col + 1];
                rightColor = rightPixel.getColor();
                if (leftPixel.colorDistance(rightColor) > edgeDist)
                    leftPixel.setColor(Color.BLACK);
                else if (leftPixel.colorDistance(bottomColor) > edgeDist)
                    leftPixel.setColor(Color.BLACK);
                else
                    leftPixel.setColor(Color.WHITE);
            } // for
        } // for
        pixels = null;
        return;
    }// edgeDetection

    /*
     * Main method for testing - each class in Java can have a main method
     */
    public static void main(String[] args) {
        Picture beach = new Picture("beach.jpg");

        beach.explore();
        beach.negate();

        beach.explore();
        beach.grayscale();

        beach.explore();
        beach.mirrorVerticalRightToLeft();

        beach.explore();
        beach.mirrorHorizontal();

        beach.explore();
        beach.mirrorHorizontalBotToTop();

        beach.explore();
        beach.copy2(beach, 252, 454, 290, 600);

        beach.explore();
        beach.edgeDetection2(10, 2);

        beach.explore();
    }// main

    ///////////////////// things i added \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    /** Method to set red and green to zero */
    public void keepOnlyBlue() {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                pixelObj.setRed(0);
                pixelObj.setGreen(0);
            } // for
        } // for
        pixels = null;
        return;
    }// keepOnlyBlue

    /** method to set all rgb values to 255 - that rgb value */
    public void negate() {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                pixelObj.setRed(255 - pixelObj.getRed());
                pixelObj.setGreen(255 - pixelObj.getGreen());
                pixelObj.setBlue(255 - pixelObj.getBlue());
            } // for
        } // for
        pixels = null;
        return;
    }// negate

    /**
     * mirrors a picture around a mirror placed vertically from right to left.
     */
    public void mirrorVerticalRightToLeft() {
        Pixel[][] pixels = this.getPixels2D();
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++) {
            for (int col = width - 1; col >= width / 2; col--) {
                rightPixel = pixels[row][col];
                leftPixel = pixels[row][width - 1 - col];
                leftPixel.setColor(rightPixel.getColor());
            } // for
        } // for
        pixels = null;
        return;
    }// mirrorVerticalRightToLeft

    /**
     * mirrors a picture around a mirror placed horizontally from top to bottom.
     */
    public void mirrorHorizontal() {
        Pixel[][] pixels = this.getPixels2D();
        Pixel topPixel = null;
        Pixel bottomPixel = null;
        int height = pixels.length;
        for (int col = 0; col < pixels[0].length; col++) {
            for (int row = 0; row < height / 2; row++) {
                topPixel = pixels[row][col];
                bottomPixel = pixels[height - 1 - row][col];
                bottomPixel.setColor(topPixel.getColor());
            } // for
        } // for
        pixels = null;
        return;
    }// mirrorHorizontal

    /**
     * mirrors a picture around a mirror placed horizontally from bottom to top.
     */
    public void mirrorHorizontalBotToTop() {
        Pixel[][] pixels = this.getPixels2D();
        Pixel bottomPixel = null;
        Pixel topPixel = null;
        int height = pixels.length;
        for (int col = 0; col < pixels[0].length; col++) {
            for (int row = height - 1; row >= height / 2; row--) {
                bottomPixel = pixels[row][col];
                topPixel = pixels[height - 1 - row][col];
                topPixel.setColor(bottomPixel.getColor());
            } // for
        } // for
        pixels = null;
        return;
    }// mirrorHorizontalBotToTop

    /**
     * copy from the passed fromPic to the specified startRow and startCol in the
     * current picture
     * 
     * @param fromPic  the picture to copy from
     * @param startRow the start row to copy to
     * @param startCol the start col to copy to
     * @param endRow   the end row to copy to
     * @param endCol   the end col to copy to
     */
    public void copy2(Picture fromPic, int startRow, int startCol, int endRow, int endCol) {
        Pixel fromPixel = null;
        Pixel toPixel = null;
        Pixel[][] toPixels = this.getPixels2D();
        Pixel[][] fromPixels = fromPic.getPixels2D();
        for (int fromRow = 0, toRow = startRow; fromRow < fromPixels.length && toRow < endRow; fromRow++, toRow++) {
            for (int fromCol = 0, toCol = startCol; fromCol < fromPixels[0].length
                    && toCol < endCol; fromCol++, toCol++) {
                fromPixel = fromPixels[fromRow][fromCol];
                toPixel = toPixels[toRow][toCol];
                toPixel.setColor(fromPixel.getColor());
            } // for
        } // for
        toPixels = null;
        fromPixels = null;
        return;
    }// copy2

    /**
     * Method to show large changes in color in a more robust fashion
     * 
     * @param edgeDist the distance for finding edges
     */
    public void edgeDetection2(int edgeDist, int runs) {
        Pixel currentPixel = null; // the pixel being tested
        // the pixels to test, n, ne, e, se, e, sw, w, nw
        Pixel nPixel = null;
        Pixel nePixel = null;
        Pixel ePixel = null;
        Pixel sePixel = null;
        Pixel sPixel = null;
        Pixel swPixel = null;
        Pixel wPixel = null;
        Pixel nwPixel = null;

        Pixel[][] pixels = this.getPixels2D();
        // the colors of the pixels being tested
        Color nColor = null;
        Color neColor = null;
        Color eColor = null;
        Color seColor = null;
        Color sColor = null;
        Color swColor = null;
        Color wColor = null;
        Color nwColor = null;

        // how many times to run the code :D
        for (int i = 0; i < runs; i++) {
            for (int row = 1; row < pixels.length - 1; row += 3) {
                for (int col = 1; col < pixels[0].length - 1; col += 3) {
                    // fill current pixel
                    currentPixel = pixels[row][col];
                    // fill all the other pixels around it
                    nPixel = pixels[row + 1][col];
                    nePixel = pixels[row + 1][col + 1];
                    ePixel = pixels[row][col + 1];
                    sePixel = pixels[row - 1][col + 1];
                    sPixel = pixels[row - 1][col];
                    swPixel = pixels[row - 1][col - 1];
                    wPixel = pixels[row][col - 1];
                    nwPixel = pixels[row + 1][col - 1];
                    // fill in the colors
                    nColor = nPixel.getColor();
                    neColor = nePixel.getColor();
                    eColor = ePixel.getColor();
                    seColor = sePixel.getColor();
                    sColor = sPixel.getColor();
                    swColor = swPixel.getColor();
                    wColor = wPixel.getColor();
                    nwColor = nwPixel.getColor();

                    // has an edge been found
                    boolean edgeFound = false;

                    // check each surrounding pixel
                    // check N
                    if (currentPixel.colorDistance(nColor) > edgeDist) {
                        edgeFound = true;
                        nPixel.setColor(Color.BLACK);
                    } else {
                        nPixel.setColor(Color.WHITE);
                    }
                    // check NE
                    if (currentPixel.colorDistance(neColor) > edgeDist) {
                        edgeFound = true;
                        nePixel.setColor(Color.BLACK);
                    } else {
                        nePixel.setColor(Color.WHITE);
                    }
                    // check E
                    if (currentPixel.colorDistance(eColor) > edgeDist) {
                        edgeFound = true;
                        ePixel.setColor(Color.BLACK);
                    } else {
                        ePixel.setColor(Color.WHITE);
                    }
                    // check SE
                    if (currentPixel.colorDistance(seColor) > edgeDist) {
                        edgeFound = true;
                        sePixel.setColor(Color.BLACK);
                    } else {
                        sePixel.setColor(Color.WHITE);
                    }
                    // check S
                    if (currentPixel.colorDistance(sColor) > edgeDist) {
                        edgeFound = true;
                        sPixel.setColor(Color.BLACK);
                    } else {
                        sPixel.setColor(Color.WHITE);
                    }
                    // check SW
                    if (currentPixel.colorDistance(swColor) > edgeDist) {
                        edgeFound = true;
                        swPixel.setColor(Color.BLACK);
                    } else {
                        swPixel.setColor(Color.WHITE);
                    }
                    // check W
                    if (currentPixel.colorDistance(wColor) > edgeDist) {
                        edgeFound = true;
                        wPixel.setColor(Color.BLACK);
                    } else {
                        wPixel.setColor(Color.WHITE);
                    }
                    // check NW
                    if (currentPixel.colorDistance(nwColor) > edgeDist) {
                        edgeFound = true;
                        nwPixel.setColor(Color.BLACK);
                    } else {
                        nwPixel.setColor(Color.WHITE);
                    }

                    // if an edge has been found, change the current pixel to black
                    if (edgeFound) {
                        currentPixel.setColor(Color.BLACK);
                    } else {
                        currentPixel.setColor(Color.WHITE);
                    }
                } // for
            } // for
        } // for
        pixels = null;
        return;
    }// edgeDetection2

    //////////////////////////////////////////////////////////// boldeners
    //////////////////////////////////////////////////////////// ////////////////////////////////////////////////////////////
    /**
     * Method to show large changes in color made for making light writing show up
     * better on a plain background this method should fill in lines
     * 
     * @param edgeDist the distance for finding edges
     */
    public void bolden(int edgeDist) {
        Pixel currentPixel = null;
        Pixel[][] pixels = this.getPixels2D();

        Color background = pixels[1][1].getColor();

        // complementary color scheme
        Color invertedBackground = new Color(255 - background.getRed(), 255 - background.getGreen(),
                255 - background.getBlue());

        // old: used to be used to debug
        // int countBack = 0;
        // int countInvertBack = 0;
        // int numPixels = pixels.length * pixels[0].length;

        // goes through picture again, darkens edges
        /////////// working function/////////////
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                currentPixel = pixels[row][col];
                if (Pixel.colorDistance(currentPixel.getColor(), background) > edgeDist) {
                    currentPixel.setColor(invertedBackground);
                    // countInvertBack++;
                } else {
                    currentPixel.setColor(background);
                    // countBack++;
                } // if else
            } // for
        } // for
        pixels = null;
        return;
    }// bolden

    /**
     * Method to show large changes in color made for making light writing show up
     * better on a plain background this method should fill in lines sets up and
     * handles bolden2Iter
     */
    public void bolden2() {
        int edgeDist = 5;

        Pixel currentPixel = null;
        Pixel[][] pixels = this.getPixels2D();

        Color background = pixels[1][1].getColor();
        // Color invertedBackground = new Color(255 - background.getRed(), 255 -
        // background.getGreen(), 255 - background.getBlue());

        int countBack = 0;
        int countInvertBack = 0;

        // goes through picture again, darkens edges
        /////////// working function/////////////
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                currentPixel = pixels[row][col];
                // optimization: change this to only count, not apply colors
                if (currentPixel.colorDistance(background) > edgeDist) {
                    // currentPixel.setColor(invertedBackground);
                    countInvertBack++;
                } else {
                    // currentPixel.setColor(background);
                    countBack++;
                } // if else
            } // for
        } // for

        int edgeDiff = Math.abs(countInvertBack - countBack);
        int newEdgeDist = 1;
        this.bolden2Iter(pixels, newEdgeDist, 1, edgeDist, edgeDiff, edgeDist, edgeDiff, 0);

        pixels = null;
        return;
    }// bolden2

    /**
     * Method to show large changes in color made for making light writing show up
     * better on a plain background should try and find an optimal tolerance for the
     * user this method should fill in lines
     * 
     * iterates to find the best tolerance should only ever be called in bolden2
     * 
     * @param pixels       the pixels for it to analyze
     * @param edgeDist     the distance for finding edges
     * @param i            iterations, used to prevent infinite loops
     * @param bestEdgeDist the value for edgeDist that, as of now, has produced the
     *                     best results
     * @param bestEdgeDiff the difference bewteen forground and background pixels in
     *                     the best edge
     * @param lastEdge     the last edgeDist used
     * @param lastDiff     the difference resulting from the lastEdge
     */
    private void bolden2Iter(Pixel[][] pixels, int edgeDist, int i, int bestEdgeDist, int bestEdgeDiff,
            int lastEdgeDist, int lastEdgeDiff, int x) {
        // optimization, have these bits just be passed by the handler, reduce ram
        // usage?
        // bruh, this optimization cut ram usage (for this tool) to like ... a fourth
        // what it was
        // Picture tempCopyPicture = new Picture(this);
        // Pixel[][] pixels = tempCopyPicture.getPixels2D();

        Pixel currentPixel = null;
        Color background = pixels[1][1].getColor();
        // Color invertedBackground = new Color(255 - background.getRed(), 255 -
        // background.getGreen(), 255 - background.getBlue());

        int countBack = 0;
        int countInvertBack = 0;

        // goes through picture again, darkens edges
        /////////// working function/////////////
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                currentPixel = pixels[row][col];
                // optimization 1: change this to only count, not apply colors
                if (currentPixel.colorDistance(background) > edgeDist) {
                    // currentPixel.setColor(invertedBackground);
                    countInvertBack++;
                } else {
                    // currentPixel.setColor(background);
                    countBack++;
                } // if else
            } // for
        } // for

        // iterate to find best edge dist
        if (i < 25) {
            int newEdgeDist = edgeDist + 10;

            int edgeDiff = Math.abs(countBack - countInvertBack);
            if (edgeDiff < bestEdgeDiff) {
                bestEdgeDiff = edgeDiff;
                bestEdgeDist = edgeDist;
            } // if

            if (newEdgeDist >= 255)
                newEdgeDist = 5;
            else if (newEdgeDist < 5)
                newEdgeDist = 255;

            this.bolden2Iter(pixels, newEdgeDist, i + 1, bestEdgeDist, bestEdgeDiff, edgeDist, edgeDiff, x);
            return;
        } // if
          // finally, actually apply the best edge dist
        else {
            pixels = null;
            this.bolden(bestEdgeDist);
        } // else
        return;
    }// bolden2Iter

    /** small hack, lets Picture use the getExtension method from SimplePicture */
    public String getExtension() {
        return super.getExtension();
    } // getExtension

} // this } is the end of class Picture, put all new methods before this