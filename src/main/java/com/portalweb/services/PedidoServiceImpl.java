package com.portalweb.services;

import com.portalweb.controllers.EmailController;
import com.portalweb.models.ItemPedido;
import com.portalweb.models.Pedido;
import com.portalweb.models.Usuario;
import com.portalweb.repositories.ItemPedidoRepository;
import com.portalweb.repositories.PedidoRepository;
import com.portalweb.soapunoee.ImportarXMLResponseImportarXMLResult;
import com.portalweb.soapunoee.WSUNOEESoapProxy;
import org.apache.axis.message.MessageElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.FetchProfile;
import javax.xml.rpc.holders.ShortHolder;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepositoryObj;

    @Autowired
    private ItemPedidoRepository itemPedidoRepositoryObj;

    private static final Logger log = LoggerFactory.getLogger(PedidoServiceImpl.class);


    String p1CodigoPedido = "";
    String p2UsuarioPedido = "";
    String p3Oc = "";
    String p4TablaItems;
    String p5PuntoEnvio = "";
    String p6mensajeErrorWb = "";
    String p7razonSocialCliente = "";
    EmailController emailController;




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
    public Pedido save(Pedido pedido) {
        return pedidoRepositoryObj.save(pedido);
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

    @Override
    public String obtenerPuntoEnvio(String nit, String idSucursal, String idPuntoEnvio) {
        return pedidoRepositoryObj.puntoEnvio(nit, idSucursal, idPuntoEnvio);
    }

    @Override
    public List<String> obtenerConectorPedido(String idPedido) {
        return pedidoRepositoryObj.obtenerConectorPedido(idPedido);
    }


    // Ejecutar el WebService
    @Override
    public List<String> ejecucionWS(String pvstrDatos, Pedido pedido, Usuario usuario, List<ItemPedido> listaItemPedido, String emailAsesor) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.submit(() -> {
            try {
                System.out.println(pvstrDatos);

                String mensajeConector = "";
                int valorConector = 0;

                WSUNOEESoapProxy proxy = new WSUNOEESoapProxy();
                ShortHolder holder = new ShortHolder((short) 0);
                ImportarXMLResponseImportarXMLResult result = proxy.importarXML(pvstrDatos, holder);
                MessageElement[] elements = result.get_any();


                for (MessageElement element : elements) {
                    mensajeConector += element.getAsString();
                    valorConector = element.getLength();
                }

                // Se ajustan el resto de datos
                pedido.setEstado((short) 1);
                pedido.setEstadoWB((short) valorConector);
                pedido.setMensajeWB(mensajeConector);
                pedido.setFechaPedido(new Date());



                //se guarda el pedido editado en la BD
                pedidoRepositoryObj.save(pedido);


                //Se organiza la informacion que va a contener el email a enviarse
                parametrosMail(pedido, usuario, listaItemPedido);


                //Se arma la plantilla y se envia el email
                sendMailCliente(usuario);

                System.out.println("valor conector " + valorConector);
                if (valorConector == 1){
                    sendMailAsesor(pedido, emailAsesor);
                }


            } catch (Exception e) {
                e.printStackTrace();
                log.info("error envio web service " + e);
            }
        });
        return new ArrayList<>();
    }





    //Se establecen datos para enviar por email
    public void parametrosMail(Pedido pedido, Usuario usuario, List<ItemPedido> listaItemPedido) {
        p1CodigoPedido = String.valueOf(pedido.getId());
        p2UsuarioPedido = usuario.getNombre();
        p3Oc = pedido.getOc();
        p4TablaItems = "";
        for (ItemPedido item : listaItemPedido) {
            p4TablaItems +=
                    "<td class=\"tg-w6x5\">" + item.getCodItem() + "</b></td>\n" +
                            "<td class=\"tg-w6x5\">" + item.getDescripcion() + "</td>\n" +
                            "<td class=\"tg-w6x5\">" + item.getDescripcionExt1() + "</td>\n" +
                            "<td class=\"tg-w6x5\">" + item.getDescripcionExt2() + "</td>\n" +
                            "<td class=\"tg-w6x5\">" + item.getUnidadMedida() + "</td>\n" +
                            "<td class=\"tg-w6x5\">" + item.getCantidad() + "</td>\n" +
                            "</tr>\n";
        }
        p5PuntoEnvio = "<p><strong> Punto de env&#237o: </strong>" + pedido.getPuntoEnvio() + "<p>\n"
                + "<p><strong> Sucursal a facturar: </strong>" + pedido.getSucursalFacturar() + "<p>\n"
                + "<p><strong> Sucursal a remisionar: </strong>" + pedido.getSucursalRemisionar() + "<p>\n"
                + "<p><strong> Direcci&#243n de env&#237o: </strong>" + pedido.getDireccion() + "<p>\n"
                + "<p><strong> Vendedor: </strong>" + pedido.getTerceroVendedor() + "<p>\n"
                + "<p><strong> Contacto: </strong>" + pedido.getContacto() + "<p>\n"
                + " </div>\n";
        p6mensajeErrorWb = pedido.getMensajeWB();
        p7razonSocialCliente = usuario.getNit() + " - " + usuario.getCliente();
    }



    //Email enviado al cliente
    public void sendMailCliente(Usuario usuarioActual) throws FileNotFoundException {
        emailController = new EmailController();
        String asunto = "Su solicitud de pedido fue creada exitosamente";
        emailController.generarTemplate(p1CodigoPedido, p2UsuarioPedido, p3Oc, p4TablaItems, p5PuntoEnvio);
        String cuerpo = emailController.getTemplateCliente();
        emailController.sendMail(usuarioActual.getEmail(), asunto, cuerpo);
    }



    //Email enviado al asesor
    public void sendMailAsesor(Pedido pedido, String emailAsesor) throws FileNotFoundException, SQLException {
        emailController = new EmailController();
        String asunto = "Pedido con errores";
        emailController.generarTemplateAsesor(p1CodigoPedido, p2UsuarioPedido, p6mensajeErrorWb, p4TablaItems, p5PuntoEnvio, p7razonSocialCliente);
        String cuerpo = emailController.getTemplateAsesor();
        System.out.println("prueba para enviar al email del asesor " + emailAsesor);
        /////////////////////////////////////////////////////////////////////
        //RECORDAR CAMBIAR AL FINAL PARA QUE ENVIE EL CORREO AL ASESOR
        /////////////////////////////////////////////////////////////////////
        //emailController.sendMail(emailAsesor, asunto, cuerpo);
    }


    // Eliminar pedido en borrador
    @Override
    public void deletePedidoBorradorById(Long id) {
        pedidoRepositoryObj.deleteById(id);
    }



    //Obtener listado pedidos error asesor
    @Override
    public List<Pedido> obtenerPedidoEstadoAsesor(String terceroAsesor) {
        return pedidoRepositoryObj.findAllByEstadoWBAndTerceroAsesorOrderByFechaPedidoDesc((short) 1, terceroAsesor);
    }


    //Obtener listado pedidos error
    @Override
    public List<Pedido> obtenerPedidoEstado() {
        return pedidoRepositoryObj.findAllByEstadoWBOrderByFechaPedidoDesc((short) 1);
    }
}
