package com.github.poad.examples.java.webapps.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.seasar.extension.dbcp.impl.ConnectionPoolImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class S2JdbcMetrics {
    private final ConnectionPoolImpl pool;

    S2JdbcMetrics(MeterRegistry meterRegistry, @Autowired ConnectionPoolImpl pool) {
        this.pool = pool;

        meterRegistry.gauge("jdbc.s2.active", this, S2JdbcMetrics::activePool);
        meterRegistry.gauge("jdbc.s2.free", this, S2JdbcMetrics::freePool);
        meterRegistry.gauge("jdbc.s2.tx.active", this, S2JdbcMetrics::txActivePool);
        meterRegistry.gauge("jdbc.s2.min", this, S2JdbcMetrics::minPool);
        meterRegistry.gauge("jdbc.s2.max", this, S2JdbcMetrics::maxPool);

        meterRegistry.gauge("jdbc.s2.max.wait", this, S2JdbcMetrics::maxWait);
        meterRegistry.gauge("jdbc.s2.max.timeout", this, S2JdbcMetrics::timeout);
        meterRegistry.gauge("jdbc.s2.max.validation.interval", this, S2JdbcMetrics::validationInterval);
    }

    private int activePool() {
        return pool.getActivePoolSize();
    }

    private int freePool() {
        return pool.getFreePoolSize();
    }

    private int txActivePool() {
        return pool.getTxActivePoolSize();
    }

    private int maxPool() {
        return pool.getMaxPoolSize();
    }

    private int minPool() {
        return pool.getMinPoolSize();
    }

    private long maxWait() {
        return pool.getMaxWait();
    }

    private int timeout() {
        return pool.getTimeout();
    }

    private long validationInterval() {
        return pool.getValidationInterval();
    }
}
