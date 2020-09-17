package com.portalweb.services;

import com.portalweb.models.ItemPedido;
import com.portalweb.repositories.ItemPedidoRepository;
import com.portalweb.repositories.UnoEERepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPedidoServiceImpl implements ItemPedidoService{

    @Autowired
    private ItemPedidoRepository itemPedidoRepositoryObj;

    @Autowired
    private UnoEERepository unoEERepositoryObj;


    @Override
    public List<ItemPedido> listaItemsPedido() {
        return (List<ItemPedido>) itemPedidoRepositoryObj.findAll();
    }


    @Override
    public ItemPedido findById(Long id) {
        return itemPedidoRepositoryObj.findById(id).get();
    }


    @Override
    public void saveItem(ItemPedido itemPedido) {
        itemPedidoRepositoryObj.save(itemPedido);
    }


    @Override
    public void delete(ItemPedido itemPedido) {
        itemPedidoRepositoryObj.delete(itemPedido);
    }

    @Override
    public Integer contarMostrarPanel(String nit) {
        return itemPedidoRepositoryObj.contarMostrarPanel(nit).size();
    }

    @Override
    public List<String> obtenerListaItemPatron() {
        return itemPedidoRepositoryObj.listaItemPatron();
    }


    @Override
    public List<String> consultaTipoExtensiones(int idItem) {
        return unoEERepositoryObj.execTipoExtensiones(idItem);
    }


    @Override
    public List<String> consultaExt1(String tipoExt) {
        return unoEERepositoryObj.execListaExt1(tipoExt);
    }

    @Override
    public List<String> consultaExt2(String tipoExt) {
        return unoEERepositoryObj.execListaExt2(tipoExt);
    }


    @Override
    public List<String> consultaUnidadInventario(int idItem) {
        return unoEERepositoryObj.execUnidadInventario(idItem);
    }

    @Override
    public List<String> consultaDecimalesUnidadInventario(String unidadMedida) {
        return unoEERepositoryObj.execDecimalesUnidadInventario(unidadMedida);
    }

    @Override
    public List<String> consultaDescripcionLabelExt1(String tipoExt) {
        return unoEERepositoryObj.execDescripcionLabelExt1(tipoExt);
    }

    @Override
    public List<String> consultaDescripcionLabelExt2(String tipoExt) {
        return unoEERepositoryObj.execDescripcionLabelExt2(tipoExt);
    }


    @Override
    public List<String> contarHomologarItemDuplicado(String nit, String codCliente) {
        return itemPedidoRepositoryObj.contarHomologarItemDuplicado(nit, codCliente);
    }

    //Obtiene el nombre completo obtenido del codigo de barras
    @Override
    public String ItemCodBarras(String codBarras) {
        try{
            return itemPedidoRepositoryObj.obtenerItemCodBarras(codBarras).get(0);
        }
        catch (Exception e){
            return "vacio:"+codBarras;
        }

    }


    //Borra el item de un borrador
    @Override
    public void deleteItemBorrador(Long id) {
        itemPedidoRepositoryObj.deleteById(id);
    }



    //Ejecutar procedimiento sincronizar items
    @Override
    public void ejecutarSincronizarItems(String idPedido, String nit) {
        itemPedidoRepositoryObj.sincronizarItems(idPedido, nit);
    }


    //Obtener precioItemEcuador
    @Override
    public List<String> obtenerPrecioItemEcuador(String codItem, String nit, String unidadMedia) {
        return itemPedidoRepositoryObj.obtenerPrecioItemEcuador(codItem.trim(),nit,unidadMedia);
    }
}
