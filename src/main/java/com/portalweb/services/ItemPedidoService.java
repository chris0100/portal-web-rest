package com.portalweb.services;

import com.portalweb.models.ItemPedido;

import java.util.List;

public interface ItemPedidoService {

    List<ItemPedido> listaItemsPedido();

    ItemPedido findById(Long id);

    void saveItem(ItemPedido itemPedido);

    void delete(ItemPedido itemPedido);

}
