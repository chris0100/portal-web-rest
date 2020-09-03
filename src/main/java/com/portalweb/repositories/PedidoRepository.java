package com.portalweb.repositories;

import com.portalweb.models.ItemPedido;
import com.portalweb.models.Pedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends CrudRepository<Pedido,Long> {


    @Query("select t from Pedido t where t.idUsuario.nit = ?1 and t.estado = ?2 order by t.fechaPedido desc")
    List<Pedido> findAllByNitAndEstado(String nit, short estado);

    @Query("select t from Pedido t where t.idUsuario.nit = ?1 and t.estadoWB = ?2 order by t.fechaPedido desc")
    List<Pedido> findAllByNitAndEstadoWB(String nit, short estadoWB);



}
