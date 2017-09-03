package uk.co.akm.test.motion.test.examples;

import uk.co.akm.test.motion.particle.Particle;
import uk.co.akm.test.motion.particle.examples.Projectile;
import uk.co.akm.test.motion.test.AbstractTestRun;

/**
 * Created by Thanos Mavroidis on 03/09/2017.
 */
public final class ProjectileTestRun extends AbstractTestRun {
    private static final int RANGE_INDEX = 0;
    private static final int END_POINT_INDEX = 1;

    private final double g = 9.81;
    private final double v0 = 100;
    private final double theta = Math.PI/4; // 45 degree launch angle.
    private final double t = 2*v0*Math.sin(theta)/g; // Time of (projectile) flight.

    private final double exactRange = v0*Math.cos(theta)*t;

    public ProjectileTestRun() {
        super(
                new int[]{10, 100, 1000, 10000, 100000},
                new String[]{"range", "~range", "range error fraction", "end-height", "~end-height", "end-height error"}
        );
    }

    @Override
    protected double duration() {
        return t;
    }

    @Override
    protected Particle initialState() {
        return new Projectile(g, v0, theta);
    }

    @Override
    public double exactResult(int index) {
        switch (index) {
            case RANGE_INDEX: return exactRange;

            case END_POINT_INDEX: return 0; // Projectile height at the end of flight is zero.

            default: throw new IllegalArgumentException("Illegal result index: " + index);
        }
    }

    @Override
    public double approxResult(int index) {
        switch (index) {
            case RANGE_INDEX: return particle.x();

            case END_POINT_INDEX: return particle.y();

            default: throw new IllegalArgumentException("Illegal result index: " + index);
        }
    }

    @Override
    public double resultErrorFraction(int index) {
        switch (index) {
            case END_POINT_INDEX: return Math.abs(approxResult(index)); // Cannot get error fraction, since exact result is zero.

            default: return super.resultErrorFraction(index);
        }
    }
}
