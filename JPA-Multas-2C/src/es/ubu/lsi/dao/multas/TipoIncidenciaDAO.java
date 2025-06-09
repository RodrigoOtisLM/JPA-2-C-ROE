package es.ubu.lsi.dao.multas;

import es.ubu.lsi.dao.JpaDAO;
import es.ubu.lsi.model.multas.TipoIncidencia;
import javax.persistence.TypedQuery;
import javax.persistence.NoResultException;
import java.util.List;

/**
 * DAO para la entidad TipoIncidencia
 * Proporciona operaciones de acceso a datos específicas para tipos de incidencias
 */
public class TipoIncidenciaDAO extends JpaDAO<TipoIncidencia, Long> {

    /**
     * Constructor que especifica la clase de entidad
     */
    public TipoIncidenciaDAO() {
        super(TipoIncidencia.class);
    }

    /**
     * Implementación obligatoria del método abstracto findAll
     * Devuelve TODAS las instancias de TipoIncidencia
     */
    @Override
    public List<TipoIncidencia> findAll() {
        TypedQuery<TipoIncidencia> query = getEntityManager()
            .createQuery("SELECT t FROM TipoIncidencia t ORDER BY t.id", TipoIncidencia.class);
        return query.getResultList();
    }
    
    /**
     * Busca un tipo de incidencia por su ID
     * Devuelve null si no existe
     */
    public TipoIncidencia findByIdSafe(Long id) {
        try {
            return findById(id);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Verifica si existe un tipo de incidencia con el ID dado
     */
    public boolean existsById(Long id) {
        Long count = getEntityManager()
            .createQuery("SELECT COUNT(t) FROM TipoIncidencia t WHERE t.id = :id", Long.class)
            .setParameter("id", id)
            .getSingleResult();
        return count > 0;
    }
    
    /**
     * Obtiene el valor (puntos) de un tipo de incidencia
     * Útil para cálculos de puntos sin cargar la entidad completa
     */
    public Integer getValorById(Long id) {
        try {
            return getEntityManager()
                .createQuery("SELECT t.valor FROM TipoIncidencia t WHERE t.id = :id", Integer.class)
                .setParameter("id", id)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    /**
     * Busca tipos de incidencia por descripción
     */
    public List<TipoIncidencia> findByDescripcion(String descripcion) {
        TypedQuery<TipoIncidencia> query = getEntityManager()
            .createQuery("SELECT t FROM TipoIncidencia t " +
                        "WHERE UPPER(t.descripcion) LIKE UPPER(:descripcion)", TipoIncidencia.class);
        query.setParameter("descripcion", "%" + descripcion + "%");
        return query.getResultList();
    }
}
