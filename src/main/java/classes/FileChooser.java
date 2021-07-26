package classes;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.util.Properties;
import java.io.*;
import java.net.*;

/**
 * A class to make working with a file chooser easier. 
 * It uses a JFileChooser to let the user
 * pick a file and returns the chosen file name.
 * based on work by Barbara Ericson ericson@cc.gatech.edu
 * 
 * @author Anthony Michael
 */
public class FileChooser 
{

    /////////////////////// methods /////////////////////////////

    /**
     * Method to get the full path for the passed file name
     * @param fileName the name of a file
     * @return the full path for the file
     */
    public static String getMediaPath(String fileName) 
    {
        String path = null;
        String directory = getMediaDirectory();
        boolean done = true;

        // get the full path
        path = directory + fileName;
        return path;
    }

    /**
     * Method to pick an item using the file chooser
     * @param fileChooser the file Chooser to use
     * @return the path name
     */
    public static String pickPath(JFileChooser fileChooser)
    {
        String path = null;

        /* create a JFrame to be the parent of the file 
         * chooser open dialog if you don't do this then 
         * you may not see the dialog.
         */
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);

        // get the return value from choosing a file
        int returnVal = fileChooser.showOpenDialog(frame);

        // if the return value says the user picked a file 
        if (returnVal == JFileChooser.APPROVE_OPTION)
            path = fileChooser.getSelectedFile().getPath();
        return path;
    }

    /**
     * Method to let the user pick a file and return
     * the full file name as a string.  If the user didn't 
     * pick a file then the file name will be null.
     * @return the full file name of the picked file or null
     */
    public static String pickAFile()
    {
        JFileChooser fileChooser = null;

        // start off the file name as null
        String fileName = null;

        // get the current media directory
        String mediaDir = getMediaDirectory();

        /* create a file for this and check that the directory exists
         * and if it does set the file chooser to use it
         */
        try {
            File file = new File(mediaDir);
            if (file.exists())
                fileChooser = new JFileChooser(file);
        } catch (Exception ex) {
        }

        // if no file chooser yet create one
        if (fileChooser == null)
            fileChooser = new JFileChooser();

        // pick the file
        fileName = pickPath(fileChooser);

        return fileName;
    }
    
    /**
     * Method to let the user pick a file and return
     * the full file name as a string.  If the user didn't 
     * pick a file then the file name will be null.
     * @param title title of the file chooser
     * @return the full file name of the picked file or null
     */
    public static String pickAFile(String title)
    {
        JFileChooser fileChooser = null;
        
        // start off the file name as null
        String fileName = null;
        
        // get the current media directory
        String mediaDir = getMediaDirectory();

        /* create a file for this and check that the directory exists
         * and if it does set the file chooser to use it
         */
        try {
            File file = new File(mediaDir);
            if (file.exists())
                fileChooser = new JFileChooser(file);
        } catch (Exception ex) {
        }

        // if no file chooser yet create one
        if (fileChooser == null)
            fileChooser = new JFileChooser();

        //set title
        fileChooser.setDialogTitle(title);
        
        // pick the file
        fileName = pickPath(fileChooser);

        return fileName;
    }
    
    /**
     * Method to let the user pick a file and return
     * the full file name as a string.  If the user didn't 
     * pick a file then the file name will be null.
     * @param title title of the file chooser
     * @param directory the directory to open the file chooser in
     * @return the full file name of the picked file or null
     */
    public static String pickAFile(String title, String directory)
    {
        JFileChooser fileChooser = null;

        // start off the file name as null
        String fileName = null;

        // if passed directory is null, set it to the default media directory
        if (directory == null) directory = getMediaDirectory();

        /* create a file for this and check that the directory exists
         * and if it does set the file chooser to use it
         */
        try {
            File file = new File(directory);
            if (file.exists())
                fileChooser = new JFileChooser(file);
        } catch (Exception ex) {
        }

        // if no file chooser yet create one
        if (fileChooser == null)
            fileChooser = new JFileChooser();
        
        //set title
        fileChooser.setDialogTitle(title);
        
        // pick the file
        fileName = pickPath(fileChooser);

        return fileName;
    }

    /**
     * Method to get the directory for the media
     * @return the media directory
     */
    public static String getMediaDirectory() 
    {
        String directory = null;
        boolean done = false;
        File dirFile = null;
        
        try {
            //this directory should be the pictures folder on windows, linux, and macOS
            directory = System.getProperty("user.home") + System.getProperty("file.separator") + "Pictures" + System.getProperty("file.separator"); // ~\Pictures\    SHOULD work on windows and linux, idk though
            
            //check if user is running windows, if so, check if they have a onedrive folder at their home directory, if so, change directory to the pictures folder in that, otherwise don't change directory
            if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {//windows
                if ((new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Onedrive" + System.getProperty("file.separator"))).isDirectory()) 
                    directory = System.getProperty("user.home") + System.getProperty("file.separator") + "Onedrive" + System.getProperty("file.separator") + "Pictures" + System.getProperty("file.separator");;
            }
            
            dirFile = new File(directory);
            if (dirFile.exists()) {
                //setMediaPath(directory);
                return directory;
            }
        } catch (Exception e) {}
        /*
        // try to find the images directory
        try {
            // get the URL for where we loaded this class 
            Class currClass = Class.forName("classes.FileChooser");
            URL classURL = currClass.getResource("FileChooser.class");
            URL fileURL = new URL(classURL,"../images/");
            directory = fileURL.getPath();
            directory = URLDecoder.decode(directory, "UTF-8");
            dirFile = new File(directory);
            if (dirFile.exists()) {
                //setMediaPath(directory);
                return directory;
            }
        } catch (Exception ex) {
        }
        */

        return directory;
    }

}
