package com.oakspro.truelaw;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    TextInputLayout email_tl, password_tl;
    String login_token=null;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    LinearLayout ll1;
    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn=findViewById(R.id.login_btn);
        email_tl=findViewById(R.id.email_tl);
        password_tl=findViewById(R.id.password_tl);
        ll1=findViewById(R.id.ll1);
        loader=findViewById(R.id.loader);

        preferences=getSharedPreferences("MyLogin", MODE_PRIVATE);
        editor=preferences.edit();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //login code verification

                String email=email_tl.getEditText().getText().toString();
                String pass=password_tl.getEditText().getText().toString();
                if (!TextUtils.isEmpty(email)){
                    if (!TextUtils.isEmpty(pass)){

                        validateUser(email, pass);
                    }else {
                        password_tl.setError("Enter Valid Password");
                    }
                }else{
                    email_tl.setError("Enter Valid Email");
                }

                //intent
               // startActivity(new Intent(LoginActivity.this, DashActivity.class));
            }
        });
    }

    private void validateUser(String email, String pass) {
        ll1.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);
        StringRequest request=new StringRequest(Request.Method.POST, Constants.login_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    boolean status=object.has("SUCCESS");
                    if (status){
                        login_token=object.getString("TOKEN");
                        Log.i("Token", login_token);
                        editor.putBoolean("isLogin", true);
                        editor.putString("token", login_token);
                        editor.commit();
                        editor.apply();
                        Intent intent=new Intent(LoginActivity.this, InterestActivity.class);
                        intent.putExtra("token", login_token);
                        startActivity(intent);
                    }else{
                        loader.setVisibility(View.GONE);
                        ll1.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this, "Enter Valid Credentials", Toast.LENGTH_SHORT).show();
                        email_tl.setError("");
                        password_tl.setError("");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    loader.setVisibility(View.GONE);
                    ll1.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                loader.setVisibility(View.GONE);
                ll1.setVisibility(View.VISIBLE);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data=new HashMap<>();
                data.put("userName", email);
                data.put("password", pass);
                return data;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}