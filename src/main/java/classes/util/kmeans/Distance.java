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
 * Defines a contract to calculate distance between two feature vectors. The less the
 * calculated distance, the more two items are similar to each other.
 */
public interface Distance {
    /**
     * Calculates the distance between two feature vectors.
     *
     * @param f1 The first set of features.
     * @param f2 The second set of features.
     * @return Calculated distance.
     * @throws IllegalArgumentException If the given feature vectors are invalid.
     */
    <N extends Number, M extends Number> double calculate(Map<String, N> f1, Map<String, M> f2);
}
