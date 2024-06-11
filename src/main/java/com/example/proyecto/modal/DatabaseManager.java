package com.example.proyecto.modal;

import com.example.proyecto.interfaz.PrincipalView;
import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.MessageManager;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * La clase DatabaseManager se encarga de gestionar la conexión a la base de datos.
 * Utiliza SQLite y JDBC para establecer la conexión.
 *
 * @author Alberto Castro <AlbertoCastrovas@gmail.com>
 */
public class DatabaseManager {

    private final PrincipalView view;
    public DatabaseManager(PrincipalView view) {
        this.view = view;
    }

    /**
     * Crea una nueva base de datos o se conecta a una existente.
     * Utiliza DriverManager para obtener una conexión a la base de datos SQLite.
     * Si la base de datos especificada en DATABASE_URL no existe, SQLite creará una nueva.
     * Si la base de datos ya existe, SQLite simplemente se conectará a ella.
     *
     * @throws SQLException Si ocurre un error al conectar a la base de datos.
     */
    public void createNewDatabase() throws SQLException{
        // Crear el directorio si no existe
        File directory = new File(Constantes.DATABASE).getParentFile();
        if (directory != null && !directory.exists()) {
            if (!directory.mkdirs()) {
                view.mostrarMensaje(MessageManager.getMessage("error.directory.create"), false);
                return;
            }
        }

        try (Connection conn = connect()) {
            if (conn != null) {
                UsuarioDAO usuarioDAO = new UsuarioDAO(view, this);
                EmpresaDAO empresaDAO = new EmpresaDAO(view, this);
                EleccionesDAO eleccionesDAO = new EleccionesDAO(view, this);
                CandidatosDAO candidatosDAO = new CandidatosDAO(view, this);

                usuarioDAO.createTableUsuario();
                empresaDAO.createTableEmpresa();
                eleccionesDAO.createTableElecciones();
                candidatosDAO.createTableCandidatos();
            }
        } catch (SQLException e) {
            view.mostrarMensaje(String.format(MessageManager.getMessage("error.database.create"), e.getMessage()), false);
        }
    }

    /**
     * Establece una conexión con la base de datos SQLite.
     *
     * @return Connection a la base de datos.
     * @throws SQLException Si ocurre un error al conectar a la base de datos.
     */
    public Connection connect() throws SQLException{
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(Constantes.DATABASE_URL);
        } catch (SQLException e) {
            view.mostrarMensaje((String.format(MessageManager.getMessage("error.database.connect"), e.getMessage())), false);
        }
        return connection;
    }
}

