package classes;

import javax.swing.JFileChooser;
import java.io.*;

/**
 * This class contains class (static) methods
 * that will help you test the Picture class 
 * methods.  Uncomment the methods and the code
 * in the main to test.
 * 
 * @author Anthony Michael 
 */
public class PictureTester
{
    /** Method to test zeroBlue */
    public static void testZeroBlue()
    {
        Picture beach = new Picture("beach.jpg");
        beach.explore();
        beach.zeroBlue();
        beach.explore();
    }

    /** Method to test mirrorVertical */
    public static void testMirrorVertical()
    {
        Picture caterpillar = new Picture("caterpillar.jpg");
        caterpillar.explore();
        caterpillar.mirrorVertical();
        caterpillar.explore();
    }

    /** Method to test mirrorTemple */
    public static void testMirrorTemple()
    {
        Picture temple = new Picture("temple.jpg");
        temple.explore();
        temple.mirrorTemple();
        temple.explore();
    }

    /** Method to test the collage method */
    public static void testCollage()
    {
        Picture canvas = new Picture("640x480.jpg");
        canvas.createCollage();
        canvas.explore();
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
        Picture basePic = new Picture(FileChooser.pickAFile());
        Picture grayPic = new Picture(basePic);
        Picture faithfulPic = new Picture(basePic);
        Picture faithfullPlusPic = new Picture(basePic);
        Picture faithfullBalancePic = new Picture(basePic);
        Picture faithfullBalancePlusPic = new Picture(basePic);
        String extension = basePic.getFileName().substring(basePic.getFileName().indexOf("."));
        String pathName = basePic.getFileName().substring(0, basePic.getFileName().indexOf("."));
        
        //1 = 5grayscale, 2 = faithful, 3 = faithful+, 4= faithful-balance, 5=faithful-balance+
        //apply filters and save
        grayPic.simplifyColors(1);
        grayPic.write(pathName + "-grayscale" + extension);
        faithfulPic.simplifyColors(2);
        faithfulPic.write(pathName + "-faithful" + extension);
        faithfulPic.simplifyColors(2);
        faithfulPic.write(pathName + "-faithful" + extension);
        faithfullPlusPic.simplifyColors(3);
        faithfullPlusPic.write(pathName + "-faithful+" + extension);
        faithfullBalancePic.simplifyColors(4);
        faithfullBalancePic.write(pathName + "-faithful-balance" + extension);
        faithfullBalancePlusPic.simplifyColors(5);
        faithfullBalancePlusPic.write(pathName + "-faithful-balance+" + extension);
        System.out.println("done");
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
        //testZeroBlue();
        //testKeepOnlyBlue();
        //testKeepOnlyRed();
        //testKeepOnlyGreen();
        //testNegate();
        //testGrayscale();
        //testFixUnderwater();
        //testMirrorVertical();
        //testMirrorHorizontal();
        //testMirrorHorizontalBotToTop();
        //testMirrorTemple();
        //testMirrorArms();
        //testMirrorGull();
        //testMirrorDiagonal();
        //testCollage();
        //testMyCollage();
        //testCopy();
        //testCopy2();
        //testEdgeDetection();
        //testEdgeDetection2();
        //testSimplifyColors();
        testBolden();
        //testChromakey();
        //testEncodeAndDecode();
        //testGetCountRedOverValue(250);
        //testSetRedToHalfValueInTopHalf();
        //testClearBlueOverValue(200);
        //testGetAverageForColumn(0);
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

    /** method to test mirrorArms() */
    public static void testMirrorArms()
    {
        Picture snowman = new Picture("snowman.jpg");
        snowman.explore();
        snowman.mirrorArms();
        snowman.explore();
    }

    /** method to test mirrorGull() */
    public static void testMirrorGull()
    {
        Picture seagull = new Picture("seagull.jpg");
        seagull.explore();
        seagull.mirrorGull();
        seagull.explore();
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

    /** method to test myCollage() */
    public static void testMyCollage()
    {
        Picture canvas = new Picture("640x480.jpg");

        canvas.explore();
        canvas.myCollage();
        canvas.explore();
    }
} 