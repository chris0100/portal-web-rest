package com.portalweb.repositories;

import com.portalweb.models.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {


    public List<Usuario> findAllByUsername(String username);

    public List<Usuario> findAllByEmailAndNit(String email, String nit);

    Usuario findByToken(String token);

    @Query(value = "SELECT top 1 f200_razon_social from [UnoEE].[dbo].[t200_mm_terceros] " +
            "where f200_nit = ?1 and f200_nit = ?2", nativeQuery = true)
    String obtenerRazonSocial(String nit, String password);

    Usuario findByUsername(String username);

}
