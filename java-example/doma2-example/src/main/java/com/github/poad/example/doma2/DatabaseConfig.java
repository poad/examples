package com.github.poad.example.doma2;

import org.seasar.doma.SingletonConfig;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.MysqlDialect;
import org.seasar.doma.jdbc.tx.LocalTransactionDataSource;
import org.seasar.doma.jdbc.tx.LocalTransactionManager;
import org.seasar.doma.jdbc.tx.TransactionManager;

import javax.sql.DataSource;

@SingletonConfig
public class DatabaseConfig implements Config {

    private static final DatabaseConfig instance = new DatabaseConfig();

    private final LocalTransactionDataSource dataSource;

    private final TransactionManager transactionManager;

    private final Dialect dialect;

    private DatabaseConfig() {
        dialect = new MysqlDialect();
        dataSource = new LocalTransactionDataSource(
                "jdbc:mysql://localhost/testdb", "root", null
        );
        transactionManager = new LocalTransactionManager(
                dataSource.getLocalTransaction(getJdbcLogger()));
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public Dialect getDialect() {
        return dialect;
    }

    @Override
    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public static DatabaseConfig singleton() { return instance; }
}
