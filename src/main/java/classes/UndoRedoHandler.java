package classes;


/**
 * Write a description of class UndoRedoHandler here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class UndoRedoHandler
{
    /**
     * idea, every time the user applies a tool, send a call to this class which
     * includes the tool used (the currentTool[][] 2d array should work for this)
     * as well as an arraylist/array that has the parameters of the tool
     * 
     * need to have a list/file of all tools applied, and 
     * when asked to undo,
     * reload the original image and apply all tools up to the most recent, 
     * when asked to redo, 
     * either do nothing, or apply the tool most recently undone
     * 
     * in the future, maybe cache images everytime a tool is applied
     */
    
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class UndoRedoHandler
     */
    public UndoRedoHandler()
    {
        // initialise instance variables
        x = 0;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public int sampleMethod(int y)
    {
        // put your code here
        return x + y;
    }
}
