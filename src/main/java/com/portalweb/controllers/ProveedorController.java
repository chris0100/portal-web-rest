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
        resultado.add(rowIdTercero + "");
        return resultado;
    }


    //CAMBIAR------EN EL MOMENTO DEL DESPLIEGUE--->
    // RECORDAR CAMBIAR EL NIT Y EL ROW IDTERCERO
    // Crear el PDF despues de seleccionar PCI
    // "1000001792904285001"
    @GetMapping("/crear-pdf-iva/{mesInicio}/{mesFin}/{anioGrabable}/{rowIdTercero}")
    public ResponseEntity<?> crearPDFIva(@PathVariable String mesInicio, @PathVariable String mesFin,
                                         @PathVariable String anioGrabable,
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

        if (fin < inicio) {
            response.put("mensaje", "La fecha final no puede ser menor que la fecha de inicio");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_GATEWAY);
        } else if (Integer.parseInt(anioGrabable) > anioActual) {
            response.put("mensaje", "El documento no se encuentra disponible, el año es mayor al año actual");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_GATEWAY);
        } else if (fin > mesFinActual && Integer.parseInt(anioGrabable) == anioActual) {
            response.put("mensaje", "El documento no se encuentra disponible");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_GATEWAY);
        } else {
            // Se recarga la consulta IVA UNOEE
            listaCertificadoRetencionIVA = unoEEServiceObj.recargarConsultarIvaUnoEE(mesInicio, mesFin, anioGrabable, rowIdTercero);
            System.out.println(listaCertificadoRetencionIVA);

            if (listaCertificadoRetencionIVA.isEmpty()) {
                response.put("mensaje", "No se le aplico retención en el periodo seleccionado");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(listaCertificadoRetencionIVA, HttpStatus.OK);
            }
        }
    }


    //Generar pdf de retencion
    @GetMapping("/crear-pdf-retencion/{anioGrabable}/{rowIdTercero}/{ciudadConsignoParam}/{ciudadPracticoParam}/{tipoCertificado}")
    public ResponseEntity<?> crearPDFRetencion(@PathVariable String anioGrabable, @PathVariable int rowIdTercero,
                                               @PathVariable String ciudadConsignoParam, @PathVariable String ciudadPracticoParam,
                                               @PathVariable String tipoCertificado) throws SQLException {


        System.out.println(anioGrabable);

        Map<String, Object> response = new HashMap<>();

        List<CertificadoRetencionIVA> listaCertificadoRetencionIVA = new ArrayList<>();

        Calendar calAnioActual = Calendar.getInstance();
        int anioActual = calAnioActual.get(Calendar.YEAR);

        if (Integer.parseInt(anioGrabable.trim()) >= anioActual) {
            response.put("mensaje", "El documento no se encuentra disponible");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_GATEWAY);
        }

        //Recargar la consulta de la retencion
        listaCertificadoRetencionIVA = unoEEServiceObj.recargarConsultarRetencion(anioGrabable, rowIdTercero,
                ciudadConsignoParam, ciudadPracticoParam, tipoCertificado);

        if (listaCertificadoRetencionIVA.isEmpty()) {
            response.put("mensaje", "No se le aplico retención en el periodo seleccionado");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(listaCertificadoRetencionIVA, HttpStatus.OK);
        }
    }


}
