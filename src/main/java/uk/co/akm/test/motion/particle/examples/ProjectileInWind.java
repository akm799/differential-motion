package uk.co.akm.test.motion.particle.examples;

import uk.co.akm.test.motion.particle.Particle;

/**
 * Created by Thanos Mavroidis on 10/09/2017.
 */
public final class ProjectileInWind extends Particle {
    private final double a;
    private final double k;
    private final double vwx;
    private final double vwz;

    public ProjectileInWind(double a,
                            double k,
                            double vwx,
                            double vwz,
                            double vx0,
                            double vy0,
                            double vz0,
                            double x0,
                            double y0,
                            double z0) {
        super(vx0, vy0, vz0, x0, y0, z0);

        this.a = a;
        this.k = k;
        this.vwx = vwx;
        this.vwz = vwz;
    }

    @Override
    protected void updateAcceleration(double dt) {
        ax = -k*(vx() - vwx);
        ay = -k*vy() - a;
        az = -k*(vz() - vwz);
    }
}
