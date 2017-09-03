package uk.co.akm.test.motion.test;

import uk.co.akm.test.motion.particle.MotionIntegrator;
import uk.co.akm.test.motion.particle.Particle;
import uk.co.akm.test.motion.test.result.Result;

/**
 * Created by Thanos Mavroidis on 03/09/2017.
 */
public abstract class AbstractTestRun implements TestRun {
    private final int[] nSteps;
    private final String[] headers;

    protected Particle particle;

    protected AbstractTestRun(int[] nSteps, String[] headers) {
        if (headers.length%N_COLUMNS_PER_RESULT != 0) {
            throw new IllegalArgumentException("Number of headers (" + headers.length + ") is not divisible by " + N_COLUMNS_PER_RESULT + ".");
        }

        this.nSteps = nSteps;
        this.headers = headers;
    }

    @Override
    public final int[] numberOfSteps() {
        return nSteps;
    }

    @Override
    public final  String[] headers() {
        return headers;
    }

    @Override
    public final int numberOfTestResults() {
        return headers.length/N_COLUMNS_PER_RESULT;
    }

    @Override
    public final void runTest(int n) {
        final double t = duration();
        particle = initialState();
        MotionIntegrator.integrateMotion(particle, t, n);
    }

    protected abstract double duration();

    protected abstract Particle initialState();

    @Override
    public final double resultErrorFraction(int index) {
        final Result exact = exactResult(index);
        final Result approx = approxResult(index);

        return exact.absDifferenceFraction(approx);
    }
}
