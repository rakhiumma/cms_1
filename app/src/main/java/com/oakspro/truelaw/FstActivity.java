package com.oakspro.truelaw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FstActivity extends AppCompatActivity {

    ImageButton nxtBtn;
    TextView skipTx;
    LinearLayout ll1, ll2;
    int index=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fst);
        //set ids

        ll1=findViewById(R.id.ll1);
        ll2=findViewById(R.id.ll2);
        skipTx=findViewById(R.id.skip_txt);
        nxtBtn=findViewById(R.id.nxt_btn);

        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (index<2){
                    ll1.setVisibility(View.GONE);
                    ll2.setVisibility(View.VISIBLE);
                    index++;
                }else{
                    Intent intent=new Intent(FstActivity.this, SignActivity.class);
                    startActivity(intent);
                }
            }
        });

        skipTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FstActivity.this, SignActivity.class);
                startActivity(intent);
            }
        });

    }
}