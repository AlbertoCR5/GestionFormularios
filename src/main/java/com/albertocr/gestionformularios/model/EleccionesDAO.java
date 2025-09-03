package com.albertocr.gestionformularios.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO para gestionar las operaciones CRUD de la tabla 'elecciones'.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.4
 */
public class EleccionesDAO {

    private static final Logger logger = LoggerFactory.getLogger(EleccionesDAO.class);

    public boolean crearEleccion(Eleccion eleccion) {
        String sql = """
            INSERT INTO elecciones(id_empresa, numero_trabajadores, fecha_constitucion, promotores, localidad_fecha, fecha, tipo_eleccion)
            VALUES(?, ?, ?, ?, ?, ?, ?)
            """;
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setPreparedStatement(pstmt, eleccion);
            pstmt.executeUpdate();
            logger.info("Elección insertada para la empresa con ID: {}", eleccion.getIdEmpresa());
            return true;
        } catch (SQLException e) {
            logger.error("Error al insertar la elección para la empresa con ID: {}", eleccion.getIdEmpresa(), e);
            return false;
        }
    }

    public List<Eleccion> buscarPorEmpresa(int idEmpresa) {
        List<Eleccion> elecciones = new ArrayList<>();
        String sql = "SELECT * FROM elecciones WHERE id_empresa = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idEmpresa);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    elecciones.add(crearEleccionDesdeResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error al buscar elecciones para la empresa con ID '{}'", idEmpresa, e);
        }
        return elecciones;
    }

    /**
     * Busca la última elección registrada para una empresa específica, ordenada por fecha descendente.
     *
     * @param idEmpresa El ID de la empresa.
     * @return Un {@link Optional} que contiene la última elección si se encuentra, o vacío si no.
     */
    public Optional<Eleccion> buscarUltimaEleccionPorEmpresa(int idEmpresa) {
        String sql = "SELECT * FROM elecciones WHERE id_empresa = ? ORDER BY fecha DESC, id_eleccion DESC LIMIT 1";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idEmpresa);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(crearEleccionDesdeResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error al buscar la última elección para la empresa con ID '{}'", idEmpresa, e);
        }
        return Optional.empty();
    }

    public List<Eleccion> buscarTodas() {
        List<Eleccion> elecciones = new ArrayList<>();
        String sql = "SELECT * FROM elecciones";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                elecciones.add(crearEleccionDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error("Error al buscar todas las elecciones.", e);
        }
        return elecciones;
    }

    private void setPreparedStatement(PreparedStatement pstmt, Eleccion eleccion) throws SQLException {
        pstmt.setInt(1, eleccion.getIdEmpresa());
        pstmt.setInt(2, eleccion.getNumeroTrabajadores());
        pstmt.setDate(3, eleccion.getFechaConstitucion() != null ? Date.valueOf(eleccion.getFechaConstitucion()) : null);
        pstmt.setString(4, eleccion.getPromotores());
        pstmt.setString(5, eleccion.getLocalidadFecha());
        pstmt.setDate(6, eleccion.getFecha() != null ? Date.valueOf(eleccion.getFecha()) : null);
        pstmt.setString(7, eleccion.getTipoEleccion());
    }

    private Eleccion crearEleccionDesdeResultSet(ResultSet rs) throws SQLException {
        Eleccion eleccion = new Eleccion();
        eleccion.setId(rs.getInt("id_eleccion"));
        eleccion.setIdEmpresa(rs.getInt("id_empresa"));
        eleccion.setNumeroTrabajadores(rs.getInt("numero_trabajadores"));
        Date fechaConst = rs.getDate("fecha_constitucion");
        if (fechaConst != null) {
            eleccion.setFechaConstitucion(fechaConst.toLocalDate());
        }
        eleccion.setPromotores(rs.getString("promotores"));
        eleccion.setLocalidadFecha(rs.getString("localidad_fecha"));
        Date fecha = rs.getDate("fecha");
        if (fecha != null) {
            eleccion.setFecha(fecha.toLocalDate());
        }
        eleccion.setTipoEleccion(rs.getString("tipo_eleccion"));
        return eleccion;
    }
}
