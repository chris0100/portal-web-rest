package com.portalweb.controllers;

import com.portalweb.models.Pedido;
import com.portalweb.services.PedidoService;
import com.portalweb.services.UnoEEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class PedidoController {

    @Autowired
    private PedidoService pedidoServiceObj;

    @Autowired
    private UnoEEService unoEEServiceObj;


    //Obtiene la lista de los pedidos
    @GetMapping("/listar-pedidos")
    public List<Pedido> listarPedidos() {
        return pedidoServiceObj.listaPedidos();
    }





    //Obtener la lista de pedidos en borrador
    @GetMapping("/listar-pedidos-borrador/{nit}")
    public List<Pedido> listarPedidosBorrador(@PathVariable String nit) {
        return pedidoServiceObj.listarPedidosBorrador(nit, (short) 0);
    }



    //Obtener la lista de pedidos en verificacion
    @GetMapping("/listar-pedidos-verificacion/{nit}")
    public List<Pedido> listarPedidosVerificacion(@PathVariable String nit) {
        return pedidoServiceObj.listarPedidosVerificacion(nit, (short) 1);
    }



    //Obtener la lista del historial de pedidos
    @GetMapping("/listar-historial-pedidos/{nit}/{fechaParametro1}/{fechaParametro2}")
    public List<PedidoUnoeeController> listarHistorialPedidos(@PathVariable String nit,
                                                              @PathVariable String fechaParametro1,
                                                              @PathVariable String fechaParametro2) throws SQLException {

        //Se obtiene el rowIdCliente
        String rowIdCliente = unoEEServiceObj.findRowIdCliente(nit);

        List<PedidoUnoeeController> listaHistorialPedidos = unoEEServiceObj.listaHistorialPedido(nit,fechaParametro1
                ,fechaParametro2,rowIdCliente);

        return listaHistorialPedidos;
    }


    //Obtener la lista de item de acuerdo al rowId del pedidoUnoEE del cliente
    @GetMapping("/obtener-lista-items-pedidoUnoEE/{rowId}")
    public List<ItemPedidoUnoEEController> listaItemsPedido(@PathVariable int rowId) throws SQLException {
        List<ItemPedidoUnoEEController> listaItemsPedido = unoEEServiceObj.listaItemsByRowId(rowId);

        return listaItemsPedido;
    }





}
