package uk.co.akm.test.motion.test.examples;

import uk.co.akm.test.motion.particle.Particle;
import uk.co.akm.test.motion.particle.examples.ProjectileInAir;
import uk.co.akm.test.motion.test.result.impl.PointResult;

/**
 * Created by Thanos Mavroidis on 10/09/2017.
 */
public final class ProjectileInAirTestRun extends AbstractProjectileInAirTestRun {

    protected PointResult evaluateTarget(double timeOfFlight) {
        final double x = (vx0/k)*(1 - Math.exp(-k*timeOfFlight));

        return new PointResult(x, 0, 0);
    }

    @Override
    protected Particle initialState() {
        return new ProjectileInAir(g, k, vx0, vy0, 0, 0, 0, 0);
    }
}

/*
y-force = gravity + air-resistance = -g -k*vy
                          g    g
vy(t) = exp(-kt) * (vy0 + -) - -
                          k    k

            1          g                     g
y(t) = y0 + - * (vy0 + -) * (1 - exp(-kt)) - - * t
            k          k                     k

x-force = air-resistance = -k*vx

            vx0
x(t) = x0 + --- * (1 - exp(-kt))
             k

Initial conditions: x0 = 0, y0 = 0, vx0 = v0*cos(theta), vy0 = v0*sin(theta)
*/

