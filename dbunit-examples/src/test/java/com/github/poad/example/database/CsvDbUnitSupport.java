package com.github.poad.example.database;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.operation.DatabaseOperation;

public class CsvDbUnitSupport {

	protected void load(File csvDir, DataSource dataSource) throws SQLException, DatabaseUnitException {
		Connection conn = dataSource.getConnection();
		IDatabaseConnection dc = new DatabaseConnection(conn);
		IDataSet dataset = new CsvDataSet(csvDir);
		DatabaseOperation.CLEAN_INSERT.execute(dc, dataset);
	}
}
