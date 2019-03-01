package org.uhafactory.jpa;

import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.hibernate.query.Query;
import org.springframework.batch.item.database.orm.AbstractHibernateQueryProvider;
import org.springframework.data.jpa.repository.support.Querydsl;

public abstract class AbstractHibernateQueryDslQueryProvider extends AbstractHibernateQueryProvider {
    @Override
    public Query createQuery() {
        Session session = getStatefulSession();
        SessionImpl sessionImpl = session.unwrap(SessionImpl.class);

        PathBuilder builder = (new PathBuilderFactory()).create(targetClass());
        Querydsl querydsl = new Querydsl(sessionImpl, builder);

        Query query = getQuery(querydsl).createQuery().unwrap(Query.class);
        return query;
    }

    protected abstract Class targetClass();

    protected abstract JPAQuery getQuery(Querydsl querydsl);

    protected abstract void afterPropertiesSet() throws Exception;
}
