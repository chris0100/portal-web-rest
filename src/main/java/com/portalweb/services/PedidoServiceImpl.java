package com.portalweb.services;

import com.portalweb.models.Pedido;
import com.portalweb.repositories.ItemPedidoRepository;
import com.portalweb.repositories.PedidoRepository;
import com.portalweb.soapunoee.ImportarXMLResponseImportarXMLResult;
import com.portalweb.soapunoee.WSUNOEESoapProxy;
import org.apache.axis.message.MessageElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.rpc.holders.ShortHolder;
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
    public List<String> ejecucionWS(String pvstrDatos, Pedido pedido) {
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



                //Se arma la plantilla y se envia el email



            } catch (Exception e) {
                e.printStackTrace();
                log.info("error envio web service " + e);
            }
        });
        return new ArrayList<>();
    }

}
