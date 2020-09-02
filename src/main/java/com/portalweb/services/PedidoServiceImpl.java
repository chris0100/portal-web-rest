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


    //Listar todos los pedidos
    @Override
    public List<Pedido> listaPedidos() {
        return (List<Pedido>) pedidoRepositoryObj.findAll();
    }


    //Encontrar un pedido por id
    @Override
    public Pedido findById(Long id) {
        return pedidoRepositoryObj.findById(id).get();
    }


    //Guardar un pedido
    @Override
    public void save(Pedido pedido) {
        pedidoRepositoryObj.save(pedido);
    }


    //Borrar un pedido
    @Override
    public void delete(Pedido pedido) {
        pedidoRepositoryObj.delete(pedido);
    }


    //Listar un pedido en estado borrador(0 estado) con el nit del usuario que consulta
    @Override
    public List<Pedido> listarPedidosBorrador(String nit, short estado) {
        return pedidoRepositoryObj.findAllByNitAndEstado(nit, estado);
    }

    //Listar un pedido en estado de verificacion(1 estadowb) con el nit del usuario que consulta
    @Override
    public List<Pedido> listarPedidosVerificacion(String nit, short estadoWB) {
        return pedidoRepositoryObj.findAllByNitAndEstadoWB(nit, estadoWB);
    }


}
