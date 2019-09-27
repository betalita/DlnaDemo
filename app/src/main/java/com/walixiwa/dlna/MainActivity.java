package com.walixiwa.dlna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.walixiwa.dlnaserver.DlnaDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.find).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DlnaDialog(MainActivity.this)
                        .setUrl("http://192.168.0.108:2333/xsbc.mp3")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        });
    }
}
