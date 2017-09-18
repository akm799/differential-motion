package uk.co.akm.test.motion.root;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for the root finder used to numerically solve transcedental equations.
 */
public final class RootFinderTest {
    private final double tolerance = 1E-20;
    private final RootFinder underTest = new RootFinder();

    /**
     * Test for a solution to sin(x)=0.
     */
    @Test
    public void sinTest() {
        final Function sin = (x -> Math.sin(x));
        final Function cos = (x -> Math.cos(x));
        final double xMin = 3*Math.PI/4;
        final double xMax = 3*Math.PI/2;
        final double minImprovement = 0.00001;
        final double exact = Math.PI;

        test("sin(x)", sin, cos, xMin, xMax, minImprovement, exact);
    }

    /**
     * Test for the solution to ln(x)=0.
     */
    @Test
    public void lnTest() {
        final Function ln = (x -> Math.log(x));
        final Function inv = (x -> 1/x);
        final double xMin = 0.001;
        final double xMax = 20;
        final double minImprovement = 0.00001;
        final double exact = 1;

        test("ln(x)", ln, inv, xMin, xMax, minImprovement, exact);
    }

    private void test(String title, Function function, Function derivative, double xMin, double xMax, double minImprovement, double exact) {
        final double approx = underTest.findRoot(function, derivative, xMin, xMax, minImprovement);
        System.out.println(title + ":  exact=" + exact + " approx=" + approx + " error-fraction=" + Math.abs((exact - approx))/exact);
        Assert.assertEquals(exact, approx, tolerance);
    }
}
