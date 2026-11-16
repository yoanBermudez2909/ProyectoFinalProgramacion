module co.edu.uniquindio.cinemauq {
    requires javafx.controls;
    requires javafx.fxml;

    opens co.edu.uniquindio.cinemauq.modelo to javafx.base;
    exports co.edu.uniquindio.cinemauq.app;
    exports co.edu.uniquindio.cinemauq.modelo;
    exports co.edu.uniquindio.cinemauq.excepciones;
}