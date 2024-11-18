package profesorado;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

public class Profesorado {

    private ArrayList<Profesor> profesores;

    public Profesorado(String rutaXML) {
        this.profesores = cargarProfesores(rutaXML);
    }

    private ArrayList<Profesor> cargarProfesores(String rutaXML) {
        ArrayList<Profesor> lista = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(rutaXML));

            NodeList nodes = doc.getElementsByTagName("profesor");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);

                String nombre = element.getElementsByTagName("nombre").item(0).getTextContent();
                String apellidos = element.getElementsByTagName("apellidos").item(0).getTextContent();
                String mail = element.getElementsByTagName("mail").item(0).getTextContent();
                String genero = element.getAttribute("genero");
                int fechaComienzo = Integer.parseInt(element.getElementsByTagName("fecha-comienzo").item(0).getTextContent());
                String departamento = element.getElementsByTagName("departamento").item(0).getTextContent();
                double sueldo = Double.parseDouble(element.getElementsByTagName("sueldo").item(0).getTextContent());

                lista.add(new Profesor(nombre, apellidos, mail, genero, fechaComienzo, departamento, sueldo));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public ArrayList<Profesor> mostrarTodos() {
        return profesores;
    }

    public ArrayList<Profesor> mostrarProfesoras() {
        ArrayList<Profesor> profesoras = new ArrayList<>();
        for (Profesor profesor : profesores) {
            if (profesor.getGenero().equalsIgnoreCase("F")) {
                profesoras.add(profesor);
            }
        }
        return profesoras;
    }

    public ArrayList<Profesor> mostrarInformaticos() {
        ArrayList<Profesor> informaticos = new ArrayList<>();
        for (Profesor profesor : profesores) {
            if (profesor.getDepartamento().equalsIgnoreCase("Informática")) {
                informaticos.add(profesor);
            }
        }
        return informaticos;
    }

    public Profesor obtenerProfesorMasAntiguo() {
        return profesores.stream()
                .min(Comparator.comparingInt(Profesor::getFechaComienzo))
                .orElse(null);
    }

    public double sumarSueldos() {
        return profesores.stream()
                .mapToDouble(Profesor::getSueldo)
                .sum();
    }

    public boolean profesorYaExiste(String email) {
        return profesores.stream().anyMatch(prof -> prof.getMail().equalsIgnoreCase(email));
    }

    public void guardarNuevoProfesor(String rutaSalida, Profesor nuevoProfesor) {
        if (profesorYaExiste(nuevoProfesor.getMail())) {
            System.out.println("El profesor con email " + nuevoProfesor.getMail() + " ya existe. No se añadirá nuevamente.");
            return;
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("profesorado");
            doc.appendChild(root);

            for (Profesor prof : profesores) {
                root.appendChild(crearNodoProfesor(doc, prof));
            }

            root.appendChild(crearNodoProfesor(doc, nuevoProfesor));
            profesores.add(nuevoProfesor); 

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(new File(rutaSalida)));

            System.out.println("Profesor añadido exitosamente: " + nuevoProfesor);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Element crearNodoProfesor(Document doc, Profesor prof) {
        Element profesor = doc.createElement("profesor");
        profesor.setAttribute("genero", prof.getGenero());

        profesor.appendChild(crearElemento(doc, "nombre", prof.getNombre()));
        profesor.appendChild(crearElemento(doc, "apellidos", prof.getApellidos()));
        profesor.appendChild(crearElemento(doc, "mail", prof.getMail()));
        profesor.appendChild(crearElemento(doc, "fecha-comienzo", String.valueOf(prof.getFechaComienzo())));
        profesor.appendChild(crearElemento(doc, "departamento", prof.getDepartamento()));
        profesor.appendChild(crearElemento(doc, "sueldo", String.valueOf(prof.getSueldo())));

        return profesor;
    }

    private Element crearElemento(Document doc, String tag, String valor) {
        Element element = doc.createElement(tag);
        element.setTextContent(valor);
        return element;
    }
}
