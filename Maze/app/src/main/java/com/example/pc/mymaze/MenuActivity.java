package com.example.pc.mymaze;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    Button paybt,aboutbt,changepicturebt,functionbt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        paybt = (Button) findViewById(R.id.pay);
        paybt.setOnClickListener(this);
        aboutbt = (Button) findViewById(R.id.about);
        aboutbt.setOnClickListener(this);
        changepicturebt = (Button) findViewById(R.id.back_ground);
        changepicturebt.setOnClickListener(this);
        functionbt = (Button) findViewById(R.id.function);
        functionbt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.pay:
                break;
            case R.id.back_ground:
                Intent intent1 = new Intent(MenuActivity.this,change_backGround.class);
                startActivity(intent1);
                break;
            case R.id.about:
                Intent intent = new Intent(MenuActivity.this,aboutUsActivity.class);
                startActivity(intent);
                break;
            case R.id.function:
                ImageView iv = new ImageView(this);
                iv.setImageResource(R.drawable.main_ui);
                new AlertDialog.Builder(this).setTitle("操作说明").setView(iv)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).show();
                break;
            default:
                break;
        }
    }
}
