package uk.co.akm.test.motion.test.comp;

import uk.co.akm.test.motion.comp.StateComparator;
import uk.co.akm.test.motion.particle.State;

/**
 * Created by Thanos Mavroidis on 09/01/2019.
 */
public final class AverageDifference implements StateComparator, StateComparisonResult<Double> {
    private final StateSingleValueSelector valueSelector;

    private int count = 0;
    private double sum = 0.0;

    public AverageDifference(StateSingleValueSelector valueSelector) {
        this.valueSelector = valueSelector;
    }

    @Override
    public void stateUpdated(State exact, State approximate) {
        sum += Math.abs(valueSelector.value(approximate) - valueSelector.value(exact));
        count++;
    }

    @Override
    public Double getComparisonResult() {
        return sum/count;
    }
}
