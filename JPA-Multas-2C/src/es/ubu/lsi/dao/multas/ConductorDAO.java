package es.ubu.lsi.dao.multas;

import es.ubu.lsi.dao.JpaDAO;
import es.ubu.lsi.model.multas.Conductor;
import javax.persistence.TypedQuery;
import javax.persistence.NoResultException;
import java.util.List;

/**
 * DAO para la entidad Conductor
 * Proporciona operaciones de acceso a datos específicas para conductores
 */
public class ConductorDAO extends JpaDAO<Conductor, String> {

    /**
     * Constructor que especifica la clase de entidad
     */
    public ConductorDAO() {
        super(Conductor.class);
    }

    /**
     * Implementación obligatoria del método abstracto findAll
     * Devuelve TODAS las instancias de Conductor
     */
    @Override
    public List<Conductor> findAll() {
        TypedQuery<Conductor> query = getEntityManager()
            .createQuery("SELECT c FROM Conductor c", Conductor.class);
        return query.getResultList();
    }
    
    /**
     * Busca un conductor por NIF con sus incidencias cargadas
     * Útil para las operaciones que requieren acceso a las incidencias
     */
    public Conductor findByNifWithIncidencias(String nif) {
        try {
            TypedQuery<Conductor> query = getEntityManager()
                .createQuery("SELECT c FROM Conductor c " +
                            "LEFT JOIN FETCH c.incidencias i " +
                            "LEFT JOIN FETCH i.tipoIncidencia " +
                            "WHERE c.nif = :nif", Conductor.class);
            query.setParameter("nif", nif);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    /**
     * Busca conductores por vehículo
     * Útil para operaciones relacionadas con vehículos específicos
     */
    public List<Conductor> findByVehiculo(String idauto) {
        TypedQuery<Conductor> query = getEntityManager()
            .createQuery("SELECT c FROM Conductor c " +
                        "WHERE c.vehiculo.idauto = :idauto", Conductor.class);
        query.setParameter("idauto", idauto);
        return query.getResultList();
    }
    
    /**
     * Actualiza los puntos de un conductor
     * Método de conveniencia para operaciones frecuentes
     */
    public void updatePuntos(String nif, int puntos) {
        getEntityManager()
            .createQuery("UPDATE Conductor c SET c.puntos = :puntos WHERE c.nif = :nif")
            .setParameter("puntos", puntos)
            .setParameter("nif", nif)
            .executeUpdate();
    }
    
    /**
     * Verifica si existe un conductor con el NIF dado
     */
    public boolean existsByNif(String nif) {
        Long count = getEntityManager()
            .createQuery("SELECT COUNT(c) FROM Conductor c WHERE c.nif = :nif", Long.class)
            .setParameter("nif", nif)
            .getSingleResult();
        return count > 0;
    }
}