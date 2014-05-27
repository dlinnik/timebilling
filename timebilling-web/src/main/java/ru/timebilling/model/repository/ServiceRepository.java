package ru.timebilling.model.repository;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ru.timebilling.model.domain.BillingReport;
import ru.timebilling.model.domain.Expense;
import ru.timebilling.model.domain.Project;
import ru.timebilling.model.domain.Service;
import ru.timebilling.model.domain.User;

@RepositoryRestResource(collectionResourceRel = "srv", path = "srv")
public interface ServiceRepository extends PagingAndSortingRepository<Service, Long>{

	public Iterable<Service> findByEmployee(User user);
//	public Iterable<Service> findByProject(Project project);
	public Page<Service> findByProject(Project project, Pageable pageable);
	public Iterable<Service> findByProjectOrderByDateDesc(Project project);
	
	public Page<Service> findByReport(BillingReport report, Pageable pageable);

	
    @Query("SELECT e FROM Service e WHERE e.project.id = :projectId AND e.report IS NULL AND e.date >= :startDate AND e.date <= :endDate")
    public Iterable<Service> findToBill(
    		@Param("projectId") Long projectId,
    		@Param("startDate") Date startDate,
    		@Param("endDate") Date endDate);

}
