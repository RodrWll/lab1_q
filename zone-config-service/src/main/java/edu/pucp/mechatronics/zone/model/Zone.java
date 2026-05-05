package edu.pucp.mechatronics.zone.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "zones")
public class Zone {

    @Id
    private Long id;

    private String tipo;

    private String diametroTuberia;

    private Double caudalMaximo;

    private Double presionMaxima;

    public Zone() {
    }

    public Zone(Long id, String tipo, String diametroTuberia, Double caudalMaximo, Double presionMaxima) {
        this.id = id;
        this.tipo = tipo;
        this.diametroTuberia = diametroTuberia;
        this.caudalMaximo = caudalMaximo;
        this.presionMaxima = presionMaxima;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDiametroTuberia() {
        return diametroTuberia;
    }

    public void setDiametroTuberia(String diametroTuberia) {
        this.diametroTuberia = diametroTuberia;
    }

    public Double getCaudalMaximo() {
        return caudalMaximo;
    }

    public void setCaudalMaximo(Double caudalMaximo) {
        this.caudalMaximo = caudalMaximo;
    }

    public Double getPresionMaxima() {
        return presionMaxima;
    }

    public void setPresionMaxima(Double presionMaxima) {
        this.presionMaxima = presionMaxima;
    }
}
