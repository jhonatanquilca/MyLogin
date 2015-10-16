package login.mylogin.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;


import login.mylogin.R;
import login.mylogin.tools.Constantes;
import login.mylogin.web.VolleySingleton;

public class ActividadPrincipal extends Activity {
//    http://cursoandroidstudio.blogspot.com/2015/01/base-de-datos-remotas-login.html

    /**
     * variables de controles visuales
     */
    private EditText input_user;
    private EditText input_password;
    private Button buttom_iniciar_sesion;

    private ProgressDialog pDialog;

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
                new AttemptLogin().execute();
            }
        });
    }

    class AttemptLogin extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActividadPrincipal.this);
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();


        }

        @Override
        protected String doInBackground(String... args) {
//            int success;
            String nombreUsuario = input_user.getText().toString();
            String contraseñaUsuario = input_password.getText().toString();
            final String newUrl = Constantes.makeLoginUrl(nombreUsuario, contraseñaUsuario.toString());
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
                                            Toast.makeText(getApplicationContext(), "" + response.toString(), Toast.LENGTH_LONG).show();


                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
//                                                Toast.makeText(getApplicationContext(), "Error Volley: " + error.getMessage() + newUrl, Toast.LENGTH_LONG).show();
                                            Toast.makeText(getApplicationContext(), "Error de conexión " + newUrl, Toast.LENGTH_LONG).show();
                                        }
                                    }
                            )
                    );


            return "Login correcto!";

        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(ActividadPrincipal.this, file_url, Toast.LENGTH_LONG).show();
            }
        }
    }
}

