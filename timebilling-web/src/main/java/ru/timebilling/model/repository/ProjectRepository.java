package ru.timebilling.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;




import ru.timebilling.model.domain.Project;

@RepositoryRestResource(collectionResourceRel = "prj", path = "prj")
public interface ProjectRepository extends CrudRepository<Project, Long>{

	@RestResource(path="filter", rel="filter")
	public Iterable<Project> findByNameStartingWithOrClientStartingWith(@Param("name") String name, @Param("client") String client);
	
    @Query("SELECT p FROM Project p INNER JOIN p.assignments a WHERE a.user.id = :userId")
    public Iterable<Project> findAssigned(@Param("userId") Long userId);

}
