package com.portalweb.controllers;


import com.portalweb.models.Pedido;
import com.portalweb.services.ItemPedidoService;
import com.portalweb.services.PedidoService;
import com.portalweb.services.UnoEEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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



    // Consultar el tipo de extensiones
    @GetMapping("/tipo-extensiones/{idItem}")
    public List<String> consultaTipoExtensiones(@PathVariable String idItem){
        return itemPedidoServiceObj.consultaTipoExtensiones(Integer.parseInt(idItem));
    }


    //Consulta Ext1(TAMAÑO)
    @GetMapping("/ext1/{tipoExt}")
    public List<String> consultaExt1(@PathVariable String tipoExt){
        return itemPedidoServiceObj.consultaExt1(tipoExt);
    }



    //Consulta Ext2(COLOR)
    @GetMapping("/ext2/{tipoExt}")
    public List<String> consultaExt2(@PathVariable String tipoExt){
        return itemPedidoServiceObj.consultaExt2(tipoExt);
    }


    //Consultar unidad inventario
    @GetMapping("/unidad-inventario/{idItem}")
    public List<String> consultaUnidadInventario(@PathVariable String idItem) {
        return itemPedidoServiceObj.consultaUnidadInventario(Integer.parseInt(idItem));
    }


    //Consulta decimales unidad inventario
    @GetMapping("/decimales-unidad-inventario/{unidadMedida}")
    public List<String> consultaDecimalesUnidadInventario(@PathVariable String unidadMedida){
        return itemPedidoServiceObj.consultaDecimalesUnidadInventario(unidadMedida);
    }


    //Consulta descripcion label ext1
    @GetMapping("/consulta-descripcion-label-ext1/{tipoExt}")
    public List<String> consultaDescripcionLabelExt1(@PathVariable String tipoExt){
        List<String> listaExt1 = itemPedidoServiceObj.consultaDescripcionLabelExt1(tipoExt);
        String ext1="";
        for(String obj : listaExt1){
            ext1 = Character.toUpperCase(obj.charAt(0)) + obj.substring(1, obj.length()).toLowerCase();
        }
       listaExt1.add(0,ext1);
        listaExt1.remove(1);
        return listaExt1;
    }


    //Consulta descripcion label ext2
    @GetMapping("/consulta-descripcion-label-ext2/{tipoExt}")
    public List<String> consultaDescripcionLabelExt2(@PathVariable String tipoExt){
        List<String> listaExt2 = itemPedidoServiceObj.consultaDescripcionLabelExt2(tipoExt);
        String ext2="";
        for(String obj : listaExt2){
            ext2 = Character.toUpperCase(obj.charAt(0)) + obj.substring(1, obj.length()).toLowerCase();
        }
        listaExt2.add(0,ext2);
        listaExt2.remove(1);
        return listaExt2;
    }



    // Obtener Bodega Item
    @GetMapping("/obtener-bodega-item/{idItem}")
    public List <String> obtenerBodegaItem(@PathVariable String idItem) throws SQLException {
        List<String> listaBodegaItem = new ArrayList<>();
        listaBodegaItem.add(unoEEServiceObj.obtenerBodegaItem(Integer.parseInt(idItem)));
        return listaBodegaItem;
    }



    //Conteo sobre si existe un item duplicado homologado
    @GetMapping("/contar-homologar-item-duplicado/{nit}/{codCliente}")
    public List<String> contarHomologarItemDuplicado(@PathVariable String nit, @PathVariable String codCliente){
        return itemPedidoServiceObj.contarHomologarItemDuplicado("890924167", codCliente);
    }




    // Guardar el pedido borrador en la base de datos
    @PostMapping("/add-pedido-borrador")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addPedidoBorrador(@Valid @RequestBody Pedido pedido, BindingResult result) {

        Pedido pedidoNuevo = null;

        Map<String, Object> response = new HashMap<>();

        // Se revisan los errores
        if (result.hasErrors()){
            System.out.println("han ocurrido errores");

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "el campo " + err.getField() + " : " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try{
            //Se guarda el pedido en la base de datos
            System.out.println("**********************************************");
            System.out.println("Este es el borrador a crear " + pedido.toString());
            System.out.println("***********************************************");

            // Se asigna fecha actual
            pedido.setFechaPedido(new Date());



            pedidoNuevo = pedidoServiceObj.save(pedido);
        } catch(DataAccessException e){
            System.out.println("existe un error para guardar en la BD");
            response.put("mensaje", "Error al realizar la creacion del pedido en la BD");
            response.put("error", e.getMessage() + " : " + e.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El borrador del pedido ha sido creado con exito");

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }















}
