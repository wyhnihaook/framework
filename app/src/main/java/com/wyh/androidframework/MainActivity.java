package com.wyh.androidframework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wyh.modulecommon.constant.Routers;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv=findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(Routers.USER_MAIN)
                        .withLong("key1", 666L)
                        .withString("name", "888")
//                        .withObject("key4", new Test("Jack", "Rose"))
                        .navigation();

            }
        });

    }
}
