package com.portalweb.controllers;

import com.portalweb.models.Role;
import com.portalweb.models.Usuario;
import com.portalweb.repositories.RoleRepository;
import com.portalweb.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioServiceObj;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepositoryObj;

    String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=BD_PortalWeb_2;user=sa;password=3K4$.2015$#";


    // Obtiene la sucursal a traves del nit
    @GetMapping("/obtener-sucursal/{nitCliente}")
    public ResponseEntity<?> obtenerSucursal(@PathVariable String nitCliente) throws SQLException {
        List<String> listadoSucursal = new ArrayList<>();

        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stm = null;
        String query = "SELECT [f201_id_sucursal]+' - '+[f201_descripcion_sucursal] " +
                "FROM [UnoEE].[dbo].[t201_mm_clientes] t201 " +
                "inner join [UnoEE].[dbo].[t200_mm_terceros] t200 on t200.f200_rowid = t201.f201_rowid_tercero " +
                "where t200.f200_nit = '" + nitCliente + "' and t200.f200_ind_estado = 1 ";

        try {
            stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                listadoSucursal.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("ha ocurrido un error en la extraccion");
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
        return new ResponseEntity<>(listadoSucursal, HttpStatus.OK);
    }


    //Permite obtener el punto de envio con el nit y la sucursal
    @GetMapping("/obtener-punto-envio/{nitCliente}/{sucursal}")
    public ResponseEntity<?> obtenerPuntoEnvio(@PathVariable String nitCliente, @PathVariable String sucursal) throws SQLException {
        List<String> listadoPuntoEnvio = new ArrayList<>();

        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stm = null;
        String query = "select f215_id + ' - ' +f215_descripcion + ' - ' +isnull(f013_descripcion,'') from [UnoEE].[dbo].t215_mm_puntos_envio_cliente inner join [UnoEE].[dbo].t200_mm_terceros t200 on t200.f200_rowid = [UnoEE].[dbo].t215_mm_puntos_envio_cliente.f215_rowid_tercero left outer join [UnoEE].[dbo].t015_mm_contactos on	f215_rowid_contacto = f015_rowid left outer join [UnoEE].[dbo].t011_mm_paises	on	f015_id_pais = f011_id left outer join [UnoEE].[dbo].t012_mm_deptos on f015_id_depto = f012_id and f015_id_pais = f012_id_pais left outer join [UnoEE].[dbo].t013_mm_ciudades	on	f015_id_ciudad = f013_id and f015_id_pais = f013_id_pais and f015_id_depto = f013_id_depto where t200.f200_nit = '" + nitCliente + "' and f215_id_sucursal =" + sucursal + " and f215_ind_estado = 1";

        try {
            stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                listadoPuntoEnvio.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("ha ocurrido un error en la extraccion");
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
        return new ResponseEntity<>(listadoPuntoEnvio, HttpStatus.OK);
    }


    // Valida si la entidad dinamica esta habilitada
    @GetMapping("/validar-entidad-dinamica/{nitCliente}/{sucursal}")
    public ResponseEntity<?> validarEntidadDinamica(@PathVariable String nitCliente, @PathVariable String sucursal) throws SQLException {

        List<String> respuestaHabilitada = new ArrayList<>();

        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stm = null;
        String query = "SELECT c.f753_dato_texto \n" +
                "  FROM [UnoEE_2].[dbo].[t201_mm_clientes] b \n" +
                "  inner join [UnoEE_2].[dbo].t200_mm_terceros x  on x.f200_rowid = b.f201_rowid_tercero \n" +
                "  inner join  [UnoEE_2].[dbo].t753_mm_movto_entidad_columna c on c.f753_rowid_movto_entidad=b.f201_rowid_movto_entidad \n" +
                "  inner join [UnoEE_2].[dbo].t743_mm_entidad_atributo d on c.f753_rowid_entidad_atributo=d.f743_rowid \n" +
                "  inner join [UnoEE_2].[dbo].t742_mm_entidad e on d.f743_rowid_entidad=e.f742_rowid \n" +
                "  where  e.f742_etiqueta='HABILITAR PORTAL WEB' and d.f743_id='HABILITAR PORTAL WEB' and x.f200_nit = '" + nitCliente + "'\n" +
                "  and b.f201_id_sucursal =" + sucursal + "order by c.f753_fecha_ts_actualizacion desc";

        try {
            stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                respuestaHabilitada.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("ha ocurrido un error en la extraccion");
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
        return new ResponseEntity<>(respuestaHabilitada, HttpStatus.OK);
    }


    //Actualizar datos de configuracion del usuario logueado
    @PutMapping("/update-cliente/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> update(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id) {

        Usuario usuarioActual = usuarioServiceObj.findById(id);

        Usuario usuarioUpdated = null;

        Map<String, Object> response = new HashMap<>();

        // Si existen errores
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }


        //Si el usuario que se quiere editar no existe en la BD
        if (usuarioActual == null) {
            response.put("mensaje", "Error: No se puede editar");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }


        //Se pasan los dos datos que se quieren editar
        try {
            usuarioActual.setNombre(usuario.getNombre().trim());
            usuarioActual.setEmail(usuario.getEmail().trim());

            usuarioUpdated = usuarioServiceObj.save(usuarioActual);
        } catch (DataAccessException de) {
            response.put("mensaje", "Error al actualizar el usuario en la Base de Datos");
            response.put("error", "Error critico de almacenamiento");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //Si se crea correctamente el usuario envia mensaje satisfactorio.
        response.put("mensaje", "El usuario ha sido actualizado con exito");
        response.put("usuario", usuarioUpdated);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }


    //Modifica la contraseña del usuario
    @PutMapping("/update-pass-cliente/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> modificarPassword(@Valid @RequestBody String[] pass, BindingResult result,
                                               @PathVariable Long id) {

        //Localiza al usuario
        Usuario usuario = usuarioServiceObj.findById(id);

        Usuario usuarioUpdated = null;

        Map<String, Object> response = new HashMap<>();


        // Si existen errores
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }


        //Si el usuario que se quiere editar no existe en la BD
        if (usuario == null) {
            response.put("mensaje", "Error: No se puede editar");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        //pass[0] = actual ; pass[1] = nuevo
        //Revisa que el password actual coincida con el de la BD
        if (passwordEncoder.matches(pass[0].trim(), usuario.getPassword().trim())) {

            try {
                //Se encripta el nuevo password
                String nuevaPasswordEnc = passwordEncoder.encode(pass[1].trim());
                System.out.println();
                usuario.setPassword(nuevaPasswordEnc);
                usuarioUpdated = usuarioServiceObj.save(usuario);

            } catch (DataAccessException de) {
                response.put("mensaje", "Error al actualizar la contraseña en la Base de Datos");
                response.put("error", "Error critico de almacenamiento");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            //Si se crea correctamente el usuario envia mensaje satisfactorio.
            response.put("mensaje", "La contraseña ha sido actualizada con exito");
            response.put("usuario", usuarioUpdated);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

        } else {
            response.put("mensaje", "La contraseña actual no es correcta");
            response.put("error", "Error de contraseña");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }




    //Creacion de usuario
    @PostMapping("/crear-usuario")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addUsuario(@Valid @RequestBody Usuario usuario, BindingResult result) {

        Usuario usuarioNuevo = null;

        System.out.println(usuario.getRolesPrincipal()[0] + " : " + usuario.getRolesPrincipal().length);

        Map<String, Object> response = new HashMap<>();

        // Se revisan si hay errores
        if (result.hasErrors()) {
            System.out.println("ha habido un error en la creacion");

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo " + err.getField() + " - " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }


        //Setear las condiciones y restricciones a verdadero
        usuario.setCondicionesRestricciones(true);

        //Se encripta la contraseña
        String password = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(password);

        //Se leen los respectivos roles para que sean guardados en el usuario.
        // Se traen de ANGULAR los string con id_rol
        List<Role> listaRol = new ArrayList<>();
        for (String obj : usuario.getRolesPrincipal()) {
            Role roleEncontrado = roleRepositoryObj.findById(obj);
            listaRol.add(roleEncontrado);
        }

        //Se cargan los roles en el usuario
        usuario.setRoles(listaRol);
        usuario.setIntentosBloqueo(3);

        System.out.println(usuario);

        try {
            //cambiar luego
            usuarioNuevo = usuarioServiceObj.save(usuario);
        } catch (DataAccessException e) {
            System.out.println("ha habido un error 2");
            response.put("mensaje", "Error al realizar la creacion de usuario en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El usuario ha sido creado con exito");
        response.put("usuario", usuarioNuevo);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }




    //Traer lista de usuarios
    @GetMapping("/listar-usuarios")
    public List<Usuario> listarUsuarios() {
        return usuarioServiceObj.findAll();
    }




    //Traer un solo usuario a traves del id
    @GetMapping("/obtener-usuario/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> obtenerUsuario(@PathVariable Long id) {

        //Se crean los obj de usuario y response
        Usuario usuario = null;
        Map<String, Object> response = new HashMap<>();

        //Busca el cliente por id en la BD
        try {
            usuario = usuarioServiceObj.findById(id);
        } catch (DataAccessException de) {
            response.put("mensaje", "Error al realizar la consulta en la Base de Datos");
            response.put("error", de.getMessage() + " : " + de.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //Si el usuario no aparece en la BD, envia response
        if (usuario == null) {
            response.put("mensaje", "El usuario no se encuentra en la Base de Datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        //Si el usuario se encuentra
        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }





    //Actualizar otro usuario desde Admin
    @PutMapping("/editar-otro-usuario/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> actualizarOtroUsuario(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id) {

        //Se localiza el usuario actual en la BD
        Usuario usuarioActual = usuarioServiceObj.findById(id);

        //Se crea un usuario actualizado vacio que luego se carga de datos y se envia a front(ANG)
        Usuario usuarioUpdated = null;

        //Respuesta
        Map<String, Object> response = new HashMap<>();

        //Si llegan a existir errores
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo " + err.getField() + " - " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }


        //Si el usuario que llega a existir no se encuentra en lA bd
        if (usuarioActual == null) {
            response.put("errors", "No se puede editar el cliente, no se encuentra en la BD");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }


        //Se pasan los datos
        try {
            usuarioActual.setNombre(usuario.getNombre());
            usuarioActual.setNit(usuario.getNit());
            usuarioActual.setUsername(usuario.getUsername());
            usuarioActual.setEmail(usuario.getEmail());
            usuarioActual.setPrimerLogin(usuario.isPrimerLogin());


            //Se leen los respectivos roles para que sean guardados en el usuario.
            // Se traen de ANG los string con id_rol
            if (usuario.getRolesPrincipal() != null) {
                List<Role> listaRol = new ArrayList<>();
                for (String obj : usuario.getRolesPrincipal()) {
                    Role roleEncontrado = roleRepositoryObj.findById(obj);
                    listaRol.add(roleEncontrado);
                }

                //Se cargan los roles en el usuario actual
                usuarioActual.setRoles(listaRol);
            }


            //Se guarda el usuario en la base de datos
            usuarioUpdated = usuarioServiceObj.save(usuarioActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Ha ocurrido un error para editar el usuario");
            response.put("error", e.getMessage() + " : " + e.getMostSpecificCause());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //Si no hay errores
        response.put("mensaje", "Se ha editado correctamente al usuario");
        response.put("usuario", usuarioUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }






    //Modifica la contraseña del usuario
    @PutMapping("/update-pass-otro-usuario/{id}/{primerLogin}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> modificarPasswordOtroUsuario(@Valid @RequestBody String pass, BindingResult result,
                                                          @PathVariable Long id, @PathVariable boolean primerLogin) {

        System.out.println("nuevo pass: " + pass);

        //Localiza al usuario
        Usuario usuario = usuarioServiceObj.findById(id);

        Usuario usuarioUpdated = null;

        Map<String, Object> response = new HashMap<>();


        // Si existen errores
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }


        //Si el usuario que se quiere editar no existe en la BD
        if (usuario == null) {
            response.put("mensaje", "Error: No se puede editar");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }


        try {
            //Se encripta el nuevo password
            String nuevaPasswordEnc = passwordEncoder.encode(pass.trim());
            usuario.setPassword(nuevaPasswordEnc);
            usuario.setPrimerLogin(primerLogin);
            usuarioUpdated = usuarioServiceObj.save(usuario);

        } catch (DataAccessException de) {
            response.put("mensaje", "Error al actualizar la contraseña en la Base de Datos");
            response.put("error", "Error critico de almacenamiento");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //Si se crea correctamente el usuario envia mensaje satisfactorio.
        response.put("mensaje", "La contraseña ha sido actualizada con exito");
        response.put("usuario", usuarioUpdated);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }




    //Modifica la contraseña del usuario
    @PutMapping("/reestablecer-password/{token}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> reestablecerPassword(@Valid @RequestBody String pass, BindingResult result,@PathVariable String token) {

        System.out.println("nuevo pass: " + pass);

        //Localiza al usuario
        Usuario usuario = usuarioServiceObj.findByToken(token);

        Usuario usuarioUpdated = null;

        Map<String, Object> response = new HashMap<>();


        // Si existen errores
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }


        //Si el usuario que se quiere editar no existe en la BD
        if (usuario == null) {
            response.put("mensaje", "Error: No se puede actualizar la contraseña");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }


        try {
            //Se encripta el nuevo password BCRYPT
            String nuevaPasswordEnc = passwordEncoder.encode(pass.trim());
            usuario.setPassword(nuevaPasswordEnc);

            //JSF NO FUNCIONAL
/*            try{
                String contrasena = DigestUtil.generateDigest(pass);
                usuario.setPassword(contrasena);
            } catch (NoSuchAlgorithmException  | UnsupportedEncodingException  ex) {
                System.out.println("error al enc");
            }*/

            usuario.setToken(null);
            usuario.setEstadoToken(null);
            usuario.setHoraToken(null);
            usuario.setIntentosBloqueo(3);

            //Se guarda el usuario en la base de datos
            usuarioUpdated = usuarioServiceObj.save(usuario);

        } catch (DataAccessException de) {
            response.put("mensaje", "Error al actualizar la contraseña en la Base de Datos");
            response.put("error", "Error critico de almacenamiento");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //Si se crea correctamente el usuario envia mensaje satisfactorio.
        response.put("mensaje", "La contraseña ha sido actualizada con exito");
        response.put("usuario", usuarioUpdated);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }




    //Traer un solo usuario a traves del nit y el email
    @GetMapping("/generar-token-proveedor/{email}/{nit}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> obtenerUsuarioProveedorByEmailAndNit(@PathVariable String email, @PathVariable String nit) {
        //Se crean los obj de usuario y response
        Usuario usuario = null;
        Map<String, Object> response = new HashMap<>();
        List<Usuario> listaUsuarioEncontrado = new ArrayList<>();

        //Busca el cliente por id en la BD
        try {
            listaUsuarioEncontrado = usuarioServiceObj.findAllByEmailAndNit(email, nit);
            System.out.println(listaUsuarioEncontrado.size());
            usuario = listaUsuarioEncontrado.get(0);

            //Si el usuario no aparece en la BD, envia response
            if (usuario == null) {
                response.put("mensaje", "El usuario no se encuentra en la Base de Datos");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
            else{
                //Si el usuario se encuentra se genera el respectivo token
                String token = UUID.randomUUID().toString().toUpperCase();
                usuario.setToken(token);
                usuario.setHoraToken(new Date());
                usuario.setEstadoToken(true);

                //Se guarda el usuario en la BD
                usuarioServiceObj.save(usuario);


                //Se envia correo electronico al usuario
                usuarioServiceObj.sendMailRecuperarContrasena(usuario);
            }

        } catch (DataAccessException | FileNotFoundException de) {
            response.put("mensaje", "Error al realizar la consulta en la Base de Datos");
            response.put("error", de.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //Si el usuario se encuentra
        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }



    //Traer un solo usuario a traves del nit y el email
    @GetMapping("/generar-token-cliente/{email}/{nit}/{sucursal}/{puntoEnvio}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> obtenerUsuarioClienteByEmailAndNitAndSucursalAndPuntoEnvio(@PathVariable String email, @PathVariable String nit,
                                                                  @PathVariable String sucursal, @PathVariable String puntoEnvio) {
        //Se crean los obj de usuario y response
        Usuario usuario = null;
        Map<String, Object> response = new HashMap<>();

        //Busca el cliente por id en la BD
        try {
            usuario = usuarioServiceObj.findByCorreoRecuperarContra(email, nit, sucursal, puntoEnvio);

            //Si el usuario no aparece en la BD, envia response
            if (usuario == null) {
                response.put("mensaje", "El usuario no se encuentra en la Base de Datos");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
            else{
                //Si el usuario se encuentra se genera el respectivo token
                String token = UUID.randomUUID().toString().toUpperCase();
                usuario.setToken(token);
                usuario.setHoraToken(new Date());
                usuario.setEstadoToken(true);

                //Se guarda el usuario en la BD
                usuarioServiceObj.save(usuario);


                //Se envia correo electronico al usuario
                usuarioServiceObj.sendMailRecuperarContrasena(usuario);
            }

        } catch (DataAccessException | FileNotFoundException de) {
            response.put("mensaje", "Error al realizar la consulta en la Base de Datos");
            response.put("error", de.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //Si el usuario se encuentra
        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }












    //Realiza el procesamiento del token
    @GetMapping("/procesar-token/{token}")
    public List<String> procesarToken(@PathVariable String token){
        System.out.println("este es el token que se recibe: " + token);

        //Lista de resultados
        List<String> resultados = new ArrayList<>();

        //Se busca el usuario a partir del token
        Usuario usuario = usuarioServiceObj.findByToken(token);

        if (usuario == null){
            resultados.add("El token no coincide o tiene algun error");
            return resultados;
        }
        else{
            Date horaActual = new Date();
            int horaToken = Integer.parseInt(String.valueOf(usuario.getHoraToken().getHours())+String.valueOf(usuario.getHoraToken().getMinutes()));
            int hora = Integer.parseInt(String.valueOf(horaActual.getHours())+horaActual.getMinutes());
            int diferencia = hora - horaToken;

            if(usuario.getEstadoToken() && diferencia <= 60) {
                //token = true;
                System.out.println("usuario actual: " + usuario.getEmail());
                resultados.add("correcto para cambio");
            }
            else {
                resultados.add("no se puede cambiar la contraseña");
                return resultados;
            }
        }
        return resultados;
    }



    //Revisa si la contraseña es igual al nombre del usuario para saber si el usuario es nuevo
    @GetMapping("/nit-password-usuario-nuevo/{nit}/{password}/{username}")
    public List<String> nitPasswordUsuarioNuevo(@PathVariable String nit, @PathVariable String password, @PathVariable String username){
        List<String> resultado = new ArrayList<>();
        String razonSocial = usuarioServiceObj.obtenerRazonSocialUsuarioNuevo(nit, password);
        Usuario nitConsulta = usuarioServiceObj.findByValidarUsername(username);

        System.out.println(nitConsulta);
        System.out.println(razonSocial);

        try{
            resultado.add(razonSocial);
            resultado.add(nitConsulta.getUsername());
        }catch (Exception e) {
            resultado.add("error de datos");
            return resultado;
        }
        return resultado;
    }






}
