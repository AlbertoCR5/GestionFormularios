package com.albertocr.gestionformularios.model;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Gestiona la conexión y la inicialización de la base de datos SQLite.
 *
 * @author Alberto Castro <AlbertoCastroCR>
 * @version 1.5
 */
public final class DatabaseManager {

    private static final String URL = "jdbc:sqlite:elecciones.db";
    private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);

    private DatabaseManager() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no puede ser instanciada.");
    }

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            logger.error("Error fatal al conectar con la base de datos en {}", URL, e);
            throw e;
        }
    }

    public static void inicializarBaseDatos() throws SQLException {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            crearTablaUsuarios(stmt);
            crearTablaEmpresas(stmt);
            crearTablaElecciones(stmt);
            crearTablaCandidatos(stmt);
            crearUsuarioAdminPorDefectoSiNecesario(conn);
        } catch (SQLException e) {
            logger.error("Error al inicializar la base de datos.", e);
            throw e;
        }
    }

    private static void crearUsuarioAdminPorDefectoSiNecesario(Connection conn) throws SQLException {
        String sqlCheck = "SELECT COUNT(*) FROM usuarios";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlCheck)) {

            if (rs.next() && rs.getInt(1) == 0) {
                logger.info("No se encontraron usuarios. Creando usuario administrador por defecto.");
                String sqlInsert = "INSERT INTO usuarios(nombre_usuario, contrasena, rol, debe_cambiar_contrasena) VALUES(?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
                    String hashContrasena = BCrypt.hashpw("admin", BCrypt.gensalt());
                    pstmt.setString(1, "admin");
                    pstmt.setString(2, hashContrasena);
                    pstmt.setString(3, Usuario.Rol.ADMIN.name());
                    pstmt.setInt(4, 1);
                    pstmt.executeUpdate();
                    logger.info("Usuario 'admin' creado con éxito.");
                }
            } else {
                logger.info("La base de datos ya contiene usuarios.");
            }
        }
    }

    private static void crearTablaUsuarios(Statement stmt) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS usuarios (
                id_usuario INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre_usuario TEXT NOT NULL UNIQUE,
                contrasena TEXT NOT NULL,
                rol TEXT NOT NULL DEFAULT 'STANDARD',
                debe_cambiar_contrasena INTEGER NOT NULL DEFAULT 0
            );""";
        stmt.execute(sql);
        logger.info("Tabla 'usuarios' verificada o creada correctamente.");
    }

    private static void crearTablaEmpresas(Statement stmt) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS empresas (
                id_empresa INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre_empresa TEXT NOT NULL,
                cif TEXT NOT NULL UNIQUE,
                nombre_comercial TEXT,
                nombre_centro TEXT,
                direccion TEXT,
                municipio TEXT,
                comarca TEXT,
                provincia TEXT,
                codigo_postal TEXT,
                numero_iss TEXT,
                numero_id TEXT,
                localidad TEXT,
                actividad TEXT,
                convenio TEXT
            );""";
        stmt.execute(sql);
        logger.info("Tabla 'empresas' verificada o creada correctamente.");
    }

    private static void crearTablaElecciones(Statement stmt) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS elecciones (
                id_eleccion INTEGER PRIMARY KEY AUTOINCREMENT,
                id_empresa INTEGER,
                numero_trabajadores INTEGER,
                fecha_constitucion DATE,
                promotores TEXT,
                localidad_fecha TEXT,
                fecha DATE,
                tipo_eleccion TEXT,
                FOREIGN KEY (id_empresa) REFERENCES empresas (id_empresa)
            );""";
        stmt.execute(sql);
        logger.info("Tabla 'elecciones' verificada o creada correctamente.");
    }

    private static void crearTablaCandidatos(Statement stmt) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS candidatos (
                id_candidato INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                apellidos TEXT NOT NULL,
                dni TEXT NOT NULL UNIQUE,
                colegio TEXT
            );""";
        stmt.execute(sql);
        logger.info("Tabla 'candidatos' verificada o creada correctamente.");
        // Intento de migración: si la columna 'colegio' no existe en versiones anteriores, añadirla.
        try {
            stmt.execute("ALTER TABLE candidatos ADD COLUMN colegio TEXT;");
        } catch (SQLException e) {
            // Si la columna ya existe, SQLite arrojará una excepción; la ignoramos porque no es crítica.
            logger.debug("Posible columna 'colegio' ya existente en 'candidatos' - ignorando ALTER TABLE.");
        }
    }
}
