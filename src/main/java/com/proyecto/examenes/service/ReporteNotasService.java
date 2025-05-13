package com.proyecto.examenes.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.sql.*;

@Service
public class ReporteNotasService {

    private final Connection connection;

    public ReporteNotasService() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "examenes",
                "admin"
        );
    }

    public byte[] generarExcelNotas(Long idExamen) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT u.nombre AS estudiante, e.nombre AS examen, r.fecha, r.nota_total, r.tiempo_empleado, r.ip_origen " +
                        "FROM resultados r " +
                        "JOIN usuarios u ON u.id = r.id_estudiante " +
                        "JOIN examenes e ON e.id = r.id_examen " +
                        "WHERE r.id_examen = ?")) {

            stmt.setLong(1, idExamen);
            ResultSet rs = stmt.executeQuery();

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Notas");

            Row header = sheet.createRow(0);
            String[] cols = {"Estudiante", "Examen", "Fecha", "Nota", "Tiempo (min)", "IP"};
            for (int i = 0; i < cols.length; i++) {
                header.createCell(i).setCellValue(cols[i]);
            }

            int rowIdx = 1;
            while (rs.next()) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(rs.getString("estudiante"));
                row.createCell(1).setCellValue(rs.getString("examen"));
                row.createCell(2).setCellValue(rs.getDate("fecha").toString());
                row.createCell(3).setCellValue(rs.getDouble("nota_total"));
                row.createCell(4).setCellValue(rs.getDouble("tiempo_empleado"));
                row.createCell(5).setCellValue(rs.getString("ip_origen"));
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            workbook.close();
            return bos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
