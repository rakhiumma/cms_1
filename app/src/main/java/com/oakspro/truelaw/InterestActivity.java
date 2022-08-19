package com.oakspro.truelaw;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterestActivity extends AppCompatActivity {

    ChipGroup chipGroup;
    String topics[]={"Family Law","Criminal Law","Corporate Law","Intellectual Property Law"};
    Button finishbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        chipGroup=findViewById(R.id.law_chip);
        finishbtn=findViewById(R.id.finish_btn);

        List<String> list=new ArrayList<String>(Arrays.asList(topics));
        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                Chip chip;
                chip=group.findViewById(checkedId);
                String t_name=chip.getText().toString();
                list.add(t_name);
            }
        });


        finishbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topics=list.toArray(topics);
                String tst=String.valueOf(topics);
                Log.i("Topics Selected",list.toString());
                updateInterestedTopics(topics);
            }
        });
    }

    private void updateInterestedTopics(String[] topics) {
        StringRequest request=new StringRequest(Request.Method.POST, Constants.add_interested_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    boolean status=jsonObject.has("SUCCESS");
                    if (status){
                        Toast.makeText(InterestActivity.this, "Topics Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(InterestActivity.this, DashActivity.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data=new HashMap<>();
                for (int i=0; i<topics.length; i++){
                    String str=topics[i];
                    data.put("interests[]", str);
                }
                return data;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}