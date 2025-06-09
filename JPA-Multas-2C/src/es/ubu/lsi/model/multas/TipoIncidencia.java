package es.ubu.lsi.model.multas;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad que representa los tipos de incidencias (multas)
 * Define la gravedad y los puntos a descontar
 */
@Entity
@Table(name = "TIPOINCIDENCIA")
public class TipoIncidencia {
    
    @Id
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "DESCRIPCION", length = 30)
    private String descripcion;
    
    @Column(name = "VALOR")
    private Integer valor;
    
    // Relación bidireccional One-to-Many con Incidencia
    @OneToMany(mappedBy = "tipoIncidencia", fetch = FetchType.LAZY)
    private Set<Incidencia> incidencias = new HashSet<>();
    
    // Constructor vacío requerido por JPA
    public TipoIncidencia() {}
    
    public TipoIncidencia(Long id, String descripcion, Integer valor) {
        this.id = id;
        this.descripcion = descripcion;
        this.valor = valor;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Integer getValor() {
        return valor;
    }
    
    public void setValor(Integer valor) {
        this.valor = valor;
    }
    
    public Set<Incidencia> getIncidencias() {
        return incidencias;
    }
    
    public void setIncidencias(Set<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }
    
    @Override
    public String toString() {
        return "TipoIncidencia [id=" + id + ", descripcion=" + descripcion + ", valor=" + valor + "]";
    }
}