package uk.co.akm.test.motion.particle.examples;

import uk.co.akm.test.motion.particle.Particle;

/**
 * Particle of mass m moving in a straight line along the x-axis under a constant force f and experiencing a resistance
 * k*v where v is the particle speed. The initial particle speed is 0 and the initial particle position is 0.
 *
 * Created by Thanos Mavroidis on 21/10/2017.
 */
public class ParticleWithMassInAir extends Particle {
    private final double m;
    private final double f;
    private final double k;

    /**
     * @param m the particle mass
     * @param f the particle propulsion force
     * @param k the particle resistance coefficient
     */
    public ParticleWithMassInAir(double m, double f, double k) {
        super(0, 0, 0, 0, 0, 0);

        this.m = m;
        this.f = f;
        this.k = k;
    }

    @Override
    protected void updateAcceleration(double dt) {
        ax = (f - k*vx())/m;
    }
}
