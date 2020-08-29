package com.portalweb.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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


    @GetMapping("/obtener-listado")
    public ResponseEntity<?> generar() throws SQLException {
        List<String> listado = new ArrayList<>();

        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=BD_PortalWeb_2;user=sa;password=3K4$.2015$#";

        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stm = null;
        String query = "SELECT [f201_id_sucursal]+' - '+[f201_descripcion_sucursal] " +
                "FROM [UnoEE].[dbo].[t201_mm_clientes] t201 " +
                "inner join [UnoEE].[dbo].[t200_mm_terceros] t200 on t200.f200_rowid = t201.f201_rowid_tercero " +
                "where t200.f200_nit = '890924167' and t200.f200_ind_estado = 1 ";

        try {
            stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                listado.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("ha ocurrido un error en la extraccion");
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
        return new ResponseEntity<>(listado, HttpStatus.OK);
    }

}
