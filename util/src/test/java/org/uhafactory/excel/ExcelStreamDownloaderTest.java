package org.uhafactory.excel;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by lineplus on 2017. 5. 18..
 */
public class ExcelStreamDownloaderTest {
    @Test
    public void testWriteHeader() throws IOException {
        OutputStream out = mock(OutputStream.class);
        ExcelConverter<Long> converter = mock(ExcelConverter.class);

        List<String> headers = Lists.newArrayList("header");
        given(converter.getHeader()).willReturn(headers);

        ExcelStreamGenerator generator = mock(ExcelStreamGenerator.class);
        byte[] byteHeader = new byte[]{0x10};

        given(generator.getHeader(headers)).willReturn(byteHeader);

        ExcelStreamDownloader<Long> excelStreamDownloader = new ExcelStreamDownloader<>(out, converter);
        excelStreamDownloader.setExcelStreamGenerator(generator);
        excelStreamDownloader.writeHeader();

        verify(out).write(byteHeader);
    }

    @Test
    public void testGenerateRow() throws IOException {
        //given
        OutputStream out = mock(OutputStream.class);
        ExcelConverter<Long> converter = mock(ExcelConverter.class);
        given(converter.generateRow(1L)).willReturn(Lists.newArrayList("1_"));
        given(converter.generateRow(2L)).willReturn(Lists.newArrayList("2_"));

        ExcelStreamGenerator generator = mock(ExcelStreamGenerator.class);
        given(generator.generateRow(Lists.newArrayList("1_"))).willReturn(new byte[]{0x01, 0x02});
        given(generator.generateRow(Lists.newArrayList("2_"))).willReturn(new byte[]{0x03, 0x04});

        ExcelStreamDownloader<Long> excelStreamDownloader = new ExcelStreamDownloader<>(out, converter);
        excelStreamDownloader.setExcelStreamGenerator(generator);

        List<Long> data = Lists.newArrayList(1L, 2L);

        //then
        excelStreamDownloader.write(data);

        //verify
        verify(converter).before(data);
        verify(out).write(new byte[]{0x01, 0x02});
        verify(out).write(new byte[]{0x03, 0x04});
    }

    @Test
    public void testClose() throws IOException {
        //given
        OutputStream out = mock(OutputStream.class);
        ExcelStreamGenerator generator = mock(ExcelStreamGenerator.class);
        given(generator.getFooter()).willReturn(new byte[]{0x01,0x05});
        ExcelStreamDownloader<Long> excelStreamDownloader = new ExcelStreamDownloader<>(out, null);
        excelStreamDownloader.setExcelStreamGenerator(generator);

        //then
        excelStreamDownloader.close();

        verify(out).write(new byte[]{0x01,0x05});
        verify(out).close();
    }
}