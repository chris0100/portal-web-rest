package com.portalweb.repositories;

import com.portalweb.models.Pedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnoEERepository extends CrudRepository<Pedido,Long> {

    @Query(value= "select top 1 convert(varchar(20),f200_rowid) from [UnoEE].[dbo].t200_mm_terceros where f200_nit = ?1 ",
            nativeQuery = true)
    String findRowidCliente(String nit);
}
