package com.portalweb.controllers;


import com.portalweb.models.Pedido;
import com.portalweb.services.ItemPedidoService;
import com.portalweb.services.PedidoService;
import com.portalweb.services.UnoEEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private ItemPedidoService itemPedidoServiceObj;


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


    //Obtener lista de items de verificacion
    @GetMapping("/obtener-lista-items-vb/{idPedido}")
    public Pedido listaItemPedidos(@PathVariable Long idPedido){
        Pedido pedidoActual = pedidoServiceObj.findById(idPedido);
        System.out.println(pedidoActual.getListaItemPedido().size());
        return pedidoActual;
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



    //Obtener punto de envio de acuerdo al nit y la sucursal
    @GetMapping("/obtener-punto-envio/{nit}/{idSucursal}/{idPuntoEnvio}")
    public List<String> obtenerPuntoEnvio(@PathVariable String nit, @PathVariable String idSucursal, @PathVariable String idPuntoEnvio){
        List<String> lista = new ArrayList<>();
        lista.add(pedidoServiceObj.obtenerPuntoEnvio(nit,idSucursal,idPuntoEnvio));
       return lista;
    }



    //Cuenta el item homologado para saber que pestaña habilitar en la pagina crear pedido
    //homologado = tiene su propio codigo de cliente.
    //no homologado = realiza la busqueda por codigo de barras.
    @GetMapping("/contar-mostrar-panel/{nit}")
    public List<Integer> contarMostrarPanel(@PathVariable String nit){
        List<Integer> lista = new ArrayList<>();
        lista.add(itemPedidoServiceObj.contarMostrarPanel(nit));
        System.out.println(lista.get(0));
        return lista;
    }



    //Consulta la lista item Patron para mostrar os items disponibles
    @GetMapping("/lista-item-patron")
    public List<String> listaItemPatron(){
        return itemPedidoServiceObj.obtenerListaItemPatron();
    }









}
