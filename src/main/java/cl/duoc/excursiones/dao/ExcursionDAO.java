package cl.duoc.excursiones.dao;

import cl.duoc.excursiones.model.Excursion;

import java.util.List;

/**
 * Operaciones de almacenamiento que utiliza la ventana.
 * <p>
 * Si una operación no puede completarse, debe lanzar PersistenciaException con un
 * mensaje que indique qué operación falló; la ventana muestra ese mensaje en el
 * área de mensajes.
 */
public interface ExcursionDAO {

    /**
     * @return todas las excursiones almacenadas, cada una como objeto de su tipo
     *         (Trekking o Kayak), con todos sus datos y su identificador.
     */
    List<Excursion> obtenerTodos() throws PersistenciaException;

    /**
     * Guarda una excursión nueva con todos sus datos, incluidos los propios de su tipo.
     * El identificador lo genera la base de datos.
     */
    void insertar(Excursion excursion) throws PersistenciaException;

    /**
     * Modifica todos los datos de la excursión que tiene el mismo identificador,
     * incluidos los propios de su tipo.
     */
    void actualizar(Excursion excursion) throws PersistenciaException;

    /**
     * Elimina la excursión con el identificador indicado.
     */
    void eliminar(int id) throws PersistenciaException;
}
