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

import classes.util.*;
import classes.util.kmeans.Centroid;
import classes.util.kmeans.EuclideanDistance;
import classes.util.kmeans.KMeans;
import classes.util.kmeans.Record;

import java.awt.*;
//import java.awt.font.*;
//import java.awt.geom.*;
import java.awt.image.BufferedImage;
//import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List
import java.util.ArrayList;

/**
 * A class that represents a picture. This class inherits from SimplePicture and
 * allows the student to add functionality to the Picture class. based on work
 * by Barbara Ericson ericson@cc.gatech.edu
 * 
 * @author Anthony Rubick
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
     * Constructor that takes a buffered image
     *    /**
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
     * simplifies an image to a given number of colors using the k-means clustering algorithm
     * @param k number of clusters
     * @param maxIterations maximum number of iterations of k-means to do
     */
    public void kMeansSimplify(int k, int maxIterations, boolean removeDuplicates) {
        //data
        // data
        Pixel currentPixel = null;
        Pixel[][] pixels = this.getPixels2D();
        List<Record<Float>> records = super.getPixelsRecordHSL(); //dataset of all pixels in the image
        Map<Centroid, List<Record<Float>>> clusters; //most prevalant color groups as returned by KMeans
        Color[] colors = new Color[k]; //most prevalent colors
        int i; //used as in a later for loop
        
        //remove duplicate records
        if (removeDuplicates) {
            DataAnalysisTools.removeDuplicates(records);
        }
        
        //call to KMeans class passing the images colors
        clusters = KMeans.fit(records, k, new EuclideanDistance(), maxIterations);
        
        //extract the colors from colors
        i=0;
        for (Centroid centroid : clusters.keySet()) {
            //found out that when you use HSL values rather than HSB values, the clustering algorithm clusters much more like a person would 
            float h,s,b;
            h = centroid.getCoordinates().get("hue").floatValue();
            s = centroid.getCoordinates().get("saturation").floatValue();
            b = centroid.getCoordinates().get("lightness").floatValue();
        
            colors[i] = new Color(Color.HSBtoRGB(h, s, b));
            i++;
        }// for
        
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                currentPixel = pixels[row][col];

                // parse the fiveNumSum colors and compare to background
                int bestIndex = 0;
                for (int e = 1; e < colors.length; e++) {
                    if (colors[e] == null || colors[e].equals(null)) {}
                    else if (currentPixel.colorDistance(colors[e]) < currentPixel.colorDistance(colors[bestIndex])) {
                        bestIndex = e;
                    } // if
                } // for

                currentPixel.setColor(colors[bestIndex]);
            } // for
        } // for
    } // kMeansSimplify
    /**
     * simplifies an image to a given number of colors using the k-means clustering algorithm, finds its own value for k, much slower :(
     * @param maxK maximum k, num of clusters
     * @param maxIterations maximum number of iterations of k-means to do
     * @param removeDuplicates wether or not to remove duplicate colors
     */
    public void kMeansSimplifyAutoK(int maxK, int maxIterations, boolean removeDuplicates) {
        //data
        int k;
        Pixel currentPixel = null;
        Pixel[][] pixels = this.getPixels2D();
        List<Record<Float>> records = super.getPixelsRecordHSL(); //dataset of all pixels in the image
        Map<Centroid, List<Record<Float>>> clusters; //most prevalant color groups as returned by KMeans
        Color[] colors; //most prevalent colors
        int i; //used as in a later for loop
        
        //remove duplicate records
        if (removeDuplicates) {
            DataAnalysisTools.removeDuplicates(records);
        }
        //call to KMeans class passing the images colors
        clusters = KMeans.fit(records, new EuclideanDistance(), maxIterations, maxK); //this version of the fit method calculates the optimal k
        k = clusters.size();
        System.out.println("K: " + k);
        colors = new Color[k];
        
        //extract the colors from colors
        i=0;
        for (Centroid centroid : clusters.keySet()) {
            //found out that when you use HSL values rather than HSB values, the clustering algorithm clusters much more like a person would 
            float h,s,b;
            h = centroid.getCoordinates().get("hue").floatValue();
            s = centroid.getCoordinates().get("saturation").floatValue();
            b = centroid.getCoordinates().get("lightness").floatValue();
        
            colors[i] = new Color(Color.HSBtoRGB(h, s, b));
            i++;
        }// for
        
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                currentPixel = pixels[row][col];

                // parse the fiveNumSum colors and compare to background
                int bestIndex = 0;
                for (int e = 1; e < colors.length; e++) {
                    if (colors[e] == null || colors[e].equals(null)) {}
                    else if (currentPixel.colorDistance(colors[e]) < currentPixel.colorDistance(colors[bestIndex])) {
                        bestIndex = e;
                    } // if
                } // for

                currentPixel.setColor(colors[bestIndex]);
            } // for
        } // for
    } // kMeansSimplify
    
    /**
     * this method simplifies an image to five colors, how the colors are chosen
     * depend on the parameters
     * 
     * @param sortMode method used to sort the colors in the image, 0=hue, 1=z-score, 2=integer representation (raw)
     * @param removeDuplicates whether or not to remove duplicates from the list of colors (done before sort)
     * @param colorGenMode method used to generate colors, 0=5 num sum, 1=SD + mean
     * @param specialOperations which special operations to apply at end (a boolean array, where true/false at following indexes represent whether or not to apply the operation), 0=grayscale, 1=invert,
     */
    public void simplifyColors(int sortMode, boolean removeDuplicates, int colorGenMode, Boolean[] specialOperations) {
        // data
        Pixel currentPixel = null;
        Pixel[][] pixels = this.getPixels2D();
        ArrayList<Integer> pictureColors = new ArrayList<Integer>();//array of colors
        // 5 colors
        Color[] colors = new Color[5];
        
        // get an array list of the integer representations of all the pixels color
        // values
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                pictureColors.add(pixelObj.getColor().getRGB());
            } // for
        } // for

        //if they want duplicates removed, do so, also do this if color gen is set to k-means
        if (removeDuplicates) {
            DataAnalysisTools.removeDuplicates(pictureColors);
        } //removeDuplicates if


        //sort picture colors based on how the user wants them sorted
        //0=hue, 1=z-score, 2=integer representation (raw)
        switch (sortMode) {
            case 0:
                //TODO: refactor this comparator into a util class
                //sort by hue    
                Collections.sort(pictureColors, new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        // DATA
                        Color c1 = new Color(o1);
                        Color c2 = new Color(o2);
                        // rank by hue
                        return ColorTools.getHue(c1.getRed(), c1.getGreen(), c1.getBlue()) - ColorTools.getHue(c2.getRed(), c2.getGreen(), c2.getBlue());
                    } // public int compare
                });
                break;
            case 1:
                //sort by z-scores
                //DATA
                final double mean = DataAnalysisTools.calcMean(pictureColors); //made final so the lamda function can use it
                final double sd = DataAnalysisTools.calcStandardDeviation(pictureColors); //made final so the lamda function can use it
                //TODO: refactor this comparator into a util class
                //sort by z-score
                Collections.sort(pictureColors, new Comparator<Integer>() {
                    @Override
                    public int compare(Integer c1, Integer c2) {
                        // DATA
                        double z1, z2;
                        // find z scores of each z=(x-mean)/SD;
                        z1 = (c1 - mean) / sd;
                        z2 = (c2 - mean) / sd;

                        // do abs value
                        z1 = Math.abs(z1);
                        z2 = Math.abs(z2);

                        // rank by z-score
                        if (z1 > z2)
                            return 1;
                        else if (z1 < z2)
                            return -1;
                        else
                            return 0;
                    } // public int compare
                });
                break;
            case 2:
            default:
                //generic sort of integer representations of colors
                Collections.sort(pictureColors);
                break;
        } //sortMode switch
    

        //generate colors based on colorGenMode
        //0=5 num sum, 1=k-means clustering, 2=SD + mean
        switch (colorGenMode) {
            case 0: 
                // find the five num sum
                //min
                colors[0] = new Color(pictureColors.get(0));
                //q1
                colors[1] = new Color(pictureColors.get((int) (pictureColors.size() / 4.0)));
                // find the median
                colors[2] = new Color(pictureColors.get((int) (pictureColors.size() / 2.0)));
                // q3
                colors[3] = new Color(pictureColors.get((int) (pictureColors.size() * (3.0 / 5.0))));
                // max
                colors[4] = new Color(pictureColors.get(pictureColors.size() - 1));
                break;
            case 1:
            default:
                //either do SD+mean stuff with r,g,and b color channels, or the hue,sat, and brightness channels, depending on what user chose for sorting
                //0 = hue, else = rgb channels
                switch (sortMode) {
                    case 0:
                        //DATA    
                        ArrayList<Float> hueList = new ArrayList<Float>();
                        ArrayList<Float> saturationList = new ArrayList<Float>();
                        ArrayList<Float> brightnessList = new ArrayList<Float>();
                        float meanH=0, meanS=0, meanB=0;
                        double sdH=0, sdS=0, sdB=0;

                        //find mean and SD of the hue
                        for (int c : pictureColors) {
                            Color color = new Color(c);
                            float r=color.getRed()/255.0f,g=color.getGreen()/255.0f,b=color.getBlue()/255.0f;
                            float[] hsl = ColorTools.getHSL(r, g, b);
                            //add to lists for reference later
                            hueList.add(hsl[0]);
                            saturationList.add(hsl[1]);
                            brightnessList.add(hsl[2]);
                            //sum up for mean
                            meanH += hsl[0];
                            meanS += hsl[1];
                            meanB += hsl[2];
                        }//mean loop
                        meanH /= hueList.size();
                        meanS /= saturationList.size();
                        meanB /= brightnessList.size();
                        
                        //sd
                        sdH = DataAnalysisTools.calcStandardDeviation(hueList);
                        sdS = DataAnalysisTools.calcStandardDeviation(saturationList);
                        sdB = DataAnalysisTools.calcStandardDeviation(brightnessList);

                        //gen 5 colors
                        colors[0] = Color.getHSBColor(
                            meanH - 1.0f*(float)sdH,
                            meanS - 1.0f*(float)sdS,
                            meanB - 1.0f*(float)sdB
                        );
                        colors[1] = Color.getHSBColor(
                            meanH - 0.5f*(float)sdH,
                            meanS - 0.5f*(float)sdS,
                            meanB - 0.5f*(float)sdB
                        );
                        colors[2] = Color.getHSBColor(
                            meanH,
                            meanS,
                            meanB
                        );
                        colors[3] = Color.getHSBColor(
                            meanH + 0.5f*(float)sdH,
                            meanS + 0.5f*(float)sdS,
                            meanB + 0.5f*(float)sdB
                        );
                        colors[4] = Color.getHSBColor(
                            meanH + 1.0f*(float)sdH,
                            meanS + 1.0f*(float)sdS,
                            meanB + 1.0f*(float)sdB
                        );

                        //System.out.println("meanH: " + meanH + "; sdH " + sdH + "\nmeanS: " + meanS + "; sds " + sdS + "\nmeanB: " + meanB + "; sdB " + sdB);
                        
                        break;
                    default:
                        // find mean and standard deviation for each color channel, then create colors with that
                        ArrayList<Integer> rList = new ArrayList<Integer>();
                        ArrayList<Integer> gList = new ArrayList<Integer>();
                        ArrayList<Integer> bList = new ArrayList<Integer>();
                        float meanRed = 0, meanGreen = 0, meanBlue = 0;
                        double sdRed = 0, sdGreen = 0, sdBlue = 0;

                        // find mean of each color channel
                        for (int color : pictureColors) {
                            Color c = new Color(color);
                            rList.add(c.getRed());
                            gList.add(c.getGreen());
                            bList.add(c.getBlue());
                            // sum each color channel
                            meanRed += c.getRed();
                            meanGreen += c.getGreen();
                            meanBlue += c.getBlue();
                        } // for
                          // divide by total
                        meanRed /= pictureColors.size();
                        meanGreen /= pictureColors.size();
                        meanBlue /= pictureColors.size();

                        sdRed = DataAnalysisTools.calcStandardDeviation(rList);
                        sdGreen = DataAnalysisTools.calcStandardDeviation(gList);
                        sdBlue = DataAnalysisTools.calcStandardDeviation(bList);
                        // create 5 colors with mean and sd
                        // mean
                        colors[2] = new Color((int) (meanRed), (int) (meanGreen), (int) (meanBlue));
                        // +- 0.5 Standard Deviations
                        colors[1] = new Color(
                            Math.abs((int) (meanRed - 0.5 * sdRed) % 255),
                            Math.abs((int) (meanGreen - 0.5 * sdGreen) % 255),
                            Math.abs((int) (meanBlue - 0.5 * sdBlue) % 255)
                        );
                        colors[3] = new Color(
                            Math.abs((int) (meanRed + 0.5 * sdRed) % 255),
                            Math.abs((int) (meanGreen + 0.5 * sdGreen) % 255),
                            Math.abs((int) (meanBlue + 0.5 * sdBlue) % 255)
                        );
                        // +- 1 Standard Deviations
                        colors[0] = new Color(
                            Math.abs((int) (meanRed - sdRed) % 255),
                            Math.abs((int) (meanGreen - sdGreen) % 255), 
                            Math.abs((int) (meanBlue - sdBlue) % 255)
                        );
                        colors[4] = new Color(
                            Math.abs((int) (meanRed + sdRed) % 255),
                            Math.abs((int) (meanGreen + sdGreen) % 255), 
                            Math.abs((int) (meanBlue + sdBlue) % 255)
                        );
                }//sd+mean gen switch
                break;
        }//color gen switch

        //recolor image
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                currentPixel = pixels[row][col];

                // parse the fiveNumSum colors and compare to background
                int bestIndex = 0;
                for (int e = 1; e < colors.length; e++) {
                    if (currentPixel == null || colors[e] == null) {
                        //TODO: this is just a hack to stop it from crashing 
                    }
                    else if (currentPixel.colorDistance(colors[e]) < currentPixel
                            .colorDistance(colors[bestIndex])) {
                                bestIndex = e;
                    } // if
                } // for

                currentPixel.setColor(colors[bestIndex]);
            } // for
        } // for

        //special operations
        //0=none, 1=grayscale, 2=invert, 3=both
        if (specialOperations.length <= 2) { //array is proper size
            if (specialOperations[0]) { //grayscale?
                this.grayscale();
            }
            if (specialOperations[1]) { //invert?
                this.negate();
            }
        }//special operations switch
        return;
    } // simplifyColors
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
     9 = K-means: uses an implementation of the k-means algorithm to calculate the colors
     */
    /**
     * this method simplifies an image to five colors, how the colors are chosen
     * depend on the algorithm choosen uses the 5 number summary of all the pixels
     * color values (as integers) to balance the image better
     * 
     * @param mode which mode to use, 0 = equidistant from color wheel, 1 = grayscale, 2 = faithful, 3 = faithful+, 4 = balance, 5 = balance+, 6 = SD+mean, 7 = Zed, 8 = Zed+, 9 = kmeans
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
        // 5 colors
        Color[] colors = new Color[5];
        
        //TODO: remove the k-means stuff from this once the implementation in KMeansSimplify() is as good as or better than this one
        //current difference, complexity wise, comes down to this being I*N faster as it doesn't stop early if the state doesn't change
        
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
                // remove duplicates from picture colors
                DataAnalysisTools.removeDuplicates(pictureColors);
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

                fiveNumSumColors[0] = new Color(min);
                fiveNumSumColors[1] = new Color(q1);
                fiveNumSumColors[2] = new Color(med);
                fiveNumSumColors[3] = new Color(q3);
                fiveNumSumColors[4] = new Color(max);
            break;

            /** balance+ */
            case 5:
                // find 5 num sum, this time summarize the occurances of each color rather than
                // the colors themselves
                // remove duplicates from picture colors
                DataAnalysisTools.removeDuplicates(pictureColors);
            /** balance */
            case 4:
                // sort the array
                Collections.sort(pictureColors, new Comparator<Integer>() {
                    @Override
                        public int compare(Integer o1, Integer o2) {
                            // DATA
                            Color c1 = new Color(o1);
                            Color c2 = new Color(o2);
                            // rank by hue
                            return ColorTools.getHue(c1.getRed(), c1.getGreen(), c1.getBlue()) - ColorTools.getHue(c2.getRed(), c2.getGreen(), c2.getBlue());
                        } // public int compare
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

                fiveNumSumColors[0] = new Color(min);
                fiveNumSumColors[1] = new Color(q1);
                fiveNumSumColors[2] = new Color(med);
                fiveNumSumColors[3] = new Color(q3);
                fiveNumSumColors[4] = new Color(max);
            break;
            
            /** grayscale */
            case 1:
            /** SD+mean */
            case 6:
                // find mean and standard deviation for each color channel, then create colors
                // with that
                ArrayList<Integer> rList = new ArrayList<Integer>();
                ArrayList<Integer> gList = new ArrayList<Integer>();
                ArrayList<Integer> bList = new ArrayList<Integer>();
                float meanRed = 0, meanGreen = 0, meanBlue = 0;
                double sdRed = 0, sdGreen = 0, sdBlue = 0;
                

                // find mean of each color channel
                for (int color : pictureColors) {
                    Color c = new Color(color);
                    rList.add(c.getRed());
                    gList.add(c.getGreen());
                    bList.add(c.getBlue());
                    // sum each color channel
                    meanRed += c.getRed();
                    meanGreen += c.getGreen();
                    meanBlue += c.getBlue();
                } // for
                  // divide by total
                meanRed /= pictureColors.size();
                meanGreen /= pictureColors.size();
                meanBlue /= pictureColors.size();

                // sd
                sdRed = DataAnalysisTools.calcStandardDeviation(rList);
                sdGreen = DataAnalysisTools.calcStandardDeviation(gList);
                sdBlue = DataAnalysisTools.calcStandardDeviation(bList);
                
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
                
                fiveNumSumColors[0] = new Color(min);
                fiveNumSumColors[1] = new Color(q1);
                fiveNumSumColors[2] = new Color(med);
                fiveNumSumColors[3] = new Color(q3);
                fiveNumSumColors[4] = new Color(max);
            break;
            
            /** Zed+ */
            case 7:
                // remove duplicates from picture colors
                DataAnalysisTools.removeDuplicates(pictureColors);
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

                fiveNumSumColors[0] = new Color(min);
                fiveNumSumColors[1] = new Color(q1);
                fiveNumSumColors[2] = new Color(med);
                fiveNumSumColors[3] = new Color(q3);
                fiveNumSumColors[4] = new Color(max);
            break;
            /* k-means */
            case 9:
                //remove duplicates 
                DataAnalysisTools.removeDuplicates(pictureColors);

                //first step, create 5 random 3d (corresponding to r,g,b) points (centroids) with coordinates between min and max rbg values
                int nC = 10; //number of centroids to use
                int nK = 10; //number of iterations
                Color[] centroids = new Color[nC];
                for (int n=0;n<nC;n++) { //forgy initialization method, choose random colors from the image as starting points
                    centroids[n] = new Color(pictureColors.get( (int)(Math.random() * pictureColors.size() - 1)) );
                }
                
                ArrayList<ArrayList<Integer> > centroidAssignments = new ArrayList<ArrayList<Integer>>(); //colors assigned to the centroids
                for (int n=0;n<nC;n++) centroidAssignments.add(new ArrayList<Integer>());

                //(repeat for the number of iterations or until # of assignments per centroid no longer changes)
                //max iterations (k) = 10
                for (int i = 0; i < nK; i++) {
                    //go through every coordinate (color) and assign it to it's nearest centroid
                    for (int c : pictureColors) {
                        Color color = new Color(c);
                        int nearest = 0; //index of best match in the centroids array
                        double minDistance = Double.MAX_VALUE; //distance from best match

                        //find euclidien distance
                        for (int j = 0; j < centroids.length; j++) {
                            double distance = Math.sqrt(
                                Math.pow(color.getRed()-centroids[j].getRed(),2) + Math.pow(color.getGreen()-centroids[j].getGreen(),2) + Math.pow(color.getBlue()-centroids[j].getBlue(),2)
                            );

                            if (distance < minDistance) {
                                minDistance = distance;
                                nearest = j;
                            }
                        }

                        //assign
                        centroidAssignments.get(nearest).add(c); 
                    }

                    //relocate the centroid to the middle (mean) of its assignments
                    for (int j = 0; j < centroids.length; j++) { //parse the centroids and their assignments
                        int sumR = 0;
                        int sumG = 0;
                        int sumB = 0;
                        for (int c : centroidAssignments.get(j)) {
                            Color color = new Color(c);
                            sumR += color.getRed();
                            sumG += color.getGreen();
                            sumB += color.getBlue();
                        }
                        int n = centroidAssignments.get(j).size();
                        centroids[j] = new Color(sumR/n, sumG/n, sumB/n);
                    }
                }
                //assign colors from centroids
                fiveNumSumColors = centroids;
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
                
                fiveNumSumColors[0] = new Color(min);
                fiveNumSumColors[1] = new Color(q1);
                fiveNumSumColors[2] = new Color(med);
                fiveNumSumColors[3] = new Color(q3);
                fiveNumSumColors[4] = new Color(max);
            break;
        } // switch-case

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
    
    /////////////////////// methods ///////////////////////////////////////
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