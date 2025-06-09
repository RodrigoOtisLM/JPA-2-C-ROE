package es.ubu.lsi.model.multas;

import javax.persistence.*;

/**
 * Entidad que representa una incidencia (multa)
 * Tiene clave compuesta (fecha + nif) y se relaciona con Conductor y TipoIncidencia
 */
@Entity
@Table(name = "INCIDENCIA")
public class Incidencia {
    
    // Clave compuesta embebida
    @EmbeddedId
    private IncidenciaPK id;
    
    @Column(name = "ANOTACION")
    @Lob  // Para campos CLOB
    private String anotacion;
    
    // Relación Many-to-One con Conductor
    // insertable=false, updatable=false porque el NIF ya está en la clave primaria
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NIF", insertable = false, updatable = false)
    private Conductor conductor;
    
    // Relación Many-to-One con TipoIncidencia
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDTIPO")
    private TipoIncidencia tipoIncidencia;
    
    // Constructor vacío requerido por JPA
    public Incidencia() {}
    
    public Incidencia(IncidenciaPK id, String anotacion, TipoIncidencia tipoIncidencia) {
        this.id = id;
        this.anotacion = anotacion;
        this.tipoIncidencia = tipoIncidencia;
    }
    
    // Getters y Setters
    public IncidenciaPK getId() {
        return id;
    }
    
    public void setId(IncidenciaPK id) {
        this.id = id;
    }
    
    public String getAnotacion() {
        return anotacion;
    }
    
    public void setAnotacion(String anotacion) {
        this.anotacion = anotacion;
    }
    
    public Conductor getConductor() {
        return conductor;
    }
    
    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }
    
    public TipoIncidencia getTipoIncidencia() {
        return tipoIncidencia;
    }
    
    public void setTipoIncidencia(TipoIncidencia tipoIncidencia) {
        this.tipoIncidencia = tipoIncidencia;
    }
    
    @Override
    public String toString() {
        return "Incidencia [id=" + id + ", anotacion=" + anotacion + ", conductor=" + conductor 
               + ", tipoIncidencia=" + tipoIncidencia + "]";
    }
}