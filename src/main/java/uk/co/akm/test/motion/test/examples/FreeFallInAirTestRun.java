package uk.co.akm.test.motion.test.examples;

import uk.co.akm.test.motion.particle.Particle;
import uk.co.akm.test.motion.particle.examples.ProjectileInAir;
import uk.co.akm.test.motion.test.AbstractTestRun;
import uk.co.akm.test.motion.test.result.Result;
import uk.co.akm.test.motion.test.result.impl.DoubleResult;
import uk.co.akm.test.motion.test.result.impl.PointResult;

/**
 * Created by Thanos Mavroidis on 04/09/2017.
 */
public final class FreeFallInAirTestRun extends AbstractTestRun {
    private static final int TERMINAL_VELOCITY_INDEX = 0;
    private static final int ENDPOINT_INDEX = 1;
    private static final int LENGTH_INDEX = 2;

    private final double g = 9.81;
    private final double k = 0.0654; // Should give terminal velocity of 150 m/s.
    private final double t = 1000; // Sufficiently long time to allow (near) reach of terminal velocity.
    private final double vt = -g/k;
    private final double y0 = Math.abs( (g/k) * ((1 - Math.exp(-k*t))/k - t) ); // Initial height so that the final height is zero.

    public FreeFallInAirTestRun() {
        super(
                new int[]{10, 100, 1000, 10000, 100000, 1000000, 10000000},
                new String[]{"terminal-velocity", "~terminal-velocity", "terminal-velocity error fraction", "end-point", "~end-point", "end-point error", "length", "~length", "length error fraction"}
        );
    }

    @Override
    protected double duration() {
        return t;
    }

    @Override
    protected Particle initialState() {
        return new ProjectileInAir(g, k, 0, 0, 0, 0, y0, 0);
    }

    @Override
    public Result exactResult(int index) {
        switch (index) {
            case TERMINAL_VELOCITY_INDEX: return new DoubleResult(vt);

            case ENDPOINT_INDEX: return new PointResult(0, 0, 0);

            case LENGTH_INDEX: return new DoubleResult(y0);

            default: throw new IllegalArgumentException("Illegal result index: " + index);
        }

    }

    @Override
    public Result approxResult(int index) {
        switch (index) {
            case TERMINAL_VELOCITY_INDEX: return new DoubleResult(particle.vy());

            case ENDPOINT_INDEX: return new PointResult(particle);

            case LENGTH_INDEX: return new DoubleResult(particle.length());

            default: throw new IllegalArgumentException("Illegal result index: " + index);
        }
    }
}
