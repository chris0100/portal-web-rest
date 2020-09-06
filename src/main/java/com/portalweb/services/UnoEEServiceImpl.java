package com.portalweb.services;

import com.portalweb.controllers.ItemPedidoUnoEEController;
import com.portalweb.controllers.PedidoUnoeeController;
import com.portalweb.repositories.UnoEERepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UnoEEServiceImpl implements UnoEEService{

    @Autowired
    private UnoEERepository unoEERepositoryObj;


    @Override
    public String findRowIdCliente(String nit) {
        return unoEERepositoryObj.findRowidCliente(nit);
    }


    @Override
    public List<PedidoUnoeeController> listaHistorialPedido(String nit, String fechaParametro1, String fechaParametro2, String rowIdCliente) throws SQLException {
        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;

        String query = "EXEC UnoEE.dbo.sp_pv_cons_pedido 1,'001',' ','',1,0," + rowIdCliente.trim() + ",0,0,'" + fechaParametro1 + "','" + fechaParametro2 + "',7,0,0,0,0,0,0,1,10092,'PORTALWEB-HISTORIALPEDIDOS',4300";

        List<PedidoUnoeeController> listaPedidosUnoEE = new ArrayList<>();

        //Se establece la conexion para aplicar el query
        try {
            System.out.println("Esta realizando la conexion");
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                PedidoUnoeeController pedidoUnoee = new PedidoUnoeeController();
                pedidoUnoee.setRowid(rs.getInt("f_rowid"));
                pedidoUnoee.setNumeroDocumento(rs.getString("f_nrodocto"));
                pedidoUnoee.setRazonSocialCliente(rs.getString("f_cliente_fact_razon_soc"));
                pedidoUnoee.setEstado(rs.getString("f_estado"));
                pedidoUnoee.setNotas(rs.getString("f_notas"));
                pedidoUnoee.setOrdenCompra(rs.getString("f_orden_compra"));
                pedidoUnoee.setValorBrutoDocumento(rs.getBigDecimal("f_valor_bruto_docto"));
                pedidoUnoee.setValorImpDocumento(rs.getBigDecimal("f_valor_imp_docto"));
                pedidoUnoee.setValorNetoDocumento(rs.getBigDecimal("f_valor_neto_docto"));
                pedidoUnoee.setDireccion1(rs.getString("f_direccion1"));
                pedidoUnoee.setFecha(rs.getDate("f_fecha"));
                pedidoUnoee.setFechaEntrega(rs.getDate("f_fecha_entrega"));
                pedidoUnoee.setContacto(rs.getString("f_contacto"));
                pedidoUnoee.setVendedor(rs.getString("f_vendedor_razon_soc"));
                pedidoUnoee.setBarrio(rs.getString("f_desc_barrio"));
                pedidoUnoee.setPuntoEnvio(rs.getString("f_desc_punto_envio"));
                pedidoUnoee.setIdPuntoEnvio(rs.getString("f_punto_envio"));
                pedidoUnoee.setSucursalFactura(rs.getString("f_cliente_resumen_factura"));
                pedidoUnoee.setSucursalDespacho(rs.getString("f_cliente_resumen_despacho"));
                listaPedidosUnoEE.add(pedidoUnoee);
            }
            if (listaPedidosUnoEE.isEmpty()) {
                return null;
            }
            return listaPedidosUnoEE;
        }
        catch (SQLException e) {
            System.out.println("error " + e);
            return null;
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    @Override
    public List<ItemPedidoUnoEEController> listaItemsByRowId(int rowId) throws SQLException {
        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;

        String query = "SELECT distinct [f431_rowid] ,[f431_rowid_pv_docto] ,t120.f120_id codItem ,t120.f120_descripcion descripcion ,t120.f120_id_unidad_inventario und ,CAST( LEFT(LTRIM(RTRIM(t121.f121_id_ext1_detalle)) + '    ', 4)as nvarchar(4))+ ' - '+LTRIM(RTRIM(t117.f117_descripcion)) ext1 ,CAST( LEFT(LTRIM(RTRIM(t121.f121_id_ext2_detalle)) + '    ', 5)as nvarchar(5))+ ' - '+LTRIM(RTRIM(t119.f119_descripcion)) ext2 ,t431.f431_cant1_pedida cantPedida ,t431.f431_precio_unitario_base precioUnd ,t431.f431_vlr_bruto valorBruto ,t431.f431_vlr_imp valorImp ,t431.f431_vlr_neto valorNeto , t431.f431_fecha_entrega fechaEntrega FROM [UnoEE].[dbo].[t431_cm_pv_movto] t431 inner join [UnoEE].[dbo].t121_mc_items_extensiones t121 on t121.f121_rowid = t431.f431_rowid_item_ext inner join [UnoEE].[dbo].t120_mc_items t120 on t120.f120_rowid = t121.f121_rowid_item inner join [UnoEE].[dbo].t117_mc_extensiones1_detalle t117 on t117.f117_id = t121.f121_id_ext1_detalle inner join [UnoEE].[dbo].t119_mc_extensiones2_detalle t119 on t119.f119_id = t121.f121_id_ext2_detalle where f431_rowid_pv_docto = " + rowId;

        ItemPedidoUnoEEController itemPedidoUnoee;
        List<ItemPedidoUnoEEController> listaItemPedidosUnoEE = new ArrayList<>();

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                itemPedidoUnoee = new ItemPedidoUnoEEController();
                itemPedidoUnoee.setCodItem(rs.getString("codItem"));
                itemPedidoUnoee.setDescripcion(rs.getString("descripcion"));
                itemPedidoUnoee.setUnd(rs.getString("und"));
                itemPedidoUnoee.setExt1(rs.getString("ext1"));
                itemPedidoUnoee.setExt2(rs.getString("ext2"));
                itemPedidoUnoee.setCantPedida(rs.getBigDecimal("cantPedida"));
                itemPedidoUnoee.setPrecioUnd(rs.getBigDecimal("precioUnd"));
                itemPedidoUnoee.setValorBruto(rs.getBigDecimal("valorBruto"));
                itemPedidoUnoee.setValorImp(rs.getBigDecimal("valorImp"));
                itemPedidoUnoee.setValorNeto(rs.getBigDecimal("valorNeto"));
                itemPedidoUnoee.setFechaEntrega(rs.getDate("fechaEntrega"));
                listaItemPedidosUnoEE.add(itemPedidoUnoee);
            }
            if (listaItemPedidosUnoEE.isEmpty()) {
                return null;
            }
            return listaItemPedidosUnoEE;
        }
        catch (SQLException e) {
            System.out.println("error " + e);
            return null;
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }



    public String obtenerBodegaItem(int codItem) throws SQLException {
        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;

        String query = "SELECT top 1 t150.f150_id idBodega FROM [t753_mm_movto_entidad_columna] t753 inner join t149_mc_tipo_inv_serv t149  on t149.f149_rowid_movto_entidad = t753.f753_rowid_movto_entidad inner join t150_mc_bodegas t150 on t150.f150_rowid = t753.f753_dato_numero inner join v121 v121 on v121.v121_id_tipo_inv_serv =  t149.f149_id WHERE v121.v121_id_item = " + codItem;
        String bodega = "";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                bodega = rs.getString("idBodega");
                break;
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return bodega;
    }



}
