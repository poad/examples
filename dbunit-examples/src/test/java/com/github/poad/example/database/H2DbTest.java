package com.github.poad.example.database;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.dbunit.DatabaseUnitException;
import org.flywaydb.core.Flyway;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class H2DbTest extends CsvDbUnitSupport {

	private static final String DB_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=MYSQL;DB_CLOSE_ON_EXIT=FALSE;IGNORECASE=TRUE;INIT=CREATE SCHEMA IF NOT EXISTS \"public\"\\;";

	@BeforeTest
	public static void setup() {
		// Create the Flyway instance
		Flyway flyway = new Flyway();

		// Point it to the database
		flyway.setDataSource(DB_URL, "sa", null);

		// Start the migration
		flyway.migrate();
	}

	@BeforeClass
	public void prepareDb() throws SQLException, DatabaseUnitException {
		try (BasicDataSource dataSource = new BasicDataSource()) {
			dataSource.setDriverClassName("org.h2.Driver");
			dataSource.setUrl(DB_URL);
			dataSource.setUsername("sa");
			load(new File("test/db/csv"), dataSource);
		}

	}

	@Test
	public static void test() throws SQLException {
		try (BasicDataSource dataSource = new BasicDataSource()) {
			dataSource.setDriverClassName("org.h2.Driver");
			dataSource.setUrl(DB_URL);
			dataSource.setUsername("sa");
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
}
