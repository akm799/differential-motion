package uk.co.akm.test.motion.test;

import uk.co.akm.test.motion.format.TextTable;

/**
 * Created by Thanos Mavroidis on 03/09/2017.
 */
public final class TestRunner {

    public void runTests(TestRun testRun, TextTable table) {
        table.addRow(assembleTestHeaders(testRun));

        for (int n : testRun.numberOfSteps()) {
            testRun.runTest(n);
            table.addRow(assembleTestResults(n, testRun));
        }
    }

    private String[] assembleTestHeaders(TestSpec testSpec) {
        final String[] resultHeaders = testSpec.headers();
        final String[] headers = new String[resultHeaders.length + 1];

        headers[0] = "N";
        System.arraycopy(resultHeaders, 0, headers, 1, resultHeaders.length);

        return headers;
    }

    private Object[] assembleTestResults(int n, TestSpec testSpec) {
        final Object[] results = new Object[TestSpec.N_COLUMNS_PER_RESULT*testSpec.numberOfTestResults() + 1];

        int j = 0;
        results[j++] = n;
        for (int i=0 ; i<testSpec.numberOfTestResults() ; i++) {
            results[j++] = testSpec.exactResult(i);
            results[j++] = testSpec.approxResult(i);
            results[j++] = testSpec.resultErrorFraction(i);
        }

        return results;
    }
}
