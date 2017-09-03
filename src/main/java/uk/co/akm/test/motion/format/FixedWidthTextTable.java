package uk.co.akm.test.motion.format;

import java.io.PrintStream;
import java.util.*;

/**
 * Created by Thanos Mavroidis on 02/09/2017.
 */
public class FixedWidthTextTable implements TextTable {
    private final Collection<Collection<String>> table = new ArrayList<>();
    private final Collection<Collection<String>> paddings = new ArrayList<>();

    private List<Integer> columnWidths;
    private final Map<Integer, String> paddingsStore = new HashMap<>();

    private final String columnSpacing;

    public FixedWidthTextTable(String columnSpacing) {
        this.columnSpacing = columnSpacing;
    }

    @Override
    public void addRow(Object... columns) {
        if (columns != null) {
            checkWidth(columns);
            addRowElements(columns);
        }
    }

    private void checkWidth(Object... columns) {
        if (columnWidths == null) {
            columnWidths = new ArrayList<>(columns.length);
            for (int i=0 ; i<columns.length ; i++) {columnWidths.add(0);}
        } else {
            if (columnWidths.size() != columns.length) {
                throw new IllegalArgumentException("Row " + columns + " has " + columns.length + " elements but the number of columns in this fixed width table is already defined as " + columnWidths.size() + ".");
            }
        }
    }

    private void addRowElements(Object... columns) {
        final Collection<String> row = new ArrayList<>();
        Arrays.stream(columns).forEach(c -> row.add(c == null ? "" : c.toString()));
        table.add(row);
    }

    @Override
    public void print(PrintStream ps) {
        if (paddings.isEmpty()) {
            preparePaddings();
        }

        printWithPaddings(ps);
    }

    private void printWithPaddings(PrintStream ps) {
        final Iterator<Collection<String>> rows = table.iterator();
        final Iterator<Collection<String>> rowPaddings = paddings.iterator();
        while (rows.hasNext()) {
            printRowWithPaddings(rows.next(), rowPaddings.next(), ps);
            ps.println();
        }
    }

    private void printRowWithPaddings(Collection<String> row, Collection<String> rowPaddings, PrintStream ps) {
        final Iterator<String> columns = row.iterator();
        final Iterator<String> columnPaddings = rowPaddings.iterator();
        while (columns.hasNext()) {
            ps.print(columns.next());
            ps.print(columnPaddings.next());
            ps.print(columnSpacing);
        }
    }

    private void preparePaddings() {
        evaluateMaxColumnLengths();
        buildPaddings();
    }

    private void evaluateMaxColumnLengths() {
        for (Collection<String> row : table) {
            int i = 0;
            for (String s : row) {
                if (s.length() > columnWidths.get(i)) {
                    columnWidths.set(i, s.length());
                }
                i++;
            }
        }
    }

    private void buildPaddings() {
        for (Collection<String> row : table) {
            final Collection<String> rowPaddings = new ArrayList<>(columnWidths.size());
            fillPaddingsForRow(row, rowPaddings);
            paddings.add(rowPaddings);
        }
    }

    private void fillPaddingsForRow(Collection<String> row, Collection<String> rowPaddings) {
        int i = 0;
        for (String s : row) {
            final int paddingLength = columnWidths.get(i) - s.length();
            rowPaddings.add(getOrBuildPadding(paddingLength));
            i++;
        }
    }

    private String getOrBuildPadding(int length) {
        if (!paddingsStore.containsKey(length)) {
            paddingsStore.put(length, buildPadding(length));
        }

        return paddingsStore.get(length);
    }

    private String buildPadding(int length) {
        final char[] blanks = new char[length];
        Arrays.fill(blanks, ' ');

        return new String(blanks);
    }
}
