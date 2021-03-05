
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List
import java.util.ArrayList;

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class. 
 * based on work by Barbara Ericson ericson@cc.gatech.edu
 * 
 * @author Anthony Rubick
 */
public class Picture extends SimplePicture 
{
    ///////////////////// constructors //////////////////////////////////

    /**
     * Constructor that takes no arguments 
     */
    public Picture ()
    {
        /* not needed but use it to show the implicit call to super()
         * child constructors always call a parent constructor 
         */
        super();  
    }

    /**
     * Constructor that takes a file name and creates the picture 
     * @param fileName the name of the file to create the picture from
     */
    public Picture(String fileName)
    {
        // let the parent class handle this fileName
        super(fileName);
    }

    /**
     * Constructor that takes the width and height
     * @param height the height of the desired picture
     * @param width the width of the desired picture
     */
    public Picture(int height, int width)
    {
        // let the parent class handle this width and height
        super(width,height);
    }

    /**
     * Constructor that takes a picture and creates a 
     * copy of that picture
     * @param copyPicture the picture to copy
     */
    public Picture(Picture copyPicture)
    {
        // let the parent class do the copy
        super(copyPicture);
    }

    /**
     * Constructor that takes a buffered image
     * @param image the buffered image to use
     */
    public Picture(BufferedImage image)
    {
        super(image);
    }

    ////////////////////// methods added for the image manipulation GUI //////////////////////
    /**
     * remove, remove one or more color channels by setting the values to 0
     * @param removeRed whether or not to remove Red
     * @param removeGreen whether or not to remove Green
     * @param removeBlue whether or not to remove Blue
     */
    public void removeColorChannels(boolean removeRed, boolean removeGreen, boolean removeBlue)
    {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                if (removeRed) {
                    pixelObj.setRed(0);
                }
                if (removeGreen) {
                    pixelObj.setGreen(0);
                }
                if (removeBlue) {
                    pixelObj.setBlue(0);
                }
            }
        }
        pixels = null;
        return;
    }

    /**
     * Trim, trims a color channel to be in between the passed min and max values
     * @param color an integer representing the color to be trimmed (0 = red; 1 = green; 2 = blue)
     * @param min the minimum value, any color values below this will be increased to this value
     * @param max the maximum value, any color values above this will be decreased to this value
     */
    public void trimColor(int color, int min, int max)
    {
        Pixel[][] pixels = this.getPixels2D();
        int i;
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                switch (color) 
                { 
                    case 0: //trim red
                    i = pixelObj.getRed();
                    if(i < min) pixelObj.setRed(min);
                    else if (i > max) pixelObj.setRed(max); 
                    break;
                    case 1: //trim green
                    i = pixelObj.getGreen();
                    if(i < min) pixelObj.setGreen(min);
                    else if (i > max) pixelObj.setGreen(max);
                    break;
                    case 2: //trim blue
                    i = pixelObj.getBlue();
                    if(i < min) pixelObj.setBlue(min);
                    else if (i > max) pixelObj.setBlue(max);
                    break;
                }
            }
        }
        pixels = null;
        return;
    }

    /**
     * Negate, negates one or more color channels by setting the values to 255 - current value
     * @param negateRed whether or not to negate Red
     * @param negateGreen whether or not to negate Green
     * @param negateBlue whether or not to negate Blue
     */
    public void negateColorChannels(boolean negateRed, boolean negateGreen, boolean negateBlue)
    {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                if (negateRed) {
                    pixelObj.setRed(255 - pixelObj.getRed());
                }
                if (negateGreen) {
                    pixelObj.setGreen(255 - pixelObj.getGreen());
                }
                if (negateBlue) {
                    pixelObj.setBlue(255 - pixelObj.getBlue());
                }
            }
        }
        pixels = null;
        return;
    }

    /** 
     * method to put the image in grayscale by setting the red green and blue values to the average of the 3
     */
    public void grayscale()
    {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                double average = (pixelObj.getRed() + pixelObj.getGreen() + pixelObj.getBlue()) / 3;
                pixelObj.setRed((int)average);
                pixelObj.setGreen((int)average);
                pixelObj.setBlue((int)average);
            }
        }
        pixels = null;
        return;
    }

    /**
     * relaceColorWithColor, parses the picture, setting all pixels of the passed colorToReplace to the passed colorToSet
     * @param colorToReplace the color to replace
     * @param colorToSet the color to replace with
     * @param tolerance (FUTURE) will allow the user to set a tolerance, replacing all colors within this distance of colorToReplace
     */
    public void replaceColorWithColor(Color colorToReplace, Color colorToSet, int tolerance)
    {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                if (Pixel.colorDistance(pixelObj.getColor(), colorToReplace) <= tolerance) {
                    pixelObj.setRed(colorToSet.getRed());
                    pixelObj.setGreen(colorToSet.getGreen());
                    pixelObj.setBlue(colorToSet.getBlue());
                }
            }
        }
        pixels = null;
        return;
    }

    /**
     * brightens an image
     */
    public void brighten() 
    {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                pixelObj.setColor(pixelObj.getColor().brighter());
            }
        }
        pixels = null;
        return;
    }

    /**
     * darkens an image
     */
    public void darken() 
    {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                pixelObj.setColor(pixelObj.getColor().darker());
            }
        }
        pixels = null;
        return;
    }

    /** 
     * this method simplifies an image to five colors, 
     * the colors are based off of the background color (color of the top left pixel) 
     * and are equidistant from eachother on the color wheel
     * uses the 5 number summary of all the pixels color values (as integers) to balance the image better
     * 
     */
    public void simplifyColors()
    {
        Pixel currentPixel = null;
        Pixel[][] pixels = this/*tempCopyPicture*/.getPixels2D();

        Color background = pixels[1][1].getColor();
        //if the background colors saturation, or brightness is really low, increase it
        if (true) {
            float[] bgHSBval = new float[3];
            Color.RGBtoHSB(background.getRed(),background.getGreen(),background.getBlue(), bgHSBval);
            if (Math.abs(bgHSBval[1]) < 0.3) {
                bgHSBval[1] = 0.3f;
            }
            if (Math.abs(bgHSBval[2]) < 0.3) {
                bgHSBval[2] = 0.3f;
            }
            background = Color.getHSBColor(bgHSBval[0], bgHSBval[1], bgHSBval[2]);
        }

        // get 5 number summary of the image
        //get an array list of the integer representations of all the pixels color values
        ArrayList<Integer> pictureColors = new ArrayList<Integer>();
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                pictureColors.add(pixelObj.getColor().getRGB());
            }
        }
        //sort the array
        Collections.sort(pictureColors);
        //find the median
        int med = pictureColors.get( (int)(pictureColors.size() / 2.0));
        //find the min and max
        int min = Collections.min(pictureColors);
        int max = Collections.max(pictureColors);
        // find q1 and q3
        int q1 = pictureColors.get( (int)(pictureColors.size() / 4.0));
        int q3 = pictureColors.get( (int)(pictureColors.size() * (3.0 / 5.0) ));

        int[] fiveNumSum = {min, q1, med, q3, max};
        Color[] fiveNumSumColors = { (new Color(min)) , (new Color(q1)) , (new Color(med)) , (new Color(q3)) , (new Color(max)) };

        //generate 5 colors
        Color[] colors = new Color[5];
        float[] hsbValues = new float[3];
        hsbValues = Color.RGBtoHSB(background.getRed(),background.getGreen(),background.getBlue(), hsbValues);

        for(int i=0; i<colors.length; i++) {
            colors[i] = Color.getHSBColor((float)(hsbValues[0] + (0.2*i))%1, hsbValues[1], hsbValues[2]);
        }
        /*
        colors[0] = Color.getHSBColor(hsbValues[0],hsbValues[1], hsbValues[2]);
        colors[1] = Color.getHSBColor((float)(hsbValues[0]+0.2)%1,hsbValues[1], hsbValues[2]);
        colors[2] = Color.getHSBColor((float)(hsbValues[0]+0.4)%1,hsbValues[1], hsbValues[2]);
        colors[3] = Color.getHSBColor((float)(hsbValues[0]+0.6)%1,hsbValues[1], hsbValues[2]);
        colors[4] = Color.getHSBColor((float)(hsbValues[0]+0.8)%1,hsbValues[1], hsbValues[2]);
         */
        
        //clear the colors ArrayList to save some memory or something
        pictureColors.clear();
        
        //recolor image
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < pixels[0].length; col++)
            {
                currentPixel = pixels[row][col];

                //parse the fiveNumSum colors and compare to background
                int fmsBestIndex = 0;
                for (int e = 1; e < fiveNumSumColors.length; e++) {
                    if (currentPixel.colorDistance(fiveNumSumColors[e]) < currentPixel.colorDistance(fiveNumSumColors[fmsBestIndex])) fmsBestIndex = e;
                }

                currentPixel.setColor(colors[fmsBestIndex]);
            }
        }
        pixels = null;
        return;
    }
    ////////////////////// methods ///////////////////////////////////////

    /**
     * Method to return a string with information about this picture.
     * @return a string with information about the picture such as fileName,
     * height and width.
     */
    public String toString()
    {
        String output = "Picture, filename " + getFileName() + 
            " height " + getHeight() 
            + " width " + getWidth();
        return output;

    }

    /** Method to set the blue to 0 */
    public void zeroBlue()
    {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                pixelObj.setBlue(0);
            }
        }
        pixels = null;
        return;
    }

    /** Method that mirrors the picture around a 
     * vertical mirror in the center of the picture
     * from left to right */
    public void mirrorVertical()
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < width / 2; col++)
            {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][width - 1 - col];
                rightPixel.setColor(leftPixel.getColor());
            }
        } 
        pixels = null;
        return;
    }

    /** Mirror just part of a picture of a temple */
    public void mirrorTemple()
    {
        int mirrorPoint = 276;
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int count = 0;
        Pixel[][] pixels = this.getPixels2D();

        // loop through the rows
        for (int row = 27; row < 97; row++)
        {
            // loop from 13 to just before the mirror point
            for (int col = 13; col < mirrorPoint; col++)
            {
                count ++;
                leftPixel = pixels[row][col];      
                rightPixel = pixels[row]                       
                [mirrorPoint - col + mirrorPoint];
                rightPixel.setColor(leftPixel.getColor());
            }
        }
        System.out.println(count);
        
        pixels = null;
        return;
    }

    /** copy from the passed fromPic to the
     * specified startRow and startCol in the
     * current picture
     * @param fromPic the picture to copy from
     * @param startRow the start row to copy to
     * @param startCol the start col to copy to
     */
    public void copy(Picture fromPic, 
    int startRow, int startCol)
    {
        Pixel fromPixel = null;
        Pixel toPixel = null;
        Pixel[][] toPixels = this.getPixels2D();
        Pixel[][] fromPixels = fromPic.getPixels2D();
        for (int fromRow = 0, toRow = startRow; 
        fromRow < fromPixels.length &&
        toRow < toPixels.length; 
        fromRow++, toRow++)
        {
            for (int fromCol = 0, toCol = startCol; 
            fromCol < fromPixels[0].length &&
            toCol < toPixels[0].length;  
            fromCol++, toCol++)
            {
                fromPixel = fromPixels[fromRow][fromCol];
                toPixel = toPixels[toRow][toCol];
                toPixel.setColor(fromPixel.getColor());
            }
        }   
        toPixels = null;
        fromPixels = null;
        return;
    }

    /** Method to create a collage of several pictures */
    public void createCollage()
    {
        Picture flower1 = new Picture("flower1.jpg");
        Picture flower2 = new Picture("flower2.jpg");
        this.copy(flower1,0,0);
        this.copy(flower2,100,0);
        this.copy(flower1,200,0);
        Picture flowerNoBlue = new Picture(flower2);
        flowerNoBlue.zeroBlue();
        this.copy(flowerNoBlue,300,0);
        this.copy(flower1,400,0);
        this.copy(flower2,500,0);
        this.mirrorVertical();
        this.write("collage.jpg");
    }

    /** Method to show large changes in color 
     * @param edgeDist the distance for finding edges
     */
    public void edgeDetection(int edgeDist)
    {
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        Pixel bottomPixel = null;
        Pixel[][] pixels = this.getPixels2D();
        Color rightColor = null;
        Color bottomColor = null;
        for (int row = 0; row < pixels.length-1; row++)
        {
            for (int col = 0; 
            col < pixels[0].length-1; col++)
            {
                leftPixel = pixels[row][col];
                bottomPixel = pixels[row+1][col];
                bottomColor = bottomPixel.getColor();
                rightPixel = pixels[row][col+1];
                rightColor = rightPixel.getColor();
                if (leftPixel.colorDistance(rightColor) > 
                edgeDist)
                    leftPixel.setColor(Color.BLACK);
                else if (leftPixel.colorDistance(bottomColor) >
                edgeDist)
                    leftPixel.setColor(Color.BLACK);
                else
                    leftPixel.setColor(Color.WHITE);
            }
        }
        pixels = null;
        return;
    }

    /* Main method for testing - each class in Java can have a main 
     * method 
     */
    public static void main(String[] args) 
    {
        Picture beach = new Picture("beach.jpg");
        beach.explore();
        beach.zeroBlue();

        beach.explore();
        beach.keepOnlyBlue();

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
        beach.mirrorArms();

        beach.explore();
        beach.mirrorGull();

        beach.explore();
        beach.copy2(beach, 252,454,290,600);

        beach.explore();
        beach.myCollage();

        beach.explore();
        beach.edgeDetection2(10,2);

        beach.explore();
    }

    ///////////////////// things i added \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    /** Method to set red and green to zero*/
    public void keepOnlyBlue()
    {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                pixelObj.setRed(0);
                pixelObj.setGreen(0);
            }
        }
        pixels = null;
        return;
    }

    /** method to set all rgb values to 255 - that rgb value */
    public void negate()
    {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                pixelObj.setRed(255 - pixelObj.getRed());
                pixelObj.setGreen(255 - pixelObj.getGreen());
                pixelObj.setBlue(255 - pixelObj.getBlue());
            }
        }
        pixels = null;
        return;
    }

    /**
     *  mirrors a picture around a mirror
     *  placed vertically from right to left.
     */
    public void mirrorVerticalRightToLeft()
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = width - 1; col >= width / 2; col--)
            {
                rightPixel = pixels[row][col];
                leftPixel = pixels[row][width - 1 - col];
                leftPixel.setColor(rightPixel.getColor());
            }
        } 
        pixels = null;
        return;
    }

    /**
     *  mirrors a picture around a mirror
     *  placed horizontally from top to bottom.
     */
    public void mirrorHorizontal()
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel topPixel = null;
        Pixel bottomPixel = null;
        int height = pixels.length;
        for (int col = 0; col < pixels[0].length; col++)
        {
            for (int row = 0; row < height / 2; row++)
            {
                topPixel = pixels[row][col];
                bottomPixel = pixels[height - 1 - row][col];
                bottomPixel.setColor(topPixel.getColor());
            }
        }
        pixels = null;
        return;
    }

    /**
     *  mirrors a picture around a mirror
     *  placed horizontally from bottom to top.
     */
    public void mirrorHorizontalBotToTop()
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel bottomPixel = null;
        Pixel topPixel = null;
        int height = pixels.length;
        for (int col = 0; col < pixels[0].length; col++)
        {
            for (int row = height -1; row >= height / 2; row--)
            {
                bottomPixel = pixels[row][col];
                topPixel = pixels[height - 1 - row][col];
                topPixel.setColor(bottomPixel.getColor());
            }
        }
        pixels = null;
        return;
    }

    /**
     * mirror the arms on the snowman (“snowman.jpg”) to
     * make a snowman with 4 arms.
     */
    public void mirrorArms()
    {
        Pixel originalPixel = null;
        Pixel dupePixel = null;
        int count = 0;
        Pixel[][] pixels = this.getPixels2D();
        int leftMirrorPoint = 190;
        int rightMirrorPoint = 197;

        // loop through the rows 
        //left arm (r: 158 - 190; c: 103-170)
        for (int row = 155; row < 190; row++)
        {
            for (int col = 103; col < 170; col++)
            {
                originalPixel = pixels[row][col];      
                //set the dupePixel to a different row as the originalPixel
                dupePixel = pixels[leftMirrorPoint + (leftMirrorPoint - row)][col];
                dupePixel.setColor(originalPixel.getColor());
            }
        }
        //right arm (r: 172 - 197; c 239-295)
        for (int row = 197; row > 172; row --)
        {
            for (int col = 239; col < 295; col++)
            {
                originalPixel = pixels[row][col];      
                //set the dupePixel to a different row as the originalPixel
                dupePixel = pixels[row + 15][col];
                dupePixel.setColor(originalPixel.getColor());
            }
        }
        pixels = null;
        return;
    }

    /**
     * mirror the seagull (“seagull.jpg”) to the right so
     * that there are two seagulls on the beach near each other
     */
    public void mirrorGull()
    {
        Pixel originalPixel = null;
        Pixel dupePixel = null;
        int count = 0;
        Pixel[][] pixels = this.getPixels2D();

        //top left corner of og seagull: r = 233; c = 238
        //bottom right corner of og seagull: r = 322; c = 342

        //translate seagull: r = -10; c = -83;
        for (int row = 233; row < 322; row++)
        {
            for (int col = 238; col < 342; col++)
            {
                originalPixel = pixels[row][col];      
                //set the dupePixel to a different row as the originalPixel
                dupePixel = pixels[row - 5][col - 110];
                dupePixel.setColor(originalPixel.getColor());
            }
        }
        pixels = null;
        return;
    }

    /** copy from the passed fromPic to the
     * specified startRow and startCol in the
     * current picture
     * @param fromPic the picture to copy from
     * @param startRow the start row to copy to
     * @param startCol the start col to copy to
     * @param endRow the end row to copy to
     * @param endCol the end col to copy to
     */
    public void copy2(Picture fromPic, 
    int startRow, int startCol, int endRow, int endCol)
    {
        Pixel fromPixel = null;
        Pixel toPixel = null;
        Pixel[][] toPixels = this.getPixels2D();
        Pixel[][] fromPixels = fromPic.getPixels2D();
        for (int fromRow = 0, toRow = startRow; 
        fromRow < fromPixels.length &&
        toRow < endRow; 
        fromRow++, toRow++)
        {
            for (int fromCol = 0, toCol = startCol; 
            fromCol < fromPixels[0].length &&
            toCol < endCol;  
            fromCol++, toCol++)
            {
                fromPixel = fromPixels[fromRow][fromCol];
                toPixel = toPixels[toRow][toCol];
                toPixel.setColor(fromPixel.getColor());
            }
        } 
        toPixels = null;
        fromPixels = null;
        return;  
    }

    /** Method to create a collage of several pictures */
    public void myCollage()
    {
        Picture flower1 = new Picture("flower1.jpg");
        Picture flower2 = new Picture("flower2.jpg");
        Picture squidward = new Picture("squidward.jpg");

        this.copy(flower1,0,0);
        this.copy(flower2,0,100);
        this.copy(squidward,0,200);
        Picture flowerNoBlue = new Picture(flower2);
        flowerNoBlue.zeroBlue();

        Picture negativeSquid = new Picture("squidward.jpg");
        negativeSquid.negate();

        Picture graySquid = new Picture("squidward.jpg");
        graySquid.grayscale();

        this.copy(flowerNoBlue,0,300);
        this.copy(graySquid,0,400);
        this.copy(negativeSquid,0,500);

        this.mirrorHorizontal();
        this.write("collage.jpg");
    }

    /** Method to show large changes in color
     * hopefully more efficient
     * @param edgeDist the distance for finding edges
     */
    public void edgeDetection2(int edgeDist, int runs)
    {
        Pixel currentPixel = null; //the pixel being tested
        //the pixels to test, n, ne, e, se, e, sw, w, nw
        Pixel nPixel = null; 
        Pixel nePixel = null;
        Pixel ePixel = null;
        Pixel sePixel = null; 
        Pixel sPixel = null;
        Pixel swPixel = null;
        Pixel wPixel = null; 
        Pixel nwPixel = null;

        Pixel[][] pixels = this.getPixels2D();
        //the colors of the pixels being tested
        Color nColor = null; 
        Color neColor = null;
        Color eColor = null;
        Color seColor = null; 
        Color sColor = null;
        Color swColor = null;
        Color wColor = null; 
        Color nwColor = null;

        // how many times to run the code :D
        for (int i = 0; i < runs; i++)
        {
            for (int row = 1; row < pixels.length-1; row+=3)
            {
                for (int col = 1; 
                col < pixels[0].length-1; col+=3)
                {
                    //fill current pixel
                    currentPixel = pixels[row][col];
                    //fill all the other pixels around it
                    nPixel = pixels[row + 1][col]; 
                    nePixel = pixels[row+1][col+1];
                    ePixel = pixels[row][col+1];
                    sePixel = pixels[row-1][col+1]; 
                    sPixel = pixels[row-1][col];
                    swPixel = pixels[row-1][col-1];
                    wPixel = pixels[row][col-1]; 
                    nwPixel = pixels[row+1][col-1];
                    //fill in the colors
                    nColor = nPixel.getColor(); 
                    neColor = nePixel.getColor();
                    eColor = ePixel.getColor();
                    seColor = sePixel.getColor(); 
                    sColor = sPixel.getColor();
                    swColor = swPixel.getColor();
                    wColor = wPixel.getColor(); 
                    nwColor = nwPixel.getColor();

                    //has an edge been found
                    boolean edgeFound = false;

                    //check each surrounding pixel
                    // check N
                    if (currentPixel.colorDistance(nColor) > edgeDist)
                    {
                        edgeFound = true;
                        nPixel.setColor(Color.BLACK);
                    } else
                    {
                        nPixel.setColor(Color.WHITE);
                    }
                    // check NE
                    if (currentPixel.colorDistance(neColor) > edgeDist)
                    {
                        edgeFound = true;
                        nePixel.setColor(Color.BLACK);
                    } else
                    {
                        nePixel.setColor(Color.WHITE);
                    }
                    // check E
                    if (currentPixel.colorDistance(eColor) > edgeDist)
                    {
                        edgeFound = true;
                        ePixel.setColor(Color.BLACK);
                    } else
                    {
                        ePixel.setColor(Color.WHITE);
                    }
                    // check SE
                    if (currentPixel.colorDistance(seColor) > edgeDist)
                    {
                        edgeFound = true;
                        sePixel.setColor(Color.BLACK);
                    } else
                    {
                        sePixel.setColor(Color.WHITE);
                    }
                    // check S
                    if (currentPixel.colorDistance(sColor) > edgeDist)
                    {
                        edgeFound = true;
                        sPixel.setColor(Color.BLACK);
                    } else
                    {
                        sPixel.setColor(Color.WHITE);
                    }
                    // check SW
                    if (currentPixel.colorDistance(swColor) > edgeDist)
                    {
                        edgeFound = true;
                        swPixel.setColor(Color.BLACK);
                    } else
                    {
                        swPixel.setColor(Color.WHITE);
                    }
                    // check W
                    if (currentPixel.colorDistance(wColor) > edgeDist)
                    {
                        edgeFound = true;
                        wPixel.setColor(Color.BLACK);
                    } else
                    {
                        wPixel.setColor(Color.WHITE);
                    }
                    // check NW
                    if (currentPixel.colorDistance(nwColor) > edgeDist)
                    {
                        edgeFound = true;
                        nwPixel.setColor(Color.BLACK);
                    } else
                    {
                        nwPixel.setColor(Color.WHITE);
                    }

                    //if an edge has been found, change the current pixel to black
                    if (edgeFound)
                    {
                        currentPixel.setColor(Color.BLACK);
                    } else 
                    {
                        currentPixel.setColor(Color.WHITE);
                    }
                }
            }
        }
        pixels = null;
        return;
    }
    //////////////////////////////////////////////////////////// boldeners ////////////////////////////////////////////////////////////
    /** Method to show large changes in color
     * made for making light writing show up better on a plain background
     * this method should fill in lines
     * @param edgeDist the distance for finding edges
     */
    public void bolden(int edgeDist)
    {
        Pixel currentPixel = null;
        Pixel[][] pixels = this.getPixels2D();

        Color background = pixels[1][1].getColor();

        //complementary color scheme
        Color invertedBackground = new Color(255 - background.getRed(), 255 - background.getGreen(), 255 - background.getBlue());

        int countBack = 0;
        int countInvertBack = 0;
        int numPixels = pixels.length * pixels[0].length;

        //goes through picture again, darkens edges
        ///////////working function/////////////
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < pixels[0].length; col++)
            {
                currentPixel = pixels[row][col];
                if (Pixel.colorDistance(currentPixel.getColor(), background) > edgeDist)
                {
                    currentPixel.setColor(invertedBackground);
                    countInvertBack ++;
                } 
                else 
                {
                    currentPixel.setColor(background);
                    countBack ++;
                }
            }
        }
        pixels = null;
        return;
    }

    /** Method to show large changes in color
     * made for making light writing show up better on a plain background
     * this method should fill in lines
     * sets up and handles bolden2Iter
     */
    public void bolden2()
    {
        int edgeDist = 5;

        Pixel currentPixel = null;
        Pixel[][] pixels = this.getPixels2D();

        Color background = pixels[1][1].getColor();
        //Color invertedBackground = new Color(255 - background.getRed(), 255 - background.getGreen(), 255 - background.getBlue());

        int countBack = 0;
        int countInvertBack = 0;

        //goes through picture again, darkens edges
        ///////////working function/////////////
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < pixels[0].length; col++)
            {
                currentPixel = pixels[row][col];
                //optimization: change this to only count, not apply colors
                if (currentPixel.colorDistance(background) > edgeDist)
                {
                    //currentPixel.setColor(invertedBackground);
                    countInvertBack ++;
                }
                else {
                    //currentPixel.setColor(background);
                    countBack ++;
                }
            }
        }
        
        int edgeDiff = Math.abs(countInvertBack - countBack);
        int newEdgeDist = 1;
        this.bolden2Iter(pixels, newEdgeDist, 1,edgeDist, edgeDiff, edgeDist, edgeDiff, 0);
        
        pixels = null;
        return;
        
    }

    /** 
     * Method to show large changes in color
     * made for making light writing show up better on a plain background
     * should try and find an optimal tolerance for the user
     * this method should fill in lines
     * 
     * iterates to find the best tolerance
     * should only ever be called in bolden2
     * 
     * @param pixels the pixels for it to analyze
     * @param edgeDist the distance for finding edges
     * @param i iterations, used to prevent infinite loops
     * @param bestEdgeDist the value for edgeDist that, as of now, has produced the best results
     * @param bestEdgeDiff the difference bewteen forground and background pixels in the best edge
     * @param lastEdge the last edgeDist used
     * @param lastDiff the difference resulting from the lastEdge 
     */
    private void bolden2Iter(Pixel[][] pixels, int edgeDist, int i, int bestEdgeDist, int bestEdgeDiff, int lastEdgeDist, int lastEdgeDiff, int x)
    {
        //optimization, have these bits just be passed by the handler, reduce ram usage?
        //bruh, this optimization cut ram usage (for this tool) to like ... a fourth what it was 
        //Picture tempCopyPicture = new Picture(this);
        //Pixel[][] pixels = tempCopyPicture.getPixels2D();

        Pixel currentPixel = null;
        Color background = pixels[1][1].getColor();
        //Color invertedBackground = new Color(255 - background.getRed(), 255 - background.getGreen(), 255 - background.getBlue());

        int countBack = 0;
        int countInvertBack = 0;

        //goes through picture again, darkens edges
        ///////////working function/////////////
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < pixels[0].length; col++)
            {
                currentPixel = pixels[row][col];
                //optimization 1: change this to only count, not apply colors
                if (currentPixel.colorDistance(background) > edgeDist)
                {
                    //currentPixel.setColor(invertedBackground);
                    countInvertBack ++;
                }
                else {
                    //currentPixel.setColor(background);
                    countBack ++;
                }
            }
        }

        //iterate to find best edge dist
        if ( i < 25) {
            int newEdgeDist = edgeDist + 10;

            int edgeDiff = Math.abs(countBack - countInvertBack);
            if (edgeDiff < bestEdgeDiff) {bestEdgeDiff = edgeDiff; bestEdgeDist = edgeDist;}

            if (newEdgeDist >=255) newEdgeDist = 5;
            else if (newEdgeDist < 5) newEdgeDist = 255;

            this.bolden2Iter(pixels, newEdgeDist, i + 1, bestEdgeDist, bestEdgeDiff, edgeDist, edgeDiff, x);
            return;
        }
        else {pixels=null;this.bolden(bestEdgeDist);} //finally, actually apply the best edge dist
        return;
    }

    /** small hack, lets Picture use the getExtension method from SimplePicture*/
    public String getExtension() 
    {
        return super.getExtension();
    }

} // this } is the end of class Picture, put all new methods before this