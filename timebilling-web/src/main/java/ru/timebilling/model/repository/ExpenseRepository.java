package ru.timebilling.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import ru.timebilling.model.domain.Expense;
import ru.timebilling.model.domain.Project;

public interface ExpenseRepository extends PagingAndSortingRepository<Expense, Long>{

	public Page<Expense> findByProject(Project project, Pageable pageable);
}