//package com.example.proyecto.modal;
//
//import com.example.proyecto.util.CumplimentarPDFException;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class Anexo_Delegado_Prevencion {
//
//    private boolean casillaInscripcion;
//
//    // 1 Datos Persona Solicitante
//    private String apellidos;
//    private String nombre;
//    private char tipoDocumento;
//    private String DNI;
//    private char sexo;
//    private String esHombre, esMujer;
//    private String tipoVia;
//    private String nombreVia;
//    private short numero;
//    private char letra;
//    private double kilometro;
//    private String bloque;
//    private String portal;
//    private short escalera;
//    private short planta;
//    private String puerta;
//    private String localidad;
//    private String provincia;
//    private String CP;
//    private String pais;
//    private String telefono;
//    private String correoElectronico;
//
//    // 2 Autorizacion Expresa
//    private boolean casillaNotificacion;
//
//    // 3 Datos Centro Trabajo
//    private String nombreEmpresa;
//    private String CIF;
//    private String cuentaCotizacion;
//    private String tipoViaEmpresa;
//    private String nombreViaEmpresa;
//    private short numeroViaEmpresa;
//    private char letraViaEmpresa;
//    private double kmViaEmpresa;
//    private String bloqueViaEmpresa;
//    private String portalViaEmpresa;
//    private short escaleraViaEmpresa;
//    private short plantaViaEmpresa;
//    private String puertaViaEmpresa;
//    private String localidadEmpresa;
//    private String provinciaEmpresa;
//    private String CPostalEmpresa;
//    private String telefonoEmpresa;
//    private String avtividadEconomicaEmpresa;
//    private String CnaeEmpresa;
//    private int trabajadores;
//    private int trabajadoras;
//    private String convenioColectivo;
//    private boolean comiteSeguridad;
//
//    // 5 Otros Datos
//    private char tipoEmpresa;
//    private char gestionPrevencion;
//
//    // 6 Datos Elecciones
//    private String fechaEscrutinio;
//    private String numeroActaELectoral;
//
//    // 7 Datos Designacion Delegados/as
//    private boolean entreRepresentantes;
//    private String sindicato;
//    private String formacion;
//
//    // 9 Documentacion Adjunta
//    private char delegadosComite;
//
//    // 10 Solicitud
//    private String lugar;
//    private String dia;
//    private String mes;
//    private String anio;
//    private boolean delegadoPrevencion;
//    private String nombreCompleto;
//
//    public Anexo_Delegado_Prevencion(){
//    }
//    public Anexo_Delegado_Prevencion(boolean casillaInscripcion, String apellidos, String nombre, char casillaDocumento,
//                                     String DNI, char sexo, String provincia, String pais, boolean casillaNotificacion,
//                                     String nombreEmpresa, String CIF, String tipoViaEmpresa, String nombreViaEmpresa,
//                                     short numeroViaEmpresa, char letraViaEmpresa, double kmViaEmpresa, String bloqueViaEmpresa,
//                                     String portalViaEmpresa, short escaleraViaEmpresa, short plantaViaEmpresa, String puertaViaEmpresa,
//                                     String localidadEmpresa, String provinciaEmpresa, String CPostalEmpresa, String avtividadEconomicaEmpresa,
//                                     String CnaeEmpresa, int trabajadores, int trabajadoras, String convenioColectivo, boolean comiteSeguridad,
//                                     char tipoEmpresa, char gestionPrevencion, String fechaEscrutinio, boolean entreRepresentantes,
//                                     String sindicato, boolean delegadoPrevencion, String nombreCompleto) {
//        this.casillaInscripcion = casillaInscripcion;
//        this.apellidos = apellidos;
//        this.nombre = nombre;
//        this.tipoDocumento = casillaDocumento;
//        this.DNI = DNI;
//        this.sexo = sexo;
//        this.provincia = provincia;
//        this.pais = pais;
//        this.casillaNotificacion = casillaNotificacion;
//        this.nombreEmpresa = nombreEmpresa;
//        this.CIF = CIF;
//        this.tipoViaEmpresa = tipoViaEmpresa;
//        this.nombreViaEmpresa = nombreViaEmpresa;
//        this.numeroViaEmpresa = numeroViaEmpresa;
//        this.letraViaEmpresa = letraViaEmpresa;
//        this.kmViaEmpresa = kmViaEmpresa;
//        this.bloqueViaEmpresa = bloqueViaEmpresa;
//        this.portalViaEmpresa = portalViaEmpresa;
//        this.escaleraViaEmpresa = escaleraViaEmpresa;
//        this.plantaViaEmpresa = plantaViaEmpresa;
//        this.puertaViaEmpresa = puertaViaEmpresa;
//        this.localidadEmpresa = localidadEmpresa;
//        this.provinciaEmpresa = provinciaEmpresa;
//        this.CPostalEmpresa = CPostalEmpresa;
//        this.avtividadEconomicaEmpresa = avtividadEconomicaEmpresa;
//        this.CnaeEmpresa = CnaeEmpresa;
//        this.trabajadores = trabajadores;
//        this.trabajadoras = trabajadoras;
//        this.convenioColectivo = convenioColectivo;
//        this.comiteSeguridad = comiteSeguridad;
//        this.tipoEmpresa = tipoEmpresa;
//        this.gestionPrevencion = gestionPrevencion;
//        this.fechaEscrutinio = fechaEscrutinio;
//        this.entreRepresentantes = entreRepresentantes;
//        this.sindicato = sindicato;
//        this.delegadoPrevencion = delegadoPrevencion;
//        this.nombreCompleto = nombreCompleto;
//    }
//
//    public Anexo_Delegado_Prevencion(boolean casillaInscripcion, String apellidos, String nombre, char casillaDocumento,
//                                     String DNI, char sexo, String tipoVia, String nombreVia, short numero, char letra, double kilometro,
//                                     String bloque, String portal, short escalera, short planta, String puerta, String localidad,
//                                     String provincia, String CP, String pais, String telefono, String correoElectronico,
//                                     boolean casillaNotificacion, String nombreEmpresa, String CIF, String cuentaCotizacion,
//                                     String tipoViaEmpresa, String nombreViaEmpresa, short numeroViaEmpresa, char letraViaEmpresa,
//                                     double kmViaEmpresa, String bloqueViaEmpresa, String portalViaEmpresa, short escaleraViaEmpresa,
//                                     short plantaViaEmpresa, String puertaViaEmpresa, String localidadEmpresa, String provinciaEmpresa,
//                                     String CPostalEmpresa, String telefonoEmpresa, String avtividadEconomicaEmpresa, String CnaeEmpresa,
//                                     int trabajadores, int trabajadoras, String convenioColectivo, boolean comiteSeguridad,
//                                     char tipoEmpresa, char gestionPrevencion, String fechaEscrutinio, String numeroActaELectoral,
//                                     boolean entreRepresentantes, String sindicato, String formacion, char delegadosComite,
//                                     String lugar, String dia, String mes, String anio, boolean delegadoPrevencion, String nombreCompleto) {
//        this.casillaInscripcion = casillaInscripcion;
//        this.apellidos = apellidos;
//        this.nombre = nombre;
//        this.tipoDocumento = casillaDocumento;
//        this.DNI = DNI;
//        this.sexo = sexo;
//        this.tipoVia = tipoVia;
//        this.nombreVia = nombreVia;
//        this.numero = numero;
//        this.letra = letra;
//        this.kilometro = kilometro;
//        this.bloque = bloque;
//        this.portal = portal;
//        this.escalera = escalera;
//        this.planta = planta;
//        this.puerta = puerta;
//        this.localidad = localidad;
//        this.provincia = provincia;
//        this.CP = CP;
//        this.pais = pais;
//        this.telefono = telefono;
//        this.correoElectronico = correoElectronico;
//        this.casillaNotificacion = casillaNotificacion;
//        this.nombreEmpresa = nombreEmpresa;
//        this.CIF = CIF;
//        this.cuentaCotizacion = cuentaCotizacion;
//        this.tipoViaEmpresa = tipoViaEmpresa;
//        this.nombreViaEmpresa = nombreViaEmpresa;
//        this.numeroViaEmpresa = numeroViaEmpresa;
//        this.letraViaEmpresa = letraViaEmpresa;
//        this.kmViaEmpresa = kmViaEmpresa;
//        this.bloqueViaEmpresa = bloqueViaEmpresa;
//        this.portalViaEmpresa = portalViaEmpresa;
//        this.escaleraViaEmpresa = escaleraViaEmpresa;
//        this.plantaViaEmpresa = plantaViaEmpresa;
//        this.puertaViaEmpresa = puertaViaEmpresa;
//        this.localidadEmpresa = localidadEmpresa;
//        this.provinciaEmpresa = provinciaEmpresa;
//        this.CPostalEmpresa = CPostalEmpresa;
//        this.telefonoEmpresa = telefonoEmpresa;
//        this.avtividadEconomicaEmpresa = avtividadEconomicaEmpresa;
//        this.CnaeEmpresa = CnaeEmpresa;
//        this.trabajadores = trabajadores;
//        this.trabajadoras = trabajadoras;
//        this.convenioColectivo = convenioColectivo;
//        this.comiteSeguridad = comiteSeguridad;
//        this.tipoEmpresa = tipoEmpresa;
//        this.gestionPrevencion = gestionPrevencion;
//        this.fechaEscrutinio = fechaEscrutinio;
//        this.numeroActaELectoral = numeroActaELectoral;
//        this.entreRepresentantes = entreRepresentantes;
//        this.sindicato = sindicato;
//        this.formacion = formacion;
//        this.delegadosComite = delegadosComite;
//        this.lugar = lugar;
//        this.dia = dia;
//        this.mes = mes;
//        this.anio = anio;
//        this.delegadoPrevencion = delegadoPrevencion;
//        this.nombreCompleto = nombreCompleto;
//    }
//
//    public boolean isCasillaInscripcion() {
//        return casillaInscripcion;
//    }
//
//    public void setCasillaInscripcion(boolean casillaInscripcion) {
//        this.casillaInscripcion = true;
//    }
//
//    public String getApellidos() {
//        return apellidos;
//    }
//
//    public void setApellidos(String apellidos) {
//        this.apellidos = apellidos;
//    }
//
//    public String getNombre() {
//        return nombre;
//    }
//
//    public void setNombre(String nombre) {
//        this.nombre = nombre;
//    }
//
//    public char getTipoDocumento() {
//        return tipoDocumento;
//    }
//
//    public void setTipoDocumento(char tipoDocumento) {
//
//        if (tipoDocumento != '1' && tipoDocumento != '2' && tipoDocumento != '3'){
//            this.tipoDocumento = '1';
//        }
//        else {
//            this.tipoDocumento = tipoDocumento;
//        }
//    }
//
//    public String getDNI() {
//        return DNI;
//    }
//
//    public void setDNI(String DNI) throws CumplimentarPDFException {
//        ValidadorDNI validarDNI = new ValidadorDNI();
//
//        if (!validarDNI.esDNIValido(DNI)){
//            throw new CumplimentarPDFException("ERROR, DNI introducido incorrecto\n");
//        }
//        this.DNI = DNI;
//    }
//
//    public char getSexo() {
//        return sexo;
//    }
//
//    public void setSexo(String sexo) {
//
//        if (!sexo.equalsIgnoreCase("M")) {
//            this.sexo = 'H';
//            setEsHombre(sexo);
//        }
//        else {
//            this.sexo = 'M';
//            setEsMujer(sexo);
//        }
//    }
//
//    public String getEsHombre() {
//        return esHombre;
//    }
//
//    private void setEsHombre(String sexo) {
//
//        this.esHombre = "H";
//    }
//
//    public String getEsMujer() {
//        return esMujer = "M";
//    }
//
//    private void setEsMujer(String sexo) {
//
//    }
//    public String getTipoVia() {
//        return tipoVia;
//    }
//
//    public void setTipoVia(String tipoVia) {
//        this.tipoVia = tipoVia;
//    }
//
//    public String getNombreVia() {
//        return nombreVia;
//    }
//
//    public void setNombreVia(String nombreVia) {
//        this.nombreVia = nombreVia;
//    }
//
//    public short getNumero() {
//        return numero;
//    }
//
//    public void setNumero(short numero) {
//        this.numero = numero;
//    }
//
//    public char getLetra() {
//        return letra;
//    }
//
//    public void setLetra(char letra) {
//        this.letra = letra;
//    }
//
//    public double getKilometro() {
//        return kilometro;
//    }
//
//    public void setKilometro(double kilometro) {
//        this.kilometro = kilometro;
//    }
//
//    public String getBloque() {
//        return bloque;
//    }
//
//    public void setBloque(String bloque) {
//        this.bloque = bloque;
//    }
//
//    public String getPortal() {
//        return portal;
//    }
//
//    public void setPortal(String portal) {
//        this.portal = portal;
//    }
//
//    public short getEscalera() {
//        return escalera;
//    }
//
//    public void setEscalera(short escalera) {
//        this.escalera = escalera;
//    }
//
//    public short getPlanta() {
//        return planta;
//    }
//
//    public void setPlanta(short planta) {
//        this.planta = planta;
//    }
//
//    public String getPuerta() {
//        return puerta;
//    }
//
//    public void setPuerta(String puerta) {
//        this.puerta = puerta;
//    }
//
//    public String getLocalidad() {
//        return localidad;
//    }
//
//    public void setLocalidad(String localidad) {
//        this.localidad = localidad;
//    }
//
//    public String getProvincia() {
//        return provincia;
//    }
//
//    public void setProvincia(String provincia) {
//        this.provincia = provincia;
//    }
//
//    public String getCP() {
//        return CP;
//    }
//
//    public void setCP(String CP) throws CumplimentarPDFException {
//
//        if (CP.length() != Constantes.DIGITOS_CODIGO_POSTAL) {
//            throw new CumplimentarPDFException("ERROR, Código postal introducido incorrecto\n");
//        }
//        this.CP = CP;
//    }
//
//    public String getPais() {
//        return pais;
//    }
//
//    public void setPais(String pais) {
//        this.pais = pais;
//    }
//
//    public String getTelefono() {
//        return telefono;
//    }
//
//    public void setTelefono(String telefono) throws CumplimentarPDFException {
//
//        // Verifica si el número de teléfono tiene exactamente 9 dígitos
//        if (!telefono.matches("\\d{9}")){
//            throw new CumplimentarPDFException("ERROR, teléfono introducido incorrecto\n");
//        }
//        this.telefono = telefono;
//    }
//
//    public String getCorreoElectronico() {
//        return correoElectronico;
//    }
//
//    public void setCorreoElectronico(String correoElectronico) throws CumplimentarPDFException {
//
//        // Expresión regular para validar el formato básico de un correo electrónico
//        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
//
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(correoElectronico);
//
//        if (!matcher.matches()){
//            throw new CumplimentarPDFException("ERROR, correo electrónico introducido no válido\n");
//        }
//
//        this.correoElectronico = correoElectronico;
//    }
//
//    public boolean isCasillaNotificacion() {
//        return casillaNotificacion;
//    }
//
//    public void setCasillaNotificacion(boolean casillaNotificacion) {
//        this.casillaNotificacion = casillaNotificacion;
//    }
//
//    public String getNombreEmpresa() {
//        return nombreEmpresa;
//    }
//
//    public void setNombreEmpresa(String nombreEmpresa) {
//        this.nombreEmpresa = nombreEmpresa;
//    }
//
//    public String getCIF() {
//        return CIF;
//    }
//
//    public void setCIF(String CIF) throws CumplimentarPDFException {
//        ValidadorCIF validarCIF = new ValidadorCIF();
//
//        if (!validarCIF.validarCIF(CIF)){
//            throw new CumplimentarPDFException("ERROR, CIF introducido no válido\n");
//        }
//        this.CIF = CIF;
//    }
//
//    public String getCuentaCotizacion() {
//        return cuentaCotizacion;
//    }
//
//    public void setCuentaCotizacion(String cuentaCotizacion) {
//        this.cuentaCotizacion = cuentaCotizacion;
//    }
//
//    public String getTipoViaEmpresa() {
//        return tipoViaEmpresa;
//    }
//
//    public void setTipoViaEmpresa(String tipoViaEmpresa) {
//        this.tipoViaEmpresa = tipoViaEmpresa;
//    }
//
//    public String getNombreViaEmpresa() {
//        return nombreViaEmpresa;
//    }
//
//    public void setNombreViaEmpresa(String nombreViaEmpresa) {
//        this.nombreViaEmpresa = nombreViaEmpresa;
//    }
//
//    public short getNumeroViaEmpresa() {
//        return numeroViaEmpresa;
//    }
//
//    public void setNumeroViaEmpresa(short numeroViaEmpresa) {
//        this.numeroViaEmpresa = numeroViaEmpresa;
//    }
//
//    public char getLetraViaEmpresa() {
//        return letraViaEmpresa;
//    }
//
//    public void setLetraViaEmpresa(char letraViaEmpresa) {
//        this.letraViaEmpresa = letraViaEmpresa;
//    }
//
//    public double getKmViaEmpresa() {
//        return kmViaEmpresa;
//    }
//
//    public void setKmViaEmpresa(double kmViaEmpresa) {
//        this.kmViaEmpresa = kmViaEmpresa;
//    }
//
//    public String getBloqueViaEmpresa() {
//        return bloqueViaEmpresa;
//    }
//
//    public void setBloqueViaEmpresa(String bloqueViaEmpresa) {
//        this.bloqueViaEmpresa = bloqueViaEmpresa;
//    }
//
//    public String getPortalViaEmpresa() {
//        return portalViaEmpresa;
//    }
//
//    public void setPortalViaEmpresa(String portalViaEmpresa) {
//        this.portalViaEmpresa = portalViaEmpresa;
//    }
//
//    public short getEscaleraViaEmpresa() {
//        return escaleraViaEmpresa;
//    }
//
//    public void setEscaleraViaEmpresa(short escaleraViaEmpresa) {
//        this.escaleraViaEmpresa = escaleraViaEmpresa;
//    }
//
//    public short getPlantaViaEmpresa() {
//        return plantaViaEmpresa;
//    }
//
//    public void setPlantaViaEmpresa(short plantaViaEmpresa) {
//        this.plantaViaEmpresa = plantaViaEmpresa;
//    }
//
//    public String getPuertaViaEmpresa() {
//        return puertaViaEmpresa;
//    }
//
//    public void setPuertaViaEmpresa(String puertaViaEmpresa) {
//        this.puertaViaEmpresa = puertaViaEmpresa;
//    }
//
//    public String getLocalidadEmpresa() {
//        return localidadEmpresa;
//    }
//
//    public void setLocalidadEmpresa(String localidadEmpresa) {
//        this.localidadEmpresa = localidadEmpresa;
//    }
//
//    public String getProvinciaEmpresa() {
//        return provinciaEmpresa;
//    }
//
//    public void setProvinciaEmpresa(String provinciaEmpresa) {
//        this.provinciaEmpresa = provinciaEmpresa;
//    }
//
//    public String getCPostalEmpresa() {
//        return CPostalEmpresa;
//    }
//
//    public void setCPostalEmpresa(String CPostalEmpresa) throws CumplimentarPDFException {
//
//        if (CP.length() != Constantes.DIGITOS_CODIGO_POSTAL) {
//            throw new CumplimentarPDFException("ERROR, Código postal introducido incorrecto\n");
//        }
//        this.CPostalEmpresa = CPostalEmpresa;
//    }
//
//    public String getTelefonoEmpresa() {
//        return telefonoEmpresa;
//    }
//
//    public void setTelefonoEmpresa(String telefonoEmpresa) throws CumplimentarPDFException {
//        // Elimina cualquier espacio en blanco del número de teléfono
//        this.telefono = this.telefono.replaceAll("\\s", "");
//
//        // Verifica si el número de teléfono tiene exactamente 9 dígitos
//        if (!telefono.matches("\\d{9}")){
//            throw new CumplimentarPDFException("ERROR, teléfono introducido incorrecto\n");
//        }
//        this.telefonoEmpresa = telefonoEmpresa;
//    }
//
//    public String getAvtividadEconomicaEmpresa() {
//        return avtividadEconomicaEmpresa;
//    }
//
//    public void setAvtividadEconomicaEmpresa(String avtividadEconomicaEmpresa) {
//        this.avtividadEconomicaEmpresa = avtividadEconomicaEmpresa;
//    }
//
//    public String getCnaeEmpresa() {
//        return CnaeEmpresa;
//    }
//
//    public void setCnaeEmpresa(String cnaeEmpresa) {
//        CnaeEmpresa = cnaeEmpresa;
//    }
//
//    public int getTrabajadores() {
//        return trabajadores;
//    }
//
//    public void setTrabajadores(int trabajadores) {
//        this.trabajadores = trabajadores;
//    }
//
//    public int getTrabajadoras() {
//        return trabajadoras;
//    }
//
//    public void setTrabajadoras(int trabajadoras) {
//        this.trabajadoras = trabajadoras;
//    }
//
//    public String getConvenioColectivo() {
//        return convenioColectivo;
//    }
//
//    public void setConvenioColectivo(String convenioColectivo) throws CumplimentarPDFException {
//        String formatoConvenio = Constantes.FORMATO_CONVENIO;
//
//        // Compila la expresión regular
//        Pattern patron = Pattern.compile(formatoConvenio);
//
//        if (!patron.matcher(convenioColectivo).matches()){
//            throw new CumplimentarPDFException("ERROR, el número de convenio no es válido ya que no contiene 14 dígitos\n");
//        }
//        this.convenioColectivo = convenioColectivo;
//    }
//
//    public boolean isComiteSeguridad() {
//        return comiteSeguridad;
//    }
//
//    public void setComiteSeguridad(boolean comiteSeguridad) {
//        this.comiteSeguridad = comiteSeguridad;
//    }
//
//    public char getTipoEmpresa() {
//        return tipoEmpresa;
//    }
//
//    public void setTipoEmpresa(char tipoEmpresa) {
//        this.tipoEmpresa = tipoEmpresa;
//    }
//
//    public char getGestionPrevencion() {
//        return gestionPrevencion;
//    }
//
//    public void setGestionPrevencion(char gestionPrevencion) {
//        this.gestionPrevencion = gestionPrevencion;
//    }
//
//    public String getFechaEscrutinio() {
//        return fechaEscrutinio;
//    }
//
//    public void setFechaEscrutinio(String fechaEscrutinio) throws CumplimentarPDFException {
//        ValidadorFecha validadorFecha = new ValidadorFecha();
//
//        if (!validadorFecha.esFormatoFechaValido((fechaEscrutinio))){
//            throw new CumplimentarPDFException("ERROR, formato de fecha introducido no válido\n");
//        }
//        this.fechaEscrutinio = fechaEscrutinio;
//    }
//
//    public String getNumeroActaELectoral() {
//        return numeroActaELectoral;
//    }
//
//    public void setNumeroActaELectoral(String numeroActaELectoral) throws CumplimentarPDFException {
//
//        Pattern patron = Pattern.compile("^([0-9]{1,4})/([0-9]{4})$");
//        Matcher matcher = patron.matcher(numeroActaELectoral);
//
//        if (!matcher.matches()){
//            throw new CumplimentarPDFException("ERROR, El formato del preaviso no es válido. -->".concat( Constantes.FORMATO_PREAVISO.concat("\n")));
//        }
//        this.numeroActaELectoral = numeroActaELectoral;
//    }
//
//    public boolean isEntreRepresentantes() {
//        return entreRepresentantes;
//    }
//
//    public void setEntreRepresentantes(boolean entreRepresentantes) {
//        this.entreRepresentantes = entreRepresentantes;
//    }
//
//    public String getSindicato() {
//        return sindicato;
//    }
//
//    public void setSindicato(String sindicato) {
//        this.sindicato = sindicato;
//    }
//
//    public String getFormacion() {
//        return formacion;
//    }
//
//    public void setFormacion(String formacion) {
//        this.formacion = formacion;
//    }
//
//    public char getDelegadosComite() {
//        return delegadosComite;
//    }
//
//    public void setDelegadosComite(char delegadosComite) {
//        this.delegadosComite = delegadosComite;
//    }
//
//    public String getLugar() {
//        return lugar;
//    }
//
//    public void setLugar(String lugar) {
//        this.lugar = lugar;
//    }
//
//    public String getDia() {
//        return dia;
//    }
//
//    public void setDia(String dia) {
//        this.dia = validadorFecha.getDia();
//    }
//
//    public String getMes() {
//        return mes;
//    }
//
//    public void setMes(String mes) {
//        this.mes = fechaToLetras.convertirMesLetras(mes);
//    }
//
//    public String getAnio() {
//        return anio;
//    }
//
//    public void setAnio(String anio) {
//        this.anio = validadorFecha.getAnio();
//    }
//
//    public boolean isDelegadoPrevencion() {
//        return delegadoPrevencion;
//    }
//
//    public void setDelegadoPrevencion(boolean delegadoPrevencion) {
//        this.delegadoPrevencion = delegadoPrevencion;
//    }
//
//    public String getNombreCompleto() {
//        return nombreCompleto;
//    }
//
//    public void setNombreCompleto(String nombreCompleto) {
//        this.nombreCompleto = (nombre + " " + apellidos).toUpperCase();
//    }
//}
