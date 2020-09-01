package com.portalweb.services;


import com.portalweb.models.Usuario;
import com.portalweb.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UsuarioServices,UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepositoryObj;


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositoryObj.findByUsername(username);

        if (usuario != null) {
            System.out.println("datos usuario: " + usuario.getUsername() + ": " + usuario.getNombre() + ": " + usuario.getPassword() + ":" + usuario.getId());
        }

        if (usuario == null){
            System.out.println("Usuario no existe");
            throw new UsernameNotFoundException("Error: no existe el usuario");
        }


        //Se carga la lista de roles.
        List<GrantedAuthority> authorities = usuario.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getNombre().trim()))
                .peek(authority -> System.out.println("role: " + authority.getAuthority()))
                .collect(Collectors.toList());

        return new User(usuario.getUsername().trim(), usuario.getPassword().trim(), true, true,
                true, true, authorities);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findByUsername(String username) {
        return usuarioRepositoryObj.findByUsername(username);
    }


    @Override
    public Usuario findById(Long id) {
        return usuarioRepositoryObj.findById(id).get();
    }

    @Override
    public List<Usuario> findAll() {
        return (List<Usuario>) usuarioRepositoryObj.findAll();
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepositoryObj.save(usuario);
    }
}
