package com.portalweb.services;


import com.portalweb.models.Usuario;

import java.io.FileNotFoundException;
import java.util.List;

public interface UsuarioServices {

    public Usuario findByUsername(String username);

    public Usuario findById(Long id);

    public Usuario save(Usuario usuario);

    public List<Usuario> findAll();

    public List<Usuario> findAllByEmailAndNit(String email, String nit);

    Usuario findByCorreoRecuperarContra(String email, String nit, String sucursal, String puntoEnvio);

    void sendMailRecuperarContrasena(Usuario usuario) throws FileNotFoundException;

    Usuario findByToken(String token);

    String obtenerRazonSocialUsuarioNuevo(String nit, String password);

    Usuario findByValidarUsername(String username);

    String nitContraseñaUsuarioNuevoProveedor(String nit, String password);
}
