package com.proyecto.examenes.repository;

import com.proyecto.examenes.model.Pregunta;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class  PreguntaRepository {

    private final Connection connection;

    public PreguntaRepository() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "examenes",
                "admin"
        );
    }

    public Long crearPregunta(Pregunta pregunta) {
        try (CallableStatement stmt = connection.prepareCall("{ call crear_pregunta(?, ?, ?, ?, ?, ?, ?, ?, ?) }")) {
            stmt.setString(1, pregunta.getTexto());
            stmt.setString(2, pregunta.getTipo());
            stmt.setDouble(3, pregunta.getPorcentaje());
            stmt.setString(4, pregunta.getDificultad());
            if (pregunta.getTiempoLimite() != null) {
                stmt.setInt(5, pregunta.getTiempoLimite());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }
            stmt.setString(6, pregunta.getEsPublica());
            stmt.setLong(7, pregunta.getIdTema());
            stmt.setLong(8, pregunta.getIdProfesor());
            stmt.registerOutParameter(9, Types.NUMERIC);

            stmt.execute();
            return stmt.getLong(9);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Pregunta> findPreguntasPorTema(Long idTema, int limite) {
        List<Pregunta> lista = new ArrayList<>();
        String sql = "SELECT * FROM preguntas WHERE id_tema = ? FETCH FIRST ? ROWS ONLY";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idTema);
            stmt.setInt(2, limite);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pregunta p = new Pregunta();
                p.setId(rs.getLong("id"));
                p.setTexto(rs.getString("texto"));
                p.setTipo(rs.getString("tipo"));
                p.setDificultad(rs.getString("dificultad"));
                p.setPorcentaje((double) rs.getInt("porcentaje"));
                p.setTiempoLimite(rs.getInt("tiempo_limite"));
                p.setEsPublica(rs.getString("es_publica"));
                p.setIdTema(rs.getLong("id_tema"));
                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}
