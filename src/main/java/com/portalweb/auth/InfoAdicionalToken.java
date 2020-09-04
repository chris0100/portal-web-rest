package com.portalweb.auth;


import com.portalweb.models.Usuario;
import com.portalweb.services.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InfoAdicionalToken implements TokenEnhancer {


    @Autowired
    private UsuarioServices usuarioServicesObj;



    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        System.out.println(authentication.getName());

        Usuario usuario = usuarioServicesObj.findByUsername(authentication.getName());

        Map<String, Object> info = new HashMap<>();
        info.put("nombre", usuario.getNombre());
        info.put("nit", usuario.getNit());
        info.put("cliente", usuario.getCliente());
        info.put("id_sucursal", usuario.getIdSucursal());
        info.put("email",usuario.getEmail());
        info.put("descripcion_punto_envio", usuario.getDescripcionPuntoEnvio());
        info.put("id_usuario", usuario.getId());
        info.put("descripcion_sucursal", usuario.getDescripcionSucursal());
        info.put("id_punto_envio", usuario.getIdPuntoEnvio());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }






}
