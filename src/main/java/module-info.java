/**
 * Define el módulo de la aplicación GestionFormularios.
 * Especifica las dependencias y los paquetes que se abren para permitir la reflexión
 * por parte de frameworks como JavaFX.
 */
module com.albertocr.gestionformularios {
    // Módulos de JavaFX requeridos
    requires javafx.controls;
    requires javafx.fxml;

    // Módulos estándar de Java
    requires java.sql;
    requires java.logging;

    // Dependencias del proyecto
    // Nota: `jbcrypt` (org.mindrot:jbcrypt:0.4) no proporciona un nombre de módulo
    // explícito, por lo que Maven lo sitúa como "automatic module" (filename-based).
    // Resultado: durante la compilación aparece el aviso sobre filename-based automodules.
    // Decisión: mantener `requires jbcrypt;` para permitir acceso directo desde el módulo
    // mientras se documenta este hecho. En futuras iteraciones podemos:
    //  - reemplazar por una dependencia modular, o
    //  - usar Moditect para generar una versión modular de jbcrypt en el build.
    requires jbcrypt;
    requires org.apache.pdfbox;
    requires org.xerial.sqlitejdbc;
    requires org.slf4j;
    requires java.desktop;

    // Abre los paquetes a JavaFX para que pueda acceder a ellos mediante reflexión
    opens com.albertocr.gestionformularios.main to javafx.graphics, javafx.fxml;
    opens com.albertocr.gestionformularios.controller to javafx.fxml;
    opens com.albertocr.gestionformularios.controller.admin to javafx.fxml;
    opens com.albertocr.gestionformularios.controller.calendario to javafx.fxml;
    opens com.albertocr.gestionformularios.controller.candidato to javafx.fxml;
    opens com.albertocr.gestionformularios.controller.escrutinio to javafx.fxml;
    opens com.albertocr.gestionformularios.controller.preaviso to javafx.fxml;
    opens com.albertocr.gestionformularios.controller.usuario to javafx.fxml;
    opens com.albertocr.gestionformularios.model to javafx.base;
    opens com.albertocr.gestionformularios.interfaz to javafx.fxml;
    opens com.albertocr.gestionformularios.interfaz.escrutinio to javafx.fxml;
}