package ru.timebilling.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.orm.hibernate3.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:/hibernate-db.properties")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ru.timebilling.model.repository")
public class DataConfig implements DisposableBean {
	static final Logger logger = LoggerFactory.getLogger(DataConfig.class);

	@Value("#{ environment['database.driverClassName']?:'' }")
	private String dbDriverClass;
	@Value("#{ environment['database.url']?:'' }")
	private String dbUrl;
	@Value("#{ environment['database.username']?:'' }")
	private String dbUserName = "";
	@Value("#{ environment['database.password']?:'' }")
	private final String dbPassword = "";
	@Value("#{ environment['database.vendor']?:'' }")
	private String dbVendor = "H2";

	private EmbeddedDatabase ed;

	@Bean
	public DataSource dataSource() {
		logger.info("*** 1. Creating dataSource");

		logger.info("URL '{}'", dbUrl);
		logger.trace("Driver '{}'", dbDriverClass);
		logger.trace("UserName '{}'", dbUserName);
		logger.trace("dbPassword '{}'", dbPassword);
		BasicDataSource bean = new BasicDataSource();
		bean.setPassword(dbPassword);
		bean.setUrl(dbUrl);
		bean.setUsername(dbUserName);
		bean.setDriverClassName(dbDriverClass);
		bean.setTestOnBorrow(true);
		bean.setTestOnReturn(true);
		bean.setTestWhileIdle(true);
		bean.setTimeBetweenEvictionRunsMillis(1800000);
		bean.setMinEvictableIdleTimeMillis(1800000);
		bean.setNumTestsPerEvictionRun(3);
		return bean;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() throws SQLException {
		logger.info("Enter: EntityManagerFactory");
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);
		vendorAdapter.setShowSql(true);


		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("ru.timebilling.model.domain");
		factory.setPersistenceUnitName("TimebillingPU");
		factory.setDataSource(dataSource());

		Properties ps = new Properties();
		ps.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		ps.put("hibernate.hbm2ddl.auto", "update");
		// ps.put("hibernate.connection.url",
		// "jdbc:h2:file:/tmp/test.db;IFEXISTS=TRUE");
		factory.setJpaProperties(ps);

		factory.afterPropertiesSet();
		logger.info("Exit: EntityManagerFactory");

		return factory.getObject();
	}

	@Bean
	public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
		return entityManagerFactory.createEntityManager();
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws SQLException {

		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory());
		return txManager;
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

	@Override
	public void destroy() {

		if (this.ed != null) {
			this.ed.shutdown();
		}

	}
}
