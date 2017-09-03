package uk.co.akm.test.motion.test.examples;

import uk.co.akm.test.motion.particle.Particle;
import uk.co.akm.test.motion.particle.examples.Firecracker;
import uk.co.akm.test.motion.test.AbstractTestRun;

/**
 * Created by Thanos Mavroidis on 03/09/2017.
 */
public final class FirecrackerTestRun extends AbstractTestRun {
    private final double a = 1;
    private final double t = 10; // Arbitrary time
    private final double exactLength = a*t*t/2;

    public FirecrackerTestRun() {
        super(
                new int[]{10, 100, 1000, 10000, 100000},
                new String[]{"length", "~length", "length error fraction"}
        );
    }

    @Override
    protected double duration() {
        return t;
    }

    @Override
    protected Particle initialState() {
        return new Firecracker(a, 0, 0, 0, 0);
    }

    @Override
    public double exactResult(int index) {
        return exactLength;
    }

    @Override
    public double approxResult(int index) {
        return particle.length();
    }
}
