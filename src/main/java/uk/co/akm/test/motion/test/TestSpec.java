package uk.co.akm.test.motion.test;



/**
 * Created by Thanos Mavroidis on 03/09/2017.
 */
public interface TestSpec {
    int N_COLUMNS_PER_RESULT = 3;

    int[] numberOfSteps();

    String[] headers();

    int numberOfTestResults();

    double exactResult(int index);

    double approxResult(int index);

    double resultErrorFraction(int index);
}
