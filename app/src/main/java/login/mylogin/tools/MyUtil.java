package login.mylogin.tools;


import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Key;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Jhonatan Quilca
 */
public class MyUtil {
    private static final String ALGORITMO = "AES";
    //Hay que respetar que la longitud de la clave tenga 16 digitos.
    private static final byte[] valor_clave = "VALORDETUCLAVE00".getBytes();
// key 16 bytes lenght (what you want and of course, valid characters)


    public static Map<String, String> toMap(JSONObject object) throws JSONException {
        Map<String, String> map = new HashMap();
        Iterator keys = object.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            map.put(key, object.getString(key));
        }
        return map;
    }

    public static String obtenerError(Map<String, String> maping, String key) {
        String error = maping.get(key).replace("\"", "").replace("[", "").replace("]", "").replace(".", "");
        String[] errors = error.split(",");
//        Log.i("Error json 0",errors[0]);
        return errors[0];
    }

    public static String primeraMayuscula(String string) {
        string = string.toLowerCase();
        String primeraLetra = string.charAt(0) + "";
        String resto = string.substring(1, string.length() - 1);
        return primeraLetra + resto;
    }

    public static String encriptar(String texto_a_encriptar) throws Exception {

        Key key = new SecretKeySpec(valor_clave, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encrypted = cipher.doFinal(texto_a_encriptar.getBytes("UTF-8"));
        String texto_encriptado = Base64.encodeToString(encrypted, Base64.DEFAULT);//new String(encrypted, "UTF-8");

        return texto_encriptado;


    }

    public static String desencriptar(String texto_encriptado) throws Exception {

        Key key = new SecretKeySpec(valor_clave, ALGORITMO);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decodificar_texto = Base64.decode(texto_encriptado.getBytes("UTF-8"), Base64.DEFAULT);
        byte[] desencriptado = cipher.doFinal(decodificar_texto);

        return new String(desencriptado, "UTF-8");
    }


}