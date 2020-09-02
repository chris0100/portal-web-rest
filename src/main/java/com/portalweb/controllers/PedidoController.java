package com.portalweb.controllers;

import com.portalweb.models.Pedido;
import com.portalweb.services.PedidoService;
import com.portalweb.services.UnoEEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class PedidoController {

    @Autowired
    private PedidoService pedidoServiceObj;

    @Autowired
    private UnoEEService unoEEServiceObj;


    //Obtiene la lista de los pedidos
    @GetMapping("/listar-pedidos")
    public List<Pedido> listarPedidos() {
        return pedidoServiceObj.listaPedidos();
    }





    //Obtener la lista de pedidos en borrador
    @GetMapping("/listar-pedidos-borrador/{nit}")
    public List<Pedido> listarPedidosBorrador(@PathVariable String nit) {
        return pedidoServiceObj.listarPedidosBorrador(nit, (short) 0);
    }





    //Obtener la lista de pedidos en verificacion
    @GetMapping("/listar-pedidos-verificacion/{nit}")
    public List<Pedido> listarPedidosVerificacion(@PathVariable String nit) {
        return pedidoServiceObj.listarPedidosVerificacion(nit, (short) 1);
    }





    //Obtener la lista del historial de pedidos
    @GetMapping("/listar-historial-pedidos/{nit}/{fechaParametro1}/{fechaParametro2}")
    public List<PedidoUnoeeController> listarHistorialPedidos(@PathVariable String nit,
                                                              @PathVariable String fechaParametro1,
                                                              @PathVariable String fechaParametro2) throws SQLException {

        //Se obtiene el rowIdCliente
        String rowIdCliente = unoEEServiceObj.findRowIdCliente(nit);

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





}
