package ru.timebilling.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import ru.timebilling.model.domain.BillingReport;

public interface BillingReportRepository extends PagingAndSortingRepository<BillingReport, Long>{

}
