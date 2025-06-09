package es.ubu.lsi.model.multas;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad que representa un vehículo
 * Un vehículo puede tener múltiples conductores
 */
@Entity
@Table(name = "VEHICULO")
public class Vehiculo {
    
    @Id
    @Column(name = "IDAUTO", length = 3)
    private String idauto;
    
    @Column(name = "NOMBRE", length = 50, nullable = false, unique = true)
    private String nombre;
    
    // Embedding de DireccionPostal - los campos se mapean directamente en la tabla VEHICULO
    @Embedded
    private DireccionPostal direccionPostal;
    
    // Relación bidireccional One-to-Many con Conductor
    // mappedBy indica que Conductor es el propietario de la relación
    // FetchType.LAZY para cargar conductores solo cuando sea necesario
    @OneToMany(mappedBy = "vehiculo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Conductor> conductores = new HashSet<>();
    
    // Constructor vacío requerido por JPA
    public Vehiculo() {}
    
    public Vehiculo(String idauto, String nombre, DireccionPostal direccionPostal) {
        this.idauto = idauto;
        this.nombre = nombre;
        this.direccionPostal = direccionPostal;
    }
    
    // Getters y Setters
    public String getIdauto() {
        return idauto;
    }
    
    public void setIdauto(String idauto) {
        this.idauto = idauto;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public DireccionPostal getDireccionPostal() {
        return direccionPostal;
    }
    
    public void setDireccionPostal(DireccionPostal direccionPostal) {
        this.direccionPostal = direccionPostal;
    }
    
    public Set<Conductor> getConductores() {
        return conductores;
    }
    
    public void setConductores(Set<Conductor> conductores) {
        this.conductores = conductores;
    }
    
    // Métodos de conveniencia para mantener la coherencia bidireccional
    public void addConductor(Conductor conductor) {
        this.conductores.add(conductor);
        conductor.setVehiculo(this);
    }
    
    public void removeConductor(Conductor conductor) {
        this.conductores.remove(conductor);
        conductor.setVehiculo(null);
    }
    
    @Override
    public String toString() {
        return "Vehiculo [idauto=" + idauto + ", nombre=" + nombre + ", direccionPostal=" + direccionPostal + "]";
    }
}