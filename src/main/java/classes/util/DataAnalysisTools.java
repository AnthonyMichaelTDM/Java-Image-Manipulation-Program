package classes.util;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * class with a variety of tools regarding data analysis for arrays and arraylists
 * 
 * @author Anthony Rubick
 */
public class DataAnalysisTools {

    

    /**
     * remove duplicates from the passed collection
     * @param <N> the Type of data the collection stores
     * @param data collection to trim
     */
    public static <N extends Number> void removeDuplicates(Collection<N> data) {
        //DATA
        Set<N> set;
        // remove duplicates from picture colors using a hashset
        // Create a new LinkedHashSet
        set = new LinkedHashSet<>();
        // Add The elements to set
        set.addAll(data);
        // Clear the list
        data.clear();
        // add the elements of set
        // with no duplicates to the list
        data.addAll(set);
    }

    /**
     * calculate the standard deviation of the passed collection
     * @param <N> the Type of data the collection stores
     * @param data collection to analyze
     * @return standard deviation of the data collection 
     */
    public static <N extends Number> double calcStandardDeviation(Collection<N> data) {
        //DATA
        double mean = 0.0f;
        double sumXMinusMeanSquared=0.0;
        double sum = 0;
        int n = data.size();
        double sd = 0.0;

        //find standard deviation
        // mean
        for (N i : data) {
            sum += (double) i;
        } // for
        mean = sum / n;

        // standard deviation sqrt( sum( (x-mean)^2 ) / n )
        // sum((x-mean)^2)
        for (N i : data) {
            // (x-mean)^2
            sumXMinusMeanSquared += Math.pow((double)i - mean, 2);
        } // for
        sd = Math.sqrt(sumXMinusMeanSquared / n);

        //return
        return sd;
    }

    /**
     * calculate the mean of the passed collection
     * @param data collection to analyze
     * @return mean of the data collection 
     */
    public static double calcMean(Collection<Integer> data) {
        //DATA
        int sum = 0;
        int n = data.size();
        double mean = 0.0f;

        //find mean
        for (int i : data) {
            sum += i;
        } // for
        mean = sum / n;
        
        //return
        return mean;
    }


}