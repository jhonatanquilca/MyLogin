package login.mylogin.tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import login.mylogin.web.VolleySingleton;

/**
 * Created by Jhonatan Quilca on 18/10/2015.
 */
public class JsonParser {


    private static JSONObject generalJson;
    Context context = null;


    ProgressDialog pDialog = null;


    public JsonParser(Context context) {
        this.context = context;
    }

    public static JSONObject getGeneralJson() {
        return generalJson;
    }

    public static void setGeneralJson(JSONObject generalJson) {
        JsonParser.generalJson = generalJson;
    }

    public JSONObject jsonFronUrl(String url, final ProgressDialog progresDialog) {
JSONObject jsonResult=null;

        VolleySingleton.getInstanceAsinc(context).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                url,
                                (String) null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                            Log.i("Json Parser", response.toString());
                                        jsonResult=response;
                                        Log.i("Json Parser", response.toString());
                                    }

                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        if (progresDialog != null) {
                                            pDialog.hide();
                                        }
                                        Toast.makeText(context, "Error de conexión ", Toast.LENGTH_LONG).show();
                                    }
                                }
                        )
                );
        Log.i("Json Parser", jsonResult != null ? jsonResult : "nose que pasa");
        return jsonResult
    }


}
