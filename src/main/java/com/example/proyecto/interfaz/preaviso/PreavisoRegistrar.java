package com.example.proyecto.interfaz.preaviso;

import com.example.proyecto.interfaz.PrincipalView;
import com.example.proyecto.modal.DatabaseManager;
import com.example.proyecto.modal.EleccionesDAO;
import com.example.proyecto.modal.EmpresaDAO;
import com.example.proyecto.modal.Preaviso;
import com.example.proyecto.util.Constantes;
import com.example.proyecto.util.CumplimentarPDFException;
import com.example.proyecto.util.MessageManager;
import com.example.proyecto.util.Registro;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * La clase `PreavisoRegistrar` gestiona la lógica de registro de preaviso.
 *
 * @autor Alberto Castro <AlbertoCastrovas@gmail.com>
 * @version 1.0
 */
public class PreavisoRegistrar {

    private final PrincipalView nuevaVentanaPreaviso;

    /**
     * Constructor de la clase PreavisoRegistrar.
     *
     * @param nuevaVentanaPreaviso La vista principal.
     */
    public PreavisoRegistrar(@NotNull PrincipalView nuevaVentanaPreaviso) {
        this.nuevaVentanaPreaviso = nuevaVentanaPreaviso;
    }

    /**
     * Registra el preaviso.
     *
     * @param nuevoPreaviso El objeto Preaviso que se va a registrar.
     * @param stage La ventana de preaviso.
     */
    public void registrarPreaviso(@NotNull Preaviso nuevoPreaviso, @NotNull Stage stage) throws CumplimentarPDFException, SQLException {
        GridPane mensajeConfirmacion = construirMensajeConfirmacion(nuevoPreaviso);

        Optional<ButtonType> result = nuevaVentanaPreaviso.mostrarAlertaConfirmacion(mensajeConfirmacion);
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Crea instancia de DatabaseManager y EmpresaDAO
                DatabaseManager dbManager = new DatabaseManager(nuevaVentanaPreaviso);
                EmpresaDAO empresaDAO = new EmpresaDAO(nuevaVentanaPreaviso, dbManager);
                EleccionesDAO eleccionesDAO = new EleccionesDAO(nuevaVentanaPreaviso, dbManager);

                // Inserta los datos de la empresa en la base de datos
                empresaDAO.insertEmpresa(nuevoPreaviso);
                eleccionesDAO.insertFechaConstitucion(nuevoPreaviso);

                // Registra el preaviso
                Registro registro = new Registro(nuevoPreaviso, nuevaVentanaPreaviso);
                registro.registrarNuevoPreaviso(nuevoPreaviso);
                stage.close();
            } catch (SQLException | IOException e) {
                nuevaVentanaPreaviso.mostrarMensaje(MessageManager.getMessage("preaviso.error_db") + e.getMessage(), false);
            }
        }
    }

    /**
     * Construye el mensaje de confirmación para el registro del preaviso.
     *
     * @param nuevoPreaviso El objeto Preaviso que se va a registrar.
     * @return Un GridPane con los datos del preaviso.
     */
    @NotNull
    private GridPane construirMensajeConfirmacion(@NotNull Preaviso nuevoPreaviso) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(Constantes.ESPACIADO_HGAP);
        gridPane.setVgap(Constantes.ESPACIADO_VGAP);
        gridPane.setPadding(new Insets(Constantes.ESPACIADO_PADDING));

        int rowIndex = 0;
        agregarFila(gridPane, MessageManager.getMessage("preaviso.nombre_empresa"), nuevoPreaviso.getNombreEmpresa(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.cif"), nuevoPreaviso.getCIF(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.nombre_comercial"), nuevoPreaviso.getNombreComercial(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.nombre_centro"), nuevoPreaviso.getNombreCentro(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.direccion"), nuevoPreaviso.getDireccion(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.municipio"), nuevoPreaviso.getMunicipio(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.codigo_postal"), nuevoPreaviso.getCodigoPostal(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.provincia"), nuevoPreaviso.getProvincia(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.num_trabajadores"), nuevoPreaviso.getTotalTrabajadores(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.num_seguridad_social"), nuevoPreaviso.getNumeroISS(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.mes_eleccion"), nuevoPreaviso.getMesElecciones(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.promotores"), nuevoPreaviso.getPromotores(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.fecha_inicio"), nuevoPreaviso.getFechaConstitucion(), rowIndex++);
        agregarFila(gridPane, MessageManager.getMessage("preaviso.fecha_preaviso"), nuevoPreaviso.getFechaPreaviso(), rowIndex);

        return gridPane;
    }

    /**
     * Método auxiliar para agregar una fila al GridPane.
     *
     * @param gridPane El GridPane al que se agregará la fila.
     * @param label    La etiqueta de la fila.
     * @param value    El valor de la fila.
     * @param rowIndex El índice de la fila.
     */
    private void agregarFila(@NotNull GridPane gridPane, @NotNull String label, @NotNull String value, int rowIndex) {
        Text labelText = new Text(STR."\{label}: ");
        Text valueText = new Text(value);
        valueText.setStyle(Constantes.FONT_WEIGHT_BOLD);

        gridPane.add(labelText, 0, rowIndex);
        gridPane.add(valueText, 1, rowIndex);
    }
}
