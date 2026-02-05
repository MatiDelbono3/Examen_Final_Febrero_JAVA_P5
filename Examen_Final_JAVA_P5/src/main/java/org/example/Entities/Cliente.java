package org.example.Entities;



import jakarta.persistence.*;

@Entity
@Table(name = "Cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCliente;

    private String nombre;
    private String email;
    private boolean activo;
    public Cliente() {
    }

    public Cliente(String nombre, String email, boolean activo) {
        this.setNombre(nombre);
        this.setEmail(email);
        this.setActivo(activo);
    }

    public  int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
