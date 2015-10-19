package login.mylogin.ui.actividades;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import login.mylogin.R;
import login.mylogin.tools.Constantes;
import login.mylogin.tools.MyUtil;
import login.mylogin.web.VolleySingleton;

public class ActividadPrincipal extends Activity {
//

    /**
     * variables de controles visuales
     */
    private EditText input_user;
    private EditText input_password;
    private Button buttom_iniciar_sesion;

    private ProgressDialog pDialog;
    private VolleySingleton requestQueue;
    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_principal);

        String id = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
        Toast.makeText(getApplicationContext(), "ID DEL DISPOSITIVO:" + id, Toast.LENGTH_LONG).show();

        skipLogin(id);

        input_user = (EditText) findViewById(R.id.input_user);
        input_password = (EditText) findViewById(R.id.input_password);
        buttom_iniciar_sesion = (Button) findViewById(R.id.buttom_iniciar_sesion);


/**
 * acciones para controles visuales
 */
        TextWatcher tw = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String string1 = input_user.getText().toString();
                String string2 = input_password.getText().toString();
                buttom_iniciar_sesion.setEnabled((!string1.equals("") && !string2.equals("")) ? true : false);
            }
        };

        input_password.addTextChangedListener(tw);
        input_user.addTextChangedListener(tw);


        //action clic del boton iniciar sesion
        buttom_iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(input_password.getWindowToken(), 0);

                pDialog = new ProgressDialog(ActividadPrincipal.this);
                pDialog.setMessage("Iniciando...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();

                final String newUrl = Constantes.makeLoginUrl(input_user.getText().toString(), input_password.getText().toString());
                // Realizar petición de logeo
                VolleySingleton.getInstance(getApplicationContext()).
                        addToRequestQueue(
                                new JsonObjectRequest(
                                        Request.Method.GET,
                                        newUrl,
                                        (String) null,
                                        new Response.Listener<JSONObject>() {


                                            @Override
                                            public void onResponse(JSONObject response) {
                                                porcesarRespuesta(response);
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                pDialog.hide();
                                                Toast.makeText(getApplicationContext(), "Error de conexión ", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                )
                        );


            }
        });
    }

    private void skipLogin(String id_dispositivo) {
        final String newUrl = Constantes.MOVILE_USER_UPDATE + id_dispositivo;
        // Realizar petición de logeo
        VolleySingleton.getInstance(getApplicationContext()).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                newUrl,
                                (String) null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {

                                            //obtener la psocion success del json
                                            Boolean success = Boolean.valueOf(response.getString("success"));

                                            if (success) {

//                                                Intent i = new Intent(ActividadPrincipal.this, ActividadSecundaria.class);
//                                                finish();
//                                                startActivity(i);

                                            } else {


                                            }

                                        } catch (JSONException e) {
//                                                    Toast.makeText(ActividadPrincipal.this, "Error de Codificacion " + e.getMessage(), Toast.LENGTH_LONG).show();
                                            Toast.makeText(ActividadPrincipal.this, "Error de conexión" + e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        pDialog.hide();
                                        Toast.makeText(getApplicationContext(), "Error de conexión ", Toast.LENGTH_LONG).show();
                                    }
                                }
                        )
                );


    }


    public void porcesarRespuesta(JSONObject response) {
        try {

            //obtener la psocion success del json
            Boolean success = Boolean.valueOf(response.getString("success"));

            if (success) {
                pDialog.hide();
                /*paso a la sigiente penatalla*/
                Intent i = new Intent(ActividadPrincipal.this, ActividadSecundaria.class);
                finish();
                startActivity(i);

            } else {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ActividadPrincipal.this);
                AlertDialog alert11 = null;
                try {

                    Map<String, String> maping = new MyUtil().toMap(response.getJSONObject("errors"));

                    for (String key : maping.keySet()) {
                        EditText campo = null;
                        switch (key) {
                            case "username":
                                // campo = (EditText) findViewById(R.id.input_user);
                                //  campo.setHint(MyUtil.obtenerError(maping, key));
                                //  campo.setHintTextColor(getResources().getColor(R.color.red_300));
                                builder1.setMessage(MyUtil.obtenerError(maping, key));

                                builder1.setPositiveButton("Aceptar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });


                                alert11 = builder1.create();

                                alert11.show();

                                break;
                            case "password":
                                //campo = (EditText) findViewById(R.id.input_password);
                                //campo.setHint(MyUtil.obtenerError(maping, key));
                                //campo.setHintTextColor(getResources().getColor(R.color.red_300));
                                builder1.setMessage(MyUtil.obtenerError(maping, key));

                                builder1.setPositiveButton("Aceptar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });


                                alert11 = builder1.create();
                                alert11.show();

                                break;
                        }

                        break;
                    }


                } catch (JSONException e1) {
                    Toast.makeText(getApplicationContext(), "Erros: " + response.getString("errors"), Toast.LENGTH_LONG).show();
                }

                pDialog.hide();

            }

        } catch (JSONException e) {
//  Toast.makeText(ActividadPrincipal.this, "Error de Codificacion " + e.getMessage(), Toast.LENGTH_LONG).show();
            Toast.makeText(ActividadPrincipal.this, "Error de conexión" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
