package edu.pucp.mechatronics.audit.repository;

import edu.pucp.mechatronics.audit.model.FlowReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowReportRepository extends JpaRepository<FlowReport, Long> {
}
