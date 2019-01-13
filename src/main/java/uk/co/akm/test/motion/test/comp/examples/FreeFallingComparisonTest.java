package uk.co.akm.test.motion.test.comp.examples;

import uk.co.akm.test.motion.comp.ComparisonParticle;
import uk.co.akm.test.motion.comp.MutableState;
import uk.co.akm.test.motion.particle.State;
import uk.co.akm.test.motion.test.comp.AverageDifference;
import uk.co.akm.test.motion.test.comp.StateComparisonResult;
import uk.co.akm.test.motion.test.comp.StateComparisonTest;
import uk.co.akm.test.motion.test.comp.StateSingleValueSelector;

/**
 * Created by Thanos Mavroidis on 09/01/2019.
 */
public final class FreeFallingComparisonTest implements StateComparisonTest<Double> {
    private final double g;
    private final double y0;
    private final boolean quadSpace;
    private final String title;
    private final String resultDescription;

    private final AverageDifference yDifference;

    public FreeFallingComparisonTest(double g, double y0, boolean quadSpace, String title, String resultDescription, StateSingleValueSelector valueSelector) {
        this.g = g;
        this.y0 = y0;
        this.quadSpace = quadSpace;
        this.title = title;
        this.resultDescription = resultDescription;
        this.yDifference = buildYDifference(valueSelector);
    }

    private AverageDifference buildYDifference(StateSingleValueSelector valueSelector) {
        return new AverageDifference(valueSelector);
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public String resultDescription() {
        return resultDescription;
    }

    @Override
    public ComparisonParticle initialState() {
        final ComparisonParticle particle = new FreeFallingParticle(g, y0, quadSpace);
        particle.setComparator(yDifference);

        return particle;
    }

    @Override
    public StateComparisonResult<Double> result() {
        return yDifference;
    }

    private static final class FreeFallingParticle extends ComparisonParticle {
        private final double y0;
        private final double ayOver2;
        private final MutableState exactState = new MutableState();

        public FreeFallingParticle(double g, double y0, boolean quadSpace) {
            super(0, 0, 0, 0, y0, 0, quadSpace);

            this.ay = -g;
            this.y0 = y0;
            this.ayOver2 = this.ay/2;
        }

        @Override
        protected State eExactState(double t) {
            exactState.setVy(ay*t);
            exactState.setY(y0 + ayOver2*t*t);
            exactState.setT(t);

            return exactState;
        }

        @Override
        protected void updateAcceleration(double dt) {}
    }
}
