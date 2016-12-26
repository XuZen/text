package com.example.administrator.myweakreference;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/12/25 0025.
 */

public class SecondActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item);
    }
    public void play(View view){
        EventBus.getDefault().post("123");
    }
}
