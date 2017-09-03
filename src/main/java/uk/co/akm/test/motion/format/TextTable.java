package uk.co.akm.test.motion.format;

import java.io.PrintStream;

/**
 * Created by Thanos Mavroidis on 02/09/2017.
 */
public interface TextTable {

    void addRow(Object... columns);

    void print(PrintStream ps);
}
