package org.uhafactory.excel;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import lombok.Setter;

/**
 */
public class ExcelStreamDownloader<T> implements Closeable {
    private final OutputStream out;
    @Setter
    private ExcelStreamGenerator excelStreamGenerator = new XmlExcelStreamGenerator();

    private ExcelConverter converter;

    public ExcelStreamDownloader(OutputStream out, ExcelConverter converter) {
        this.out = out;
        this.converter = converter;
    }

    public void writeHeader() throws IOException {
        out.write(excelStreamGenerator.getHeader(converter.getHeader()));
    }

    public void write(List<T> elements) throws IOException {
        converter.before(elements);
        for(T element : elements) {
            out.write(generateRow(converter.generateRow(element)));
        }
    }

    @Override
    public void close() throws IOException {
        if(out != null) {
            out.write(excelStreamGenerator.getFooter());
            out.close();
        }
    }

    protected byte[] generateRow(List<String> str) {
        return excelStreamGenerator.generateRow(str);
    }
}
