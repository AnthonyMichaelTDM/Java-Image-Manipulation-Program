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

package classes.util;

/**
 * class containing various tools regarding colors
 * 
 * @author Anthony Rubick
 */
public class ColorTools {

    /**
     * class that returns the Hue of a color with the given r g and b values
     * @param r red channel (0-255)
     * @param g green channel (0-255)
     * @param b blue channel (0-255)
     * @return degree of the hue
     */
    public static int getHue(int r, int g, int b) {
        float cmax = Math.max(r, Math.max(g, b));
        float cmin = Math.min(r, Math.min(g, b));
        float deltaC = cmax - cmin;
        float h;
        //find hue as a fraction
        if (deltaC < 0.00000001f) { //practically zero
            return 0;
        }
        else if (cmax == r) {
            h = (((g - b) / deltaC));
        } else if (cmax == g) {
            h = (((b - r) / deltaC) + 2);
        } else {
            h = (((r - g) / deltaC) + 4);
        } // if else-if else
        //convert hue to degrees
        h = (h*60 + 360) % 360;

        return Math.round(h);
    } // public int[] getHSV
    
    /**
     * class that returns the HSB values of a color with the given r g and b values
     * @param r red channel (0.0f-1.0f)
     * @param g green channel (0.0f-1.0f)
     * @param b blue channel (0.0f-1.0f)
     * @return float array containing the equivalent HSB values
     */
    public static float[] getHSL(float r, float g, float b) {
        //DATA
        float cmax = Math.max(r, Math.max(g, b));
        float cmin = Math.min(r, Math.min(g, b));
        float deltaC = cmax - cmin + 0.00000001f;
        float sumC = cmax + cmin;
        float h, s, l;
        // find hue as a fraction
        if (cmax == r) {
            h = (((g - b + 0.00000001f) / deltaC));
        } else if (cmax == g) {
            h = (((b - r + 0.00000001f) / deltaC) + 2.0f);
        } else {
            h = (((r - g + 0.00000001f) / deltaC) + 4.0f);
        } // if else-if else
        l = (sumC) / 2;

        // find saturation
        if (Math.abs(cmax - cmin) < 0.00000001f) 
        {
            s = 0.0f; // if min and max are close enough
            h = 0.0f;
        }
        else if (l <= 0.5f)
        {
            s = deltaC / sumC;
        }
        else
        {
            s = deltaC / (2.0f - deltaC);
        }

        // return
        return new float[]{h,s,l};
    } // public int[] getHSV
}
