package es.ubu.lsi.service.multas;

import es.ubu.lsi.dao.multas.ConductorDAO;
import es.ubu.lsi.dao.multas.IncidenciaDAO;
import es.ubu.lsi.dao.multas.TipoIncidenciaDAO;
import es.ubu.lsi.dao.multas.VehiculoDAO;
import es.ubu.lsi.model.multas.*;
import es.ubu.lsi.service.PersistenceException;
import es.ubu.lsi.service.PersistenceService;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

public class ServiceImpl extends PersistenceService implements Service {

    @Override
    public void insertarIncidencia(Date fecha, String nif, long tipo) throws PersistenceException {
        EntityManager em = createSession();
        try {
            beginTransaction(em);

            ConductorDAO conductorDAO = new ConductorDAO();
            conductorDAO.setEntityManager(em);

            TipoIncidenciaDAO tipoDAO = new TipoIncidenciaDAO();
            tipoDAO.setEntityManager(em);

            IncidenciaDAO incidenciaDAO = new IncidenciaDAO();
            incidenciaDAO.setEntityManager(em);

            Conductor conductor = conductorDAO.findById(nif);
            if (conductor == null) {
                throw new IncidentException(IncidentError.NOT_EXIST_DRIVER);
            }

            TipoIncidencia tipoIncidencia = tipoDAO.findByIdSafe(tipo);
            if (tipoIncidencia == null) {
                throw new IncidentException(IncidentError.NOT_EXIST_INCIDENT_TYPE);
            }

            int puntosActuales = conductor.getPuntos();
            int valor = tipoIncidencia.getValor();

            if (puntosActuales - valor < 0) {
                throw new IncidentException(IncidentError.NOT_AVAILABLE_POINTS);
            }

            // Insertar incidencia
            IncidenciaPK pk = new IncidenciaPK(fecha, nif);
            Incidencia incidencia = new Incidencia(pk, null, tipoIncidencia);
            incidencia.setConductor(conductor);
            incidenciaDAO.persist(incidencia);

            conductor.setPuntos(puntosActuales - valor);

            commitTransaction(em);
        } catch (IncidentException e) {
            rollbackTransaction(em);
            throw e;
        } catch (Exception e) {
            rollbackTransaction(em);
            throw new PersistenceException("Error insertando incidencia", e);
        } finally {
            close(em);
        }
    }

    @Override
    public void indultar(String nif) throws PersistenceException {
        EntityManager em = createSession();
        try {
            beginTransaction(em);

            ConductorDAO conductorDAO = new ConductorDAO();
            conductorDAO.setEntityManager(em);

            IncidenciaDAO incidenciaDAO = new IncidenciaDAO();
            incidenciaDAO.setEntityManager(em);

            Conductor conductor = conductorDAO.findByNifWithIncidencias(nif);
            if (conductor == null) {
                throw new IncidentException(IncidentError.NOT_EXIST_DRIVER);
            }

            incidenciaDAO.deleteAllByConductorNif(nif);
            conductor.setPuntos(MAXIMO_PUNTOS);

            commitTransaction(em);
        } catch (IncidentException e) {
            rollbackTransaction(em);
            throw e;
        } catch (Exception e) {
            rollbackTransaction(em);
            throw new PersistenceException("Error indultando conductor", e);
        } finally {
            close(em);
        }
    }

    @Override
    public List<Vehiculo> consultarVehiculos() throws PersistenceException {
        EntityManager em = createSession();
        try {
            beginTransaction(em);

            VehiculoDAO vehiculoDAO = new VehiculoDAO();
            vehiculoDAO.setEntityManager(em);

            List<Vehiculo> vehiculos = vehiculoDAO.findAllWithGraph();

            commitTransaction(em);
            return vehiculos;
        } catch (Exception e) {
            rollbackTransaction(em);
            throw new PersistenceException("Error consultando vehiculos", e);
        } finally {
            close(em);
        }
    }
}
