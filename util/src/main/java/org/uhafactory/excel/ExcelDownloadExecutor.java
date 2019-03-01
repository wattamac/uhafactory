package org.uhafactory.excel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.uhafactory.paging.OneBasedPagedRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 */
public abstract class ExcelDownloadExecutor<T> {
    private static final int EXCEL_PAGE_SIZE = 100;
    public abstract Page<T> pagingResult(Pageable pageable);

    public void execute(HttpServletResponse response, ExcelConverter<T> excelConverter) throws IOException {
        response.addHeader("Content-Disposition", "attachment; filename=\"" + excelConverter.getFileNameWithDatetime() + "\"");

        try (ExcelStreamDownloader<T> downloader = new ExcelStreamDownloader(response.getOutputStream(), excelConverter)) {
            downloader.writeHeader();
            Pageable pageable = new OneBasedPagedRequest(1, EXCEL_PAGE_SIZE);
            Page<T> page;
            do {
                page = pagingResult(pageable);
                pageable = page.nextPageable();
                downloader.write(page.getContent());
            } while (page.hasNext());
        }
    }
}
