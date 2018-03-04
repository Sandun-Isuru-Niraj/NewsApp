package morasquad.me.loginapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String LOGIN_URL = "http://www.booking.pal.morasquad.me/api/applogin";

    public static final String KEY_USERNAME = "email";
    public static final String KEY_PASSWORD = "password";

    private EditText user;
    private EditText pass;
    private Button login;

    private String username;
    private String password;

    UserSessionManager sessionManager;
    String msg,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new UserSessionManager(getApplicationContext());
        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);

        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(this);
    }

    private void userLogin(){

        username = user.getText().toString().trim();
        password = pass.getText().toString().trim();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response).getJSONObject("data");
                             msg = jsonResponse.getString("msg");
                             name = jsonResponse.getString("name");
                            if (msg.equals("success")) {

                                sessionManager.createUserLoginSession("User Session ", name);
                                progressDialog.dismiss();
                                openProfile();
                                Toast.makeText(MainActivity.this, "Login Successfull!", Toast.LENGTH_LONG).show();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }
                    }


                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                }

        ){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_USERNAME,username);
                map.put(KEY_PASSWORD,password);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void openProfile(){

        Intent i = new Intent(getApplicationContext(), Profile.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(KEY_USERNAME, name);
        startActivity(i);
        finish();
    }

    @Override
    public void onClick(View v){

        userLogin();
    }
}
