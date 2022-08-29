package com.oakspro.truelaw;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorTreeAdapter;
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

public class CkycActivity extends AppCompatActivity {

    LinearLayout ll_p1, ll_p2, ll_p3;
    Button proceedBtn, submitBtn;
    RelativeLayout upload_rl;
    TextInputLayout caseidTl, ynameTl, onameTl,caseTypeTl, stateTl, distTl, areTl, descTl;
    SharedPreferences preferences;
    String login_token=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ckyc);

        preferences=getSharedPreferences("MyLogin", MODE_PRIVATE);
        login_token=preferences.getString("token", null);

        ll_p1=findViewById(R.id.ll_p1);
        ll_p2=findViewById(R.id.ll_p2);
        ll_p3=findViewById(R.id.ll_p3);

        caseidTl=findViewById(R.id.caseid_tl);
        ynameTl=findViewById(R.id.y_name);
        onameTl=findViewById(R.id.o_name);
        caseTypeTl=findViewById(R.id.case_type);
        stateTl=findViewById(R.id.state_tl);
        distTl=findViewById(R.id.dis_tl);
        areTl=findViewById(R.id.area_tl);
        descTl=findViewById(R.id.desc_tl);

        proceedBtn=findViewById(R.id.proceed_btn);
        submitBtn=findViewById(R.id.submit_btn);
        upload_rl=findViewById(R.id.upload_rl);

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_p1.setVisibility(View.GONE);
                ll_p2.setVisibility(View.VISIBLE);
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String caseid=caseidTl.getEditText().getText().toString();
                String name=ynameTl.getEditText().getText().toString();
                String oname=onameTl.getEditText().getText().toString();
                String casetype=caseTypeTl.getEditText().toString();
                String state=stateTl.getEditText().getText().toString();
                String dist=distTl.getEditText().getText().toString();
                String area=areTl.getEditText().getText().toString();
                String desc=descTl.getEditText().getText().toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(oname) && !TextUtils.isEmpty(casetype) && !TextUtils.isEmpty(state) && !TextUtils.isEmpty(dist) && !TextUtils.isEmpty(area) && !TextUtils.isEmpty(desc)){
                    submitCase(caseid, name, oname, casetype, state, dist, area, desc);
                }else{
                    Toast.makeText(CkycActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void submitCase(String caseid, String name, String oname, String casetype, String state, String dist, String area, String desc) {
        ll_p2.setVisibility(View.GONE);
        ll_p3.setVisibility(View.VISIBLE);

        StringRequest request=new StringRequest(Request.Method.POST, Constants.post_user_case, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    boolean status=jsonObject.has("SUCCESS");
                    if (status){
                        Toast.makeText(CkycActivity.this, "Submitted Successful", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(CkycActivity.this, "Failed to submit", Toast.LENGTH_SHORT).show();
                        ll_p3.setVisibility(View.GONE);
                        ll_p2.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CkycActivity.this, "Server Internal Error..!", Toast.LENGTH_SHORT).show();
                    ll_p3.setVisibility(View.GONE);
                    ll_p2.setVisibility(View.VISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CkycActivity.this, "Please Check Internet Connection...!", Toast.LENGTH_SHORT).show();
                ll_p3.setVisibility(View.GONE);
                ll_p2.setVisibility(View.VISIBLE);
            }
        }){
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> params=new HashMap<String, String>();
                params.put("SECURED_TRL_AUTH", login_token);
                // params.put("Content-Type", "application/json"); //use this for PUT
                return params;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data=new HashMap<>();
                data.put("caseId", caseid);
                data.put("clientName", name);
                data.put("oppName", oname);
                data.put("type", casetype);
                data.put("state", state);
                data.put("district", dist);
                data.put("description", dist);
                return data;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}