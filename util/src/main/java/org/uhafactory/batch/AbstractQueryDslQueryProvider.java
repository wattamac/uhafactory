package org.uhafactory.batch;

import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.batch.item.database.orm.AbstractJpaQueryProvider;
import org.springframework.data.jpa.repository.support.Querydsl;

import javax.persistence.Query;

/**
 */
public abstract class AbstractQueryDslQueryProvider extends AbstractJpaQueryProvider {
    @Override
    public Query createQuery() {
        PathBuilder builder = (new PathBuilderFactory()).create(targetClass());
        Querydsl querydsl = new Querydsl(getEntityManager(), builder);
        return getQuery(querydsl).createQuery();
    }

    protected abstract Class targetClass();

    protected abstract JPAQuery getQuery(Querydsl querydsl);
}
