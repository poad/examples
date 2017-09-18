package com.github.poad.example.doma2;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
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
        // HikariCPの初期化
        HikariConfig config = new HikariConfig();

        // MySQL用ドライバを設定
        config.setDriverClassName("com.mysql.jdbc.Driver");

        // 「jdbc:mysql://ホスト:ポート/DB名」の様なURLで指定
        config.setJdbcUrl("jdbc:mysql://localhost:3306/testdb");

        // ユーザ名、パスワード指定
        config.addDataSourceProperty("user", "root");

        // キャッシュ系の設定(任意)
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        // サーバサイドプリペアードステートメントを使用する(任意)
        config.addDataSourceProperty("useServerPrepStmts", "true");

        // 最小接続数まで接続を確保できない時に例外を投げる
        config.setInitializationFailFast(true);
        // 接続をテストするためのクエリ
        config.setConnectionInitSql("SELECT 1");

        // 接続
        HikariDataSource hikari = new HikariDataSource(config);

        dataSource = new LocalTransactionDataSource(hikari);
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
