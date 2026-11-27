package cl.duoc.excursiones.dao;

import cl.duoc.excursiones.model.Excursion;

import java.util.List;

public class MySQLExcursionDAO implements ExcursionDAO {

    @Override
    public List<Excursion> obtenerTodos()
            throws PersistenciaException {

        throw new PersistenciaException(
                "Operación no implementada"
        );
    }

    @Override
    public void insertar(Excursion excursion)
            throws PersistenciaException {

        throw new PersistenciaException(
                "Operación no implementada"
        );
    }

    @Override
    public void actualizar(Excursion excursion)
            throws PersistenciaException {

        throw new PersistenciaException(
                "Operación no implementada"
        );
    }

    @Override
    public void eliminar(int id)
            throws PersistenciaException {

        throw new PersistenciaException(
                "Operación no implementada"
        );
    }
}
