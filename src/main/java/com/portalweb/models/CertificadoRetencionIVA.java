package com.portalweb.models;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class CertificadoRetencionIVA {
    private String dircia;
    private String ciudadcia;
    private String razon;
    private String tipoid;
    private String ident;
    private String dirtercero;
    private String ciudadtercero;
    private String auxiliar;
    private String idaux;
    private BigDecimal tasa;
    private int tasabase;
    private BigDecimal base;
    private BigDecimal neto;
    private int bimestre;
    private Date desde;
    private Date hasta;
    private String firma;
    private String cargo;
    private BigDecimal totalneto;
    private int agrupapor;
    private String ciudad_practica;
    private String ciudad_consigna;
    private String f_email;
    private String f_nombre_archivo;
    private BigDecimal totalabonocta;
    private BigDecimal total_monto;
    private BigDecimal total_base;
    private BigDecimal total_retenido;
    private Date fechaExpedicion = new Date();
    private String numero_letras;
    private String convertirNumero;


    Calendar cal= Calendar.getInstance();
    private int anioGrabable = cal.get(Calendar.YEAR);
    private String mesInicio;
    private String mesFin;
    private String tipoCertificado;
    private String ciudadPracticoParam;
    private String ciudadConsignoParam;
    private String tituloCertificado;
    private int rowid_tercero;
    private Usuario usuarioActual;




    public CertificadoRetencionIVA() {
    }


    public String getConvertirNumero() {
        return convertirNumero;
    }

    public void setConvertirNumero(String convertirNumero) {
        this.convertirNumero = convertirNumero;
    }

    public String getDircia() {
        return dircia;
    }

    public void setDircia(String dircia) {
        this.dircia = dircia;
    }

    public String getCiudadcia() {
        return ciudadcia;
    }

    public void setCiudadcia(String ciudadcia) {
        this.ciudadcia = ciudadcia;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getTipoid() {
        return tipoid;
    }

    public void setTipoid(String tipoid) {
        this.tipoid = tipoid;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public String getDirtercero() {
        return dirtercero;
    }

    public void setDirtercero(String dirtercero) {
        this.dirtercero = dirtercero;
    }

    public String getCiudadtercero() {
        return ciudadtercero;
    }

    public void setCiudadtercero(String ciudadtercero) {
        this.ciudadtercero = ciudadtercero;
    }

    public String getAuxiliar() {
        return auxiliar;
    }

    public void setAuxiliar(String auxiliar) {
        this.auxiliar = auxiliar;
    }

    public String getIdaux() {
        return idaux;
    }

    public void setIdaux(String idaux) {
        this.idaux = idaux;
    }

    public BigDecimal getTasa() {
        return tasa;
    }

    public void setTasa(BigDecimal tasa) {
        this.tasa = tasa;
    }

    public int getTasabase() {
        return tasabase;
    }

    public void setTasabase(int tasabase) {
        this.tasabase = tasabase;
    }

    public BigDecimal getBase() {
        return base;
    }

    public void setBase(BigDecimal base) {
        this.base = base;
    }

    public BigDecimal getNeto() {
        return neto;
    }

    public void setNeto(BigDecimal neto) {
        this.neto = neto;
    }

    public int getBimestre() {
        return bimestre;
    }

    public void setBimestre(int bimestre) {
        this.bimestre = bimestre;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public BigDecimal getTotalneto() {
        return totalneto;
    }

    public void setTotalneto(BigDecimal totalneto) {
        this.totalneto = totalneto;
    }

    public int getAgrupapor() {
        return agrupapor;
    }

    public void setAgrupapor(int agrupapor) {
        this.agrupapor = agrupapor;
    }

    public String getCiudad_practica() {
        return ciudad_practica;
    }

    public void setCiudad_practica(String ciudad_practica) {
        this.ciudad_practica = ciudad_practica;
    }

    public String getCiudad_consigna() {
        return ciudad_consigna;
    }

    public void setCiudad_consigna(String ciudad_consigna) {
        this.ciudad_consigna = ciudad_consigna;
    }

    public String getF_email() {
        return f_email;
    }

    public void setF_email(String f_email) {
        this.f_email = f_email;
    }

    public String getF_nombre_archivo() {
        return f_nombre_archivo;
    }

    public void setF_nombre_archivo(String f_nombre_archivo) {
        this.f_nombre_archivo = f_nombre_archivo;
    }

    public BigDecimal getTotalabonocta() {
        return totalabonocta;
    }

    public void setTotalabonocta(BigDecimal totalabonocta) {
        this.totalabonocta = totalabonocta;
    }

    public BigDecimal getTotal_monto() {
        return total_monto;
    }

    public void setTotal_monto(BigDecimal total_monto) {
        this.total_monto = total_monto;
    }

    public BigDecimal getTotal_base() {
        return total_base;
    }

    public void setTotal_base(BigDecimal total_base) {
        this.total_base = total_base;
    }

    public BigDecimal getTotal_retenido() {
        return total_retenido;
    }

    public void setTotal_retenido(BigDecimal total_retenido) {
        this.total_retenido = total_retenido;
    }

    public Date getFechaExpedicion() {
        return fechaExpedicion;
    }

    public void setFechaExpedicion(Date fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    public String getNumero_letras() {
        return numero_letras;
    }

    public void setNumero_letras(String numero_letras) {
        this.numero_letras = numero_letras;
    }

    public Calendar getCal() {
        return cal;
    }

    public void setCal(Calendar cal) {
        this.cal = cal;
    }

    public int getAnioGrabable() {
        return anioGrabable;
    }

    public void setAnioGrabable(int anioGrabable) {
        this.anioGrabable = anioGrabable;
    }

    public String getMesInicio() {
        return mesInicio;
    }

    public void setMesInicio(String mesInicio) {
        this.mesInicio = mesInicio;
    }

    public String getMesFin() {
        return mesFin;
    }

    public void setMesFin(String mesFin) {
        this.mesFin = mesFin;
    }

    public String getTipoCertificado() {
        return tipoCertificado;
    }

    public void setTipoCertificado(String tipoCertificado) {
        this.tipoCertificado = tipoCertificado;
    }

    public String getCiudadPracticoParam() {
        return ciudadPracticoParam;
    }

    public void setCiudadPracticoParam(String ciudadPracticoParam) {
        this.ciudadPracticoParam = ciudadPracticoParam;
    }

    public String getCiudadConsignoParam() {
        return ciudadConsignoParam;
    }

    public void setCiudadConsignoParam(String ciudadConsignoParam) {
        this.ciudadConsignoParam = ciudadConsignoParam;
    }

    public String getTituloCertificado() {
        return tituloCertificado;
    }

    public void setTituloCertificado(String tituloCertificado) {
        this.tituloCertificado = tituloCertificado;
    }

    public int getRowid_tercero() {
        return rowid_tercero;
    }

    public void setRowid_tercero(int rowid_tercero) {
        this.rowid_tercero = rowid_tercero;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }


    @Override
    public String toString() {
        return "CertificadoRetencionIVA{" +
                "dircia='" + dircia + '\'' +
                ", ciudadcia='" + ciudadcia + '\'' +
                ", razon='" + razon + '\'' +
                ", tipoid='" + tipoid + '\'' +
                ", ident='" + ident + '\'' +
                ", dirtercero='" + dirtercero + '\'' +
                ", ciudadtercero='" + ciudadtercero + '\'' +
                ", auxiliar='" + auxiliar + '\'' +
                ", idaux='" + idaux + '\'' +
                ", tasa=" + tasa +
                ", tasabase=" + tasabase +
                ", base=" + base +
                ", neto=" + neto +
                ", bimestre=" + bimestre +
                ", desde=" + desde +
                ", hasta=" + hasta +
                ", firma='" + firma + '\'' +
                ", cargo='" + cargo + '\'' +
                ", totalneto=" + totalneto +
                ", agrupapor=" + agrupapor +
                ", ciudad_practica='" + ciudad_practica + '\'' +
                ", ciudad_consigna='" + ciudad_consigna + '\'' +
                ", f_email='" + f_email + '\'' +
                ", f_nombre_archivo='" + f_nombre_archivo + '\'' +
                ", totalabonocta=" + totalabonocta +
                ", total_monto=" + total_monto +
                ", total_base=" + total_base +
                ", total_retenido=" + total_retenido +
                ", fechaExpedicion=" + fechaExpedicion +
                ", numero_letras='" + numero_letras + '\'' +
                ", cal=" + cal +
                ", anioGabable=" + anioGrabable +
                ", mesInicio='" + mesInicio + '\'' +
                ", mesFin='" + mesFin + '\'' +
                ", tipoCertificado='" + tipoCertificado + '\'' +
                ", ciudadPracticoParam='" + ciudadPracticoParam + '\'' +
                ", ciudadConsignoParam='" + ciudadConsignoParam + '\'' +
                ", tituloCertificado='" + tituloCertificado + '\'' +
                ", rowid_tercero=" + rowid_tercero +
                ", usuarioActual=" + usuarioActual +
                '}';
    }
}



