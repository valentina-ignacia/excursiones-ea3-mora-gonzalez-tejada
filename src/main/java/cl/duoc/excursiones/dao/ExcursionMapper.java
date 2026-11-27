
package cl.duoc.excursiones.dao;

import cl.duoc.excursiones.model.Excursion;
import cl.duoc.excursiones.model.Kayak;
import cl.duoc.excursiones.model.Trekking;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExcursionMapper {

    public static Excursion mapear (ResultSet rs) throws SQLException {
        //datos comunes
        int id = rs.getInt("id");
        String codigo = rs.getString("codigo");
        int participantes = rs.getInt("participantes");
        double distanciaKm = rs.getDouble("distancia_km");

        Excursion excursion;

        //tomar tipo
        int tipo = rs.getInt("id_tipo");

        //subtipo trekking
        if (tipo == 1) {
            double desnivelMetros = rs.getDouble("desnivel_metros");
            boolean guiaBilingue = rs.getBoolean("guia_bilingue");
            boolean seguroIncluido = rs.getBoolean("seguro_incluido");

            excursion = new Trekking(codigo,participantes,distanciaKm,desnivelMetros,guiaBilingue,seguroIncluido);

        } else if (tipo == 2) {
            // subtipo kayak
            double duracionHoras = rs.getDouble("duracion_horas");
            excursion = new Kayak(codigo,participantes,distanciaKm,duracionHoras);
        } else {
            throw new SQLException("Tipo de excursión desconocido");
        }

        excursion.setId(id);
        return excursion;
    }

}