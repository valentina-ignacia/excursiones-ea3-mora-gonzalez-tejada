package cl.duoc.excursiones.dao;

import cl.duoc.excursiones.model.Excursion;

import cl.duoc.excursiones.conexion.ConexionBD;
import cl.duoc.excursiones.model.Trekking;
import cl.duoc.excursiones.model.Kayak;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLExcursionDAO implements ExcursionDAO {
    private static final String SELECT_ALL = "SELECT * FROM excursion";
    private static final String INSERT_TREKKING = "INSERT INTO excursion (codigo, participantes, distancia_km,desnivel_metros,guia_bilingue,seguro_incluido,id_tipo) VALUES (?,?,?,?,?,?,?)";
    private static final String INSERT_KAYAK = "INSERT INTO excursion (codigo, participantes, distancia_km,duracion_horas,id_tipo) VALUES (?,?,?,?,?)";
    private static final String UPDATE_TREKKING = "UPDATE excursion SET codigo = ?, participantes = ?, distancia_km = ?,desnivel_metros = ?, guia_bilingue = ?, seguro_incluido = ? WHERE id = ?";
    private static final String UPDATE_KAYAK = "UPDATE excursion SET codigo = ?, participantes = ?, distancia_km = ?, duracion_horas = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM excursion WHERE id = ?";

    //constructor vacío
    public MySQLExcursionDAO() {}

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
    public void insertar(Excursion excursion) throws PersistenciaException {
        String sql = (excursion instanceof Trekking)?INSERT_TREKKING:INSERT_KAYAK;

        try(
                Connection con = ConexionBD.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
        ){
            //comunes
            ps.setString(1,excursion.getCodigo());
            ps.setInt(2,excursion.getParticipantes());
            ps.setDouble(3, excursion.getDistanciaKm());

            //trekking
            if(excursion instanceof Trekking){
                Trekking trekking = (Trekking) excursion;
                ps.setDouble(4,trekking.getDesnivelMetros());
                ps.setBoolean(5,trekking.isGuiaBilingue());
                ps.setBoolean(6,trekking.isSeguroIncluido());
                ps.setInt(7,1);

            }else{
                //kayak
                Kayak kayak = (Kayak) excursion;
                ps.setDouble(4,kayak.getDuracionHoras());
                ps.setInt(5,2);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Error al insertar excursion: " ,e);
        }
    }

    @Override
    public void actualizar(Excursion excursion) throws PersistenciaException {
        String sql = (excursion instanceof Trekking)?UPDATE_TREKKING:UPDATE_KAYAK;

        try(
                Connection con = ConexionBD.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
        ){
            //comunes
            ps.setString(1,excursion.getCodigo());
            ps.setInt(2,excursion.getParticipantes());
            ps.setDouble(3, excursion.getDistanciaKm());

            //trekking
            if(excursion instanceof Trekking){
                Trekking trekking = (Trekking) excursion;
                ps.setDouble(4,trekking.getDesnivelMetros());
                ps.setBoolean(5,trekking.isGuiaBilingue());
                ps.setBoolean(6,trekking.isSeguroIncluido());
                ps.setInt(7, trekking.getId());

            }else{
                //kayak
                Kayak kayak = (Kayak) excursion;
                ps.setDouble(4,kayak.getDuracionHoras());
                ps.setInt(5, kayak.getId());
            }
            ps.executeUpdate();


        } catch (SQLException e) {
            throw new PersistenciaException (
                    "Error al actualizar excursion: " ,e);
        }
    }

    @Override
    public void eliminar(int id)
            throws PersistenciaException {

        throw new PersistenciaException(
                "Operación no implementada"
        );
    }
}
