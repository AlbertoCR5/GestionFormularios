package com.albertocr.gestionformularios.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO para gestionar las operaciones CRUD de la tabla 'empresas'.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.4
 */
public class EmpresaDAO {

    private static final Logger logger = LoggerFactory.getLogger(EmpresaDAO.class);

    public boolean guardarOActualizar(Empresa empresa) {
        String sql = """
            INSERT INTO empresas (nombre_empresa, cif, nombre_comercial, nombre_centro, direccion, municipio, comarca, provincia, codigo_postal, numero_iss, numero_id, localidad, actividad, convenio)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            ON CONFLICT(cif) DO UPDATE SET
                nombre_empresa = excluded.nombre_empresa,
                nombre_comercial = excluded.nombre_comercial,
                nombre_centro = excluded.nombre_centro,
                direccion = excluded.direccion,
                municipio = excluded.municipio,
                comarca = excluded.comarca,
                provincia = excluded.provincia,
                codigo_postal = excluded.codigo_postal,
                numero_iss = excluded.numero_iss,
                numero_id = excluded.numero_id,
                localidad = excluded.localidad,
                actividad = excluded.actividad,
                convenio = excluded.convenio;
            """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setPreparedStatement(pstmt, empresa);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            logger.error("Error al guardar o actualizar la empresa con CIF '{}'", empresa.getCif(), e);
            return false;
        }
    }

    public Optional<Empresa> buscarPorId(int id) {
        String sql = "SELECT * FROM empresas WHERE id_empresa = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(crearEmpresaDesdeResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error al buscar la empresa con ID '{}'", id, e);
        }
        return Optional.empty();
    }

    public Optional<Empresa> buscarPorCif(String cif) {
        String sql = "SELECT * FROM empresas WHERE cif = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cif);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(crearEmpresaDesdeResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error al buscar la empresa con CIF '{}'", cif, e);
        }
        return Optional.empty();
    }

    public List<Empresa> buscarTodas() {
        List<Empresa> empresas = new ArrayList<>();
        String sql = "SELECT * FROM empresas";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                empresas.add(crearEmpresaDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error("Error al obtener todas las empresas", e);
        }
        return empresas;
    }

    private void setPreparedStatement(PreparedStatement pstmt, Empresa empresa) throws SQLException {
        pstmt.setString(1, empresa.getNombre());
        pstmt.setString(2, empresa.getCif());
        pstmt.setString(3, empresa.getNombreComercial());
        pstmt.setString(4, empresa.getNombreCentro());
        pstmt.setString(5, empresa.getDireccion());
        pstmt.setString(6, empresa.getMunicipio());
        pstmt.setString(7, empresa.getComarca());
        pstmt.setString(8, empresa.getProvincia());
        pstmt.setString(9, empresa.getCodigoPostal());
        pstmt.setString(10, empresa.getNumeroISS());
        pstmt.setString(11, empresa.getNumeroId());
        pstmt.setString(12, empresa.getLocalidad());
        pstmt.setString(13, empresa.getActividad());
        pstmt.setString(14, empresa.getConvenio());
    }

    private Empresa crearEmpresaDesdeResultSet(ResultSet rs) throws SQLException {
        Empresa empresa = new Empresa();
        empresa.setId(rs.getInt("id_empresa"));
        empresa.setNombre(rs.getString("nombre_empresa"));
        empresa.setCif(rs.getString("cif"));
        empresa.setNombreComercial(rs.getString("nombre_comercial"));
        empresa.setNombreCentro(rs.getString("nombre_centro"));
        empresa.setDireccion(rs.getString("direccion"));
        empresa.setMunicipio(rs.getString("municipio"));
        empresa.setComarca(rs.getString("comarca"));
        empresa.setProvincia(rs.getString("provincia"));
        empresa.setCodigoPostal(rs.getString("codigo_postal"));
        empresa.setNumeroISS(rs.getString("numero_iss"));
        empresa.setNumeroId(rs.getString("numero_id"));
        empresa.setLocalidad(rs.getString("localidad"));
        empresa.setActividad(rs.getString("actividad"));
        empresa.setConvenio(rs.getString("convenio"));
        return empresa;
    }
}
