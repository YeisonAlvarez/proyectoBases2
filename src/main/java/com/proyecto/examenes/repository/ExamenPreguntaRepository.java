package com.proyecto.examenes.repository;

import com.proyecto.examenes.model.ExamenPregunta;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ExamenPreguntaRepository {

    private final Connection connection;

    public ExamenPreguntaRepository() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "examenes",
                "admin"
        );
    }

    public boolean asignarPregunta(ExamenPregunta ep) {
        try (CallableStatement stmt = connection.prepareCall("{ call asignar_pregunta_examen(?, ?, ?) }")) {
            stmt.setLong(1, ep.getIdExamen());
            stmt.setLong(2, ep.getIdPregunta());
            stmt.setDouble(3, ep.getPorcentaje());

            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, Object> obtenerExamenConPreguntas(Long idExamen) {
        Map<String, Object> resultado = new HashMap<>();

        try {
            // Obtener examen
            PreparedStatement stmtExamen = connection.prepareStatement("SELECT * FROM examenes WHERE id = ?");
            stmtExamen.setLong(1, idExamen);
            ResultSet rsExamen = stmtExamen.executeQuery();
            if (rsExamen.next()) {
                Map<String, Object> examen = new HashMap<>();
                examen.put("id", rsExamen.getLong("id"));
                examen.put("nombre", rsExamen.getString("nombre"));
                examen.put("descripcion", rsExamen.getString("descripcion"));
                resultado.put("examen", examen);
            }

            // Obtener preguntas asignadas al examen
            PreparedStatement stmtPreguntas = connection.prepareStatement("""
            SELECT p.* FROM preguntas p
            JOIN examen_pregunta ep ON p.id = ep.id_pregunta
            WHERE ep.id_examen = ?
        """);
            stmtPreguntas.setLong(1, idExamen);
            ResultSet rsPreguntas = stmtPreguntas.executeQuery();

            List<Map<String, Object>> preguntas = new ArrayList<>();

            while (rsPreguntas.next()) {
                Long idPregunta = rsPreguntas.getLong("id");
                Map<String, Object> pregunta = new HashMap<>();
                pregunta.put("id", idPregunta);
                pregunta.put("texto", rsPreguntas.getString("texto"));
                pregunta.put("tipo", rsPreguntas.getString("tipo"));
                pregunta.put("porcentaje", rsPreguntas.getDouble("porcentaje"));
                pregunta.put("dificultad", rsPreguntas.getString("dificultad"));
                pregunta.put("tiempoLimite", rsPreguntas.getInt("tiempo_limite"));

                // Subpreguntas
                PreparedStatement stmtSub = connection.prepareStatement("SELECT * FROM subpreguntas WHERE id_pregunta_padre = ?");
                stmtSub.setLong(1, idPregunta);
                ResultSet rsSub = stmtSub.executeQuery();
                List<Map<String, Object>> subpreguntas = new ArrayList<>();
                while (rsSub.next()) {
                    Long idSub = rsSub.getLong("id");
                    Map<String, Object> sub = new HashMap<>();
                    sub.put("id", idSub);
                    sub.put("texto", rsSub.getString("texto"));
                    sub.put("porcentaje", rsSub.getDouble("porcentaje"));
                    sub.put("idPreguntaPadre", rsSub.getLong("id_pregunta_padre"));

                    // Opciones de la subpregunta
                    PreparedStatement stmtOpcSub = connection.prepareStatement("SELECT * FROM opciones WHERE id_subpregunta = ?");
                    stmtOpcSub.setLong(1, idSub);
                    ResultSet rsOpcSub = stmtOpcSub.executeQuery();
                    List<Map<String, Object>> opcionesSub = new ArrayList<>();
                    while (rsOpcSub.next()) {
                        Map<String, Object> opcion = new HashMap<>();
                        opcion.put("id", rsOpcSub.getLong("id"));
                        opcion.put("texto", rsOpcSub.getString("texto"));
                        opcion.put("esCorrecta", rsOpcSub.getString("es_correcta"));
                        opcion.put("idPregunta", rsOpcSub.getObject("id_pregunta"));
                        opcionesSub.add(opcion);
                    }
                    sub.put("opciones", opcionesSub);

                    subpreguntas.add(sub);
                }
                pregunta.put("subpreguntas", subpreguntas);

                // Opciones de la pregunta principal
                PreparedStatement stmtOpc = connection.prepareStatement("SELECT * FROM opciones WHERE id_pregunta = ? AND id_subpregunta IS NULL");
                stmtOpc.setLong(1, idPregunta);
                ResultSet rsOpc = stmtOpc.executeQuery();
                List<Map<String, Object>> opciones = new ArrayList<>();
                while (rsOpc.next()) {
                    Map<String, Object> opcion = new HashMap<>();
                    opcion.put("id", rsOpc.getLong("id"));
                    opcion.put("texto", rsOpc.getString("texto"));
                    opcion.put("esCorrecta", rsOpc.getString("es_correcta"));
                    opcion.put("idSubpregunta", rsOpc.getObject("id_subpregunta"));
                    opciones.add(opcion);
                }
                pregunta.put("opciones", opciones);

                preguntas.add(pregunta);
            }

            resultado.put("preguntas", preguntas);
            return resultado;

        } catch (SQLException e) {
            e.printStackTrace();
            return Map.of("error", "Error al cargar el examen");
        }
    }

}
