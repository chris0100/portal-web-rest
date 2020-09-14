package com.portalweb.services;

import com.portalweb.models.ItemPedido;

import java.util.List;

public interface ItemPedidoService {

    List<ItemPedido> listaItemsPedido();

    ItemPedido findById(Long id);

    void saveItem(ItemPedido itemPedido);

    void delete(ItemPedido itemPedido);

    Integer contarMostrarPanel(String nit);

    List<String> obtenerListaItemPatron();

    List<String> consultaTipoExtensiones(int idItem);

    List<String> consultaExt1(String tipoExt);

    List<String> consultaExt2(String tipoExt);

    List<String> consultaUnidadInventario(int idItem);

    List<String> consultaDecimalesUnidadInventario(String unidadMedida);

    List<String> consultaDescripcionLabelExt1(String tipoExt);

    List<String> consultaDescripcionLabelExt2(String tipoExt);

    List<String> contarHomologarItemDuplicado(String nit, String codCliente);

    String ItemCodBarras(String codBarras);

    void deleteItemBorrador(Long id);

}
