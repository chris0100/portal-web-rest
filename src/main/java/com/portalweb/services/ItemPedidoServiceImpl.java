package com.portalweb.services;

import com.portalweb.models.ItemPedido;
import com.portalweb.repositories.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPedidoServiceImpl implements ItemPedidoService{

    @Autowired
    private ItemPedidoRepository itemPedidoRepositoryObj;


    @Override
    public List<ItemPedido> listaItemsPedido() {
        return (List<ItemPedido>) itemPedidoRepositoryObj.findAll();
    }


    @Override
    public ItemPedido findById(Long id) {
        return itemPedidoRepositoryObj.findById(id).get();
    }


    @Override
    public void saveItem(ItemPedido itemPedido) {
        itemPedidoRepositoryObj.save(itemPedido);
    }


    @Override
    public void delete(ItemPedido itemPedido) {
        itemPedidoRepositoryObj.delete(itemPedido);
    }
}
