package ru.timebilling.model.repository;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import ru.timebilling.model.domain.Expense;
import ru.timebilling.model.domain.Project;

public interface ExpenseRepository extends PagingAndSortingRepository<Expense, Long>{

	public Page<Expense> findByProject(Project project, Pageable pageable);
	
    @Query("SELECT e FROM Expense e WHERE e.project.id = :projectId AND e.report IS NULL AND e.date >= :startDate AND e.date <= :endDate")
    public Iterable<Expense> findToBill(
    		@Param("projectId") Long projectId,
    		@Param("startDate") Date startDate,
    		@Param("endDate") Date endDate);

}