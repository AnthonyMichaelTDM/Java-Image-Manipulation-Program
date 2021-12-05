package classes.util.kmeans;

import java.util.Map;
import java.util.Objects;

public class Centroid {
    //DATA
    private final Map<String, Double> coordinates;

    /**
     * constructor
     * @param coordinates coordinates of the centroid
     */
    public Centroid(Map<String, Double> coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * 
     * @return coordinates
     */
    public Map<String, Double> getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Centroid centroid = (Centroid) o;
        return Objects.equals(getCoordinates(), centroid.getCoordinates());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCoordinates());
    }

    @Override
    public String toString() {
        return "Centroid " + coordinates;
    }
}
