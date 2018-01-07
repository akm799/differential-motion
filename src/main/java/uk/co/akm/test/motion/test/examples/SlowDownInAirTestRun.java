package uk.co.akm.test.motion.test.examples;

import uk.co.akm.test.motion.particle.Particle;
import uk.co.akm.test.motion.particle.examples.FreeParticleWithMassInAir;
import uk.co.akm.test.motion.test.AbstractTestRun;
import uk.co.akm.test.motion.test.result.Result;
import uk.co.akm.test.motion.test.result.impl.DoubleResult;
import uk.co.akm.test.motion.test.result.impl.PointResult;

/**
 * Created by Thanos Mavroidis on 07/01/2018.
 */
public final class SlowDownInAirTestRun extends AbstractTestRun {
    private static final int TERMINAL_VELOCITY_INDEX = 0;
    private static final int ENDPOINT_INDEX = 1;

    private final double v0 = 2.5; // Initial velocity of 2.5 m/sec
    private final double m = 168;
    private final double k = 10; // Should give maximum distance of 42 metres.
    private final double t = 1000; // Sufficiently long time to allow (near) reach of the zero terminal velocity.
    private final double vt = 0;
    private final double xLimit = v0*m/k;

    public SlowDownInAirTestRun() {
        super(
                new int[]{10, 100, 1000, 10000, 100000, 1000000, 10000000},
                new String[]{"terminal-velocity", "~terminal-velocity", "terminal-velocity error fraction", "end-point", "~end-point", "end-point error"}
        );
    }

    @Override
    protected double duration() {
        return t;
    }

    @Override
    protected Particle initialState() {
        return new FreeParticleWithMassInAir(m, k, v0);
    }

    @Override
    public Result exactResult(int index) {
        switch (index) {
            case TERMINAL_VELOCITY_INDEX: return new DoubleResult(vt);

            case ENDPOINT_INDEX: return new PointResult(xLimit, 0, 0);

            default: throw new IllegalArgumentException("Illegal result index: " + index);
        }

    }

    @Override
    public Result approxResult(int index) {
        switch (index) {
            case TERMINAL_VELOCITY_INDEX: return new DoubleResult(particle.vx());

            case ENDPOINT_INDEX: return new PointResult(particle);

            default: throw new IllegalArgumentException("Illegal result index: " + index);
        }
    }
}
