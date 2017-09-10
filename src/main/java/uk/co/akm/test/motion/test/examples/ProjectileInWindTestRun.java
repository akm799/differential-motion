package uk.co.akm.test.motion.test.examples;

import uk.co.akm.test.motion.particle.Particle;
import uk.co.akm.test.motion.particle.examples.ProjectileInWind;
import uk.co.akm.test.motion.test.result.impl.PointResult;

/**
 * Created by Thanos Mavroidis on 10/09/2017.
 */
public final class ProjectileInWindTestRun extends AbstractProjectileInAirTestRun {
    private final double vw = 10; // Wind speed
    private final double windAngle = Math.PI/4; // 45 degree wind angle measured from the x-axis to z-axis.
    private final double vwx = vw*Math.cos(windAngle);
    private final double vwz = vw*Math.sin(windAngle);

    protected PointResult evaluateTarget(double timeOfFlight) {
        final double x = targetHorizontalCoordinate(vx0, vwx, timeOfFlight);
        final double z = targetHorizontalCoordinate(0, vwz, timeOfFlight);

        return new PointResult(x, 0, z);
    }

    private double targetHorizontalCoordinate(double v0, double vw, double t) {
        return (v0 - vw)*(1 - Math.exp(-k*t))/k + vw*t;
    }

    @Override
    protected Particle initialState() {
        return new ProjectileInWind(g, k, vwx, vwz, vx0, vy0, 0, 0, 0, 0);
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

Wind speed along the x-z plane, i.e. with a zero y component.

x,z-force = air-resistance in wind = -k*(v - vw)

            v0 - vw
x(t) = x0 + ------- * (1 - exp(-kt)) + vw*t
               k


Initial conditions: x0 = 0, y0 = 0, z0 = 0, vx0 = v0*cos(launchAngle), vy0 = v0*sin(launchAngle), vz0 = 0
*/

