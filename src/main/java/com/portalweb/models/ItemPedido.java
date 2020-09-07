package com.portalweb.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "t004_item_pedido")
public class ItemPedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item")
    private Long idItem;

    @Column(name = "cod_item")
    private Integer codItem;

    @Size(max = 100)
    @Column(name = "cod_cliente")
    private String codCliente;

    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;

    @Size(max = 20)
    @Column(name = "id_ext1_detalle")
    private String idExt1Detalle;

    @Size(max = 20)
    @Column(name = "id_ext2_detalle")
    private String idExt2Detalle;

    @Size(max = 60)
    @Column(name = "descripcion_ext1")
    private String descripcionExt1;

    @Size(max = 60)
    @Column(name = "descripcion_ext2")
    private String descripcionExt2;

    @Size(max = 100)
    @Column(name = "descripcion_completa_item")
    private String descripcionCompletaItem;

    @Size(max = 10)
    @Column(name = "unidad_medida")
    private String unidadMedida;

    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;

    @Column(name = "precio_unitario_unoee")
    private BigDecimal precioUnitarioUnoee;

    @Size(max = 250)
    @Column(name = "notas_item")
    private String notasItem;

    @Size(max = 10)
    @Column(name = "id_bodega")
    private String idBodega;

    @Size(max = 10)
    @Column(name = "id_motivo")
    private String idMotivo;

    @Column(name = "fecha_actualizacion_precio_unoee")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacionPrecioUnoee;

    @Column(name = "unoee_item")
    private Short unoeeItem;

    @Column(name = "unoee_precios")
    private Short unoeePrecios;

    @Column(name = "unoee_portafolio")
    private Short unoeePortafolio;

    @Column(name = "unoee_listas_materiales")
    private Short unoeeListasMateriales;

    @Column(name = "cantidad", precision = 28, scale = 4)
    private BigDecimal cantidad;





    ///////////////////////////////////////
    //GETTERS AND SETTERS
    ///////////////////////////////////////
    public Long getIdItem() {
        return idItem;
    }

    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }

    public Integer getCodItem() {
        return codItem;
    }

    public void setCodItem(Integer codItem) {
        this.codItem = codItem;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdExt1Detalle() {
        return idExt1Detalle;
    }

    public void setIdExt1Detalle(String idExt1Detalle) {
        this.idExt1Detalle = idExt1Detalle;
    }

    public String getIdExt2Detalle() {
        return idExt2Detalle;
    }

    public void setIdExt2Detalle(String idExt2Detalle) {
        this.idExt2Detalle = idExt2Detalle;
    }

    public String getDescripcionExt1() {
        return descripcionExt1;
    }

    public void setDescripcionExt1(String descripcionExt1) {
        this.descripcionExt1 = descripcionExt1;
    }

    public String getDescripcionExt2() {
        return descripcionExt2;
    }

    public void setDescripcionExt2(String descripcionExt2) {
        this.descripcionExt2 = descripcionExt2;
    }

    public String getDescripcionCompletaItem() {
        return descripcionCompletaItem;
    }

    public void setDescripcionCompletaItem(String descripcionCompletaItem) {
        this.descripcionCompletaItem = descripcionCompletaItem;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getPrecioUnitarioUnoee() {
        return precioUnitarioUnoee;
    }

    public void setPrecioUnitarioUnoee(BigDecimal precioUnitarioUnoee) {
        this.precioUnitarioUnoee = precioUnitarioUnoee;
    }

    public String getNotasItem() {
        return notasItem;
    }

    public void setNotasItem(String notasItem) {
        this.notasItem = notasItem;
    }

    public String getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(String idBodega) {
        this.idBodega = idBodega;
    }

    public String getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(String idMotivo) {
        this.idMotivo = idMotivo;
    }

    public Date getFechaActualizacionPrecioUnoee() {
        return fechaActualizacionPrecioUnoee;
    }

    public void setFechaActualizacionPrecioUnoee(Date fechaActualizacionPrecioUnoee) {
        this.fechaActualizacionPrecioUnoee = fechaActualizacionPrecioUnoee;
    }

    public Short getUnoeeItem() {
        return unoeeItem;
    }

    public void setUnoeeItem(Short unoeeItem) {
        this.unoeeItem = unoeeItem;
    }

    public Short getUnoeePrecios() {
        return unoeePrecios;
    }

    public void setUnoeePrecios(Short unoeePrecios) {
        this.unoeePrecios = unoeePrecios;
    }

    public Short getUnoeePortafolio() {
        return unoeePortafolio;
    }

    public void setUnoeePortafolio(Short unoeePortafolio) {
        this.unoeePortafolio = unoeePortafolio;
    }

    public Short getUnoeeListasMateriales() {
        return unoeeListasMateriales;
    }

    public void setUnoeeListasMateriales(Short unoeeListasMateriales) {
        this.unoeeListasMateriales = unoeeListasMateriales;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }



    private static final long serialVersionUID = 1L;


    @Override
    public String toString() {
        return "ItemPedido{" +
                "idItem=" + idItem +
                ", codItem=" + codItem +
                ", codCliente='" + codCliente + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", idExt1Detalle='" + idExt1Detalle + '\'' +
                ", idExt2Detalle='" + idExt2Detalle + '\'' +
                ", descripcionExt1='" + descripcionExt1 + '\'' +
                ", descripcionExt2='" + descripcionExt2 + '\'' +
                ", descripcionCompletaItem='" + descripcionCompletaItem + '\'' +
                ", unidadMedida='" + unidadMedida + '\'' +
                ", precioUnitario=" + precioUnitario +
                ", precioUnitarioUnoee=" + precioUnitarioUnoee +
                ", notasItem='" + notasItem + '\'' +
                ", idBodega='" + idBodega + '\'' +
                ", idMotivo='" + idMotivo + '\'' +
                ", fechaActualizacionPrecioUnoee=" + fechaActualizacionPrecioUnoee +
                ", unoeeItem=" + unoeeItem +
                ", unoeePrecios=" + unoeePrecios +
                ", unoeePortafolio=" + unoeePortafolio +
                ", unoeeListasMateriales=" + unoeeListasMateriales +
                ", cantidad=" + cantidad +
                '}';
    }
}
