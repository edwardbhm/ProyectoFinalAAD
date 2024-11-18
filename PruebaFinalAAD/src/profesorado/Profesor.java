package profesorado;

public class Profesor {
    private String nombre;
    private String apellidos;
    private String mail;
    private String genero;
    private int fechaComienzo;
    private String departamento;
    private double sueldo;

    public Profesor(String nombre, String apellidos, String mail, String genero, int fechaComienzo, String departamento, double sueldo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.mail = mail;
        this.genero = genero;
        this.fechaComienzo = fechaComienzo;
        this.departamento = departamento;
        this.sueldo = sueldo;
    }

    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getMail() { return mail; }
    public String getGenero() { return genero; }
    public int getFechaComienzo() { return fechaComienzo; }
    public String getDepartamento() { return departamento; }
    public double getSueldo() { return sueldo; }

    @Override
    public String toString() {
        return apellidos + ", " + nombre + " - " + mail;
    }
}
