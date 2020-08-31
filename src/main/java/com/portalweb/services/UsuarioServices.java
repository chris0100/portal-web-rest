package com.portalweb.services;


import com.portalweb.models.Usuario;

public interface UsuarioServices {

    public Usuario findByUsername(String username);

    public Usuario findById(Long id);

    public Usuario save(Usuario usuario);
}
