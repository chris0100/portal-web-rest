package com.portalweb.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="t001_usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(unique = true, length = 20)
    private String username;

    @Column(length = 120, name = "contrasena")
    private String password;

    @Column(length = 100)
    private String nombre;

    @Column(length = 50, unique = true,name = "correo")
    @Email
    private String email;

    @Column(length = 40)
    private String nit;

    @Column(name = "descripcion_sucursal", length = 80)
    private String descripcionSucursal;

    @Column(name = "id_sucursal")
    private String idSucursal;

    @Column(name = "cliente", length = 70)
    private String cliente;

    @Column(name = "primer_login")
    private Boolean primerLogin;

   @Column(name = "token")
    private String token;

    @Column(name = "estado_token")
    private Boolean estadoToken;

    @Column(name = "hora_token")
    private Date horaToken;

    @Column(name = "intentos_bloqueo")
    private Integer intentosBloqueo;

    @Column(name = "descripcion_punto_envio")
    private String descripcionPuntoEnvio;

    @Column(name = "id_punto_envio")
    private String idPuntoEnvio;


    @Column(name = "condiciones_restricciones")
    private Boolean condicionesRestricciones;




    //Cada vez que se elimina un usuario, se elimina su rol
    @JoinTable(name = "usuario_has_roles", joinColumns = {
            @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")}, inverseJoinColumns = {
            @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")})
    @ManyToMany
    private List<Role> roles;


    @Transient
    private String[] rolesPrincipal;





    //****************************************
    //GETTERS AND SETTERS
    //****************************************

    public String[] getRolesPrincipal() {
        return rolesPrincipal;
    }

    public void setRolesPrincipal(String[] rolesPrincipal) {
        this.rolesPrincipal = rolesPrincipal;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDescripcionSucursal() {
        return descripcionSucursal;
    }

    public void setDescripcionSucursal(String descripcionSucursal) {
        this.descripcionSucursal = descripcionSucursal;
    }

    public String getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Boolean isPrimerLogin() {
        return primerLogin;
    }

    public void setPrimerLogin(Boolean primerLogin) {
        this.primerLogin = primerLogin;
    }

   public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getPrimerLogin() {
        return primerLogin;
    }

    public Boolean getEstadoToken() {
        return estadoToken;
    }

    public void setEstadoToken(Boolean estadoToken) {
        this.estadoToken = estadoToken;
    }

    public Boolean getCondicionesRestricciones() {
        return condicionesRestricciones;
    }

    public Date getHoraToken() {
        return horaToken;
    }

    public void setHoraToken(Date horaToken) {
        this.horaToken = horaToken;
    }

    public Integer getIntentosBloqueo() {
        return intentosBloqueo;
    }

    public void setIntentosBloqueo(Integer intentosBloqueo) {
        this.intentosBloqueo = intentosBloqueo;
    }

    public String getDescripcionPuntoEnvio() {
        return descripcionPuntoEnvio;
    }

    public void setDescripcionPuntoEnvio(String descripcionPuntoEnvio) {
        this.descripcionPuntoEnvio = descripcionPuntoEnvio;
    }

    public String getIdPuntoEnvio() {
        return idPuntoEnvio;
    }

    public void setIdPuntoEnvio(String idPuntoEnvio) {
        this.idPuntoEnvio = idPuntoEnvio;
    }

   public Boolean isCondicionesRestricciones() {
        return condicionesRestricciones;
    }

    public void setCondicionesRestricciones(Boolean condicionesRestricciones) {
        this.condicionesRestricciones = condicionesRestricciones;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> listaRoles) {
        this.roles = listaRoles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public static final long serialVersionUID = 1L;


    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", nit='" + nit + '\'' +
                ", descripcionSucursal='" + descripcionSucursal + '\'' +
                ", idSucursal='" + idSucursal + '\'' +
                ", cliente='" + cliente + '\'' +
                ", primerLogin=" + primerLogin +
                ", intentosBloqueo=" + intentosBloqueo +
                ", descripcionPuntoEnvio='" + descripcionPuntoEnvio + '\'' +
                ", idPuntoEnvio='" + idPuntoEnvio + '\'' +
                ", condicionesRestricciones=" + condicionesRestricciones +
                ", roles=" + roles +
                ", rolesPrincipal=" + Arrays.toString(rolesPrincipal) +
                '}';
    }
}
