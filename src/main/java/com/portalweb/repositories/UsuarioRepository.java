package com.portalweb.repositories;

import com.portalweb.models.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {


    public List<Usuario> findAllByUsername(String username);

    public List<Usuario> findAllByEmailAndNit(String email, String nit);

    Usuario findByToken(String token);
}
