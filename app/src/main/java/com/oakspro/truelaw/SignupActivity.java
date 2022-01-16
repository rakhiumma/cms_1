package com.oakspro.truelaw;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignupActivity extends AppCompatActivity {

    TextInputLayout sur_tl, name_tl, email_tl, state_tl, dist_tl, area_tl;
    ChipGroup lawchip;
    TextInputEditText sur_edt,name_edt, email_edt, state_edt, dist_edt, area_edt;
    Button nextBtn;
    TextView backTxt, filedTxt;
    int index=1;
    ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //set ids

        sur_tl=findViewById(R.id.sur_tl);
        name_tl=findViewById(R.id.name_tl);
        email_tl=findViewById(R.id.email_tl);
        state_tl=findViewById(R.id.state_tl);
        dist_tl=findViewById(R.id.dis_tl);
        area_tl=findViewById(R.id.area_tl);
        nextBtn=findViewById(R.id.next_btn);
        backTxt=findViewById(R.id.back_txt);
        filedTxt=findViewById(R.id.filed_title);
        lawchip=findViewById(R.id.law_chip);
        loadingBar=findViewById(R.id.progressBar);

        backTxt.setVisibility(View.GONE);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (index==1){
                    sur_tl.setVisibility(View.GONE);
                    name_tl.setVisibility(View.GONE);
                    email_tl.setVisibility(View.GONE);
                    backTxt.setVisibility(View.VISIBLE);
                    state_tl.setVisibility(View.VISIBLE);
                    dist_tl.setVisibility(View.VISIBLE);
                    area_tl.setVisibility(View.VISIBLE);
                    index++;
                }else if (index==2) {
                    state_tl.setVisibility(View.GONE);
                    dist_tl.setVisibility(View.GONE);
                    area_tl.setVisibility(View.GONE);
                    index++;
                    filedTxt.setText("Interested Topics!");
                    lawchip.setVisibility(View.VISIBLE);
                }else if(index==3){
                    filedTxt.setText("Creating Account...!");
                    lawchip.setVisibility(View.GONE);
                    backTxt.setVisibility(View.GONE);
                    nextBtn.setVisibility(View.GONE);
                    loadingBar.setVisibility(View.VISIBLE);

                }

            }
        });

        backTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (index==3){
                    filedTxt.setText("Personal Details");
                    lawchip.setVisibility(View.GONE);
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

                    sur_tl.setVisibility(View.VISIBLE);
                    name_tl.setVisibility(View.VISIBLE);
                    email_tl.setVisibility(View.VISIBLE);
                }

            }
        });



    }
}