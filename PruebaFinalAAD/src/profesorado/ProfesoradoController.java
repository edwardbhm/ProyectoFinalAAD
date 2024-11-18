package profesorado;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ProfesoradoController {

    @FXML
    private TextArea textOutput;

    private Profesorado profesorado;

    @FXML
    public void initialize() {
        profesorado = new Profesorado("profesorado.xml");
    }

    @FXML
    public void handleMostrarTodos() {
        StringBuilder output = new StringBuilder("Todos los profesores:\n");
        profesorado.mostrarTodos().forEach(profesor -> output.append(profesor).append("\n"));
        textOutput.setText(output.toString());
    }

    @FXML
    public void handleMostrarProfesoras() {
        StringBuilder output = new StringBuilder("Profesoras:\n");
        profesorado.mostrarProfesoras().forEach(profesor -> output.append(profesor).append("\n"));
        textOutput.setText(output.toString());
    }

    @FXML
    public void handleMostrarInformaticos() {
        StringBuilder output = new StringBuilder("Profesores de Informática:\n");
        profesorado.mostrarInformaticos().forEach(profesor -> output.append(profesor).append("\n"));
        textOutput.setText(output.toString());
    }

    @FXML
    public void handleMasAntiguo() {
        Profesor antiguo = profesorado.obtenerProfesorMasAntiguo();
        textOutput.setText("Profesor/a más antiguo/a:\n" + (antiguo != null ? antiguo : "No hay datos disponibles."));
    }

    @FXML
    public void handleSumaSueldos() {
        double sumaSueldos = profesorado.sumarSueldos();
        textOutput.setText("Suma total de sueldos:\n" + sumaSueldos);
    }

    @FXML
    public void handleAñadirProfesor() {
        Profesor nuevoProfesor = new Profesor("Laura", "Gómez", "laura.gomez@salesianas.org", "F", 2024, "Informática", 2000);
        if (profesorado.profesorYaExiste(nuevoProfesor.getMail())) {
            textOutput.setText("El profesor " + nuevoProfesor.getNombre() + " ya existe. No se puede añadir nuevamente.");
            return;
        }
        profesorado.guardarNuevoProfesor("nuevo_profesorado.xml", nuevoProfesor);
        textOutput.setText("Nuevo profesor añadido:\n" + nuevoProfesor);
    }

    @FXML
    public void handleMostrarNuevoXML() {
        try {
            profesorado = new Profesorado("nuevo_profesorado.xml");
            StringBuilder output = new StringBuilder("Contenido del nuevo XML:\n");
            profesorado.mostrarTodos().forEach(profesor -> output.append(profesor).append("\n"));
            textOutput.setText(output.toString());
        } catch (Exception e) {
            textOutput.setText("Error al cargar el nuevo archivo XML.\n" + e.getMessage());
        }
    }
}
