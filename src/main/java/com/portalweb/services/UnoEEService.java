package com.portalweb.services;

import com.portalweb.controllers.ItemPedidoUnoEEController;
import com.portalweb.controllers.PedidoUnoeeController;
import com.portalweb.models.CertificadoRetencionIVA;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface UnoEEService {

    String findRowIdCliente(String nit);

    List<PedidoUnoeeController> listaHistorialPedido(String nit, String fechaParametro1,
                                                     String fechaParametro2, String rowIdCliente) throws SQLException;

    List<ItemPedidoUnoEEController> listaItemsByRowId(int rowId) throws SQLException;

    String obtenerBodegaItem(int idItem) throws SQLException;

    List<String> obtenerDatosAsesorUnoEE(String idSucursal, String nit) throws SQLException;

    List<String> obtenerListaExt1(int codItem) throws SQLException;

    List<String> obtenerListaExt2(int codItem) throws SQLException;

    List<String> obtenerDatosItem(int codItem) throws SQLException;

    List<String> obtenerDatosItemCompleto(int codItem, String ext1, String ext2) throws SQLException;

    BigDecimal obtenerDisponibleItemCompleto(int codItem, String ext1, String ext2) throws SQLException;

    BigDecimal obtenerDisponibleItem(int codItem) throws  SQLException;

    int obtenerRowIdTercero(String nit) throws SQLException;

    List<CertificadoRetencionIVA> recargarConsultarIvaUnoEE(String mesInicio, String mesFin,
                                                            String anioGrabable, int rowIdTercero) throws SQLException;

    List<CertificadoRetencionIVA> recargarConsultarRetencion(String anioGrabable, int rowIdTercero,
                                                             String ciudadConsignoParam, String ciudadPracticoParam, String tipoCertificado) throws SQLException;
}
