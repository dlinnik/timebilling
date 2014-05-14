package ru.timebilling.model.repository;

import org.springframework.data.repository.CrudRepository;

import ru.timebilling.model.domain.BillingReport;

public interface BillingReportRepository extends CrudRepository<BillingReport, Long>{

}
