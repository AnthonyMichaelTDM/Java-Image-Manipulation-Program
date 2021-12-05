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
