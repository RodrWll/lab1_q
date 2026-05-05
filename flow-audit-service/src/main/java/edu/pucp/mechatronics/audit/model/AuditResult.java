package edu.pucp.mechatronics.audit.model;

import jakarta.persistence.*;

@Entity
@Table(name = "audit_results")
public class AuditResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idReporte;

    private String estado;

    @Column(length = 1000)
    private String detalles;

    private Boolean zonaValida;

    private Boolean caudalOk;

    private Boolean presionOk;

    private Boolean integridadDatosOk;

    public AuditResult() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(Long idReporte) {
        this.idReporte = idReporte;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public Boolean getZonaValida() {
        return zonaValida;
    }

    public void setZonaValida(Boolean zonaValida) {
        this.zonaValida = zonaValida;
    }

    public Boolean getCaudalOk() {
        return caudalOk;
    }

    public void setCaudalOk(Boolean caudalOk) {
        this.caudalOk = caudalOk;
    }

    public Boolean getPresionOk() {
        return presionOk;
    }

    public void setPresionOk(Boolean presionOk) {
        this.presionOk = presionOk;
    }

    public Boolean getIntegridadDatosOk() {
        return integridadDatosOk;
    }

    public void setIntegridadDatosOk(Boolean integridadDatosOk) {
        this.integridadDatosOk = integridadDatosOk;
    }
}
