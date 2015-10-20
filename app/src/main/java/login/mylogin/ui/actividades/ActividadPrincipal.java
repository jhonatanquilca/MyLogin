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

import java.util.HashMap;
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
    String usuarioLogeo;


    String idDispositivo;

    private ProgressDialog pDialog;
    private VolleySingleton requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_principal);


        idDispositivo = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);

        skipLogin(idDispositivo);

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
                                        Request.Method.POST,
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
        final String finalId_dispositivo = id_dispositivo;
        VolleySingleton.getInstance(getApplicationContext()).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                newUrl,
                                (String) null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {

                                            //obtener la psocion success del json
                                            Boolean success = Boolean.valueOf(response.getString("success"));

                                            if (success) {

//                                                String id_usuario = response.getString("id_user");
                                                String id_usuario = ((response.has("id_user") && !response.isNull("id_user"))) ? response.getString("id_user") : "";

                                                if (id_usuario.equals("") || id_usuario.equals("-")) {
                                                    Toast.makeText(getApplicationContext(), "Usuario:" + id_usuario, Toast.LENGTH_LONG).show();

                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Logeado:" + id_usuario, Toast.LENGTH_LONG).show();

                                                    Intent i = new Intent(ActividadPrincipal.this, ActividadSecundaria.class);
                                                    finish();
                                                    startActivity(i);
                                                }


                                            } else {
//                                                crear Movil user si no existe
                                                crearMovileUser(finalId_dispositivo);
                                            }

                                        } catch (JSONException e) {
                                            Toast.makeText(ActividadPrincipal.this, "Error de conexión on consulta de usuario json" + e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }


                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), "Error de conexión on consulta de usuario peticion ", Toast.LENGTH_LONG).show();
                                    }
                                }
                        )
                );


    }

    private void crearMovileUser(String id_dispositivo) {

        HashMap<String, String> map = new HashMap<>();// Mapeo previo pra convertir a jason
        map.put("id_dispositivo", id_dispositivo);


        // Crear nuevo objeto Json basado en el mapa
        JSONObject jsonMovilUser = new JSONObject(map);

        String newUrl = Constantes.MOVILE_USER_CREATE;
        VolleySingleton.getInstance(getApplicationContext()).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                newUrl,
                                jsonMovilUser,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {

                                            //obtener la psocion success del json
                                            Boolean success = Boolean.valueOf(response.getString("success"));

                                            if (success) {
                                                Toast.makeText(getApplicationContext(), "Exito al crear el usuario", Toast.LENGTH_SHORT).show();

                                            } else {
                                                Toast.makeText(getApplicationContext(), "Error de coneccion al crear el usuario", Toast.LENGTH_SHORT).show();

                                            }

                                        } catch (JSONException e) {
                                            Toast.makeText(ActividadPrincipal.this, "Error de conexión de creacion on json" + e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), "Error de conexión creacion on peticion ", Toast.LENGTH_LONG).show();
                                    }
                                }
                        )
                );
    }

    private void actualizarMovileUser(String id_dispositivo, String id_usuario_logeo) {


        HashMap<String, String> map = new HashMap<>();// Mapeo previo pra convertir a jason
        map.put("id_dispositivo", id_dispositivo);
        map.put("id_user", id_usuario_logeo);
        map.put("estado", "IN");

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jsonMovilUser = new JSONObject(map);

        String newUrl = Constantes.MOVILE_USER_UPDATE + id_dispositivo;

        VolleySingleton.getInstance(getApplicationContext()).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                newUrl,
                                jsonMovilUser,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {

                                            //obtener la psocion success del json
                                            Boolean success = Boolean.valueOf(response.getString("success"));

                                            if (success) {

                                                Toast.makeText(getApplicationContext(), "Exito al actualizar el usuario", Toast.LENGTH_SHORT).show();

                                            } else {
                                                Toast.makeText(getApplicationContext(), "Error de coneccion al actualizar el usuario", Toast.LENGTH_SHORT).show();

                                            }

                                        } catch (JSONException e) {
                                            Toast.makeText(ActividadPrincipal.this, "Error de conexión de actualizar on json" + e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), "Error de conexión actualizar on peticion ", Toast.LENGTH_LONG).show();
                                    }
                                }
                        )
                );
    }

    private void loadMovileUser(String id_dispositivo) {

        String newUrl = Constantes.MOVILE_USER_UPDATE + id_dispositivo;
        Toast.makeText(getApplicationContext(), newUrl, Toast.LENGTH_LONG).show();
        VolleySingleton.getInstance(getApplicationContext()).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                newUrl,
                                (String) null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {

                                            //obtener la psocion success del json
                                            Boolean success = Boolean.valueOf(response.getString("success"));

                                            if (success) {
                                                usuarioLogeo = response.getString("id_user");

                                                Toast.makeText(getApplicationContext(), "Exito al actualizar el usuario", Toast.LENGTH_SHORT).show();

                                            } else {
                                                Toast.makeText(getApplicationContext(), "Error de coneccion al actualizar el usuario", Toast.LENGTH_SHORT).show();

                                            }

                                        } catch (JSONException e) {
                                            Toast.makeText(ActividadPrincipal.this, "Error de conexión de actualizar on json" + e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), "Error de peticion load usuario", Toast.LENGTH_LONG).show();
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
                loadMovileUser(idDispositivo);

                actualizarMovileUser(idDispositivo, usuarioLogeo);
                Toast.makeText(getApplicationContext(), "Id logeo:" + usuarioLogeo, Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(ActividadPrincipal.this, ActividadSecundaria.class);
//                finish();
//                startActivity(i);

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
