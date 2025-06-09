package es.ubu.lsi.model.multas;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Clase embebible para reutilizar los campos de dirección
 * tanto en Vehiculo como en Conductor
 */
@Embeddable
public class DireccionPostal {
    
    @Column(name = "DIRECCION", length = 100)
    private String direccion;
    
    @Column(name = "CP", length = 5)
    private String codigoPostal;
    
    @Column(name = "CIUDAD", length = 20)
    private String ciudad;
    
    // Constructor vacío requerido por JPA
    public DireccionPostal() {}
    
    public DireccionPostal(String direccion, String codigoPostal, String ciudad) {
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.ciudad = ciudad;
    }
    
    // Getters y Setters
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getCodigoPostal() {
        return codigoPostal;
    }
    
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
    
    public String getCiudad() {
        return ciudad;
    }
    
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    
    @Override
    public String toString() {
        return "DireccionPostal [direccion=" + direccion + ", codigoPostal=" + codigoPostal + ", ciudad=" + ciudad + "]";
    }
}