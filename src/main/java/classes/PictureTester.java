package classes;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

//import classes.util.kmeans.KMeans;
//import javax.swing.JFileChooser;
//import java.io.*;

/**
 * This class contains class (static) methods
 * that will help you test the Picture class 
 * methods.  Uncomment the methods and the code
 * in the main to test.
 * 
 * @author Anthony Rubick 
 */
public class PictureTester
{
    /** Method to test mirrorVertical */
    public static void testMirrorVertical()
    {
        Picture caterpillar = new Picture("caterpillar.jpg");
        caterpillar.explore();
        caterpillar.mirrorVertical();
        caterpillar.explore();
    }

    /** Method to test edgeDetection */
    public static void testEdgeDetection()
    {
        /*
        Picture swan = new Picture("swan.jpg");
        swan.edgeDetection(10);
        swan.explore();
        
        Picture squidward = new Picture("squidward.jpg");
        squidward.edgeDetection(10);
        squidward.explore();
        
        Picture femaleLionAndHall = new Picture("femaleLionAndHall.jpg");
        femaleLionAndHall.edgeDetection(10);
        femaleLionAndHall.explore();
        
        Picture butterfly1 = new Picture("butterfly1.jpg");
        butterfly1.edgeDetection(10);
        butterfly1.explore();
        
        Picture arch = new Picture("arch.jpg");
        arch.edgeDetection(10);
        arch.explore();
        */
        Picture test = new Picture("test1.jpg");
        test.edgeDetection(10);
        test.explore();

    }

    /** Method to test edgeDetection2() */
    public static void testEdgeDetection2()
    {
        /*Picture swan = new Picture("swan.jpg");
        swan.edgeDetection2(10);
        swan.explore();
        
        Picture squidward = new Picture("squidward.jpg");
        squidward.edgeDetection2(10);
        squidward.explore();
        
        Picture femaleLionAndHall = new Picture("femaleLionAndHall.jpg");
        femaleLionAndHall.edgeDetection2(10);
        femaleLionAndHall.explore();
        
        Picture butterfly1 = new Picture("butterfly1.jpg");
        butterfly1.edgeDetection2(10);
        butterfly1.explore();
        
        Picture arch = new Picture("arch.jpg");
        arch.edgeDetection2(10);
        arch.explore();
        
        Picture blueMotorcycle = new Picture("blueMotorcycle.jpg");
        blueMotorcycle.edgeDetection2(10);
        blueMotorcycle.explore();
        
        Picture redMotorcycle = new Picture("redMotorcycle.jpg");
        redMotorcycle.edgeDetection2(10);
        redMotorcycle.explore();
        */

        /*
        Picture thruDoor = new Picture("thruDoor.jpg");
        thruDoor.edgeDetection2(10);
        thruDoor.explore();
        
        Picture wall = new Picture("wall.jpg");
        wall.edgeDetection2(10);
        wall.explore();
        
        Picture whiteFlower = new Picture("whiteFlower.jpg");
        whiteFlower.edgeDetection2(10);
        whiteFlower.explore();
        
        Picture beach = new Picture("beach.jpg");
        beach.edgeDetection2(10);
        beach.explore();
        
        Picture robot = new Picture("robot.jpg");
        robot.edgeDetection2(10);
        robot.explore();
        
        Picture water = new Picture("water.jpg");
        water.edgeDetection2(10);
        water.explore();
        
        Picture koala = new Picture("koala.jpg");
        koala.edgeDetection2(10);
        koala.explore();*/

        Picture test = new Picture("test1.jpg");
        test.edgeDetection2(10,1);
        test.explore();
    }
    
    /** Method to test all the simplify colors modes*/
    public static void testSimplifyColors() {
        //open a file of the users choice, then apply the 4 simplify color filters to it, and save each one
        //DATA, the images
        //open a file of the users choice, then apply the 4 simplify color filters to it, and save each one

        Picture basePic;
        try {
            basePic=new Picture(FileChooser.pickAFile());
            if (basePic.getFileName().isBlank()) throw new Exception();
        } catch (Exception e) {
            return;
        }
        Picture grayPic = new Picture(basePic);
        Picture faithfulPic = new Picture(basePic);
        Picture faithfullPlusPic = new Picture(basePic);
        Picture BalancePic = new Picture(basePic);
        Picture BalancePlusPic = new Picture(basePic);
        Picture sdMeanPic = new Picture(basePic);
        Picture zedPic = new Picture(basePic);
        Picture zedPlusPic = new Picture(basePic);
        Picture KMeanPic = new Picture (basePic);
        String extension = basePic.getFileName().substring(basePic.getFileName().lastIndexOf("."));
        String pathName = basePic.getFileName().substring(0, basePic.getFileName().lastIndexOf("."));
        
        //apply filters and save
        grayPic.simplifyColors(1);
        grayPic.write(pathName + "-grayscale" + extension);

        faithfulPic.simplifyColors(2);
        faithfulPic.write(pathName + "-faithful" + extension);

        faithfulPic.simplifyColors(2);
        faithfulPic.write(pathName + "-faithful" + extension);

        faithfullPlusPic.simplifyColors(3);
        faithfullPlusPic.write(pathName + "-faithful+" + extension);

        BalancePic.simplifyColors(4);
        BalancePic.write(pathName + "-balance" + extension);

        BalancePlusPic.simplifyColors(5);
        BalancePlusPic.write(pathName + "-balance+" + extension);

        sdMeanPic.simplifyColors(6);
        sdMeanPic.write(pathName + "-SD+mean" + extension);

        zedPic.simplifyColors(7);
        zedPic.write(pathName + "-zed" + extension);

        zedPlusPic.simplifyColors(8);
        zedPlusPic.write(pathName + "-zed+" + extension);

        KMeanPic.simplifyColors(9);
        KMeanPic.write(pathName + "-K-Mean" + extension);



        System.out.println("done");

        testSimplifyColors();

        return;
    }

    /**
     * applies various filters and whatnot to given image, or all .jpg/.png images in given directory
     */
    public static void createWallpapers(String fileOrDirectoryPath) {
        //data
        File file = new File(fileOrDirectoryPath);
        ArrayList<String> names = new ArrayList<String>(), validPaths = new ArrayList<String>();
        boolean isDirectory = false;
        Picture basePic, grayPic, grayscalePic, faithfulPic, faithfullPlusPic, BalancePic, BalancePlusPic, sdMeanPic, zedPic, zedPlusPic,KMeanPic;
        String extension, pathName;
        int n=0;

        System.out.println(fileOrDirectoryPath);

        //find out if a file or directory was given
        if (file.isDirectory()) {
            isDirectory=true;
            //fill names arraylist with contents of the directory
            names.addAll(Arrays.asList(file.list()));
        }
        else if (file.isFile()) {
            //just have the file in the names arraylist
            names.add(fileOrDirectoryPath);
        }
        else {
            System.out.println("error accessing " + fileOrDirectoryPath);
            return;
        }
        //fill validNames arraylist with all image files in names
        for (String s : names) {
            //System.out.println(s);
            //data
            File f;

            if (isDirectory) {
                f = new File(fileOrDirectoryPath + s);
            }
            else {
                f = new File(fileOrDirectoryPath);                
            }
            //figure out if f is a file, if it is, is it an image?, only add it if the answer to both questions is yess
            if (f.isFile()) {
                extension = f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf(".")).toLowerCase();
                if ((extension.equals(".jpg") || extension.equals(".png") || extension.equals(".jpeg") || extension.equals(".bmp")) && !f.getAbsolutePath().contains("-ByJIMP")) {
                    //valid image file
                    validPaths.add(f.getAbsolutePath());
                    //System.out.println(s);
                } 
            }
        }

        //apply filters to remaining valid images
        for (String s : validPaths) {
            //data
            basePic = new Picture (s);
            grayPic = new Picture(basePic);
            grayscalePic = new Picture(basePic);
            faithfulPic = new Picture(basePic);
            faithfullPlusPic = new Picture(basePic);
            BalancePic = new Picture(basePic);
            BalancePlusPic = new Picture(basePic);
            sdMeanPic = new Picture(basePic);
            zedPic = new Picture(basePic);
            zedPlusPic = new Picture(basePic);
            KMeanPic = new Picture(basePic);
            extension = basePic.getFileName().substring(basePic.getFileName().lastIndexOf("."));
            pathName = basePic.getFileName().substring(0, basePic.getFileName().lastIndexOf("."));
        
            //apply filters and save
            grayPic.simplifyColors(1);
            grayPic.write(pathName + "-gray-ByJIMP" + extension);

            grayscalePic.grayscale();
            grayscalePic.write(pathName + "-grayscale-ByJIMP" + extension);

            faithfulPic.simplifyColors(2);
            faithfulPic.write(pathName + "-faithful-ByJIMP" + extension);

            faithfulPic.simplifyColors(2);
            faithfulPic.write(pathName + "-faithful-ByJIMP" + extension);

            faithfullPlusPic.simplifyColors(3);
            faithfullPlusPic.write(pathName + "-faithful+-ByJIMP" + extension);

            BalancePic.simplifyColors(4);
            BalancePic.write(pathName + "-balance-ByJIMP" + extension);

            BalancePlusPic.simplifyColors(5);
            BalancePlusPic.write(pathName + "-balance+-ByJIMP" + extension);

            sdMeanPic.simplifyColors(6);
            sdMeanPic.write(pathName + "-SD+mean-ByJIMP" + extension);
            sdMeanPic.grayscale();
            sdMeanPic.write(pathName + "-SD+mean-grayscaled-ByJIMP" + extension);

            zedPic.simplifyColors(7);
            zedPic.write(pathName + "-zed-ByJIMP" + extension);

            zedPlusPic.simplifyColors(8);
            zedPlusPic.write(pathName + "-zed+-ByJIMP" + extension);

            KMeanPic.simplifyColors(9);
            KMeanPic.write(pathName + "-K-Mean1-ByJIMP" + extension);
            KMeanPic = new Picture(basePic);
            KMeanPic.kMeansSimplify(10, 20);
            KMeanPic.write(pathName + "-K-Mean2-ByJIMP" + extension);

            n++;
            System.out.println(n + "/" + validPaths.size() + ": done with " + s);
        }
        System.out.println("done with all :D\nenjoy!");
        return;
    }
    
    public static void testBolden() {
        //data
        Picture bolden = new Picture(FileChooser.pickAFile());
        String extension = bolden.getFileName().substring(bolden.getFileName().indexOf("."));
        String pathName = bolden.getFileName().substring(0,bolden.getFileName().indexOf("."));
        
        bolden.bolden2();
        bolden.grayscale();
        bolden.write(pathName + "-boldened" + extension);
    }
    
    /** Main method for testing.  Every class can have a main
     * method in Java */
    public static void main(String[] args)
    {
        // uncomment a call here to run a test
        // and comment out the ones you don't want
        // to run
        //testNegate();
        //testGrayscale();
        //testFixUnderwater();
        //testMirrorVertical();
        //testMirrorHorizontal();
        //testMirrorHorizontalBotToTop();
        //testMirrorDiagonal();
        //testCollage();
        //testMyCollage();
        //testCopy();
        //testCopy2();
        //testEdgeDetection();
        //testEdgeDetection2();
        //testSimplifyColors();
        //testBolden();
        //testChromakey();
        //testEncodeAndDecode();
        //testGetCountRedOverValue(250);
        //testSetRedToHalfValueInTopHalf();
        //testClearBlueOverValue(200);
        //testGetAverageForColumn(0);
        //createWallpapers("" + FileChooser.getMediaDirectory() + "Backgrounds" + System.getProperty("file.separator"));

        createWallpapers("" + FileChooser.getMediaDirectory() + "pixlab-images" + System.getProperty("file.separator") + "Backgrounds" + System.getProperty("file.separator"));
    }

    ////////////////////// tester methods i made ///////////////////////////////////////
    /** method to test keepOnlyBlue()*/
    public static void testKeepOnlyBlue()
    {
        Picture beach = new Picture("beach.jpg");
        beach.explore();
        beach.keepOnlyBlue();
        beach.explore();
    }

    /** method to test negate()*/
    public static void testNegate()
    {
        Picture beach = new Picture("beach.jpg");
        beach.explore();
        beach.negate();
        beach.explore();
    }

    /** method to test grayscale()*/
    public static void testGrayscale()
    {
        Picture beach = new Picture("beach.jpg");
        beach.explore();
        beach.grayscale();
        beach.explore();
    }

    /** method to test mirrorHorizontal */
    public static void testMirrorHorizontal()
    {
        Picture redMotorcycle = new Picture("redMotorcycle.jpg");
        redMotorcycle.explore();
        redMotorcycle.mirrorHorizontal();
        redMotorcycle.explore();
    }

    /** method to test mirrorHorizontalBotToTop() */
    public static void testMirrorHorizontalBotToTop()
    {
        Picture beach = new Picture("redMotorcycle.jpg");
        beach.explore();
        beach.mirrorHorizontalBotToTop();
        beach.explore();
    }

    /** method to test copy2() */
    public static void testCopy2()
    {
        Picture beach = new Picture("beach.jpg");
        Picture water = new Picture("water.jpg");

        beach.explore();
        beach.copy2(water, 252,454,290,600);
        beach.explore();
    }
} 