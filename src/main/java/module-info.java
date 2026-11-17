module co.edu.uniquindio.cinemauq {
    requires javafx.controls;
    requires javafx.fxml;

    opens co.edu.uniquindio.cinemauq to javafx.fxml;
    opens co.edu.uniquindio.cinemauq.viewController to javafx.fxml;
    opens co.edu.uniquindio.cinemauq.modelo;

    exports co.edu.uniquindio.cinemauq;
    exports co.edu.uniquindio.cinemauq.viewController;
    exports co.edu.uniquindio.cinemauq.modelo;
    exports co.edu.uniquindio.cinemauq.excepciones;
}