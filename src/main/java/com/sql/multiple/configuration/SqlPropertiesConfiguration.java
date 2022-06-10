package com.sql.multiple.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "datasource")
@Component
public class SqlPropertiesConfiguration {

	private String sqlUrl;
	private String sqlUsername;
	private String sqlPassword;
	
	private String postgresUrl;
	private String postgresUsername;
	private String postgresPassword;
	
	private String showSql;
	private String ddlAuto;
	private String mysqlDialect;
	private String postgresqlDialect;
}
