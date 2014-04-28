package ru.timebilling.model.service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.googlecode.flyway.core.Flyway;
 
@Component(value = "dbMigration")
public class DbMigrationRunner {
    private static Logger log = LoggerFactory.getLogger(DbMigrationRunner.class);
 
    @Autowired
    private DataSource dataSource;
 
    @PostConstruct
    public void migrate() {
        try {
        	log.info("DbMigrationRunner.migrate...");
            Flyway flyway = new Flyway();            
            flyway.setInitOnMigrate(true);
            
            flyway.setDataSource(dataSource);                       


            flyway.migrate();
            
        } catch (Exception e) {
            log.error("Error while migrate database", e);
        }
    }
}