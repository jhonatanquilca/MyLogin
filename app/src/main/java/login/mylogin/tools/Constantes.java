package login.mylogin.tools;

/**
 * Created by Jhonatan Quilca
 */
public class Constantes {
    /**
     * Transición Home -> Detalle
     */
    public static final int CODIGO_DETALLE = 100;

    /**
     * Transición Detalle -> Actualización
     */
    public static final int CODIGO_ACTUALIZACION = 101;

    /**
     * variable de elecconde peticion http decide si se pa a poner en produccion o es un a prueba local
     */
    private static final Boolean onLine = true;
    /**
     * Puerto que utilizas para la conexión.
     * Dejalo en blanco si no has configurado esta carácteristica.
     */
    private static final String PUERTO_HOST = onLine ? "" : ":80";

    /**
     * Dirección IP de genymotion o AVD
     */
    private static final String IP = onLine ? "http://www.drivehouse.bugs3.com" : "http://192.168.1.61";
    private static final String project = onLine ? "" : "/driveworkhouse";
    /**
     * URLs del Web Service
     */
    public static final String ADMIN = IP + PUERTO_HOST + project + "/cliente/clienteWs/admin";
    public static final String SEARCH = IP + PUERTO_HOST + project + "/cliente/clienteWs/search/param/";
    public static final String VIEW = IP + PUERTO_HOST + project + "/cliente/clienteWs/view/id/";
    public static final String UPDATE = IP + PUERTO_HOST + project + "/cliente/clienteWs/update/id/";
    public static final String DELETE = IP + PUERTO_HOST + project + "/cliente/clienteWs/delete/id/";
    public static final String INSERT = IP + PUERTO_HOST + project + "/cliente/clienteWs/create";

    /**
     * Clave para el valor extra que representa al identificador de un Cliente
     */
    public static final String EXTRA_ID = "IDEXTRA";
}
