package uk.co.akm.test.motion;

import uk.co.akm.test.motion.particle.State;
import uk.co.akm.test.motion.test.comp.StateComparisonTest;
import uk.co.akm.test.motion.test.comp.StateComparisonTestRunner;
import uk.co.akm.test.motion.test.comp.StateSingleValueSelector;
import uk.co.akm.test.motion.test.comp.examples.FreeFallingComparisonTest;

/**
 * Created by Thanos Mavroidis on 10/01/2019.
 */
public class Comp {

    public static void main(String[] args) {
        final Comp comp = new Comp();
        comp.runAllTests();
    }

    private Comp() {}

    private void runAllTests() {
        final StateComparisonTest<Double> freeFallingSpaceTest = buildFreeFallingSpaceTest();
        final StateComparisonTest<Double> freeFallingVelocityTest = buildFreeFallingVelocityTest();

        final StateComparisonTest[] tests = new StateComparisonTest[]{freeFallingSpaceTest, freeFallingVelocityTest};
        final StateComparisonTestRunner testRunner = new StateComparisonTestRunner(tests);
        testRunner.runComparisonTests(10, 10);

        System.out.println(freeFallingSpaceTest.title() + " test: Average y difference is " + freeFallingSpaceTest.result().getComparisonResult());
        System.out.println(freeFallingSpaceTest.title() + " test: Average y-velocity difference is " + freeFallingVelocityTest.result().getComparisonResult());
    }

    private StateComparisonTest<Double> buildFreeFallingSpaceTest() {
        final StateSingleValueSelector ySelector = (State state) -> state.y();
        return new FreeFallingComparisonTest(9.81, 1000, true, "Free falling particle space", ySelector);
    }

    private StateComparisonTest<Double> buildFreeFallingVelocityTest() {
        final StateSingleValueSelector ySelector = (State state) -> state.vy();
        return new FreeFallingComparisonTest(9.81, 1000, false, "Free falling particle velocity", ySelector);
    }
}
