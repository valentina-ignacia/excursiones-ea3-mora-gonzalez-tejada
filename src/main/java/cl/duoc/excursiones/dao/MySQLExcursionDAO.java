package cl.duoc.excursiones.dao;

import cl.duoc.excursiones.model.Excursion;

import cl.duoc.excursiones.conexion.ConexionBD;
import cl.duoc.excursiones.model.Excursion;
import cl.duoc.excursiones.model.Trekking;
import cl.duoc.excursiones.model.Kayak;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLExcursionDAO implements ExcursionDAO {
    private static final String SELECT_ALL =
            "SELECT * FROM excursion ORDER BY id";

    private static final String INSERT_TREKKING =
            "INSERT INTO excursion " +
                    "(id_tipo, codigo, participantes, distancia_km, " +
                    "desnivel_metros, guia_bilingue, seguro_incluido) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String INSERT_KAYAK =
            "INSERT INTO excursion " +
                    "(id_tipo, codigo, participantes, distancia_km, " +
                    "duracion_horas) " +
                    "VALUES (?, ?, ?, ?, ?)";

    @Override
    public List<Excursion> obtenerTodos() throws PersistenciaException {
        ArrayList<Excursion> excursiones = new ArrayList<>();
        try(Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(SELECT_ALL);
            ResultSet rs = ps.executeQuery();
        ){
            while (rs.next()){
                excursiones.add(ExcursionMapper.mapear(rs));
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener excursiones: ", e);
        }

        return excursiones;
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
