package com.portalweb.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t002_rol")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private String id;

    @Column(unique = true, length = 30)
    private String nombre;


    //GETTERS AND SETTERS
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public static final long serialVersionUID = 1L;
}
