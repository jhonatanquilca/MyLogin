package login.mylogin.models;

/**
 * Created by Jhonatan Quilca on 22/10/2015.
 */
public class MovilUser {
    private String id_dispositivo;//    `id_dispositivo` varchar(45) NOT NULL,
    private String id_user;//    `id_user` int(11) DEFAULT NULL,
    private String estado;//    `estado` enum('IN','OUT') NOT NULL DEFAULT 'OUT',

    public MovilUser(String id_dispositivo, String id_user, String estado) {
        this.id_dispositivo = id_dispositivo;
        this.id_user = id_user;
        this.estado = estado;
    }

    public String getId_dispositivo() {
        return id_dispositivo;
    }

    public void setId_dispositivo(String id_dispositivo) {
        this.id_dispositivo = id_dispositivo;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    /**
     * Compara los atributos de dos Usuario
     *
     * @param usuario Cliente externa
     * @return true si son iguales, false si hay cambios
     */
    public boolean compararCon(MovilUser usuario) {

        return this.id_dispositivo.compareTo(usuario.id_dispositivo) == 0 &&
                this.id_user.compareTo(usuario.id_user) == 0 &&
                this.estado.compareTo(usuario.estado) == 0;


    }
}
