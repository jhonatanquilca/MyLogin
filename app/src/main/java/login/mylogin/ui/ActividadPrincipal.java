package login.mylogin.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
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
    // Clase JSONParser

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_principal);

        input_user = (EditText) findViewById(R.id.input_user);
        input_password = (EditText) findViewById(R.id.input_password);
        buttom_iniciar_sesion = (Button) findViewById(R.id.buttom_iniciar_sesion);

        //action clic del boton iniciar sesion
        buttom_iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);

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
                                                try {

                                                    //obtener la psocion success del json
                                                    Boolean success = Boolean.valueOf(response.getString("success"));
                                                    String mensaje = "";

                                                    if (success) {
                                                        pDialog.hide();
                                                        mensaje = "Inicio de sesion Exitoso!";
                                                    } else {
                                                        try {

                                                            Map<String, String> maping = new MyUtil().toMap(response.getJSONObject("errors"));

                                                            for (String key : maping.keySet()) {
                                                                EditText campo = null;
                                                                switch (key) {
                                                                    case "username":
                                                                        campo = (EditText) findViewById(R.id.input_user);
                                                                        campo.setHint(MyUtil.obtenerError(maping, key));
                                                                        campo.setHintTextColor(getResources().getColor(R.color.red_300));
                                                                        break;
                                                                    case "password":
                                                                        campo = (EditText) findViewById(R.id.input_password);
                                                                        campo.setHint(MyUtil.obtenerError(maping, key));
                                                                        campo.setHintTextColor(getResources().getColor(R.color.red_300));
                                                                        break;
                                                                }

                                                            }


                                                        } catch (JSONException e1) {
                                                            Toast.makeText(
                                                                    getApplicationContext(),
                                                                    "Erros: " + response.getString("errors"),
                                                                    Toast.LENGTH_LONG).show();
                                                        }

                                                        pDialog.hide();
                                                        mensaje = "Error de Inicio de sesion";

                                                    }
                                                    Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();

                                                } catch (JSONException e) {
                                                    Toast.makeText(ActividadPrincipal.this, "Error de Codificacion " + e.getMessage(), Toast.LENGTH_LONG).show();
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
        });
    }
}
