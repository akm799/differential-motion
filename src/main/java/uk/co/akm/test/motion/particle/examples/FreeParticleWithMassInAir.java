package uk.co.akm.test.motion.particle.examples;

import uk.co.akm.test.motion.particle.Particle;

/**
 * Particle of mass m moving in a straight line along the x-axis experiencing a resistance k*v where v is the particle
 * speed. The initial particle speed is vx0 and the initial particle position is 0.
 *
 * //TODO Change the particle behaviour to be as it is at low speeds but to exhibit a resistance proportional to the
 * //TODO paticle speed squared, at high speeds.
 *
 * Created by Thanos Mavroidis on 07/01/2018.
 */
public final class FreeParticleWithMassInAir extends Particle {
    private final double m;
    private final double k;

    /**
     * @param m the particle mass
     * @param k the particle resistance coefficient
     * @param vx0 the initial particle speed (along the x-axis)
     */
    public FreeParticleWithMassInAir(double m, double k, double vx0) {
        super(vx0, 0, 0, 0, 0, 0);

        this.m = m;
        this.k = k;
    }

    @Override
    protected void updateAcceleration(double dt) {
        ax = -k*vx()/m;
    }
}
