package uk.co.akm.test.motion.test.examples;

import uk.co.akm.test.motion.particle.Particle;
import uk.co.akm.test.motion.particle.examples.Satellite;
import uk.co.akm.test.motion.test.result.Result;
import uk.co.akm.test.motion.test.result.impl.DoubleResult;
import uk.co.akm.test.motion.test.result.impl.PointResult;
import uk.co.akm.test.motion.test.AbstractTestRun;

/**
 * Created by Thanos Mavroidis on 03/09/2017.
 */
public final class SatelliteTestRun extends AbstractTestRun {
    private static final int LENGTH_INDEX = 0;
    private static final int END_POINT_INDEX = 1;

    private final double v = 1;
    private final double r = 1;
    private final double a = v*v/r; // Acceleration resulting in circular motion.
    private final double l = 2*Math.PI*r;
    private final double t = l/v; // Circular motion period.
    private final double theta = Math.PI/4; // Initial velocity vector angle between the x-z plane.
    private final double vy0 =  v*Math.sin(theta);
    private final double vz0 = -v*Math.cos(theta);

    public SatelliteTestRun() {
        super(
                new int[]{10, 100, 1000, 10000, 100000},
                new String[]{"length", "~length", "length error fraction", "end-point", "~end-point", "end-point error fraction"}
        );
    }

    @Override
    protected double duration() {
        return t;
    }

    @Override
    protected Particle initialState() {
        return new Satellite(a, 0, vy0, vz0, r, 0, 0);
    }

    @Override
    public Result exactResult(int index) {
        switch (index) {
            case LENGTH_INDEX: return new DoubleResult(l);

            case END_POINT_INDEX: return new PointResult(r, 0, 0);

            default: throw new IllegalArgumentException("Illegal result index: " + index);
        }
    }

    @Override
    public Result approxResult(int index) {
        switch (index) {
            case LENGTH_INDEX: return new DoubleResult(particle.length());

            case END_POINT_INDEX: return new PointResult(particle);

            default: throw new IllegalArgumentException("Illegal result index: " + index);
        }
    }
}
