package org.bitbucket.poad1010.example.springboot.jpa;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Map;

/**
 * Created by ken-yo on 2016/08/06.
 */
@Component
public class JpaConfig extends JpaBaseConfiguration {
    protected JpaConfig(DataSource dataSource, JpaProperties properties, ObjectProvider<JtaTransactionManager> jtaTransactionManagerProvider) {
        super(dataSource, properties, jtaTransactionManagerProvider);
    }

    @Override
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
        return new EclipseLinkJpaVendorAdapter();
    }

    @Override
    protected Map<String, Object> getVendorProperties() {
        // Turn off dynamic weaving to disable LTW lookup in static weaving mode
        return Collections.singletonMap("eclipselink.weaving", "false");
    }
}
