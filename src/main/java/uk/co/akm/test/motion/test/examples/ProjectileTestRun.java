package uk.co.akm.test.motion.test.examples;

import uk.co.akm.test.motion.particle.Particle;
import uk.co.akm.test.motion.particle.examples.Projectile;
import uk.co.akm.test.motion.test.result.Result;
import uk.co.akm.test.motion.test.result.impl.PointResult;
import uk.co.akm.test.motion.test.AbstractTestRun;

/**
 * Created by Thanos Mavroidis on 03/09/2017.
 */
public final class ProjectileTestRun extends AbstractTestRun {
    private final double g = 9.81;
    private final double v0 = 100;
    private final double theta = Math.PI/4; // 45 degree launch angle.
    private final double t = 2*v0*Math.sin(theta)/g; // Time of (projectile) flight.

    private final double exactRange = v0*Math.cos(theta)*t;

    public ProjectileTestRun() {
        super(
                new int[]{10, 100, 1000, 10000, 100000},
                new String[]{"end-point", "~end-point", "end-point error fraction"}
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
    public Result exactResult(int index) {
        return new PointResult(exactRange, 0, 0);
    }

    @Override
    public Result approxResult(int index) {
        return new PointResult(particle);
    }
}
