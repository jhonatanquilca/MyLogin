package login.mylogin.tools;

/**
 * Created by Jhonatan Quilca
 */
public class Constantes {

    /**
     * variable de elecconde peticion http decide si se pa a poner en produccion o es un a prueba local
     */
    private static final Boolean onLine = false;
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
    public static final String LOGIN = IP + PUERTO_HOST + project + "/cruge/uiWs/login/";
    public static final String LOGOUT = IP + PUERTO_HOST + project + "/cruge/uiWs/logout";

    /**
     * Clave para el valor extra que representa al identificador de un Cliente
     */
    public static final String EXTRA_ID = "IDEXTRA";

    /**
     * metodo que forma la url a ser utilizada por volley
     *
     * @param user     String nombre o email de usuario
     * @param password String cpmtraseña del usuario
     */
    public static String makeLoginUrl(String user, String password) {
//        return LOGIN + "CrugeLogon%5busername%5d/" + user + "/CrugeLogon%5bpassword%5b/" + password + "/CrugeLogon%5brememberMe%5d/0";
        return LOGIN + "CrugeLogon[username]/admin@tucorreo.com/CrugeLogon[password]/admin/CrugeLogon[rememberMe]/0";
    }


}
