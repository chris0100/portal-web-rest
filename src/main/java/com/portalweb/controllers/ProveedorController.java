package com.portalweb.controllers;
import com.portalweb.models.CertificadoRetencionIVA;
import com.portalweb.services.UnoEEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ProveedorController {

    @Autowired
    private UnoEEService unoEEServiceObj;


    // Obtener el row id del tercero
    @GetMapping("/obtener-row-id-tercero/{nit}")
    public List<String> obtenerRowIdTercero(@PathVariable String nit) throws SQLException {
        List<String> resultado = new ArrayList<>();

        int rowIdTercero = unoEEServiceObj.obtenerRowIdTercero(nit);
        System.out.println("el rowIdTercero es: " + rowIdTercero);
        resultado.add(rowIdTercero+"");
        return resultado;
    }




    // RECORDAR CAMBIAR EL NIT Y EL ROW IDTERCERO
    // Crear el PDF despues de seleccionar PCI
    @GetMapping("/crear-pdf-iva/{mesInicio}/{mesFin}/{anioGrabable}/{nit}/{rowIdTercero}")
    public ResponseEntity<?> crearPDFIva(@PathVariable String mesInicio, @PathVariable String mesFin,
                                      @PathVariable String anioGrabable, @PathVariable String nit,
                                      @PathVariable int rowIdTercero) throws SQLException {

        Map<String, Object> response = new HashMap<>();

        List<CertificadoRetencionIVA> listaCertificadoRetencionIVA = new ArrayList<>();

        int inicio = Integer.parseInt(mesInicio);
        int fin = Integer.parseInt(mesFin);
        System.out.println(inicio + " : " + fin);

        Calendar calMesActual = Calendar.getInstance();
        int mesFinActual = calMesActual.get(Calendar.MONTH);
        Calendar calAnioActual = Calendar.getInstance();
        int anioActual = calAnioActual.get(Calendar.YEAR);

        if(fin < inicio){
            response.put("mensaje", "La fecha final no puede ser menor que la fecha de inicio");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_GATEWAY);
        }
        else if(Integer.parseInt(anioGrabable) > anioActual){
            response.put("mensaje", "El documento no se encuentra disponible, el año es mayor al año actual");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_GATEWAY);
        }
        else if(fin > mesFinActual && Integer.parseInt(anioGrabable) == anioActual){
            response.put("mensaje", "El documento no se encuentra disponible");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_GATEWAY);
        }
        else{
            // Se recarga la consulta IVA UNOEE
            listaCertificadoRetencionIVA = unoEEServiceObj.recargarConsultarIvaUnoEE(mesInicio,mesFin,anioGrabable,"1000001792904285001",0);
            System.out.println(listaCertificadoRetencionIVA);

            if(listaCertificadoRetencionIVA.isEmpty()){
                response.put("mensaje", "El documento no se encuentra disponible");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
            }
            else {
                return new ResponseEntity<>(listaCertificadoRetencionIVA, HttpStatus.OK);
            }
        }
    }

}
