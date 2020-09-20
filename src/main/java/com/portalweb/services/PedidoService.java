package com.portalweb.services;


import com.portalweb.models.ItemPedido;
import com.portalweb.models.Pedido;
import com.portalweb.models.Usuario;

import java.util.List;

public interface PedidoService {

    List<Pedido> listaPedidos();

    Pedido findById(Long id);

    Pedido save(Pedido pedido);

    void delete(Pedido pedido);

    List<Pedido> listarPedidosBorrador(String nit, short estado);

    List<Pedido> listarPedidosVerificacion(String nit, short estadoWB);

    String obtenerPuntoEnvio(String nit, String idSucursal, String idPuntoEnvio);

    List<String> obtenerConectorPedido(String idPedido);

    List<String> ejecucionWS(String pvstrDatos, Pedido pedido, Usuario usuario, List<ItemPedido> listaItemPedido, String emailAsesor);

    void deletePedidoBorradorById(Long id);

    List<Pedido> obtenerPedidoEstadoAsesor(String terceroAsesor);

    List<Pedido> obtenerPedidoEstado();

    void ejecucionWSError(String pvstrDatos, Pedido pedido) throws Exception;








}
