package com.portalweb.controllers;


import com.portalweb.models.ItemExcel;
import com.portalweb.models.ItemPedido;
import com.portalweb.models.Pedido;
import com.portalweb.models.Usuario;
import com.portalweb.services.ItemPedidoService;
import com.portalweb.services.PedidoService;
import com.portalweb.services.UnoEEService;
import com.portalweb.soapunoee.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public Pedido listaItemPedidos(@PathVariable Long idPedido) {
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

        List<PedidoUnoeeController> listaHistorialPedidos = unoEEServiceObj.listaHistorialPedido(nit, fechaParametro1
                , fechaParametro2, rowIdCliente);

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
    public List<String> obtenerPuntoEnvio(@PathVariable String nit, @PathVariable String idSucursal, @PathVariable String idPuntoEnvio) {
        List<String> lista = new ArrayList<>();
        lista.add(pedidoServiceObj.obtenerPuntoEnvio(nit, idSucursal, idPuntoEnvio));
        return lista;
    }


    //Cuenta el item homologado para saber que pestaña habilitar en la pagina crear pedido
    //homologado = tiene su propio codigo de cliente.
    //no homologado = realiza la busqueda por codigo de barras.
    @GetMapping("/contar-mostrar-panel/{nit}")
    public List<Integer> contarMostrarPanel(@PathVariable String nit) {
        List<Integer> lista = new ArrayList<>();
        lista.add(itemPedidoServiceObj.contarMostrarPanel(nit));
        System.out.println(lista.get(0));
        return lista;
    }


    //Consulta la lista item Patron para mostrar os items disponibles
    @GetMapping("/lista-item-patron")
    public List<String> listaItemPatron() {
        return itemPedidoServiceObj.obtenerListaItemPatron();
    }


    // Consultar el tipo de extensiones
    @GetMapping("/tipo-extensiones/{idItem}")
    public List<String> consultaTipoExtensiones(@PathVariable String idItem) {
        return itemPedidoServiceObj.consultaTipoExtensiones(Integer.parseInt(idItem));
    }


    //Consulta Ext1(TAMAÑO)
    @GetMapping("/ext1/{tipoExt}")
    public List<String> consultaExt1(@PathVariable String tipoExt) {
        return itemPedidoServiceObj.consultaExt1(tipoExt);
    }


    //Consulta Ext2(COLOR)
    @GetMapping("/ext2/{tipoExt}")
    public List<String> consultaExt2(@PathVariable String tipoExt) {
        return itemPedidoServiceObj.consultaExt2(tipoExt);
    }


    //Consultar unidad inventario
    @GetMapping("/unidad-inventario/{idItem}")
    public List<String> consultaUnidadInventario(@PathVariable String idItem) {
        return itemPedidoServiceObj.consultaUnidadInventario(Integer.parseInt(idItem));
    }


    //Consulta decimales unidad inventario
    @GetMapping("/decimales-unidad-inventario/{unidadMedida}")
    public List<String> consultaDecimalesUnidadInventario(@PathVariable String unidadMedida) {
        return itemPedidoServiceObj.consultaDecimalesUnidadInventario(unidadMedida);
    }


    //Consulta descripcion label ext1
    @GetMapping("/consulta-descripcion-label-ext1/{tipoExt}")
    public List<String> consultaDescripcionLabelExt1(@PathVariable String tipoExt) {
        List<String> listaExt1 = itemPedidoServiceObj.consultaDescripcionLabelExt1(tipoExt);
        String ext1 = "";
        for (String obj : listaExt1) {
            ext1 = Character.toUpperCase(obj.charAt(0)) + obj.substring(1, obj.length()).toLowerCase();
        }
        listaExt1.add(0, ext1);
        listaExt1.remove(1);
        return listaExt1;
    }


    //Consulta descripcion label ext2
    @GetMapping("/consulta-descripcion-label-ext2/{tipoExt}")
    public List<String> consultaDescripcionLabelExt2(@PathVariable String tipoExt) {
        List<String> listaExt2 = itemPedidoServiceObj.consultaDescripcionLabelExt2(tipoExt);
        String ext2 = "";
        for (String obj : listaExt2) {
            ext2 = Character.toUpperCase(obj.charAt(0)) + obj.substring(1, obj.length()).toLowerCase();
        }
        listaExt2.add(0, ext2);
        listaExt2.remove(1);
        return listaExt2;
    }


    // Obtener Bodega Item
    @GetMapping("/obtener-bodega-item/{idItem}")
    public List<String> obtenerBodegaItem(@PathVariable String idItem) throws SQLException {
        List<String> listaBodegaItem = new ArrayList<>();
        listaBodegaItem.add(unoEEServiceObj.obtenerBodegaItem(Integer.parseInt(idItem)));
        return listaBodegaItem;
    }


    //Conteo sobre si existe un item duplicado homologado
    //**********************************************************************
    // RECORDAR CAMBIAR PARA NIT
    //**********************************************************************
    @GetMapping("/contar-homologar-item-duplicado/{nit}/{codCliente}")
    public List<String> contarHomologarItemDuplicado(@PathVariable String nit, @PathVariable String codCliente) {
        return itemPedidoServiceObj.contarHomologarItemDuplicado(nit.trim(), codCliente);
    }


    // Guardar el pedido borrador en la base de datos
    @PostMapping("/add-pedido-borrador")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addPedidoBorrador(@Valid @RequestBody Pedido pedido, BindingResult result) {

        Pedido pedidoNuevo = null;

        Map<String, Object> response = new HashMap<>();

        // Se revisan los errores
        if (result.hasErrors()) {
            System.out.println("han ocurrido errores");

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "el campo " + err.getField() + " : " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            //Se guarda el pedido en la base de datos
            System.out.println("**********************************************");
            System.out.println("Este es el borrador a crear " + pedido.toString());
            System.out.println("***********************************************");

            // Se asigna fecha actual
            pedido.setFechaPedido(new Date());


            pedidoNuevo = pedidoServiceObj.save(pedido);
        } catch (DataAccessException e) {
            System.out.println("existe un error para guardar en la BD");
            response.put("mensaje", "Error al realizar la creacion del pedido en la BD");
            response.put("error", e.getMessage() + " : " + e.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El borrador del pedido ha sido creado con exito");
        response.put("nuevoId", pedidoNuevo.getId() + ""); // envia el id que se genera despues de guardar en la BD

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }


    //Obtener datos del asesor UnoEE
    @GetMapping("/obtener-datos-asesor-unoee/{idSucursal}/{nit}")
    public List<String> obtenerDatosAsesorUnoEE(@PathVariable String idSucursal, @PathVariable String nit) throws SQLException {
        return unoEEServiceObj.obtenerDatosAsesorUnoEE(idSucursal, nit);
    }


    //Obtener el conector a partir del id del pedido
    @PostMapping("/obtener-conector")
    public List<String> obtenerConectorPedido(@Valid @RequestBody Pedido pedido) throws Exception {

        System.out.println("aqui ingresa el pedido " + pedido);

        //Obtiene el conector en forma de lista y lo convierte a un solo string
        List<String> listapvstrDatos = pedidoServiceObj.obtenerConectorPedido(pedido.getId().toString());
        String pvstrDatos = "";

        for (String obj : listapvstrDatos) {
            pvstrDatos += obj;
        }

        System.out.println("****************");
        System.out.println(pvstrDatos);  // datos del conector

        //Se localiza la original para editar campos de web services
        Pedido pedidoBD = pedidoServiceObj.findById(pedido.getId());

        //Obtener usuario y lista
        Usuario usuarioPedido = pedido.getIdUsuario();
        List<ItemPedido> listaItemPedidos = pedido.getListaItemPedido();

        //Ejecuta el WebService
        pedidoServiceObj.ejecucionWS(pvstrDatos, pedidoBD, usuarioPedido, listaItemPedidos, pedido.getEmailAsesor());
        return null;
    }




    //Obtener el conector a partir del id del pedido
    @PostMapping("/obtener-conector-error")
    public List<String> obtenerConectorPedidoError(@Valid @RequestBody Pedido pedido) throws Exception {

        //Obtiene el conector en forma de lista y lo convierte a un solo string
        List<String> listapvstrDatos = pedidoServiceObj.obtenerConectorPedido(pedido.getId().toString());
        String pvstrDatos = "";

        for (String obj : listapvstrDatos) {
            pvstrDatos += obj;
        }

        System.out.println("****************");
        System.out.println(pvstrDatos);  // datos del conector

        //Se localiza la original para editar campos de web services
        Pedido pedidoBD = pedidoServiceObj.findById(pedido.getId());

        List<String> respuesta = new ArrayList<>();

        ////////////////////////////////////////////////////////////
        //Se realiza la gestion del conector con web service
        pedidoServiceObj.ejecucionWSError(pvstrDatos, pedidoBD);

        System.out.println("termina de ejecutar el webservice");

        if (pedidoBD.getEstadoWB() == 1) {
            respuesta.add("El pedido no fue creado debido a errores");
        } else {
            respuesta.add("El pedido fue creado exitosamente");
        }
        return respuesta;
    }




    // Se realiza el tratamiento de los datos enviados por excel
    @PostMapping("/tratamiento-lista-items-excel")
    public List<String> tratamientoListaItemsExcel(@Valid @RequestBody List<ItemExcel> listaItemsExcel) {

        List<String> resultadoBusquedaItems = new ArrayList<>();

        //Iteramos sobre la lista, para buscarlo por codigo de barras
        for (ItemExcel obj : listaItemsExcel) {

            //La cantidad que va con el nit, debe ser -1 siempre
            if (obj.getCantidad() != -1) {
                // Buscar por el itemCodBarras
                String nombreCompletoItem = itemPedidoServiceObj.ItemCodBarras(obj.getCodigoItem());

                //Si no lo encuentra por ahi, lo busca por cod cliente
                if (nombreCompletoItem.contains("vacio")) {
                    try {
                        nombreCompletoItem = itemPedidoServiceObj.contarHomologarItemDuplicado(listaItemsExcel.get(listaItemsExcel.size() - 1).getCodigoItem(),
                                obj.getCodigoItem()).get(0);
                    } catch (Exception e) {
                        nombreCompletoItem = "vacio:" + obj.getCodigoItem();
                    }
                }

                //Si no lo encuentra por ninguno de los dos, crea el error
                if (nombreCompletoItem.contains("vacio")) {
                    resultadoBusquedaItems.add("complementoError");
                }

                //Lo añade a la lista
                resultadoBusquedaItems.add(nombreCompletoItem);
            }
        }
        return resultadoBusquedaItems;
    }


    //Eliminar un pedido en borrador
    @DeleteMapping("/eliminar-pedido-borrador/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePedidoBorrador(@PathVariable Long id) {
        pedidoServiceObj.deletePedidoBorradorById(id);
    }


    //Eliminar el item de un borrador
    @DeleteMapping("/eliminar-item-borrador/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItemBorrador(@PathVariable Long id) {
        itemPedidoServiceObj.deleteItemBorrador(id);
    }


    //Consultar lista ext1 - se usa en ventana validar item creado - para ese respectivo coditem
    @GetMapping("/obtener-lista-ext1/{codItem}")
    public List<String> obtenerListaExt1(@PathVariable int codItem) throws SQLException {
        return unoEEServiceObj.obtenerListaExt1(codItem);
    }


    //Consultar lista ext2 - se usa en ventana validar item creado - para ese respectivo coditem
    @GetMapping("/obtener-lista-ext2/{codItem}")
    public List<String> obtenerListaExt2(@PathVariable int codItem) throws SQLException {
        return unoEEServiceObj.obtenerListaExt2(codItem);
    }


    //Obtener datos del item - se usa en ventana validar item creado
    @GetMapping("/obtener-datos-item/{codItem}")
    public List<String> obtenerDatosItem(@PathVariable int codItem) throws SQLException {
        return unoEEServiceObj.obtenerDatosItem(codItem);
    }


    //Obtener datos del item con codItem,ext1,ext2 - se usa en ventana validar item
    @GetMapping("/obtener-datos-item/{codItem}/{ext1}/{ext2}")
    public List<String> obtenerDatosItemCompleto(@PathVariable int codItem,
                                                 @PathVariable String ext1,
                                                 @PathVariable String ext2) throws SQLException {
        return unoEEServiceObj.obtenerDatosItemCompleto(codItem, ext1, ext2);
    }


    //Obtener disponible item 3 params
    @GetMapping("/obtener-disponible-item/{codItem}/{ext1}/{ext2}")
    public List<String> obtenerDiponibleItem(@PathVariable int codItem,
                                             @PathVariable String ext1,
                                             @PathVariable String ext2) throws SQLException {
        List<String> resultado = new ArrayList<>();
        resultado.add(unoEEServiceObj.obtenerDisponibleItemCompleto(codItem, ext1, ext2) + "");
        return resultado;
    }


    //Obtener disponible item 1 param
    @GetMapping("/obtener-disponible-item/{codItem}")
    public List<String> obtenerDiponibleItem(@PathVariable int codItem) throws SQLException {
        List<String> resultado = new ArrayList<>();
        resultado.add(unoEEServiceObj.obtenerDisponibleItem(codItem) + "");
        return resultado;
    }


    //*************************************************
    //MANEJO DE LISTAS DE ERRORES********************************
    //*************************************************

    //Obtener lista Pedido error asesor
    @GetMapping("/obtener-lista-pedido-error-asesor/{nit}")
    public List<Pedido> obtenerListaPedidoErrorAsesor(@PathVariable String nit) {
        return pedidoServiceObj.obtenerPedidoEstadoAsesor(nit);
    }


    //Obtener lista Pedido error
    @GetMapping("/obtener-lista-pedido-error")
    public List<Pedido> obtenerListaPedidoError() {
        return pedidoServiceObj.obtenerPedidoEstado();
    }


    //Realizar sincronizacion de items del pedido
    @GetMapping("/sincronizar-items/{idPedido}/{nit}")
    public List<String> ejecutarSincronizarItems(@PathVariable String idPedido, @PathVariable String nit) {
        List<String> listado = new ArrayList<>();

        try {
            //Se ejecuta el procedimiento
            itemPedidoServiceObj.ejecutarSincronizarItems(idPedido, nit);
            listado.add("Ejecución realizada satisfactoriamente");
        } catch (Exception e) {
            listado.add("Ejecución realizada satisfactoriamente");
        }
        return listado;
    }


    // Obtener Bodega Item
    @GetMapping("/sincronizar-bodega-item/{idItem}/{codItem}")
    public List<String> sincronizarBodegaItem(@PathVariable Long idItem, @PathVariable String codItem) throws SQLException {
        String idBodega = "";
        idBodega = unoEEServiceObj.obtenerBodegaItem(Integer.parseInt(codItem));
        ItemPedido item = itemPedidoServiceObj.findById(idItem);
        item.setIdBodega(idBodega);
        System.out.println(item);
        System.out.println(idBodega);
        itemPedidoServiceObj.saveItem(item);
        return new ArrayList<>();
    }


    // Sincronizar precion ecuador (NO SE UTILIZARA POR EL MOMENTO)
    @GetMapping("/sincronizar-precio-ecuador/{idItem}")
    public List<String> sincronizarPrecioEcuador(@PathVariable Long idItem) {
        ItemPedido item = itemPedidoServiceObj.findById(idItem);

        System.out.println(item);

        // Se obtiene el precio del item Ecuador
        List<String> resultadoPrecioEcuador = new ArrayList<>();
        resultadoPrecioEcuador = itemPedidoServiceObj.obtenerPrecioItemEcuador(item.getCodCliente().trim(),
                "1792196841001", item.getUnidadMedida().trim());

        System.out.println(resultadoPrecioEcuador.get(0));

        //Se añade al item
        item.setPrecioUnitarioUnoee(new BigDecimal(resultadoPrecioEcuador.get(0)));
        System.out.println(item.getPrecioUnitarioUnoee());

        //Se obtiene la fecha de actualizacion de precio uno ee
        List<Date> fechaActualizacionPrecioUnoee = new ArrayList<>();
        fechaActualizacionPrecioUnoee = itemPedidoServiceObj.ObtenerFechaPrecioItemEcuador(item.getCodCliente().trim(),
                "1792196841001", item.getUnidadMedida().trim());

        //Se añade al item
        item.setFechaActualizacionPrecioUnoee(fechaActualizacionPrecioUnoee.get(0));
        System.out.println(item.getFechaActualizacionPrecioUnoee());

        //Se guarda la edicion del item en la BD
        itemPedidoServiceObj.saveItem(item);
        return null;
    }
    //*************************************************
    //MANEJO DE LISTAS DE ERRORES********************************
    //*************************************************


}
