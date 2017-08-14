package org.uhafactory.paging;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by lineplus on 2017. 5. 16..
 */
public class OneBasedPageImpl<T> extends PageImpl<T> {
    public OneBasedPageImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public OneBasedPageImpl(List<T> content) {
        super(content);
    }

    @Override
    public boolean hasNext(){
        return getNumber() < getTotalPages();
    }

    @Override
    public String toString() {

        String contentType = "UNKNOWN";
        List<T> content = getContent();

        if (content.size() > 0) {
            contentType = content.get(0).getClass().getName();
        }

        return String.format("Page %s of %d containing %s instances", getNumber(), getTotalPages(), contentType);
    }
}
