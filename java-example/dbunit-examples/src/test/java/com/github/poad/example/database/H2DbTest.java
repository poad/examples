package com.github.poad.example.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bitbucket.poad1010.example.database.config.DatabaseConfig;
import org.dbunit.DatabaseUnitException;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class H2DbTest extends CsvDbUnitSupport {

	@BeforeAll
	public static void setup() throws IOException, SQLException, DatabaseUnitException {
		DatabaseConfig config = DatabaseConfig.load();

		System.out.println(config.getDataSourceClassName());

		// Point it to the database
        Flyway flyway = Flyway.configure().dataSource(config.getUrl(), config.getUser(), config.getPassword()).load();

		// Start the migration
		flyway.migrate();

        try (HikariDataSource dataSource = dataSource(config)) {
			load(new File("test/db/csv"), dataSource);
		}

	}

	@Test
	public void test() throws SQLException, IOException {
        DatabaseConfig config = DatabaseConfig.load();

        try (HikariDataSource dataSource = dataSource(config)) {
			try (Connection con = dataSource.getConnection()) {
				try (PreparedStatement st = con.prepareStatement("select message from message")) {
					try (ResultSet rs = st.executeQuery()) {
						while (rs.next()) {
							System.out.println(rs.getString(1));
						}
					}
				}
			}
		}
	}

	private static HikariDataSource dataSource(DatabaseConfig config) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getUrl());
        if (config.getUser() != null) {
            hikariConfig.setUsername(config.getUser());
        }
        if (config.getPassword() != null) {
            hikariConfig.setPassword(config.getPassword());
        }
        hikariConfig.addDataSourceProperty("autoCommit", "false");
        hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");

        return new HikariDataSource(hikariConfig);
    }
}
