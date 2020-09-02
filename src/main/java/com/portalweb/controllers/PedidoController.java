package com.portalweb.controllers;

import com.portalweb.models.Pedido;
import com.portalweb.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class PedidoController {

    @Autowired
    private PedidoService pedidoServiceObj;



    //Obtiene la lista de los pedidos
    @GetMapping("/listar-pedidos")
    public List<Pedido> listarPedidos(){
        return pedidoServiceObj.listaPedidos();
    }


    //Obtener la lista de pedidos en borrador
    @GetMapping("/listar-pedidos-borrador/{nit}")
    public List<Pedido> listarPedidosBorrador(@PathVariable String nit){
        return pedidoServiceObj.listarPedidosBorrador(nit, (short) 0);
    }





}
