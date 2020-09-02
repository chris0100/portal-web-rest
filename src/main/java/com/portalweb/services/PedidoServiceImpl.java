package com.portalweb.services;

import com.portalweb.models.Pedido;
import com.portalweb.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService{

    @Autowired
    private PedidoRepository pedidoRepositoryObj;


    @Override
    public List<Pedido> listaPedidos() {
        return (List<Pedido>) pedidoRepositoryObj.findAll();
    }


    @Override
    public Pedido findById(Long id) {
        return pedidoRepositoryObj.findById(id).get();
    }


    @Override
    public void save(Pedido pedido) {
        pedidoRepositoryObj.save(pedido);
    }


    @Override
    public void delete(Pedido pedido) {
        pedidoRepositoryObj.delete(pedido);
    }
}
