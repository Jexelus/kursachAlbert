module com.example.kursachalbert.kursachalbert {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.healthmarketscience.jackcess;
    requires java.sql;

    opens com.example.kursachalbert.kursachalbert to javafx.fxml;
    exports com.example.kursachalbert.kursachalbert;
}