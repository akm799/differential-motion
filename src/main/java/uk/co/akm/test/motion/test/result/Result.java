package uk.co.akm.test.motion.test.result;

/**
 * Created by Thanos Mavroidis on 03/09/2017.
 */
public interface Result {

    double absDifference(Result other);

    double absDifferenceFraction(Result other);
}
