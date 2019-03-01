package org.uhafactory.jpa;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.FlushMode;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.query.spi.NativeQueryImplementor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class JPAIdGeneratorTest {
    private JPAIdGenerator generator = new JPAIdGenerator();

    @Mock
    private SharedSessionContractImplementor session;

    @Mock
    private NativeQueryImplementor nativeQueryImplementor;

    @Test
    public void testGenerate(){
        ReflectionTestUtils.setField(generator, "query", "query");
        given(session.createNativeQuery(eq("query"))).willReturn(nativeQueryImplementor);
        given(nativeQueryImplementor.setFlushMode(FlushMode.MANUAL)).willReturn(nativeQueryImplementor);
        given(nativeQueryImplementor.uniqueResult()).willReturn(BigDecimal.valueOf(3));

        Serializable result = generator.generate(session, new JPAIdImpl());

        assertThat(result).isEqualTo(1004L);
    }

    @Test
    public void testGenereateAlreadyExist(){
        JPAIdImpl jpaId = new JPAIdImpl();
        jpaId.setId(333L);
        assertThat(generator.generate(null, jpaId)).isEqualTo(jpaId.getId());
    }

    @Test
    public void testConfigure(){
        Properties param = mock(Properties.class);
        given(param.getProperty("sequence_name")).willReturn("test_sequence");
        generator.configure(null, param, null);

        assertThat(generator.getQuery()).isEqualTo("SELECT test_sequence.nextval FROM DUAL");
    }

    @Getter
    @Setter
    private class JPAIdImpl implements JPAId<Long> {
        private Long id;

        @Override
        public Long createId(Long sequence){
            return sequence + 1001L;
        }
    }
}