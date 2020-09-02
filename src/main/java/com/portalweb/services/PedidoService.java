package com.portalweb.services;


import com.portalweb.models.Pedido;

import java.util.List;

public interface PedidoService {

    List<Pedido> listaPedidos();

    Pedido findById(Long id);

    void save(Pedido pedido);

    void delete(Pedido pedido);
}
