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
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class PedidoServiceImpl implements PedidoService{

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
        return pedidoRepositoryObj.puntoEnvio(nit,idSucursal,idPuntoEnvio);
    }

    @Override
    public List<String> obtenerConectorPedido(String idPedido) {
        return pedidoRepositoryObj.obtenerConectorPedido(idPedido);
    }


    @Override
    public void ejecucionWS(String pvstrDatos) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.submit(() -> {
            try {
                ejecutarWebService(pvstrDatos);
            } catch (Exception e) {
                e.printStackTrace();
                log.info("error envio web service "+e);
            }
        });
    }



    public void ejecutarWebService(String pvstrDatos) throws Exception{
        System.out.println(pvstrDatos);
        WSUNOEESoapProxy proxy = new WSUNOEESoapProxy();
        ShortHolder holder = new ShortHolder((short) 0);
        ImportarXMLResponseImportarXMLResult result = proxy.importarXML(pvstrDatos, holder);
        MessageElement[] elements = result.get_any();
        System.out.println(pvstrDatos);
        for (MessageElement element : elements) {
            log.info(element.getAsString());
            log.info("numero"+element.getLength());
        }
    }
}
