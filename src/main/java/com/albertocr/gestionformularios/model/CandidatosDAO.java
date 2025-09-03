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
 * Clase DAO para gestionar las operaciones CRUD de la tabla 'candidatos' en la base de datos.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.1
 */
public class CandidatosDAO {

    private static final Logger logger = LoggerFactory.getLogger(CandidatosDAO.class);

    /**
     * Guarda o actualiza un candidato en la base de datos.
     * Si un candidato con el mismo DNI ya existe, actualiza sus datos.
     * Si no existe, lo inserta como una nueva entrada.
     *
     * @param candidato El objeto {@link Candidato} a guardar o actualizar.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public boolean guardarOActualizar(Candidato candidato) {
        String sql = """
            INSERT INTO candidatos (nombre, apellidos, dni, colegio)
            VALUES (?, ?, ?, ?)
            ON CONFLICT(dni) DO UPDATE SET
                nombre = excluded.nombre,
                apellidos = excluded.apellidos,
                colegio = excluded.colegio;
            """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setPreparedStatement(pstmt, candidato);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Candidato con DNI '{}' guardado o actualizado correctamente.", candidato.getDni());
                return true;
            }
            return false;
        } catch (SQLException e) {
            logger.error("Error al guardar o actualizar el candidato con DNI '{}'", candidato.getDni(), e);
            return false;
        }
    }

    /**
     * Busca un candidato por su DNI.
     *
     * @param dni El DNI del candidato a buscar.
     * @return Un {@link Optional} con el candidato si se encuentra, o vacío si no.
     */
    public Optional<Candidato> buscarPorDni(String dni) {
    String sql = "SELECT * FROM candidatos WHERE dni = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dni);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(crearCandidatoDesdeResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error al buscar el candidato con DNI '{}'", dni, e);
        }
        return Optional.empty();
    }

    /**
     * Obtiene una lista de todos los candidatos registrados en la base de datos.
     *
     * @return Una lista de objetos {@link Candidato}. La lista estará vacía si no hay candidatos o si ocurre un error.
     */
    public List<Candidato> buscarTodos() {
        List<Candidato> candidatos = new ArrayList<>();
        String sql = "SELECT * FROM candidatos";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                candidatos.add(crearCandidatoDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error("Error al obtener todos los candidatos", e);
        }
        return candidatos;
    }

    /**
     * Busca candidatos por colegio.
     * @param colegio nombre del colegio (por ejemplo: "Especialistas")
     * @return lista de candidatos pertenecientes al colegio
     */
    public List<Candidato> buscarPorColegio(String colegio) {
        List<Candidato> candidatos = new ArrayList<>();
        String sql = "SELECT * FROM candidatos WHERE colegio = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, colegio);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    candidatos.add(crearCandidatoDesdeResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener candidatos por colegio '{}'", colegio, e);
        }
        return candidatos;
    }

    /**
     * Elimina un candidato de la base de datos por su DNI.
     *
     * @param dni El DNI del candidato a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean eliminarPorDni(String dni) {
        String sql = "DELETE FROM candidatos WHERE dni = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dni);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Candidato con DNI '{}' eliminado correctamente.", dni);
                return true;
            } else {
                logger.warn("No se encontró ningún candidato con DNI '{}' para eliminar.", dni);
                return false;
            }
        } catch (SQLException e) {
            logger.error("Error al eliminar el candidato con DNI '{}'", dni, e);
            return false;
        }
    }

    /**
     * Método de utilidad para configurar un PreparedStatement a partir de un objeto Candidato.
     *
     * @param pstmt     El PreparedStatement a configurar.
     * @param candidato El objeto Candidato con los datos.
     * @throws SQLException Si ocurre un error al establecer los parámetros.
     */
    private void setPreparedStatement(PreparedStatement pstmt, Candidato candidato) throws SQLException {
    pstmt.setString(1, candidato.getNombre());
    pstmt.setString(2, candidato.getApellidos());
    pstmt.setString(3, candidato.getDni());
    pstmt.setString(4, candidato.getColegio());
    }

    /**
     * Método de utilidad para crear un objeto Candidato a partir de un ResultSet.
     *
     * @param rs El ResultSet de la consulta.
     * @return Un nuevo objeto Candidato mapeado.
     * @throws SQLException si ocurre un error al leer los datos.
     */
    private Candidato crearCandidatoDesdeResultSet(ResultSet rs) throws SQLException {
        Candidato candidato = new Candidato();
        candidato.setId(rs.getInt("id_candidato"));
        candidato.setNombre(rs.getString("nombre"));
        candidato.setApellidos(rs.getString("apellidos"));
        candidato.setDni(rs.getString("dni"));
        // Campo nuevo colegio (puede ser null)
        try {
            candidato.setColegio(rs.getString("colegio"));
        } catch (SQLException ignored) { }
        return candidato;
    }
}
