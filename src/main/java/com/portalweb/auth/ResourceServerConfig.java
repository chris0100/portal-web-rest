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

import java.util.Arrays;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {



    //rutas de acceso libres de autenticacion
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/obtener-sucursal/**", "/obtener-punto-envio/**",
                        "/validar-entidad-dinamica/**", "/listar-usuarios", "/obtener-usuario/**", "/listar-pedidos",
                        "/listar-pedidos-borrador/**", "/listar-pedidos-verificacion/**", "/listar-historial-pedidos/**", "/obtener-lista-items-pedidoUnoEE/**",
                        "/obtener-lista-items-vb/**", "/obtener-punto-envio/**", "/contar-mostrar-panel/**", "/lista-item-patron",
                        "/tipo-extensiones/**", "/ext1/**" ,"/ext2/**", "/unidad-inventario/**", "/decimales-unidad-inventario/**",
                        "/consulta-descripcion-label-ext1/**", "/consulta-descripcion-label-ext2/**", "/obtener-bodega-item/**" ,
                        "/contar-homologar-item-duplicado/**", "/obtener-datos-asesor-unoee/**", "/obtener-conector",
                        "/tratamiento-lista-items-excel", "/obtener-lista-ext1/**", "/obtener-lista-ext2/**", "/obtener-datos-item/**",
                        "/obtener-disponible-item/**", "/obtener-lista-pedido-error-asesor/**", "/obtener-lista-pedido-error",
                        "/sincronizar-items/**", "/sincronizar-bodega-item/**", "/sincronizar-precio-ecuador/**",
                        "/obtener-conector-error/**", "/obtener-row-id-tercero/**", "/crear-pdf-iva/**", "/crear-pdf-retencion/**",
                        "/generar-token-usuario/**", "/procesar-token/**", "/nit-password-usuario-nuevo/**")
                .permitAll()
                .antMatchers(HttpMethod.PUT, "/update-cliente/**", "/update-pass-cliente/**","/editar-otro-usuario/**","/update-pass-otro-usuario/**",
                        "/reestablecer-password/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/crear-usuario/**", "/add-pedido-borrador", "/editar-item-pedido-error")
                .permitAll()
                .antMatchers(HttpMethod.DELETE, "/eliminar-pedido-borrador/**", "/eliminar-item-borrador/**")
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
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200","*"));
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
