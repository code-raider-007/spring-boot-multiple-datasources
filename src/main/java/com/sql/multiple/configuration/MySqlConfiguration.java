package com.sql.multiple.configuration;



import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "mysqlEntityFactoryManager",
transactionManagerRef = "mysqlTransactionManager", 
basePackages = {"com.sql.multiple.persistence.mysql.repository"})
public class MySqlConfiguration {
	
	private final SqlPropertiesConfiguration sqlPropertiesConfiguration;
	
	public MySqlConfiguration(SqlPropertiesConfiguration sqlPropertiesConfiguration) {
		super();
		this.sqlPropertiesConfiguration = sqlPropertiesConfiguration;
	}
	
	@Bean(name = "mysql")
	@Qualifier("mysql")
	@Primary
	public DataSource getMySQLDataSource() {
		return DataSourceBuilder.create()
				.url(this.sqlPropertiesConfiguration.getSqlUrl())
				.username(this.sqlPropertiesConfiguration.getSqlUsername())
				.password(this.sqlPropertiesConfiguration.getSqlPassword())
				.build();
	}

	
	@Bean(name = "mysqlEntityFactoryManager")
	@Qualifier(value = "mysqlEntityFactoryManager")
	@Primary
	public LocalContainerEntityManagerFactoryBean getMySqlEntityManager(
			EntityManagerFactoryBuilder entityManagerFactoryBuilder) {

		return entityManagerFactoryBuilder.dataSource(getMySQLDataSource())
				.packages(new String[] { "com.sql.multiple.persistence.mysql.entity" })
				.properties(additionalJpaProperties())
				.persistenceUnit("mysql")
				.build();
	}

	Map<String, String> additionalJpaProperties() {
		Map<String, String> map = new HashMap<>();

		map.put("hibernate.hbm2ddl.auto", this.sqlPropertiesConfiguration.getDdlAuto());
		map.put("hibernate.dialect", this.sqlPropertiesConfiguration.getMysqlDialect());
		map.put("hibernate.show_sql", this.sqlPropertiesConfiguration.getShowSql());
		return map;
	}

	@Bean(name = "mysqlTransactionManager")
	public JpaTransactionManager getMySqlTransactionManager(
			@Qualifier("mysqlEntityFactoryManager") EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}
	 
}
