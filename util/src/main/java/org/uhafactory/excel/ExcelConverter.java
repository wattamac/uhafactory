package org.uhafactory.excel;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 */
public interface ExcelConverter<T> {
    String getFileName();

    List<String> getHeader();

    List<String> generateRow(T element);

    default void before(List<T> elements) {
    }

    default String getFileNameWithDatetime() {
        String format = "{0}-{1}.xls";
        return MessageFormat.format(format, getFileName(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
    }
}
