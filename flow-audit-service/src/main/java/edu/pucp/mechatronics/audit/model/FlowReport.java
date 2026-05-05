package edu.pucp.mechatronics.audit.model;

import jakarta.persistence.*;

@Entity
@Table(name = "flow_reports")
public class FlowReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idZona;

    private Double caudalPicoLmin;

    private Double presionPromedioBar;

    private Integer duracionMonitoreoSeg;

    private Integer intervaloTransmisionSeg;

    private Integer paquetesRecibidos;

    public FlowReport() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdZona() {
        return idZona;
    }

    public void setIdZona(Long idZona) {
        this.idZona = idZona;
    }

    public Double getCaudalPicoLmin() {
        return caudalPicoLmin;
    }

    public void setCaudalPicoLmin(Double caudalPicoLmin) {
        this.caudalPicoLmin = caudalPicoLmin;
    }

    public Double getPresionPromedioBar() {
        return presionPromedioBar;
    }

    public void setPresionPromedioBar(Double presionPromedioBar) {
        this.presionPromedioBar = presionPromedioBar;
    }

    public Integer getDuracionMonitoreoSeg() {
        return duracionMonitoreoSeg;
    }

    public void setDuracionMonitoreoSeg(Integer duracionMonitoreoSeg) {
        this.duracionMonitoreoSeg = duracionMonitoreoSeg;
    }

    public Integer getIntervaloTransmisionSeg() {
        return intervaloTransmisionSeg;
    }

    public void setIntervaloTransmisionSeg(Integer intervaloTransmisionSeg) {
        this.intervaloTransmisionSeg = intervaloTransmisionSeg;
    }

    public Integer getPaquetesRecibidos() {
        return paquetesRecibidos;
    }

    public void setPaquetesRecibidos(Integer paquetesRecibidos) {
        this.paquetesRecibidos = paquetesRecibidos;
    }
}
