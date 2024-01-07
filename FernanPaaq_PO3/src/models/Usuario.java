package models;

import java.time.LocalDate;

public class Usuario {
    //Atributos
    private String nombre;
    private String direccion;
    private String localidad;
    private String provincia;
    private int tlf;
    private String clave;
    private Envio envio1;
    private Envio envio2;

    //Getters y Setters

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Envio getEnvio1() {
        return envio1;
    }

    public void setEnvio1(Envio envio1) {
        this.envio1 = envio1;
    }

    public Envio getEnvio2() {
        return envio2;
    }

    public void setEnvio2(Envio envio2) {
        this.envio2 = envio2;
    }

    //Constructor
    public Usuario(String nombre, String clave, String direccion, String localidad, String provincia, int tlf) {
        this.nombre = nombre;
        this.clave = clave;
        this.direccion = direccion;
        this.localidad = localidad;
        this.provincia = provincia;
        this.tlf = tlf;
    }

    public boolean addEnvioUsuario(int numeroSeguimiento, String destinatario) {
        if (envio1 == null) {
            envio1 = new Envio(numeroSeguimiento, destinatario, getLocalidad(), getProvincia());
            envio1.setFechaEnvio(LocalDate.now());
        } else if (envio2 == null) {
            envio2 = new Envio(numeroSeguimiento, destinatario, getLocalidad(), getProvincia());
            envio2.setFechaEnvio(LocalDate.now());
        }
        return false;
    }

    @Override
    public String toString() {
        return "===== " + getNombre() + " =====" + "\n" +
                "- Dirección: " + getDireccion() + "\n" +
                "- Localidad: " + getLocalidad() + "\n" +
                "- Provincia: " + getProvincia() + "\n" +
                "- Envío 1: " + ((envio1 == null) ? "Vacío" : "Activo") + "\n" +
                "- Envío 2: " + ((envio2 == null) ? "Vacío" : "Activo") + "\n";
    }
}
