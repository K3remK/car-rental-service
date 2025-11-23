package com.kerem.service.impl;

import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.hibernate.type.descriptor.java.spi.JavaTypeBasicAdaptor;
import org.hibernate.type.descriptor.jdbc.NumericJdbcType;
import org.hibernate.type.internal.NamedBasicTypeImpl;

import java.util.Properties;

public class UniqueRandomIdGenerator extends SequenceStyleGenerator {

    private static final long MODULUS = 100_000_000L;
    private static final long MULTIPLIER = 10_000_005L;
    private static final long INCREMENT = 123_457L;


    public final String INCREMENT_PARAM = "increment_size";

    public final int DEFAULT_INCREMENT_SIZE = 1;

    @Override
    public Object generate( SharedSessionContractImplementor session, Object object ) {
        long generatedSequence = (long)super.generate(session, object);

        long generatedId = (generatedSequence * MULTIPLIER + INCREMENT) % MODULUS;

        return String.format("%08d", generatedId);
    }

    @Override
    public void configure(Type type, Properties parameters, ServiceRegistry serviceRegistry ) throws MappingException {
        parameters.put( INCREMENT_PARAM, DEFAULT_INCREMENT_SIZE );
        Type idType = new NamedBasicTypeImpl<>( new JavaTypeBasicAdaptor<>( Long.class ), NumericJdbcType.INSTANCE, "long" );
        super.configure( idType, parameters, serviceRegistry );
    }
}
