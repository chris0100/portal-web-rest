package com.portalweb.models;

public class ItemExcel {
    private String codigoItem;
    private Double cantidad;


    public ItemExcel() {
    }


    public String getCodigoItem() {
        return codigoItem;
    }

    public void setCodigoItem(String codigoItem) {
        this.codigoItem = codigoItem;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "ItemExcel{" +
                "codigoItem='" + codigoItem + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }
}
