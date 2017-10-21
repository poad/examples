package org.bitbucket.poad1010.example.database.config;

import java.io.IOException;

import org.bitbucket.poad1010.example.database.yaml.YamlLoader;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class DatabaseConfig {

	private String driver;
	private String dataSourceClassName;
	private String url;
	private String user;
	private String password;

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getDataSourceClassName() {
		return dataSourceClassName;
	}

	public void setDataSourceClassName(String dataSourceClassName) {
		this.dataSourceClassName = dataSourceClassName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static DatabaseConfig load() throws IOException {
		return YamlLoader.load("database.yaml", DatabaseConfig.class);
	}
}
