package com.portalweb.services;

import com.portalweb.controllers.ItemPedidoUnoEEController;
import com.portalweb.controllers.PedidoUnoeeController;

import java.sql.SQLException;
import java.util.List;

public interface UnoEEService {

    String findRowIdCliente(String nit);

    List<PedidoUnoeeController> listaHistorialPedido(String nit, String fechaParametro1,
                                                     String fechaParametro2, String rowIdCliente) throws SQLException;

    List<ItemPedidoUnoEEController> listaItemsByRowId(int rowId) throws SQLException;

    String obtenerBodegaItem(int idItem) throws SQLException;

    List<String> obtenerDatosAsesorUnoEE(String idSucursal, String nit) throws SQLException;
}
