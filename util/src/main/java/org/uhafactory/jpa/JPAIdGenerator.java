package org.uhafactory.jpa;

import lombok.Getter;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Properties;

public class JPAIdGenerator implements IdentifierGenerator, Configurable {
    private static String SELECT_SEQUENCE_QUERY = "SELECT %s.nextval FROM DUAL";

    @Getter
    private String query;

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        String sequenceName = params.getProperty("sequence_name");
        query = String.format(SELECT_SEQUENCE_QUERY, sequenceName);
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
        JPAId jpaId = (JPAId)obj;
        Serializable id = jpaId.getId();
        if (id != null) {
            return id;
        }

        long seq = ((BigDecimal)session.createNativeQuery(query).setFlushMode(FlushMode.MANUAL).uniqueResult()).longValue();
        return jpaId.createId(seq);
    }
}
