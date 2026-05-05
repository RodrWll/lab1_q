package edu.pucp.mechatronics.audit.service;

import edu.pucp.mechatronics.audit.model.AuditResult;
import edu.pucp.mechatronics.audit.model.FlowReport;
import edu.pucp.mechatronics.audit.repository.AuditResultRepository;
import edu.pucp.mechatronics.audit.repository.FlowReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AuditService {

    private static final String ZONE_SERVICE_URL = "http://ZONE-CONFIG-SERVICE/api/zones/";
    private static final double PACKET_INTEGRITY_THRESHOLD = 0.85;

    private final FlowReportRepository flowReportRepository;
    private final AuditResultRepository auditResultRepository;
    private final RestTemplate restTemplate;

    public AuditService(FlowReportRepository flowReportRepository,
                        AuditResultRepository auditResultRepository,
                        RestTemplate restTemplate) {
        this.flowReportRepository = flowReportRepository;
        this.auditResultRepository = auditResultRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public AuditResult processReport(FlowReport report) {
        FlowReport savedReport = flowReportRepository.save(report);

        AuditResult result = new AuditResult();
        result.setIdReporte(savedReport.getId());

        boolean zonaValida = false;
        boolean caudalOk = false;
        boolean presionOk = false;
        boolean integridadDatosOk = false;

        StringBuilder detalles = new StringBuilder();

        // 1. Validate zone existence
        Map<String, Object> zone = fetchZone(report.getIdZona());
        if (zone == null) {
            zonaValida = false;
            detalles.append("FALLA: Zona ").append(report.getIdZona()).append(" no existe. ");
        } else {
            zonaValida = true;
            detalles.append("Zona ").append(report.getIdZona()).append(" válida. ");

            // 2. Validate flow rate
            double caudalMaximo = ((Number) zone.get("caudalMaximo")).doubleValue();
            if (report.getCaudalPicoLmin() <= caudalMaximo) {
                caudalOk = true;
                detalles.append("Caudal OK (").append(report.getCaudalPicoLmin())
                        .append(" <= ").append(caudalMaximo).append(" L/min). ");
            } else {
                caudalOk = false;
                detalles.append("ALERTA: Caudal excedido (").append(report.getCaudalPicoLmin())
                        .append(" > ").append(caudalMaximo).append(" L/min). ");
            }

            // 3. Validate pressure
            double presionMaxima = ((Number) zone.get("presionMaxima")).doubleValue();
            if (report.getPresionPromedioBar() <= presionMaxima) {
                presionOk = true;
                detalles.append("Presión OK (").append(report.getPresionPromedioBar())
                        .append(" <= ").append(presionMaxima).append(" Bar). ");
            } else {
                presionOk = false;
                detalles.append("ALERTA: Presión excedida (").append(report.getPresionPromedioBar())
                        .append(" > ").append(presionMaxima).append(" Bar). ");
            }
        }

        // 4. Validate data integrity
        double paquetesEsperados = (double) report.getDuracionMonitoreoSeg() / report.getIntervaloTransmisionSeg();
        double paquetesMinimos = paquetesEsperados * PACKET_INTEGRITY_THRESHOLD;
        if (report.getPaquetesRecibidos() >= paquetesMinimos) {
            integridadDatosOk = true;
            detalles.append("Integridad de datos OK (")
                    .append(report.getPaquetesRecibidos()).append(" >= ")
                    .append(String.format("%.1f", paquetesMinimos)).append(" paquetes esperados). ");
        } else {
            integridadDatosOk = false;
            detalles.append("FALLA: Pérdida crítica de paquetes (")
                    .append(report.getPaquetesRecibidos()).append(" < ")
                    .append(String.format("%.1f", paquetesMinimos)).append(" paquetes mínimos). ");
        }

        // 5. Classify result
        String estado;
        if (!zonaValida || !integridadDatosOk) {
            estado = "Falla";
        } else if (!caudalOk || !presionOk) {
            estado = "Alerta";
        } else {
            estado = "Normal";
        }

        result.setZonaValida(zonaValida);
        result.setCaudalOk(caudalOk);
        result.setPresionOk(presionOk);
        result.setIntegridadDatosOk(integridadDatosOk);
        result.setEstado(estado);
        result.setDetalles(detalles.toString().trim());

        return auditResultRepository.save(result);
    }

    public Optional<AuditResult> getResultByReportId(Long reportId) {
        return auditResultRepository.findByIdReporte(reportId);
    }

    @Transactional
    public boolean deleteReport(Long reportId) {
        if (!flowReportRepository.existsById(reportId)) {
            return false;
        }
        auditResultRepository.deleteByIdReporte(reportId);
        flowReportRepository.deleteById(reportId);
        return true;
    }

    public List<AuditResult> getAllResults() {
        return auditResultRepository.findAll();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> fetchZone(Long zoneId) {
        try {
            return restTemplate.getForObject(ZONE_SERVICE_URL + zoneId, HashMap.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
