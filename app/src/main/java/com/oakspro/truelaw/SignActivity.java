package com.oakspro.truelaw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignActivity extends AppCompatActivity {

    LinearLayout ll1, ll2;
    TextView otpTxt, errorTxt, errorOTP;
    EditText mobileEd, otpEd;
    Button contBtn;
    Boolean j=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        //set id
        ll1=findViewById(R.id.ll1);
        ll2=findViewById(R.id.ll2);
        otpTxt=findViewById(R.id.otp_txt);
        mobileEd=findViewById(R.id.mobileEd);
        otpEd=findViewById(R.id.otpEd);
        contBtn=findViewById(R.id.cont_btn);
        errorTxt=findViewById(R.id.error_msg);



        contBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (j==false){
                    ll1.setVisibility(View.VISIBLE);
                    ll2.setVisibility(View.GONE);

                    String mob=mobileEd.getText().toString();

                    if (!TextUtils.isEmpty(mob)){
                        if (mob.length()==10){
                            errorTxt.setText("");

                            otpTxt.setText("OTP has sent to +91 "+mob);
                            ll1.setVisibility(View.GONE);
                            ll2.setVisibility(View.VISIBLE);
                            // make j=true when server send otp
                            j=true;

                        }else{
                            errorTxt.setText("Please Enter Valid Mobile");
                        }

                    }else{
                        errorTxt.setText("Mobile Number Required");
                    }


                }else{
                    String otp_s=otpEd.getText().toString();
                    if (!TextUtils.isEmpty(otp_s)){

                        if (otp_s.length()==6){
                            if (otp_s.equals("123456")){
                                Intent intent=new Intent(SignActivity.this, SignupActivity.class);
                                startActivity(intent);
                            }

                        }else{
                         errorOTP.setText("Please Enter 6 Digit OTP");
                        }
                    }else{
                        errorOTP.setText("Please Enter OTP");
                    }


                }
            }
        });

    }
}