import java.awt.Color;
/**
 * a main class, for testing and running the program
 *
 * @author Anthony Rubick
 * @version (a version number or a date)
 */
public class Main
{
    public static void main( String arg[]) 
    {
        Picture pix = new Picture(500,600);
        pix.explore();
        
        
        Color color = new Color(-16645117);
        System.out.println(color.toString());
        float[] hsbValues = new float[3];
        float[] fnsHSBvalues = new float[3];
        fnsHSBvalues = Color.RGBtoHSB(color.getRed(),color.getGreen(),color.getBlue(), fnsHSBvalues);
        System.out.println(fnsHSBvalues[0] + " + " + fnsHSBvalues[1] + " + " + fnsHSBvalues[2]);
        
    }
}
