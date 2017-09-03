package uk.co.akm.test.motion.test.result.impl;

import uk.co.akm.test.motion.test.result.Result;

/**
 * Created by Thanos Mavroidis on 03/09/2017.
 */
public final class DoubleResult implements Result {
    private final double value;

    public DoubleResult(double value) {
        this.value = value;
    }

    @Override
    public double absDifference(Result other) {
        if (other instanceof DoubleResult) {
            return Math.abs(value - ((DoubleResult)other).value);
        } else {
            throw new IllegalArgumentException("Input implementation class (" + other.getClass() + ") is not of type " + this.getClass());
        }
    }

    @Override
    public double absDifferenceFraction(Result other) {
        if (other instanceof DoubleResult) {
            final double diff = absDifference(other);

            return (value == 0 ? diff : diff/value);
        } else {
            throw new IllegalArgumentException("Input implementation class (" + other.getClass() + ") is not of type " + this.getClass());
        }
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
