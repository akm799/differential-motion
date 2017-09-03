package uk.co.akm.test.motion.particle.examples;

import uk.co.akm.test.motion.particle.Particle;

/**
 * Charged particle in a constant magnetic field applied along the z-axis.
 * The particle starts at the origin with an initial velocity v along the
 * y-z plane at a specified angle from the z-axis. Please note that the
 * charge of the particle is assumed to be a one unit.
 *
 * Created by Thanos Mavroidis on 02/09/2017.
 */
public final class ChargeNearMagnet extends Particle {
    private final double bz;

    /**
     * @param b the magnetic field strength along the z-axis
     * @param v the initial speed
     * @param theta the angle of the initial velocity from the z-axis
     */
    public ChargeNearMagnet(double b, double v, double theta) {
        super(0, v*Math.sin(theta), v*Math.cos(theta), 0, 0, 0);

        this.bz = b;
        this.az = 0;
    }

    @Override
    protected void updateAcceleration(double dt) {
        // The cross product between the magnetic field and velocity vectors.
        // Please note that the x and y components of the former are always zero.
        ax = -bz*vy();
        ay =  bz*vx();
    }
}
