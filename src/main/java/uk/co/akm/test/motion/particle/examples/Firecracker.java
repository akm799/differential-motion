package uk.co.akm.test.motion.particle.examples;

import uk.co.akm.test.motion.particle.Particle;

/**
 * Firecracker moving under constant acceleration along a 45 degree angle on the x-y plane.
 * Please note, that in this simple approximation, no external force is acting on the firecracker.
 *
 * Created by Thanos Mavroidis on 02/09/2017.
 */
public final class Firecracker extends Particle {

    /**
     * @param a the magnitude of the constant acceleration
     * @param vx0 the initial velocity along the x-axis
     * @param vy0 the initial velocity along the y-axis
     * @param x0 the initial x-axis position
     * @param y0 the initial y-axis position
     */
    public Firecracker(double a, double vx0, double vy0, double x0, double y0) {
        super(vx0, vy0, 0, x0, y0, 0);

        final double theta = Math.PI/4;
        this.ax = a*Math.cos(theta);
        this.ay = a*Math.sin(theta);
        this.az = 0;
    }

    /**
     * Constant acceleration (no update).
     */
    @Override
    protected void updateAcceleration(double dt) {}
}
