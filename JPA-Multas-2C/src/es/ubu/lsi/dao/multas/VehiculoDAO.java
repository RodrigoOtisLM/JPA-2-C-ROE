package es.ubu.lsi.dao.multas;

import es.ubu.lsi.dao.JpaDAO;
import es.ubu.lsi.model.multas.Vehiculo;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * DAO para la entidad Vehiculo
 * Proporciona operaciones de acceso a datos específicas para vehículos
 */
public class VehiculoDAO extends JpaDAO<Vehiculo, String> {

    /**
     * Constructor que especifica la clase de entidad
     */
    public VehiculoDAO() {
        super(Vehiculo.class);
    }

    /**
     * Implementación obligatoria del método abstracto findAll
     * Devuelve TODAS las instancias de Vehiculo
     */
    @Override
    public List<Vehiculo> findAll() {
        TypedQuery<Vehiculo> query = getEntityManager()
            .createQuery("SELECT v FROM Vehiculo v", Vehiculo.class);
        return query.getResultList();
    }
    
    /**
     * Consulta todos los vehículos con sus conductores e incidencias cargados
     * Utiliza un grafo de entidades para cargar toda la información relacionada
     * Este método es específico para la transacción consultarVehiculos()
     */
    public List<Vehiculo> findAllWithGraph() {
        TypedQuery<Vehiculo> query = getEntityManager()
            .createQuery("SELECT DISTINCT v FROM Vehiculo v " +
                        "LEFT JOIN FETCH v.conductores c " +
                        "LEFT JOIN FETCH c.incidencias i " +
                        "LEFT JOIN FETCH i.tipoIncidencia", Vehiculo.class);
        return query.getResultList();
    }
    
    /**
     * Busca un vehículo por su ID con sus conductores cargados
     */
    public Vehiculo findByIdWithConductores(String idauto) {
        TypedQuery<Vehiculo> query = getEntityManager()
            .createQuery("SELECT v FROM Vehiculo v " +
                        "LEFT JOIN FETCH v.conductores " +
                        "WHERE v.idauto = :idauto", Vehiculo.class);
        query.setParameter("idauto", idauto);
        
        List<Vehiculo> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}