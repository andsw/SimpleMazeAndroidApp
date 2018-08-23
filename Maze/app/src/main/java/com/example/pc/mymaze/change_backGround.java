package com.example.pc.mymaze;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class change_backGround extends AppCompatActivity {

    Button changeLineBt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_back_ground);
        changeLineBt = (Button) findViewById(R.id.changeLineColorBt);
        changeLineBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });
    }

    public void showPopupMenu(View view ){
        PopupMenu pop = new PopupMenu(this, view);
        pop.getMenuInflater().inflate(R.menu.chang_line_color, pop.getMenu());
        final Context con = this;
        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Drawable s;
                switch(item.getItemId()){
                    case R.id.black:MainActivity.color = Color.BLACK;
                        s = con.getDrawable(R.color.black);
                        changeLineBt.setBackground(s);
                        break;
                    case R.id.brown:MainActivity.color = R.color.brown;
                        s = con.getDrawable(R.color.brown);
                        changeLineBt.setBackground(s);
                        break;
                    case R.id.burlywood:MainActivity.color = R.color.burlywood;
                        s = con.getDrawable(R.color.burlywood);
                        changeLineBt.setBackground(s);
                        break;
                    case R.id.crimson:MainActivity.color = R.color.crimson;
                        s = con.getDrawable(R.color.crimson);
                        changeLineBt.setBackground(s);
                        break;
                    case R.id.deepskyblue:MainActivity.color = R.color.deepskyblue;
                        s = con.getDrawable(R.color.deepskyblue);
                        changeLineBt.setBackground(s);
                        break;
                    case R.id.gainsboro:MainActivity.color = R.color.gainsboro;
                        s = con.getDrawable(R.color.gainsboro);
                        changeLineBt.setBackground(s);
                        break;
                    case R.id.mediumblue:MainActivity.color = R.color.mediumblue;
                        s = con.getDrawable(R.color.mediumblue);
                        changeLineBt.setBackground(s);
                        break;
                    case R.id.lawngreen:MainActivity.color = R.color.lawngreen;
                        s = con.getDrawable(R.color.lawngreen);
                        changeLineBt.setBackground(s);
                        break;
                    case R.id.silver:MainActivity.color = R.color.silver;
                        s = con.getDrawable(R.color.silver);
                        changeLineBt.setBackground(s);
                        break;
                    case R.id.gold:MainActivity.color = R.color.gold;
                        s = con.getDrawable(R.color.gold);
                        changeLineBt.setBackground(s);
                        break;
                    case R.id.firebrick:MainActivity.color = R.color.firebrick;
                        s = con.getDrawable(R.color.firebrick);
                        changeLineBt.setBackground(s);
                        break;
                    case R.id.greenyellow:MainActivity.color = R.color.greenyellow;
                        s = con.getDrawable( R.color.greenyellow);
                        changeLineBt.setBackground(s);
                        break;
                    case R.id.lightpink:MainActivity.color = R.color.lightpink;
                        s = con.getDrawable(R.color.lightpink);
                        changeLineBt.setBackground(s);
                        break;
                    case R.id.mediumturquoise:MainActivity.color = R.color.mediumturquoise;
                        s = con.getDrawable( R.color.mediumturquoise);
                        changeLineBt.setBackground(s);
                        break;
                    case R.id.white:MainActivity.color = R.color.white;
                        s = con.getDrawable( R.color.white);
                        changeLineBt.setBackground(s);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        pop.show();
    }

}
