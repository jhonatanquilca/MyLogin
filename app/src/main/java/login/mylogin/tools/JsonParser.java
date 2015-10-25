package login.mylogin.tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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


    /**
     * Returns a JSONObject of the response at the given URL with the given PARAMS and specified METHOD. PARAMS is a JSONObject containing the POST or PUT data.
     */
    public void getJsonObjectNoParametros(int method, String url) {
        final JSONObject[] jsonObject = new JSONObject[1];

        final String[] result = new String[1];
        JsonObjectRequest request = new JsonObjectRequest(
                method,
                url,
                (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.i("Queue", jsonObject.toString());

                        result[0] = jsonObject.toString();
                        Log.i("Quewe string", result[0]);
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Volley Error", volleyError.getMessage());
            }
        }
        ) {

            @Override
            protected Response<JSONObject> parseNetworkResponse(
                    NetworkResponse response) {
                try {
                    String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    result[0] = jsonString;
                    Log.i("Quewe string", result[0]);

                    return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }
        };

        VolleySingleton.getInstance(context).
                addToRequestQueue(
                        request
                );

        Log.i("Quewe string", result[0] == null ? "es null" : result[0]);
//        return result[0];
    }

    public String getJsonObjectParametross(Context context, int method, String url, JSONObject params) {


        final String[] result = new String[1];


        JsonObjectRequest request = new JsonObjectRequest(
                method,
                url,
                params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.i("Queue", jsonObject.toString());
                        Log.d("Queue", jsonObject.toString());

                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Volley Error", volleyError.getMessage());
            }
        }
        ) {

            @Override
            protected Response<JSONObject> parseNetworkResponse(
                    NetworkResponse response) {
                try {
                    String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    result[0] = jsonString;
                    return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }
        };

        VolleySingleton.getInstance(context).
                addToRequestQueue(
                        request
                );

        return result[0];
    }

    public JSONObject requestMoviesJSON(int metondo, RequestQueue requestQueue, String url) {
        JSONObject response = null;
        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(metondo, url, (String) null, requestFuture, requestFuture);
        requestQueue.add(request);
        try {
            response = requestFuture.get(30000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
//            Log.i(e + "");
        } catch (ExecutionException e) {
//            Log.i(e + "");
        } catch (TimeoutException e) {
//            Log.i(e + "");
        }
        return response;
    }

}
