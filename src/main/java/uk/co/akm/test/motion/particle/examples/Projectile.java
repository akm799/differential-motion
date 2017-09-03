package uk.co.akm.test.motion.particle.examples;

import uk.co.akm.test.motion.particle.Particle;

/**
 * Projectile in a uniform gravitational field launched from the origin at an angle along the x-y plane.
 *
 * Created by Thanos Mavroidis on 02/09/2017.
 */
public final class Projectile extends Particle {

    /**
     * @param a the magnitude of constant gravitational acceleration
     * @param v the initial projectile speed
     * @param theta the launch angle (between the initial velocity vector and the x-axis).
     */
    public Projectile(double a, double v, double theta) {
        super(v*Math.cos(theta), v*Math.sin(theta), 0, 0, 0, 0);

        this.ax = 0;
        this.ay = -a; // Constant gravity only.
    }

    @Override
    protected void updateAcceleration(double dt) {}
}
