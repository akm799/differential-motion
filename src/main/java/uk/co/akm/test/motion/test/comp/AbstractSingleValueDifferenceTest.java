package uk.co.akm.test.motion.test.comp;

import uk.co.akm.test.motion.comp.ComparisonParticle;

/**
 * Created by Thanos Mavroidis on 14/01/2019.
 */
public abstract class AbstractSingleValueDifferenceTest implements StateComparisonTest<Double> {
    private final boolean quadSpace;
    private final String title;
    private final String resultDescription;
    private final StateSingleValueSelector valueSelector;

    private AverageDifference difference;

    protected AbstractSingleValueDifferenceTest(boolean quadSpace, String title, String resultDescription, StateSingleValueSelector valueSelector) {
        this.quadSpace = quadSpace;
        this.title = title;
        this.resultDescription = resultDescription;
        this.valueSelector = valueSelector;
    }

    @Override
    public final String title() {
        return title;
    }

    @Override
    public final String resultDescription() {
        return resultDescription;
    }

    @Override
    public final ComparisonParticle initialState() {
        difference = new AverageDifference(valueSelector);

        final ComparisonParticle particle = buildComparisonParticle(quadSpace);
        particle.setComparator(difference);

        return particle;
    }

    @Override
    public final StateComparisonResult<Double> result() {
        return difference;
    }

    protected abstract ComparisonParticle buildComparisonParticle(boolean quadSpace);
}
