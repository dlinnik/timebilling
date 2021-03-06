package ru.timebilling.model.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import ru.timebilling.model.domain.BillingReport;
import ru.timebilling.model.domain.Expense;
import ru.timebilling.model.domain.Project;

public interface ExpenseRepository extends PagingAndSortingRepository<Expense, Long>{

	public Page<Expense> findByProject(Project project, Pageable pageable);

	public Page<Expense> findByReport(BillingReport report, Pageable pageable);

    @Query("SELECT e FROM Expense e WHERE e.project.id = :projectId AND e.report IS NULL AND e.date >= :startDate AND e.date <= :endDate")
    public Iterable<Expense> findToBill(
    		@Param("projectId") Long projectId,
    		@Param("startDate") Date startDate,
    		@Param("endDate") Date endDate);
    
    @Query(value="SELECT MIN(e.date), MAX(e.date) FROM Expense e WHERE e.report IS NULL")
    public List<Object[]> findPeriod();

    @Query(value="SELECT MIN(e.date), MAX(e.date) FROM Expense e WHERE e.report IS NULL AND e.project.id = :projectId")
    public List<Object[]> findPeriodByProject(@Param("projectId") Long projectId);
    
    @Query(value="SELECT e.project, SUM(e.spentMoney), MONTH(e.date), YEAR(e.date) FROM Expense e WHERE e.report IS NULL "
    		+ "GROUP BY e.project, MONTH(e.date), YEAR(e.date) ORDER BY e.project.name ASC, MONTH(e.date) DESC, YEAR(e.date) DESC")
    public List<Object[]> billing();

    @Query(value="SELECT e.project, SUM(e.spentMoney), MONTH(e.date), YEAR(e.date) FROM Expense e WHERE e.report IS NULL AND e.project.id = :projectId "
    		+ "GROUP BY e.project, MONTH(e.date), YEAR(e.date) ORDER BY e.project.name ASC, MONTH(e.date) DESC, YEAR(e.date) DESC")
    public List<Object[]> billingByProject(@Param("projectId") Long projectId);



}