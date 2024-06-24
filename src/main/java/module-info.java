module com.example.proyectodam {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.jetbrains.annotations;
    requires java.desktop;
    requires org.apache.pdfbox;
    requires java.sql;

    opens com.example.proyecto.interfaz to javafx.fxml;
    opens com.example.proyecto.main to javafx.fxml;
    opens com.example.proyecto.controller to javafx.fxml;
    exports com.example.proyecto.interfaz;
    exports com.example.proyecto.main;
    exports com.example.proyecto.modal;
    exports com.example.proyecto.util;
    exports com.example.proyecto.controller;
    exports com.example.proyecto.interfaz.preaviso;
    opens com.example.proyecto.interfaz.preaviso to javafx.fxml;
    exports com.example.proyecto.interfaz.escrutinio;
    opens com.example.proyecto.interfaz.escrutinio to javafx.fxml;
}