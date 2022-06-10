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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "postgresqlEntityFactoryManager",
transactionManagerRef = "postgresqlTransactionManager", 
basePackages = {"com.sql.multiple.persistence.postgresql.repository"})
public class PostgresqlConfiguration {

	private final SqlPropertiesConfiguration sqlPropertiesConfiguration;
	
	public PostgresqlConfiguration(SqlPropertiesConfiguration sqlPropertiesConfiguration) {
		super();
		this.sqlPropertiesConfiguration = sqlPropertiesConfiguration;
	}
	
	@Bean(name = "postgres")
	@Qualifier("postgres")
	public DataSource getPostgreDataSource() {
		return DataSourceBuilder.create()
				.url(this.sqlPropertiesConfiguration.getPostgresUrl())
				.username(this.sqlPropertiesConfiguration.getPostgresUsername())
				.password(this.sqlPropertiesConfiguration.getPostgresPassword())
				.build();
	}

	
	  @Bean(name = "postgresqlEntityFactoryManager")
	  @Qualifier(value = "postgresqlEntityFactoryManager")
	  public LocalContainerEntityManagerFactoryBean getPostgresqlEntityManager(
			EntityManagerFactoryBuilder entityManagerFactoryBuilder) {

		return entityManagerFactoryBuilder.dataSource(getPostgreDataSource())
				.packages(new String[] { "com.sql.multiple.persistence.postgresql.entity" })
				.properties(additionalJpaProperties())
				.persistenceUnit("postgres")
				.build();
	  }
	  
		Map<String, String> additionalJpaProperties() {
			Map<String, String> map = new HashMap<>();

			map.put("hibernate.hbm2ddl.auto", this.sqlPropertiesConfiguration.getDdlAuto());
			map.put("hibernate.dialect", this.sqlPropertiesConfiguration.getPostgresqlDialect());
			map.put("hibernate.show_sql", this.sqlPropertiesConfiguration.getShowSql());
			return map;
		}
	  
		@Bean(name = "postgresqlTransactionManager")
		public JpaTransactionManager getPostgresqlTransactionManager(
				@Qualifier("postgresqlEntityFactoryManager") EntityManagerFactory entityManagerFactory) {
			JpaTransactionManager transactionManager = new JpaTransactionManager();
			transactionManager.setEntityManagerFactory(entityManagerFactory);
			return transactionManager;
		}
	 
}
