package uk.co.akm.test.motion.particle.examples;

import uk.co.akm.test.motion.particle.Particle;

/**
 * Created by Thanos Mavroidis on 04/09/2017.
 */
public final class ProjectileInAir extends Particle {
    private final double a;
    private final double k;

    public ProjectileInAir(double a, double k, double vx0, double vy0, double vz0, double x0, double y0, double z0) {
        super(vx0, vy0, vz0, x0, y0, z0);

        this.a = a;
        this.k = k;
    }

    @Override
    protected void updateAcceleration(double dt) {
        ax = -k*vx();
        ay = -k*vy() - a;
    }
}
