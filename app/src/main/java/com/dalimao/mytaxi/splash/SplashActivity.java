package com.dalimao.mytaxi.splash;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Space;

import com.dalimao.mytaxi.R;
import com.dalimao.mytaxi.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // 显示Logo动画，要大于等于21才行
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 动画设置
            final AnimatedVectorDrawable anim1 = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.anim);
            final ImageView logo = ((ImageView) findViewById(R.id.logo));
            logo.setImageDrawable(anim1);
            anim1.start();
        }
        /**
         * 延时 3 秒然后跳转到 main 页面
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 一般是在这个页面加载资源，这里没有资源加载所以就显示3秒然后跳转到MainActivity
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 3000) ;
    }
}
