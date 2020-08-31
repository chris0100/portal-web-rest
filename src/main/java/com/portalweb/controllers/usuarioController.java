package com.portalweb.controllers;

import com.portalweb.models.Usuario;
import com.portalweb.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class usuarioController {

    @Autowired
    private UsuarioService usuarioServiceObj;

    String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=BD_PortalWeb_2;user=sa;password=3K4$.2015$#";


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


    @GetMapping("/validar-entidad-dinamica/{nitCliente}/{sucursal}")
    public ResponseEntity<?> validarEntidadDinamica(@PathVariable String nitCliente, @PathVariable String sucursal) throws SQLException {

        List<String> repuestaHabilitada = new ArrayList<>();

        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stm = null;
        String query = "SELECT c.f753_dato_texto \n" +
                "  FROM [UnoEE].[dbo].[t201_mm_clientes] b \n" +
                "  inner join [UnoEE].[dbo].t200_mm_terceros x  on x.f200_rowid = b.f201_rowid_tercero \n" +
                "  inner join  [UnoEE].[dbo].t753_mm_movto_entidad_columna c on c.f753_rowid_movto_entidad=b.f201_rowid_movto_entidad \n" +
                "  inner join [UnoEE].[dbo].t743_mm_entidad_atributo d on c.f753_rowid_entidad_atributo=d.f743_rowid \n" +
                "  inner join [UnoEE].[dbo].t742_mm_entidad e on d.f743_rowid_entidad=e.f742_rowid \n" +
                "  where  e.f742_etiqueta='HABILITAR PORTAL WEB' and d.f743_id='HABILITAR PORTAL WEB' and x.f200_nit = '" + nitCliente + "'\n" +
                "  and b.f201_id_sucursal =" + sucursal + "order by c.f753_fecha_ts_actualizacion desc";

        try {
            stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                repuestaHabilitada.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("ha ocurrido un error en la extraccion");
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
        return new ResponseEntity<>(repuestaHabilitada, HttpStatus.OK);
    }




    //Actualizar datos de configuracion del usuario logueado
    @PutMapping("/update-cliente/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> update(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id) {

        Usuario usuarioActual = usuarioServiceObj.findById(id);

        Usuario usuarioUpdated = null;

        Map<String, Object> response = new HashMap<>();

        // Si existen errores
        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        //Si el usuario que se quiere editar no existe en la BD
        if (usuarioActual == null){
            response.put("mensaje", "Error: No se puede editar");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }


        //Se pasan los dos datos que se quieren editar
        try{
            usuarioActual.setNombre(usuario.getNombre());
            usuarioActual.setEmail(usuario.getEmail());

            usuarioUpdated = usuarioServiceObj.save(usuarioActual);
        }
        catch(DataAccessException de){
            response.put("mensaje", "Error al actualizar el usuario en la Base de Datos");
            response.put("error", "Error critico de almacenamiento");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //Si se crea correctamente el usuario envia mensaje satisfactorio.
        response.put("mensaje", "El usuario ha sido actualizado con exito");
        response.put("usuario", usuarioUpdated);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }





}
