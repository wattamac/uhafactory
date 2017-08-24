package org.uhafactory.excel;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.uhafactory.paging.OneBasedPageImpl;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

/**
 */
public class ExcelDownloadExecutorTest {

    @Test
    public void testExecute() throws IOException {
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream out = mock(ServletOutputStream.class);
        given(response.getOutputStream()).willReturn(out);
        ExcelConverter<Long> excelConverter = mock(ExcelConverter.class);

        given(excelConverter.getFileNameWithDatetime()).willReturn("fileName");
        given(excelConverter.getHeader()).willReturn(Lists.newArrayList("no"));

        given(excelConverter.generateRow(1L)).willReturn(Lists.newArrayList("1"));
        given(excelConverter.generateRow(2L)).willReturn(Lists.newArrayList("2"));
        given(excelConverter.generateRow(3L)).willReturn(Lists.newArrayList("3"));

        ExcelDownloadExecutor<Long> downloadExecutor = new ExcelDownloadExecutor<Long>() {
            @Override
            public Page<Long> pagingResult(Pageable pageable) {
                if(pageable.getPageNumber() == 1){
                    return new OneBasedPageImpl(Lists.newArrayList(1L, 2L), pageable, 101L);
                }

                if(pageable.getPageNumber() == 2){
                    return new OneBasedPageImpl(Lists.newArrayList(3L), pageable, 101L);
                }
                return null;
            }
        };

        downloadExecutor.execute(response, excelConverter);

        verify(response).addHeader("Content-Disposition", "attachment; filename=\"fileName\"");
        verify(excelConverter, times(3)).generateRow(anyLong());
        verify(excelConverter).generateRow(1L);
        verify(excelConverter).generateRow(2L);
        verify(excelConverter).generateRow(3L);
    }


}