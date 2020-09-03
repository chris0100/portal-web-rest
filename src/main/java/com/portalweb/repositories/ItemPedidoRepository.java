package com.portalweb.repositories;

import com.portalweb.models.ItemPedido;
import com.portalweb.models.Pedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoRepository extends CrudRepository<ItemPedido, Long> {

}
