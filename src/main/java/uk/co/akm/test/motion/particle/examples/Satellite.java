package uk.co.akm.test.motion.particle.examples;

import uk.co.akm.test.motion.particle.Particle;

/**
 * Satellite in a constant gravitational field originating from a single point at the origin.
 * Please note that, in this approximation, the magnitude of the gravitational force exerted
 * on the particle is constant (i.e. independent of the distance from the origin).
 *
 * Created by Thanos Mavroidis on 02/09/2017.
 */
public final class Satellite extends Particle {
    private double a;

    /**
     * @param a the constant gravitational acceleration magnitude
     * @param vx0 the initial velocity along the x-axis
     * @param vy0 the initial velocity along the y-axis
     * @param vz0 the initial velocity along the z-axis
     * @param x0 the x-axis position
     * @param y0 the y-axis position
     * @param z0 the z-axis position
     */
    public Satellite(double a, double vx0, double vy0, double vz0, double x0, double y0, double z0) {
        super(vx0, vy0, vz0, x0, y0, z0);

        this.a = a;
    }

    @Override
    protected void updateAcceleration(double dt) {
        final double x = x();
        final double y = y();
        final double z = z();
        final double r = Math.sqrt(x*x + y*y + z*z);

        ax = -a*x/r;
        ay = -a*y/r;
        az = -a*z/r;
    }
}
