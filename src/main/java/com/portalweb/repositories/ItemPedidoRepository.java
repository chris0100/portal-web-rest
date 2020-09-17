package com.portalweb.repositories;

import com.portalweb.models.ItemPedido;
import com.portalweb.models.Pedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoRepository extends CrudRepository<ItemPedido, Long> {

    // Cuenta los item homologados, solo con uno que encuentre es valido
    @Query(value = "exec dbo.sp_contar_item_homologado ?1", nativeQuery = true)
    List<String> contarMostrarPanel(String nit);

    // Consulta la listaItemPatron
    @Query(value = "SELECT CAST( RIGHT('0000000'+convert(varchar,isnull( f120_id ,'')),7)as nvarchar(7)) +' | ' + " +
            "CAST( LEFT(f120_descripcion + '                                        ', 40)as nvarchar(40)) " +
            "FROM UnoEE.dbo.t120_mc_items t120 inner join UnoEE.dbo.t149_mc_tipo_inv_serv t149 " +
            "ON t149.f149_id = t120.f120_id_tipo_inv_serv where t120.f120_id_tipo_inv_serv " +
            "like '%.ex%' and t120.f120_ind_venta = 1 ORDER BY f120_id ASC ", nativeQuery = true)
    List<String> listaItemPatron();


    // Consulta si el item es homologado
    @Query(value = "exec dbo.sp_homologar_cod_cliente ?1, ?2", nativeQuery = true)
    List<String> contarHomologarItemDuplicado(String nit, String codigoCliente);


    //Consulta el item por codigo de barras
    @Query(value = "exec dbo.sp_obtener_item_cod_barras ?1", nativeQuery = true)
    List<String> obtenerItemCodBarras(String codBarras);

    // Ejecucion del procedimiento ejecutar sincronizar items
    @Query(value="exec dbo.sp_sincronizar_item ?1, ?2", nativeQuery = true)
    public void sincronizarItems(String idPedido, String nit);

    //Obtener precio item ecuador
    @Query(value = "exec dbo.sp_obtener_precio_item_ecuador ?1, ?2, ?3", nativeQuery = true)
    List<String> obtenerPrecioItemEcuador(String codItem, String nit, String unidadMedia);


}
