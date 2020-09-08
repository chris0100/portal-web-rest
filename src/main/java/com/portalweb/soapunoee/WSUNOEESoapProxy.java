package com.portalweb.soapunoee;

public class WSUNOEESoapProxy implements WSUNOEESoap {
  private String _endpoint = null;
  private WSUNOEESoap wSUNOEESoap = null;
  
  public WSUNOEESoapProxy() {
    _initWSUNOEESoapProxy();
  }
  
  public WSUNOEESoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initWSUNOEESoapProxy();
  }
  
  private void _initWSUNOEESoapProxy() {
    try {
      wSUNOEESoap = (new WSUNOEELocator()).getWSUNOEESoap();
      if (wSUNOEESoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)wSUNOEESoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)wSUNOEESoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (wSUNOEESoap != null)
      ((javax.xml.rpc.Stub)wSUNOEESoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public WSUNOEESoap getWSUNOEESoap() {
    if (wSUNOEESoap == null)
      _initWSUNOEESoapProxy();
    return wSUNOEESoap;
  }
  
  public boolean crearConexionXML(String pvstrxmlConexion) throws java.rmi.RemoteException{
    if (wSUNOEESoap == null)
      _initWSUNOEESoapProxy();
    return wSUNOEESoap.crearConexionXML(pvstrxmlConexion);
  }
  
  public EjecutarConsultaXMLResponseEjecutarConsultaXMLResult ejecutarConsultaXML(String pvstrxmlParametros) throws java.rmi.RemoteException{
    if (wSUNOEESoap == null)
      _initWSUNOEESoapProxy();
    return wSUNOEESoap.ejecutarConsultaXML(pvstrxmlParametros);
  }
  
  public String leerEsquemaParametros(String pvstrxmlParametros) throws java.rmi.RemoteException{
    if (wSUNOEESoap == null)
      _initWSUNOEESoapProxy();
    return wSUNOEESoap.leerEsquemaParametros(pvstrxmlParametros);
  }
  
  public ImportarXMLResponseImportarXMLResult importarXML(String pvstrDatos, javax.xml.rpc.holders.ShortHolder printTipoError) throws java.rmi.RemoteException{
    if (wSUNOEESoap == null)
      _initWSUNOEESoapProxy();
    return wSUNOEESoap.importarXML(pvstrDatos, printTipoError);
  }
  
  public void inicializarVariablesImportacion() throws java.rmi.RemoteException{
    if (wSUNOEESoap == null)
      _initWSUNOEESoapProxy();
    wSUNOEESoap.inicializarVariablesImportacion();
  }
  
  public short siesaWEBContabilizar(String pvstrParametros) throws java.rmi.RemoteException{
    if (wSUNOEESoap == null)
      _initWSUNOEESoapProxy();
    return wSUNOEESoap.siesaWEBContabilizar(pvstrParametros);
  }
  
  
}
