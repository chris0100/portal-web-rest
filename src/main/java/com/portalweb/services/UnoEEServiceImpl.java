package com.portalweb.services;

import com.portalweb.controllers.ItemPedidoUnoEEController;
import com.portalweb.controllers.PedidoUnoeeController;
import com.portalweb.models.CertificadoRetencionIVA;
import com.portalweb.models.ConvertNumero;
import com.portalweb.repositories.UnoEERepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UnoEEServiceImpl implements UnoEEService {

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
        } catch (SQLException e) {
            System.out.println("error " + e);
            return null;
        } finally {
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
        } catch (SQLException e) {
            System.out.println("error " + e);
            return null;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    // Obtener la bodega a la cual pertenece el item
    @Override
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


    // Obtener los datos del asesor UnoEE
    @Override
    public List<String> obtenerDatosAsesorUnoEE(String idSucursal, String nit)
            throws SQLException {

        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;

        String nombreAsesor = "";
        String correoAsesor = "";
        String nitAsesor = "";

        //Query que obtiene los datos del asesor.
        String query = "Select top 1 t200asesor.f200_nit nit_asesor, t015asesor.f015_email correo_asesor ,t200asesor.f200_razon_social nombre_asesor From UnoEE.dbo.t200_mm_terceros f inner join UnoEE.dbo.t204_mm_planes_criterios t204asesor on t204asesor.f204_id  = '005' left	join  UnoEE.dbo.t207_mm_criterios_clientes t207asesor on f207_id_cia = 1 and t207asesor.f207_rowid_tercero = f.f200_rowid  and  t207asesor.f207_id_sucursal = '" + idSucursal + "' and	 t207asesor.f207_id_plan_criterios = f204_id left	join  UnoEE.dbo.t206_mm_criterios_mayores t206asesor on f206_id_cia =1 		and	 t206asesor.f206_id_plan = f207_id_plan_criterios and	 t206asesor.f206_id = f207_id_criterio_mayor inner join  UnoEE.dbo.t200_mm_terceros t200asesor on t200asesor.f200_nit = convert(varchar,(convert(int, left (f206_descripcion, patindex('%[^0-9]%',f206_descripcion +' ')-1)))) inner join  UnoEE.dbo.t015_mm_contactos t015asesor on t200asesor.f200_rowid_contacto = t015asesor.f015_rowid where f.f200_nit = '" + nit + "'";

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                nombreAsesor = rs.getString("nombre_asesor");
                correoAsesor = rs.getString("correo_asesor");
                nitAsesor = rs.getString("nit_asesor");
            }

            List<String> listaDatos = new ArrayList<>();
            listaDatos.add(nombreAsesor);
            listaDatos.add(correoAsesor);
            listaDatos.add(nitAsesor);

            return listaDatos;
        } catch (SQLException e) {
            System.out.println("error " + e);
            return new ArrayList<>();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    //Obtiene la lista ext1.
    @Override
    public List<String> obtenerListaExt1(int codItem) throws SQLException {
        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;

        List<String> listaExt1Consulta = new ArrayList<>();

        String query = "SELECT DISTINCT [f117_id] +' - '+ [f117_descripcion] ext1 FROM [UnoEE].[dbo].[t117_mc_extensiones1_detalle] inner join v121 on v121.v121_id_ext1_detalle = f117_id and v121.v121_id_extension1 = f117_id_extension1 where v121.v121_id_item = " + codItem;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                listaExt1Consulta.add(rs.getString("ext1"));
            }
            return listaExt1Consulta;
        } catch (SQLException e) {
            System.out.println("error " + e);
            return new ArrayList<>();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    //Obtiene la lista ext2
    @Override
    public List<String> obtenerListaExt2(int codItem) throws SQLException {
        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;

        //Se instancia lista de resultado
        List<String> listaExt2Consulta = new ArrayList<>();

        String query = "SELECT DISTINCT [f119_id] +' - '+ [f119_descripcion] ext2 FROM [UnoEE].[dbo].[t119_mc_extensiones2_detalle] inner join v121 on v121.v121_id_ext2_detalle = f119_id and v121.v121_id_extension2 = f119_id_extension2 where v121.v121_id_item = " + codItem;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                listaExt2Consulta.add(rs.getString("ext2"));
            }
            return listaExt2Consulta;
        } catch (SQLException e) {
            System.out.println("error " + e);
            return new ArrayList<>();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    //Obtener datos del item
    @Override
    public List<String> obtenerDatosItem(int codItem) throws SQLException {
        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;

        //descripcion(0)
        //referencia(1)
        //undInventario(2)
        //undOrden(3)
        //codBarra(4)
        List<String> datosResultado = new ArrayList<>();

        String query = "select v121_descripcion, v121_referencia, v121_id_unidad_inventario, v121_id_unidad_orden, v121_id_barras_principal from UnoEE.dbo.v121 where v121_id_item = " + codItem + "and v121_id_cia = '001'";

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                datosResultado.add(rs.getString("v121_descripcion"));
                datosResultado.add(rs.getString("v121_referencia"));
                datosResultado.add(rs.getString("v121_id_unidad_inventario"));
                datosResultado.add(rs.getString("v121_id_unidad_orden"));
                datosResultado.add(rs.getString("v121_id_barras_principal"));
            }
            return datosResultado;
        } catch (SQLException e) {
            System.out.println("error " + e);
            return new ArrayList<>();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    @Override
    public List<String> obtenerDatosItemCompleto(int codItem, String ext1, String ext2) throws SQLException {
        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;

        //descripcion(0)
        //referencia(1)
        //undInventario(2)
        //undOrden(3)
        //codBarra(4)
        List<String> datosResultado = new ArrayList<>();

        String query = "select v121_descripcion, v121_referencia, v121_id_unidad_inventario, v121_id_unidad_orden, v121_id_barras_principal from UnoEE.dbo.v121 where v121_id_item = " + codItem + " and v121_id_ext1_detalle = '" + ext1 + "' and v121_id_ext2_detalle = '" + ext2 + "' and v121_id_cia = '001'";


        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                datosResultado.add(rs.getString("v121_descripcion"));
                datosResultado.add(rs.getString("v121_referencia"));
                datosResultado.add(rs.getString("v121_id_unidad_inventario"));
                datosResultado.add(rs.getString("v121_id_unidad_orden"));
                datosResultado.add(rs.getString("v121_id_barras_principal"));
            }
            return datosResultado;
        } catch (SQLException e) {
            System.out.println("error " + e);
            return new ArrayList<>();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    //Obtiene la cantidad disponible del item
    @Override
    public BigDecimal obtenerDisponibleItemCompleto(int codItem, String ext1, String ext2) throws SQLException {
        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;

        BigDecimal disponible = new BigDecimal(0);

        String query = "SELECT sum(v400_cant_disponible_1-v400_cant_pendiente_salir_1) disponible_real FROM [UnoEE].[dbo].[v400_item_ext] v400 inner join [UnoEE].[dbo].[t150_mc_bodegas] t150 on v400.v400_rowid_bodega = t150.f150_rowid inner join [UnoEE].[dbo].v121 on v121.v121_rowid_item_ext = v400_rowid_item_ext where v121.v121_id_item = " + codItem + " and v121.v121_id_ext1_detalle = '" + ext1 + "' and v121.v121_id_ext2_detalle = '" + ext2 + "' and v121.v121_id_cia = '001' and (t150.f150_id = '001' or t150.f150_id = '002')";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                disponible = rs.getBigDecimal("disponible_real");
            }
            return disponible;
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return null;
    }


    @Override
    public BigDecimal obtenerDisponibleItem(int codItem) throws SQLException {
        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;

        BigDecimal disponible = new BigDecimal(0);

        String query = "SELECT sum(v400_cant_disponible_1-v400_cant_pendiente_salir_1) disponible_real FROM [UnoEE].[dbo].[v400_item_ext] v400 inner join [UnoEE].[dbo].[t150_mc_bodegas] t150 on v400.v400_rowid_bodega = t150.f150_rowid inner join [UnoEE].[dbo].v121 on v121.v121_rowid_item_ext = v400_rowid_item_ext where v121.v121_id_item = " + codItem + "and v121.v121_id_cia = '001' and (t150.f150_id = '001' or t150.f150_id = '002')";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                disponible = rs.getBigDecimal("disponible_real");
            }
            return disponible;
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return null;
    }


    @Override
    public int obtenerRowIdTercero(String nit) throws SQLException {
        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        String query = "select top 1 f200_rowid id_tercero from UnoEE.dbo.t200_mm_terceros where f200_nit = '" + nit + "'";

        int resultado = 0;

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                resultado = rs.getInt("id_tercero");
            }
            return resultado;
        } catch (SQLException e) {
            System.out.println("error " + e);
            return -1;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    @Override
    public List<CertificadoRetencionIVA> recargarConsultarIvaUnoEE(String mesInicio, String mesFin, String anioGrabable, int rowIdTercero) throws SQLException {
        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        String query = "exec sp_certificado_retencion 1,2,1,'PCI','2367',0," + rowIdTercero + ",$0.0000," + Integer.parseInt(anioGrabable) + "," + mesInicio + "," + mesFin + ",'YM',NULL,NULL,'Cali','Cali',0,0,0,' '";
        List<CertificadoRetencionIVA> listaCertificadoRetencionIva = new ArrayList<>();

        BigDecimal total_monto = new BigDecimal(0);
        BigDecimal total_base = new BigDecimal(0);
        BigDecimal total_retenido = new BigDecimal(0);
        CertificadoRetencionIVA certificadoRetencionIvaActual;

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                certificadoRetencionIvaActual = new CertificadoRetencionIVA();
                certificadoRetencionIvaActual.setDircia(rs.getString("dircia"));
                certificadoRetencionIvaActual.setCiudadcia(rs.getString("ciudadcia"));
                certificadoRetencionIvaActual.setRazon(rs.getString("razon"));
                certificadoRetencionIvaActual.setTipoid(rs.getString("tipoid"));
                certificadoRetencionIvaActual.setIdent(rs.getString("ident"));
                certificadoRetencionIvaActual.setDirtercero(rs.getString("dirtercero"));
                certificadoRetencionIvaActual.setCiudadtercero(rs.getString("ciudadtercero"));
                certificadoRetencionIvaActual.setAuxiliar(rs.getString("auxiliar"));
                certificadoRetencionIvaActual.setIdaux(rs.getString("idaux"));
                certificadoRetencionIvaActual.setTasa(rs.getBigDecimal("tasa"));
                certificadoRetencionIvaActual.setTasabase(rs.getInt("tasabase"));
                certificadoRetencionIvaActual.setBase(rs.getBigDecimal("base"));
                certificadoRetencionIvaActual.setNeto(rs.getBigDecimal("neto"));
                certificadoRetencionIvaActual.setBimestre(rs.getInt("bimestre"));
                certificadoRetencionIvaActual.setFirma(rs.getString("firma"));
                certificadoRetencionIvaActual.setCargo(rs.getString("cargo"));
                certificadoRetencionIvaActual.setTotalneto(rs.getBigDecimal("totalneto"));
                certificadoRetencionIvaActual.setAgrupapor(rs.getInt("agrupapor"));
                certificadoRetencionIvaActual.setCiudad_practica(rs.getString("ciudad_practica"));
                certificadoRetencionIvaActual.setCiudad_consigna(rs.getString("ciudad_consigna"));
                certificadoRetencionIvaActual.setF_email(rs.getString("f_email"));
                certificadoRetencionIvaActual.setF_nombre_archivo(rs.getString("f_nombre_archivo"));
                certificadoRetencionIvaActual.setTotalabonocta(rs.getBigDecimal("totalabonocta"));

                //SUMA PARA CALCULAR EL MONTO TOTAL
                total_monto = (total_monto).add(certificadoRetencionIvaActual.getBase());
                //SUMA PARA CALCULAR EL VL BASE
                total_base = (total_base).add(certificadoRetencionIvaActual.getBase());
                //SUMA PARA CALCULAR EL VR- RETENIDO
                total_retenido = (total_retenido).add(certificadoRetencionIvaActual.getNeto());

                try {
                    certificadoRetencionIvaActual.setDesde(rs.getDate("desde"));
                    certificadoRetencionIvaActual.setHasta(rs.getDate("hasta"));
                } catch (Exception e) {
                    certificadoRetencionIvaActual.setDesde(null);
                    certificadoRetencionIvaActual.setHasta(null);
                }
                listaCertificadoRetencionIva.add(certificadoRetencionIvaActual);
            }

            // Se cargan valores de los totales en un campo aparte
            listaCertificadoRetencionIva.get(0).setTotal_monto(total_monto);
            listaCertificadoRetencionIva.get(0).setTotal_base(total_base);
            listaCertificadoRetencionIva.get(0).setTotal_retenido(total_retenido);


/*            for (CertificadoRetencionIVA encabezado : listaCertificadoRetencionIva) {
                setRazon(encabezado.getRazon());
                setTipoid(encabezado.getTipoid());
                setIdent(encabezado.getIdent());
                setDirtercero(encabezado.getDirtercero());
                setCiudadtercero(encabezado.getCiudadtercero());
                setCiudad_practica(encabezado.getCiudad_practica());
                setCiudad_consigna(encabezado.getCiudad_consigna());
                setAuxiliar(encabezado.getAuxiliar());
                setFirma(encabezado.getFirma());
                setCargo(encabezado.getCargo());
                break;
            }*/
            return listaCertificadoRetencionIva;
        } catch (SQLException e) {
            System.out.println("error " + e);
            return null;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    //Consultar certificado retencion
    @Override
    public List<CertificadoRetencionIVA> recargarConsultarRetencion(String anioGrabable, int rowIdTercero, String ciudadConsignoParam, String ciudadPracticoParam,
                                                                    String tipoCertificado) throws SQLException {
        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        String query = "exec sp_certificado_retencion 1,0,2,'" + tipoCertificado + "','23',0," + rowIdTercero + ",$0.0000,'" + anioGrabable + "'," + 0 + "," + 0 + ",'YM',NULL,NULL,'" + ciudadPracticoParam + "','" + ciudadConsignoParam + "',0,0,0,' '";

        List<CertificadoRetencionIVA> listaCertificadoRetencionIva = new ArrayList<>();
        CertificadoRetencionIVA certificadoRetencionIvaActual;

        BigDecimal total_monto = new BigDecimal(0);
        BigDecimal total_base = new BigDecimal(0);
        BigDecimal total_retenido = new BigDecimal(0);


        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                certificadoRetencionIvaActual = new CertificadoRetencionIVA();
                certificadoRetencionIvaActual.setDircia(rs.getString("dircia"));
                certificadoRetencionIvaActual.setCiudadcia(rs.getString("ciudadcia"));
                certificadoRetencionIvaActual.setRazon(rs.getString("razon"));
                certificadoRetencionIvaActual.setTipoid(rs.getString("tipoid"));
                certificadoRetencionIvaActual.setIdent(rs.getString("ident"));
                certificadoRetencionIvaActual.setDirtercero(rs.getString("dirtercero"));
                certificadoRetencionIvaActual.setCiudadtercero(rs.getString("ciudadtercero"));
                certificadoRetencionIvaActual.setAuxiliar(rs.getString("auxiliar"));
                certificadoRetencionIvaActual.setIdaux(rs.getString("idaux"));
                certificadoRetencionIvaActual.setTasa(rs.getBigDecimal("tasa"));
                certificadoRetencionIvaActual.setTasabase(rs.getInt("tasabase"));
                certificadoRetencionIvaActual.setBase(rs.getBigDecimal("base"));
                certificadoRetencionIvaActual.setNeto(rs.getBigDecimal("neto"));
                certificadoRetencionIvaActual.setBimestre(rs.getInt("bimestre"));
                certificadoRetencionIvaActual.setFirma(rs.getString("firma"));
                certificadoRetencionIvaActual.setCargo(rs.getString("cargo"));
                certificadoRetencionIvaActual.setTotalneto(rs.getBigDecimal("totalneto"));
                certificadoRetencionIvaActual.setAgrupapor(rs.getInt("agrupapor"));
                certificadoRetencionIvaActual.setCiudad_practica(rs.getString("ciudad_practica"));
                certificadoRetencionIvaActual.setCiudad_consigna(rs.getString("ciudad_consigna"));
                certificadoRetencionIvaActual.setF_email(rs.getString("f_email"));
                certificadoRetencionIvaActual.setF_nombre_archivo(rs.getString("f_nombre_archivo"));
                certificadoRetencionIvaActual.setTotalabonocta(rs.getBigDecimal("totalabonocta"));

                //SUMA PARA CALCULAR EL MONTO TOTAL
                total_monto = (total_monto).add(certificadoRetencionIvaActual.getBase());
                //SUMA PARA CALCULAR EL VL BASE
                total_base = (total_base).add(certificadoRetencionIvaActual.getBase());
                //SUMA PARA CALCULAR EL VR- RETENIDO
                total_retenido = (total_retenido).add(certificadoRetencionIvaActual.getNeto());
                try {
                    certificadoRetencionIvaActual.setDesde(rs.getDate("desde"));
                    certificadoRetencionIvaActual.setHasta(rs.getDate("hasta"));
                } catch (Exception e) {
                    certificadoRetencionIvaActual.setDesde(null);
                    certificadoRetencionIvaActual.setHasta(null);
                }
                listaCertificadoRetencionIva.add(certificadoRetencionIvaActual);
            }

            // Se cargan valores de los totales en un campo aparte
            listaCertificadoRetencionIva.get(0).setTotal_monto(total_monto);
            listaCertificadoRetencionIva.get(0).setTotal_base(total_base);
            listaCertificadoRetencionIva.get(0).setTotal_retenido(total_retenido);
            listaCertificadoRetencionIva.get(0).setConvertirNumero(getConvertirNumero(total_retenido));

            return listaCertificadoRetencionIva;

        } catch (SQLException e) {
            System.out.println("error " + e);
            return null;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }


    //Operacion de conversion de numero
    public String getConvertirNumero(BigDecimal total_retenido){
        ConvertNumero num = new ConvertNumero();
        double aDouble = total_retenido.doubleValue();
        String numero = Double.toString(aDouble);
        return num.Convertir(numero, true);
    }






}
