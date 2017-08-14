package org.uhafactory.excel;

import java.util.List;

/**
 */
public interface ExcelStreamGenerator {
    byte[] getHeader(List<String> columns);

    byte[] getFooter();

    byte[] generateRow(List<String> values);
}
