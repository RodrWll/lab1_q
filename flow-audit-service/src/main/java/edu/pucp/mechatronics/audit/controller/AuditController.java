package edu.pucp.mechatronics.audit.controller;

import edu.pucp.mechatronics.audit.model.AuditResult;
import edu.pucp.mechatronics.audit.model.FlowReport;
import edu.pucp.mechatronics.audit.service.AuditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @PostMapping
    public ResponseEntity<AuditResult> createAudit(@RequestBody FlowReport report) {
        AuditResult result = auditService.processReport(report);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditResult> getAuditById(@PathVariable Long id) {
        return auditService.getResultByReportId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAudit(@PathVariable Long id) {
        boolean deleted = auditService.deleteReport(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<AuditResult> getAllAudits() {
        return auditService.getAllResults();
    }
}
