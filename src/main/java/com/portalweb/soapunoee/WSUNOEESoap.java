/**
 * WSUNOEESoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.portalweb.soapunoee;

public interface WSUNOEESoap extends java.rmi.Remote {
    public boolean crearConexionXML(String pvstrxmlConexion) throws java.rmi.RemoteException;
    public EjecutarConsultaXMLResponseEjecutarConsultaXMLResult ejecutarConsultaXML(String pvstrxmlParametros) throws java.rmi.RemoteException;
    public String leerEsquemaParametros(String pvstrxmlParametros) throws java.rmi.RemoteException;
    public ImportarXMLResponseImportarXMLResult importarXML(String pvstrDatos, javax.xml.rpc.holders.ShortHolder printTipoError) throws java.rmi.RemoteException;
    public void inicializarVariablesImportacion() throws java.rmi.RemoteException;
    public short siesaWEBContabilizar(String pvstrParametros) throws java.rmi.RemoteException;
}
