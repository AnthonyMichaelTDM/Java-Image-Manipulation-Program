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

package classes.util.kmeans;

import java.util.Map;

/**
 * implements algorithm to calculate distance with euclidean methods
 * 
 * @author Anthony Rubick
 */
public class EuclideanDistance implements Distance {
    
    /**
     * calculates the distance between 2 points, with any amount of dimensions
     * @param f1 a n-th dimensional point
     * @param f2 a n-th dimensional point
     * @return the distance between f1 and f2 across all dimensions
     */
    @Override
    public <N extends Number, M extends Number> double calculate(Map<String, N> f1, Map<String, M> f2) { 
        double sum = 0;
        //sum of squared differences between corresponding entries
        for (String key : f1.keySet()) {
            N v1 = f1.get(key);
            M v2 = f2.get(key);

            if (v1 != null && v2 != null) {
                sum += Math.pow(v2.doubleValue() - v1.doubleValue(), 2);
            }
        }
        //sqrt to compute the actual Euclidean distance
        return Math.sqrt(sum);
    }
}
