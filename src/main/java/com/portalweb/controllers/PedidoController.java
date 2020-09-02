package com.portalweb.controllers;

import com.portalweb.models.Pedido;
import com.portalweb.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class PedidoController {

    @Autowired
    private PedidoService pedidoServiceObj;

    @GetMapping("/listar-pedidos")
    public List<Pedido> listarPedidos(){
        return pedidoServiceObj.listaPedidos();
    }



}
