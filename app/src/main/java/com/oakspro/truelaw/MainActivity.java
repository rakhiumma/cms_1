package com.oakspro.truelaw;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView logo;
    TextView subT;

    Animation topAnimation;
    CharSequence charSequence;
    int index;
    long delay=100;
    Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set ids

        logo=findViewById(R.id.logo);
        subT=findViewById(R.id.sub_title);

        topAnimation=AnimationUtils.loadAnimation(this, R.anim.top_animation);

        logo.setAnimation(topAnimation);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                animationText("A True Platform For Law");
            }
        },3000);

    }
    Runnable runnable=new Runnable() {
        @Override
        public void run() {

            subT.setText(charSequence.subSequence(0, index++));
            if (index <= charSequence.length()){
                handler.postDelayed(runnable, delay);
            }
        }
    };
    public void animationText(CharSequence cs){
        charSequence=cs;
        index=0;
        subT.setText("");
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,delay);
    }
}