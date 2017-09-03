package uk.co.akm.test.motion.test.examples;

import uk.co.akm.test.motion.particle.Particle;
import uk.co.akm.test.motion.particle.examples.ChargeNearMagnet;
import uk.co.akm.test.motion.test.AbstractTestRun;

/**
 * Created by Thanos Mavroidis on 03/09/2017.
 */
public final class ChargeNearMagnetTestRun extends AbstractTestRun {
    private static final int LENGTH_INDEX = 0;
    private static final int Z_TRAVERSED_INDEX = 1;

    private final double v = 1;
    private final double b = 1;
    private final double theta = Math.PI/4;
    private final double t = 10; // Arbitrary time

    private final double exactLength = v*t;
    private final double exactTraversedZ = v*Math.cos(theta)*t;

    public ChargeNearMagnetTestRun() {
        super(
                new int[]{10, 100, 1000, 10000, 100000, 1000000, 10000000},
                new String[]{"length", "~length", "length error fraction", "end-z", "~end-z", "end-z error fraction"}
        );
    }

    @Override
    protected double duration() {
        return t;
    }

    @Override
    protected Particle initialState() {
        return new ChargeNearMagnet(b, v, theta);
    }

    @Override
    public double exactResult(int index) {
        switch (index) {
            case LENGTH_INDEX: return exactLength;

            case Z_TRAVERSED_INDEX: return exactTraversedZ;

            default: throw new IllegalArgumentException("Illegal result index: " + index);
        }
    }

    @Override
    public double approxResult(int index) {
        switch (index) {
            case LENGTH_INDEX: return particle.length();

            case Z_TRAVERSED_INDEX: return particle.z();

            default: throw new IllegalArgumentException("Illegal result index: " + index);
        }
    }
}
