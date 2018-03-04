package morasquad.me.loginapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    public static final String REG_URL = "http://www.booking.pal.morasquad.me/api/appreg";

    EditText txtname;
    EditText txtemail;
    EditText txtpassword;
    Button register;

    private String name;
    private String email;
    private String password;

    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    txtname = (EditText) findViewById(R.id.name);
    txtemail = (EditText) findViewById(R.id.email);
    txtpassword = (EditText) findViewById(R.id.password);
    register = (Button) findViewById(R.id.register);




    register.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            userRegister();
        }
    });



    }

    private void userRegister() {

        name = txtname.getText().toString().trim();
        email = txtemail.getText().toString().trim();
        password = txtpassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REG_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String msg;
                        try {
                            JSONObject jsonResponse = new JSONObject(response).getJSONObject("data");

                            msg = jsonResponse.getString("msg");
                            if (msg.equals("success")) {

                                Toast.makeText(Register.this, "Register Successfull!", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(Register.this, MainActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            } else {
                                Toast.makeText(Register.this, "Register Failed!", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Register.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                }

        ){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_NAME,name);
                map.put(KEY_EMAIL,email);
                map.put(KEY_PASSWORD,password);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    }

