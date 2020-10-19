package com.portalweb.auth;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {



    //rutas de acceso libres de autenticacion
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/obtener-sucursal/**", "/api/obtener-punto-envio/**",
                        "/api/validar-entidad-dinamica/**", "/api/listar-usuarios", "/api/obtener-usuario/**", "/api/listar-pedidos",
                        "/api/listar-pedidos-borrador/**", "/api/listar-pedidos-verificacion/**", "/api/listar-historial-pedidos/**", "/api/obtener-lista-items-pedidoUnoEE/**",
                        "/api/obtener-lista-items-vb/**", "/api/obtener-punto-envio/**", "/api/contar-mostrar-panel/**", "/api/lista-item-patron",
                        "/api/tipo-extensiones/**", "/api/ext1/**" ,"/api/ext2/**", "/api/unidad-inventario/**", "/api/decimales-unidad-inventario/**",
                        "/api/consulta-descripcion-label-ext1/**", "/api/consulta-descripcion-label-ext2/**", "/api/obtener-bodega-item/**" ,
                        "/api/contar-homologar-item-duplicado/**", "/api/obtener-datos-asesor-unoee/**", "/api/obtener-conector",
                        "/api/tratamiento-lista-items-excel", "/api/obtener-lista-ext1/**", "/api/obtener-lista-ext2/**", "/api/obtener-datos-item/**",
                        "/api/obtener-disponible-item/**", "/api/obtener-lista-pedido-error-asesor/**", "/api/obtener-lista-pedido-error",
                        "/api/sincronizar-items/**", "/api/sincronizar-bodega-item/**", "/api/sincronizar-precio-ecuador/**",
                        "/api/obtener-conector-error/**", "/api/obtener-row-id-tercero/**", "/api/crear-pdf-iva/**", "/api/crear-pdf-retencion/**",
                        "/api/generar-token-proveedor/**", "/api/procesar-token/**", "/api/nit-password-usuario-nuevo/**",
                        "/api/generar-token-cliente/**", "/api/nit-password-proveedor-nuevo/**", "/api/recargar-consulta-items-unoee/**", "/api/**")
                .permitAll()
                .antMatchers(HttpMethod.PUT, "/api/update-cliente/**", "/api/update-pass-cliente/**","/api/editar-otro-usuario/**","/api/update-pass-otro-usuario/**",
                        "/api/reestablecer-password/**", "/api/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/crear-usuario/**", "/api/add-pedido-borrador", "/api/editar-item-pedido-error")
                .permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/eliminar-pedido-borrador/**", "/api/eliminar-item-borrador/**")
                .permitAll()
                /*.antMatchers(HttpMethod.GET, "").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.POST, "").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.POST, "").hasRole("ADMIN")
                .antMatchers("").hasRole("ADMIN")*/
                .anyRequest().authenticated()
                .and().cors().configurationSource(corsConfigurationSource());
    }



    //Creacion de BEANS para utilizar en otros metodos.
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }




    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(){
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }











}
