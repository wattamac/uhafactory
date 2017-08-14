package org.uhafactory.paging;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;

import java.util.List;

/**
 * Created by lineplus on 2016. 7. 7..
 */
public class PageUtil {
    public static <T> Page<T> getPagedResult(Querydsl querydsl, JPQLQuery query, Pageable pageable) {
        querydsl.applyPagination(pageable, query);
        QueryResults<Class> queryResults = query.fetchResults();
        return new OneBasedPageImpl<T>((List<T>) queryResults.getResults(), pageable, queryResults.getTotal());
    }
}
