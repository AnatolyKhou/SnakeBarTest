package com.android.ld.snakebartest;

import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CoordinatorLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = (CoordinatorLayout) findViewById(R.id.layoutRoot);
        FloatingActionButton actionButton = (FloatingActionButton) findViewById(R.id.btnFloatingAction);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = SnackbarUtil.ShortSnackbar(mLayout, "妹子删了你发出的消息", SnackbarUtil.Warning).setActionTextColor(Color.RED).setAction("再次发送", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SnackbarUtil.LongSnackbar(mLayout, "妹子已将你拉黑", SnackbarUtil.Alert).setActionTextColor(Color.WHITE).show();
                    }
                });

                SnackbarUtil.SnackbarAddView(snackbar,R.layout.snackbar_layout,0);

                SnackbarUtil.SnackbarAddView(snackbar,R.layout.snackbar_layout,2);


                snackbar.show();
               /* Snackbar snackbar = Snackbar.make(mLayout, "Hello SnackBar!", Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Perform anything for the action selected

                            }
                        });
                Snackbar.SnackbarLayout snackbarLayout= (Snackbar.SnackbarLayout) snackbar.getView();
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);//设置新建布局参数
                p.gravity= Gravity.CENTER_VERTICAL;
                *//*snackbar.setActionTextColor(Color.GREEN);
                snackbarLayout.setBackgroundColor(Color.BLUE);*//*
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.snackbar_layout, null);
                snackbarLayout.addView(view,1, p);
                snackbar.show();*/
            }
        });

    }
}
