package com.portalweb.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t003_pedidos")
public class Pedido implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    @Id
    private Long id;

    @Size(max = 10)
    @Column(name = "cod_pedido")
    private String codPedido;

    @Size(max = 1073741823)
    @Column(name = "nota")
    private String nota;

    @Column(name = "estado")
    private Short estado;

    @Column(name = "fecha_pedido")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPedido;

    @Column(name = "total_bruto")
    private BigDecimal totalBruto;

    @Column(name = "descuentos")
    private BigDecimal descuentos;

    @Column(name = "impuestos")
    private BigDecimal impuestos;

    @Column(name = "total")
    private BigDecimal total;

    @Size(max = 100)
    @Column(name = "sucursal_remisionar")
    private String sucursalRemisionar;

    @Size(max = 100)
    @Column(name = "sucursal_facturar")
    private String sucursalFacturar;

    @Size(max = 150)
    @Column(name = "tercero_vendedor")
    private String terceroVendedor;

    @Size(max = 100)
    @Column(name = "contacto")
    private String contacto;

    @Size(max = 100)
    @Column(name = "direccion")
    private String direccion;

    @Size(max = 50)
    @NotEmpty(message = "El campo O.C. es obligatorio")
    @Column(name = "oc")
    private String oc;

    @JsonIgnoreProperties(value= {"t003_pedidos", "hibernateLazyInitializer", "handler"}, allowSetters = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario idUsuario;

    @Size(max = 150)
    @Column(name = "tercero_asesor")
    private String terceroAsesor;

    @Column(name = "estadoWB")
    private Short estadoWB;

    @Size(max = 1073741823)
    @Column(name = "mensajeWB")
    private String mensajeWB;

    @Size(max = 150)
    @Column(name = "punto_envio")
    private String puntoEnvio;

    @JsonIgnoreProperties(value= {"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "id_pedido")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ItemPedido> listaItemPedido;


    @Transient
    private String emailAsesor;


    public Pedido() {
    }


    public String getEmailAsesor() {
        return emailAsesor;
    }

    public void setEmailAsesor(String emailAsesor) {
        this.emailAsesor = emailAsesor;
    }

    public List<ItemPedido> getListaItemPedido() {
        return listaItemPedido;
    }

    public void setListaItemPedido(List<ItemPedido> listaItemPedido) {
        this.listaItemPedido = listaItemPedido;
    }

    public BigDecimal getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(BigDecimal impuestos) {
        this.impuestos = impuestos;
    }

    public String getPuntoEnvio() {
        return puntoEnvio;
    }

    public void setPuntoEnvio(String puntoEnvio) {
        this.puntoEnvio = puntoEnvio;
    }

    public BigDecimal getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(BigDecimal descuentos) {
        this.descuentos = descuentos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodPedido() {
        return codPedido;
    }

    public void setCodPedido(String codPedido) {
        this.codPedido = codPedido;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public BigDecimal getTotalBruto() {
        return totalBruto;
    }

    public void setTotalBruto(BigDecimal totalBruto) {
        this.totalBruto = totalBruto;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getSucursalRemisionar() {
        return sucursalRemisionar;
    }

    public void setSucursalRemisionar(String sucursalRemisionar) {
        this.sucursalRemisionar = sucursalRemisionar;
    }

    public String getSucursalFacturar() {
        return sucursalFacturar;
    }

    public void setSucursalFacturar(String sucursalFacturar) {
        this.sucursalFacturar = sucursalFacturar;
    }

    public String getTerceroVendedor() {
        return terceroVendedor;
    }

    public void setTerceroVendedor(String terceroVendedor) {
        this.terceroVendedor = terceroVendedor;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getOc() {
        return oc;
    }

    public void setOc(String oc) {
        this.oc = oc;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTerceroAsesor() {
        return terceroAsesor;
    }

    public void setTerceroAsesor(String terceroAsesor) {
        this.terceroAsesor = terceroAsesor;
    }

    public Short getEstadoWB() {
        return estadoWB;
    }

    public void setEstadoWB(Short estadoWB) {
        this.estadoWB = estadoWB;
    }

    public String getMensajeWB() {
        return mensajeWB;
    }

    public void setMensajeWB(String mensajeWB) {
        this.mensajeWB = mensajeWB;
    }

    private static final long serialVersionUID = 1L;

}
