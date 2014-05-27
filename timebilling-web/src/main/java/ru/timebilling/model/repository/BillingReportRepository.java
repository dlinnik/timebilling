package ru.timebilling.model.repository;

import java.sql.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import ru.timebilling.model.domain.BillingReport;
import ru.timebilling.model.domain.Expense;

public interface BillingReportRepository extends PagingAndSortingRepository<BillingReport, Long>{
	
    @Query("SELECT r FROM BillingReport r WHERE r.project.id = :projectId "
    		+ "AND ((r.startDate <= :d1 AND r.endDate >= :d2) "
    		+ 	"OR (r.startDate >= :d1 AND r.startDate <= :d2) "
    		+ 	"OR (r.endDate >= :d1 AND r.endDate <= :d2))")
    public Iterable<BillingReport> findByProjectAndPeriod(
    		@Param("projectId") Long projectId, 
    		@Param("d1") Date d1, 
    		@Param("d2") Date d2);
    
    @Query("SELECT r FROM BillingReport r WHERE r.project.id = :projectId "
    		+ "AND r.startDate <= :d AND r.endDate >= :d")
    public Iterable<BillingReport> findByProjectAndDateInReportPeriod(
    		@Param("projectId") Long projectId, 
    		@Param("d") Date d);
    
}
