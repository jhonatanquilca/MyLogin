package login.mylogin.tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Jhonatan Quilca
 */
public class MyUtil {
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
}