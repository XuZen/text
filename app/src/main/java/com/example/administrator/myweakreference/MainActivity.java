package com.example.administrator.myweakreference;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private OkHttpClient okHttpClient;
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化控件
        initView();
        // 注册EventBus
        EventBus.getDefault().register(this);
        // oKHttp
        okHttpClient = new OkHttpClient();
    }

    private void initView() {
        image = (ImageView) findViewById(R.id.imageView2);
    }

    public void http(View view){
        Request request = new Request.Builder().url("http://d.hiphotos.baidu.com/image/h%3D200/sign=b203ccb26559252dbc171a040499032c/e4dde71190ef76c6cc15d5839816fdfaae516756.jpg").build();
        // CALL
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }
            // 请求成功进行的回调，回调方法是在子线程中
            @Override
            public void onResponse(Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                EventBus.getDefault().post(bitmap);
            }
        });
    }
    public void butt(View view){
        Intent intent = new Intent(this,SecondActivity.class);
        startActivity(intent);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onData(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onData(Bitmap bitmap){
        image.setImageBitmap(bitmap);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销EventBus
        EventBus.getDefault().unregister(this);
    }
}
