package uk.co.akm.test.motion.particle.examples;

import uk.co.akm.test.motion.particle.Particle;

/**
 * Particle of mass m moving in a straight line along the x-axis experiencing a resistance against its motion. This
 * resistance is proportional to the square of the particle speed, when that speed is high or just proportional to the
 * speed, when the speed is low. The transition speed between high and low speed values is taken to be 1 m/sec. The
 * initial particle speed is vx0, where vx0 > 1, and the initial particle position is 0.
 *
 * Created by Thanos Mavroidis on 07/01/2018.
 */
public final class FreeParticleWithMassInAir extends Particle {
    private final double c;
    private final double vTransition = 1;

    /**
     * @param m the particle mass
     * @param k the particle resistance coefficient
     * @param vx0 the initial particle speed (along the x-axis)
     */
    public FreeParticleWithMassInAir(double m, double k, double vx0) {
        super(vx0, 0, 0, 0, 0, 0);

        this.c = k/m;
    }

    @Override
    protected void updateAcceleration(double dt) {
        final double v = vx();
        if (Math.abs(v) > vTransition) {
            final double s = (v >= 0 ? 1 : -1);
            ax = -s*c*v*v;
        } else {
            ax = -c*v;
        }
    }
}
