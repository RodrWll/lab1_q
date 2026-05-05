package edu.pucp.mechatronics.audit.repository;

import edu.pucp.mechatronics.audit.model.AuditResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuditResultRepository extends JpaRepository<AuditResult, Long> {

    Optional<AuditResult> findByIdReporte(Long idReporte);

    void deleteByIdReporte(Long idReporte);
}
