package com.oakspro.truelaw;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    TextInputLayout mob_tl,sur_tl, name_tl, email_tl, state_tl, dist_tl, area_tl, createp_tl, confirmp_tl;
   // ChipGroup lawchip;
    TextInputEditText sur_edt,name_edt, email_edt, state_edt, dist_edt, area_edt;
    Button nextBtn;
    TextView backTxt, filedTxt;
    int index=1;
    ProgressBar loadingBar;
    String mob=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //set ids

        mob_tl=findViewById(R.id.mobile_tl);
        sur_tl=findViewById(R.id.sur_tl);
        name_tl=findViewById(R.id.name_tl);
        email_tl=findViewById(R.id.email_tl);
        state_tl=findViewById(R.id.state_tl);
        dist_tl=findViewById(R.id.dis_tl);
        area_tl=findViewById(R.id.area_tl);
        nextBtn=findViewById(R.id.next_btn);
        backTxt=findViewById(R.id.back_txt);
        filedTxt=findViewById(R.id.filed_title);
        createp_tl=findViewById(R.id.createp_tl);
        confirmp_tl=findViewById(R.id.confirmp_tl);
       // lawchip=findViewById(R.id.law_chip);
        loadingBar=findViewById(R.id.progressBar);

        mob=getIntent().getStringExtra("mobile");
        mob_tl.getEditText().setText(mob);

        backTxt.setVisibility(View.GONE);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mobile=mob_tl.getEditText().getText().toString();
                String surname=sur_tl.getEditText().getText().toString();
                String name=name_tl.getEditText().getText().toString();
                String email=email_tl.getEditText().getText().toString();
                String state=state_tl.getEditText().getText().toString();
                String dist=dist_tl.getEditText().getText().toString();
                String area=area_tl.getEditText().getText().toString();
                String createp=createp_tl.getEditText().getText().toString();
                String confirmp=confirmp_tl.getEditText().getText().toString();

                if (index==1){

                    if (!TextUtils.isEmpty(mobile)){
                        if (!TextUtils.isEmpty(surname)){
                            if (!TextUtils.isEmpty(name)){
                                if (!TextUtils.isEmpty(email)){
                                    mob_tl.setVisibility(View.GONE);
                                    sur_tl.setVisibility(View.GONE);
                                    name_tl.setVisibility(View.GONE);
                                    email_tl.setVisibility(View.GONE);

                                    backTxt.setVisibility(View.VISIBLE);
                                    state_tl.setVisibility(View.VISIBLE);
                                    dist_tl.setVisibility(View.VISIBLE);
                                    area_tl.setVisibility(View.VISIBLE);
                                    index++;
                                }else{
                                    email_tl.setError("Email is required");
                                }
                            }else{
                                name_tl.setError("Name is required");
                            }
                        }else{
                            sur_tl.setError("Surname is required");
                        }
                    }else{
                        mob_tl.setError("");
                    }

                }else if (index==2) {
                    if (!TextUtils.isEmpty(state)){
                        if (!TextUtils.isEmpty(dist)){
                            if (!TextUtils.isEmpty(area)){
                                state_tl.setVisibility(View.GONE);
                                dist_tl.setVisibility(View.GONE);
                                area_tl.setVisibility(View.GONE);

                                createp_tl.setVisibility(View.VISIBLE);
                                confirmp_tl.setVisibility(View.VISIBLE);
                                index++;
                                filedTxt.setText("Create Password");
                            }else{
                                area_tl.setError("Please Enter Area");
                            }
                        }else{
                            dist_tl.setError("Please Enter District");
                        }
                    }else{
                        state_tl.setError("Please Enter State");
                    }

                }else if(index==3){

                    if (!TextUtils.isEmpty(createp)){
                        if (!TextUtils.isEmpty(confirmp)){
                            if (confirmp.equals(createp)){
                                createp_tl.setVisibility(View.GONE);
                                confirmp_tl.setVisibility(View.GONE);
                                index++;
                                filedTxt.setText("Creating Account...!");

                                backTxt.setVisibility(View.GONE);
                                nextBtn.setVisibility(View.GONE);
                                loadingBar.setVisibility(View.VISIBLE);
                                //login code
                                signupServer(mobile,surname, name, email, state, dist, area, confirmp);
                            }else{
                                confirmp_tl.setError("Password not matched");
                            }
                        }else{
                            confirmp_tl.setError("Please Enter Confirm Password");
                        }
                    }else{
                        createp_tl.setError("Please Create Password");
                    }

                }

            }
        });

        backTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (index==3){
                    filedTxt.setText("Personal Details");
                    index--;
                    state_tl.setVisibility(View.VISIBLE);
                    dist_tl.setVisibility(View.VISIBLE);
                    area_tl.setVisibility(View.VISIBLE);
                }else if (index==2){
                    state_tl.setVisibility(View.GONE);
                    dist_tl.setVisibility(View.GONE);
                    area_tl.setVisibility(View.GONE);
                    index--;
                    backTxt.setVisibility(View.GONE);

                    mob_tl.setVisibility(View.VISIBLE);
                    sur_tl.setVisibility(View.VISIBLE);
                    name_tl.setVisibility(View.VISIBLE);
                    email_tl.setVisibility(View.VISIBLE);
                }

            }
        });
            //test git pc

    }

    private void signupServer(String mobile, String surname, String name, String email, String state, String dist, String area, String confirmp) {
        StringRequest request=new StringRequest(Request.Method.POST, Constants.signup_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    boolean status=jsonObject.has("SUCCESS");
                    if (status){
                        Toast.makeText(SignupActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(SignupActivity.this, DashActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(SignupActivity.this, "Failed to signup.! Please try later.", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignupActivity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data=new HashMap<>();
                data.put("firstName", name);
                data.put("lastName", surname);
                data.put("email", email);
                data.put("mobile", mobile);
                data.put("password", confirmp);
                data.put("state", state);
                data.put("district", dist);
                data.put("area", area);
                return data;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}