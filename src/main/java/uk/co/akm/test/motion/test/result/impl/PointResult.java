package uk.co.akm.test.motion.test.result.impl;

import uk.co.akm.test.motion.particle.State;
import uk.co.akm.test.motion.test.result.Result;

/**
 * Created by Thanos Mavroidis on 03/09/2017.
 */
public final class PointResult implements Result {
    private final double x;
    private final double y;
    private final double z;
    private final double r;

    public PointResult(State particle) {
        this(particle.x(), particle.y(), particle.z());
    }

    public PointResult(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        r = Math.sqrt(x*x + y*y + z*z);
    }

    @Override
    public double absDifference(Result other) {
        if (other instanceof PointResult) {
            return absDifference((PointResult)other);
        } else {
            throw new IllegalArgumentException("Input implementation class (" + other.getClass() + ") is not of type " + this.getClass());
        }
    }

    private double absDifference(PointResult other) {
        final double dx = x - other.x;
        final double dy = y - other.y;
        final double dz = z - other.z;

        return Math.sqrt(dx*dx + dy*dy + dz*dz);
    }

    @Override
    public double absDifferenceFraction(Result other) {
        return absDifference(other)/r;
    }

    @Override
    public String toString() {
        return ("(" + x + ", " + y + ", " + z + ")");
    }
}
