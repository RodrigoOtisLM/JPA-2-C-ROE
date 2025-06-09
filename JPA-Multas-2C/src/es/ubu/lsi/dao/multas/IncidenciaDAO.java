package es.ubu.lsi.dao.multas;

import es.ubu.lsi.dao.JpaDAO;
import es.ubu.lsi.model.multas.Incidencia;
import es.ubu.lsi.model.multas.IncidenciaPK;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

/**
 * DAO para la entidad Incidencia
 * Proporciona operaciones de acceso a datos específicas para incidencias
 */
public class IncidenciaDAO extends JpaDAO<Incidencia, IncidenciaPK> {

    /**
     * Constructor que especifica la clase de entidad
     */
    public IncidenciaDAO() {
        super(Incidencia.class);
    }

    /**
     * Implementación obligatoria del método abstracto findAll
     * Devuelve TODAS las instancias de Incidencia
     */
    @Override
    public List<Incidencia> findAll() {
        TypedQuery<Incidencia> query = getEntityManager()
            .createQuery("SELECT i FROM Incidencia i " +
                        "ORDER BY i.id.fecha DESC, i.id.nif", Incidencia.class);
        return query.getResultList();
    }
    
    /**
     * Busca todas las incidencias de un conductor específico
     * Útil para operaciones de indulto y consultas por conductor
     */
    public List<Incidencia> findByConductorNif(String nif) {
        TypedQuery<Incidencia> query = getEntityManager()
            .createQuery("SELECT i FROM Incidencia i " +
                        "LEFT JOIN FETCH i.tipoIncidencia " +
                        "WHERE i.id.nif = :nif " +
                        "ORDER BY i.id.fecha DESC", Incidencia.class);
        query.setParameter("nif", nif);
        return query.getResultList();
    }
    
    /**
     * Elimina todas las incidencias de un conductor
     * Usado en la operación de indulto
     */
    public int deleteAllByConductorNif(String nif) {
        return getEntityManager()
            .createQuery("DELETE FROM Incidencia i WHERE i.id.nif = :nif")
            .setParameter("nif", nif)
            .executeUpdate();
    }
    
    /**
     * Busca incidencias por tipo
     */
    public List<Incidencia> findByTipoIncidencia(Long tipoId) {
        TypedQuery<Incidencia> query = getEntityManager()
            .createQuery("SELECT i FROM Incidencia i " +
                        "LEFT JOIN FETCH i.conductor " +
                        "WHERE i.tipoIncidencia.id = :tipoId " +
                        "ORDER BY i.id.fecha DESC", Incidencia.class);
        query.setParameter("tipoId", tipoId);
        return query.getResultList();
    }
    
    /**
     * Busca incidencias por rango de fechas
     */
    public List<Incidencia> findByFechaRange(Date fechaInicio, Date fechaFin) {
        TypedQuery<Incidencia> query = getEntityManager()
            .createQuery("SELECT i FROM Incidencia i " +
                        "LEFT JOIN FETCH i.conductor " +
                        "LEFT JOIN FETCH i.tipoIncidencia " +
                        "WHERE i.id.fecha BETWEEN :fechaInicio AND :fechaFin " +
                        "ORDER BY i.id.fecha DESC", Incidencia.class);
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaFin);
        return query.getResultList();
    }
    
    /**
     * Cuenta el número de incidencias de un conductor
     */
    public Long countByConductorNif(String nif) {
        return getEntityManager()
            .createQuery("SELECT COUNT(i) FROM Incidencia i WHERE i.id.nif = :nif", Long.class)
            .setParameter("nif", nif)
            .getSingleResult();
    }
    
    /**
     * Verifica si existe una incidencia con fecha y NIF específicos
     */
    public boolean existsByFechaAndNif(Date fecha, String nif) {
        Long count = getEntityManager()
            .createQuery("SELECT COUNT(i) FROM Incidencia i " +
                        "WHERE i.id.fecha = :fecha AND i.id.nif = :nif", Long.class)
            .setParameter("fecha", fecha)
            .setParameter("nif", nif)
            .getSingleResult();
        return count > 0;
    }
    
    /**
     * Calcula el total de puntos perdidos por un conductor
     */
    public Integer getTotalPuntosPerdidosByConductor(String nif) {
        Integer total = getEntityManager()
            .createQuery("SELECT SUM(i.tipoIncidencia.valor) FROM Incidencia i " +
                        "WHERE i.id.nif = :nif", Integer.class)
            .setParameter("nif", nif)
            .getSingleResult();
        return total != null ? total : 0;
    }
}