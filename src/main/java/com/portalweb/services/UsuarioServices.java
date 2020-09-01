package com.portalweb.services;


import com.portalweb.models.Usuario;

import java.util.List;

public interface UsuarioServices {

    public Usuario findByUsername(String username);

    public Usuario findById(Long id);

    public Usuario save(Usuario usuario);

    public List<Usuario> findAll();
}
