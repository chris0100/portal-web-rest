package com.portalweb.repositories;

import com.portalweb.models.Pedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnoEERepository extends CrudRepository<Pedido,Long> {

    @Query(value= "select top 1 convert(varchar(20),f200_rowid) from [UnoEE].[dbo].t200_mm_terceros where f200_nit = ?1 ",
            nativeQuery = true)
    String findRowidCliente(String nit);


    @Query(value = "select top 1 f120_id_extension1+f120_id_extension2 from [UnoEE].[dbo].t120_mc_items where f120_id = ?1",
    nativeQuery = true)
    List<String> execTipoExtensiones(int idItem);


    @Query(value = "SELECT CAST( LEFT(LTRIM(RTRIM(f117_id)) + '    ', 4)as nvarchar(4)) + ' -'+LTRIM(RTRIM(f117_descripcion)) FROM UnoEE.dbo.t117_mc_extensiones1_detalle WHERE f117_id_extension1 = ?1 ORDER BY f117_id ASC ", nativeQuery = true)
    List<String> execListaExt1(String tipoExt);


    @Query(value = "SELECT LTRIM(RTRIM(f119_id)) + ' -' + LTRIM(RTRIM(f119_descripcion)) FROM UnoEE.dbo.t119_mc_extensiones2_detalle WHERE f119_id_extension2 = ?1 ORDER BY f119_id ASC ", nativeQuery = true)
    List<String> execListaExt2(String tipoExt);


    @Query(value = "SELECT [f120_id_unidad_inventario] FROM [UnoEE].[dbo].[t120_mc_items] where f120_id = ?1", nativeQuery = true)
    List<String> execUnidadInventario(int idItem);

    @Query(value = "SELECT TOP 1 CAST( LEFT([f101_decimales] + ' ', 1)as nvarchar(1)) FROM [UnoEE].[dbo].[t101_mc_unidades_medida] where f101_id = ?1", nativeQuery = true)
    List<String> execDecimalesUnidadInventario(String unidadMedida);

    @Query(value = "SELECT [f116_descripcion] FROM [UnoEE].[dbo].[t116_mc_extensiones1] where f116_id = ?1", nativeQuery = true)
    List<String>  execDescripcionLabelExt1(String tipoExt);

    @Query(value = "SELECT [f118_descripcion] FROM [UnoEE].[dbo].[t118_mc_extensiones2] where f118_id = ?1", nativeQuery = true)
    List<String> execDescripcionLabelExt2(String tipoExt);
}
