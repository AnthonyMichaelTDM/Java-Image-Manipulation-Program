package classes.util.kmeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * implements the k-means clustering algorithm
 */
public class KMeans {
    
    private KMeans() {
        throw new IllegalAccessError("You shouldn't call this constructor");
    }

    /**
     * Will be used to generate random numbers.
     */
    private static final Random random = new Random();

    /**
     * Performs the K-Means clustering algorithm on the given dataset.
     *
     * @param records       The dataset.
     * @param k             Number of Clusters.
     * @param distance      To calculate the distance between two items.
     * @param maxIterations Upper bound for the number of iterations.
     * @return K clusters along with their features.
     */
    public static <N extends Number> Map<Centroid, List<Record<N>>> fit(List<Record<N>> records, int k, Distance distance, int maxIterations) {
        applyPreconditions(records, k, distance, maxIterations);

        List<Centroid> centroids = randomCentroids(records, k);
        Map<Centroid, List<Record<N>>> clusters = new HashMap<>();
        Map<Centroid, List<Record<N>>> lastState = new HashMap<>();

        // iterate for a pre-defined number of times
        for (int i = 0; i < maxIterations; i++) {
            boolean isLastIteration = i == maxIterations - 1;

            // in each iteration we should find the nearest centroid for each record
            for (Record<N> record : records) {
                Centroid centroid = nearestCentroid(record, centroids, distance);
                assignToCluster(clusters, record, centroid);
            }

            // if the assignment does not change, then the algorithm terminates
            boolean shouldTerminate = isLastIteration || clusters.equals(lastState);
            lastState = clusters;
            if (shouldTerminate) {
                break;
            }

            // at the end of each iteration we should relocate the centroids
            centroids = relocateCentroids(clusters);
            clusters = new HashMap<>();
        }

        return lastState;
    }

    /**
     * Move all cluster centroids to the average of all assigned features.
     *
     * @param clusters The current cluster configuration.
     * @return Collection of new and relocated centroids.
     */
    private static <N extends Number> List<Centroid> relocateCentroids(Map<Centroid, List<Record<N>>> clusters) {
        return clusters.entrySet().stream().map(e -> average(e.getKey(), e.getValue())).collect(toList());
    }

    /**
     * Moves the given centroid to the average position of all assigned features. If
     * the centroid has no feature in its cluster, then there would be no need for a
     * relocation. Otherwise, for each entry we calculate the average of all records
     * first by summing all the entries and then dividing the final summation value by
     * the number of records.
     *
     * @param centroid The centroid to move.
     * @param records  The assigned features.
     * @return The moved centroid.
     */
    private static <N extends Number> Centroid average(Centroid centroid, List<Record<N>> records) {
        // if this cluster is empty, then we shouldn't move the centroid
        if (records == null || records.isEmpty()) {
            return centroid;
        }

        // Since some records don't have all possible attributes, we initialize
        // average coordinates equal to current centroid coordinates
        Map<String, Double> average = centroid.getCoordinates();

        // The average function works correctly if we clear all coordinates corresponding
        // to present record attributes
        records.stream().flatMap(e -> e.getFeatures().keySet().stream()).forEach(k -> average.put(k, 0.0));

        for (Record<N> record : records) {
            record.getFeatures().forEach((k, v) -> average.compute(k, (k1, currentValue) -> v.doubleValue() + currentValue));
        }

        average.forEach((k, v) -> average.put(k, v / records.size()));

        return new Centroid(average);
    }

    /**
     * Assigns a feature vector to the given centroid. If this is the first assignment for this centroid,
     * first we should create the list.
     *
     * @param clusters The current cluster configuration.
     * @param record   The feature vector.
     * @param centroid The centroid.
     */
    private static <N extends Number> void assignToCluster(Map<Centroid, List<Record<N>>> clusters, Record<N> record, Centroid centroid) {
        clusters.compute(centroid, (key, list) -> {
            if (list == null) {
                list = new ArrayList<>();
            }

            list.add(record);
            return list;
        });
    }

    /**
     * With the help of the given distance calculator, iterates through centroids and finds the
     * nearest one to the given record.
     *
     * @param record    The feature vector to find a centroid for.
     * @param centroids Collection of all centroids.
     * @param distance  To calculate the distance between two items.
     * @return The nearest centroid to the given feature vector.
     */
    private static <N extends Number> Centroid nearestCentroid(Record<N> record, List<Centroid> centroids, Distance distance) {
        double minimumDistance = Double.MAX_VALUE;
        Centroid nearest = null;

        for (Centroid centroid : centroids) {
            double currentDistance = distance.calculate(record.getFeatures(), centroid.getCoordinates());

            if (currentDistance < minimumDistance) {
                minimumDistance = currentDistance;
                nearest = centroid;
            }
        }

        return nearest;
    }

    /**
     * Generates k random centroids. Before kicking-off the centroid generation process,
     * first we calculate the possible value range for each attribute. Then when
     * we're going to generate the centroids, we generate random coordinates in
     * the [min, max] range for each attribute.
     *
     * @param records The dataset which helps to calculate the [min, max] range for
     *                each attribute.
     * @param k       Number of clusters.
     * @return Collections of randomly generated centroids.
     */
    private static <N extends Number> List<Centroid> randomCentroids(List<Record<N>> records, int k) {
        List<Centroid> centroids = new ArrayList<>();
        Map<String, N> maxs = new HashMap<>();
        Map<String, N> mins = new HashMap<>();

        for (Record<N> record : records) { //seems really slow, optimize this soom
            record
              .getFeatures()
              .forEach((key, value) -> {
                  // compares the value with the current max and choose the bigger value between them
                  maxs.compute(key, (k1, max) -> max == null || value.doubleValue() > max.doubleValue() ? value : max);

                  // compare the value with the current min and choose the smaller value between them
                  mins.compute(key, (k1, min) -> min == null || value.doubleValue() < min.doubleValue() ? value : min);
              });
        }

        Set<String> attributes = records.stream().flatMap(e -> e.getFeatures().keySet().stream()).collect(toSet());

        for (int i = 0; i < k; i++) {
            Map<String, Double> coordinates = new HashMap<>();
            for (String attribute : attributes) {
                N max = maxs.get(attribute);
                N min = mins.get(attribute);
                coordinates.put(attribute, random.nextDouble() * (max.doubleValue() - min.doubleValue()) + min.doubleValue());
            }

            centroids.add(new Centroid(coordinates));
        }

        return centroids;
    }

    private static <N extends Number> void applyPreconditions(List<Record<N>> records, int k, Distance distance, int maxIterations) {
        if (records == null || records.isEmpty()) {
            throw new IllegalArgumentException("The dataset can't be empty");
        }

        if (k <= 1) {
            throw new IllegalArgumentException("It doesn't make sense to have less than or equal to 1 cluster");
        }

        if (distance == null) {
            throw new IllegalArgumentException("The distance calculator is required");
        }

        if (maxIterations <= 0) {
            throw new IllegalArgumentException("Max iterations should be a positive number");
        }
    }
}

