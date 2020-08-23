package com.portalweb.repositories;

import com.portalweb.models.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {


    public Usuario findByUsername(String username);
}
