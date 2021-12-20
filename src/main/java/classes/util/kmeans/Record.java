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
import java.util.Objects;

/**
 * Encapsulates all feature values for a few attributes. Optionally each record
 * can be described with the {@link #description} field.
 */
public class Record<N extends Number> {

    /**
     * The record description. For example, this can be the artist name for the famous musician
     * example.
     */
    private final String description;

    /**
     * Encapsulates all attributes and their corresponding values, i.e. features.
     */
    private final Map<String, N> features;

    public Record(String description, Map<String, N> features) {
        this.description = description;
        this.features = features;
    }

    public Record(Map<String, N> features) {
        this("", features);
    }

    public String getDescription() {
        return description;
    }

    public Map<String, N> getFeatures() {
        return features;
    }

    @Override
    public String toString() {
        String prefix = description == null || description
          .trim()
          .isEmpty() ? "Record" : description;

        return prefix + ": " + features;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        Record<?> record = (Record<?>) o;
        return Objects.equals(getDescription(), record.getDescription()) && Objects.equals(getFeatures(), record.getFeatures());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription(), getFeatures());
    }
}
