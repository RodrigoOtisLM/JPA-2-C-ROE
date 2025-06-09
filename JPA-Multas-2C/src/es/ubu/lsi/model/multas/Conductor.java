package es.ubu.lsi.model.multas;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad que representa un conductor
 * Un conductor pertenece a un vehículo y puede tener múltiples incidencias
 */
@Entity
@Table(name = "CONDUCTOR")
public class Conductor {
    
    @Id
    @Column(name = "NIF", length = 10)
    private String nif;
    
    @Column(name = "NOMBRE", length = 50, nullable = false)
    private String nombre;
    
    @Column(name = "APELLIDO", length = 50, nullable = false)
    private String apellido;
    
    // Los puntos por defecto son 12 según el script SQL
    @Column(name = "PUNTOS", precision = 3, scale = 0)
    private Integer puntos = 12;
    
    // Embedding de DireccionPostal
    @Embedded
    private DireccionPostal direccionPostal;
    
    // Relación Many-to-One con Vehiculo
    // Un conductor pertenece a un vehículo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDAUTO")
    private Vehiculo vehiculo;
    
    // Relación bidireccional One-to-Many con Incidencia
    @OneToMany(mappedBy = "conductor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Incidencia> incidencias = new HashSet<>();
    
    // Constructor vacío requerido por JPA
    public Conductor() {}
    
    public Conductor(String nif, String nombre, String apellido, DireccionPostal direccionPostal) {
        this.nif = nif;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccionPostal = direccionPostal;
    }
    
    // Getters y Setters
    public String getNif() {
        return nif;
    }
    
    public void setNif(String nif) {
        this.nif = nif;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public Integer getPuntos() {
        return puntos;
    }
    
    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }
    
    public DireccionPostal getDireccionPostal() {
        return direccionPostal;
    }
    
    public void setDireccionPostal(DireccionPostal direccionPostal) {
        this.direccionPostal = direccionPostal;
    }
    
    public Vehiculo getVehiculo() {
        return vehiculo;
    }
    
    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
    
    public Set<Incidencia> getIncidencias() {
        return incidencias;
    }
    
    public void setIncidencias(Set<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }
    
    // Métodos de conveniencia para mantener la coherencia bidireccional
    public void addIncidencia(Incidencia incidencia) {
        this.incidencias.add(incidencia);
        incidencia.setConductor(this);
    }
    
    public void removeIncidencia(Incidencia incidencia) {
        this.incidencias.remove(incidencia);
        incidencia.setConductor(null);
    }
    
    @Override
    public String toString() {
        return "Conductor [nif=" + nif + ", nombre=" + nombre + ", apellido=" + apellido 
               + ", direccionPostal=" + direccionPostal + ", puntos=" + puntos + "]";
    }
}
