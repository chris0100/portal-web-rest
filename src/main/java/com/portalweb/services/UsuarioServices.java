package com.portalweb.services;


import com.portalweb.models.Usuario;

public interface UsuarioServices {

    public Usuario findByUsername(String username);
}
