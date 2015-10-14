package login.mylogin.models;

/**
 * Created by Jhonatan Quilca
 */
public class User {
    /**
     * atributos
     */
    private String iduser;//    `iduser` int(11) NOT NULL AUTO_INCREMENT,
    private String regdate;//    `regdate` bigint(30) DEFAULT NULL,
    private String actdate;//    `actdate` bigint(30) DEFAULT NULL,
    private String logondate;//    `logondate` bigint(30) DEFAULT NULL,
    private String username;//    `username` varchar(64) DEFAULT NULL,
    private String email;//    `email` varchar(45) DEFAULT NULL,
    private String password;//    `password` varchar(64) DEFAULT NULL COMMENT 'Hashed password',
    private String authkey;//    `authkey` varchar(100) DEFAULT NULL COMMENT 'llave de autentificacion',
    private String state;//    `state` int(11) DEFAULT '0',
    private String totalsessioncounter;//    `totalsessioncounter` int(11) DEFAULT '0',
    private String currentsessioncounter;//    `currentsessioncounter` int(11) DEFAULT '0',

    public User(String iduser, String regdate, String actdate, String logondate, String username, String email, String password, String authkey, String state, String totalsessioncounter, String currentsessioncounter) {
        this.iduser = iduser;
        this.regdate = regdate;
        this.actdate = actdate;
        this.logondate = logondate;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authkey = authkey;
        this.state = state;
        this.totalsessioncounter = totalsessioncounter;
        this.currentsessioncounter = currentsessioncounter;
    }

    public String getIduser() {
        return iduser;
    }

    public String getRegdate() {
        return regdate;
    }

    public String getActdate() {
        return actdate;
    }

    public String getLogondate() {
        return logondate;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthkey() {
        return authkey;
    }

    public String getState() {
        return state;
    }

    public String getTotalsessioncounter() {
        return totalsessioncounter;
    }

    public String getCurrentsessioncounter() {
        return currentsessioncounter;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public void setActdate(String actdate) {
        this.actdate = actdate;
    }

    public void setLogondate(String logondate) {
        this.logondate = logondate;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthkey(String authkey) {
        this.authkey = authkey;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setTotalsessioncounter(String totalsessioncounter) {
        this.totalsessioncounter = totalsessioncounter;
    }

    public void setCurrentsessioncounter(String currentsessioncounter) {
        this.currentsessioncounter = currentsessioncounter;
    }

    /**
     * Compara los atributos de dos Usuario
     *
     * @param usuario Cliente externa
     * @return true si son iguales, false si hay cambios
     */
    public boolean compararCon(User usuario) {

        return this.iduser.compareTo(usuario.iduser) == 0 &&
                this.regdate.compareTo(usuario.regdate) == 0 &&
                this.actdate.compareTo(usuario.actdate) == 0 &&
                this.logondate.compareTo(usuario.logondate) == 0 &&
                this.username.compareTo(usuario.username) == 0 &&
                this.email.compareTo(usuario.email) == 0 &&
                this.password.compareTo(usuario.password) == 0 &&
                this.authkey.compareTo(usuario.authkey) == 0 &&
                this.state.compareTo(usuario.state) == 0 &&
                this.totalsessioncounter.compareTo(usuario.totalsessioncounter) == 0 &&
                this.currentsessioncounter.compareTo(usuario.currentsessioncounter) == 0;


    }
}

