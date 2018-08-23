package com.example.pc.mymaze;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by PC on 2017/9/29.
 */

public class down_menu_class extends LinearLayout implements View.OnClickListener {

    static boolean canMove = false;
    Button menubt,infobt,movebt;

    Context con;

    public down_menu_class(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.down_menu,this,true);
        menubt = findViewById(R.id.menuButton);
        movebt = findViewById(R.id.moveButton);
        infobt = findViewById(R.id.infoButton);
        con = context;

        menubt.setOnClickListener(this);

        movebt.setOnClickListener(this);

        infobt.setOnClickListener(this);
    }
    public down_menu_class(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.down_menu,this,true);
        menubt = findViewById(R.id.menuButton);
        movebt = findViewById(R.id.moveButton);
        infobt = findViewById(R.id.infoButton);
        con = context;

        menubt.setOnClickListener(this);

        movebt.setOnClickListener(this);

        infobt.setOnClickListener(this);
    }
    public down_menu_class(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.down_menu,this,true);
        menubt = findViewById(R.id.menuButton);
        movebt = findViewById(R.id.moveButton);
        infobt = findViewById(R.id.infoButton);
        con = context;

        menubt.setOnClickListener(this);

        movebt.setOnClickListener(this);

        infobt.setOnClickListener(this);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public down_menu_class(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        LayoutInflater.from(context).inflate(R.layout.down_menu,this,true);
        menubt = findViewById(R.id.menuButton);
        movebt = findViewById(R.id.moveButton);
        infobt = findViewById(R.id.infoButton);
        con = context;

        menubt.setOnClickListener(this);

        movebt.setOnClickListener(this);

        infobt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.menuButton://菜单按钮
                Intent intent = new Intent(con,MenuActivity.class);
                con.startActivity(intent);
                break;
            case R.id.infoButton://关于迷宫信息
                /**
                 * 还要显示迷信息，这些信息应该包括：
                 * 边长
                 * 格子数
                 * 最短路径走过的格子数即步数
                 * 路径长度（像素）
                 * 就这些吧。。。
                 */
                if(MainActivity.canvas == null||MainActivity.paint == null) {
                    Toast.makeText(con, "未产生地图", Toast.LENGTH_SHORT).show();
                    break;
                }
                Bitmap bitmap = Bitmap.createBitmap(MainActivity.temp.getWidth(),MainActivity.temp.getHeight(),MainActivity.temp.getConfig());
                Canvas canvas2 = new Canvas(bitmap);
                MainActivity.paint.setStyle(Paint.Style.FILL);
                MainActivity.paint.setColor(Color.RED);
                canvas2.drawBitmap(MainActivity.temp,0,0, MainActivity.paint);
                canvas2.drawCircle(50 + MainActivity.uniLen/2, 150 + MainActivity.uniLen/2, MainActivity.uniLen/2, MainActivity.paint);
                canvas2.translate(50,150);

                MainActivity.mazeObject.findPath();

                while(!MainActivity.mazeObject.path.isEmpty()){
                    //这里就可以使用到线程知识了！
                    //一面画一面显示，要画主界面估计就要用到threadOnUi
                    room temp = MainActivity.mazeObject.path.peek();
                    MainActivity.mazeObject.path.pop();
                    canvas2.drawCircle(temp.x*MainActivity.uniLen+MainActivity.uniLen/2,
                            temp.y*MainActivity.uniLen+MainActivity.uniLen/2,
                            MainActivity.uniLen/2 - 2,MainActivity.paint);
                            MainActivity.iv_canvas.setImageBitmap(bitmap);
                }

                MainActivity.iv_canvas.setImageBitmap(bitmap);//???
                break;
            case R.id.moveButton://移动
                if(MainActivity.uniLen == 0)
                    break;
                canMove = true;
                MainActivity.startcol = -1;
                MainActivity.startrow = -1;
                MainActivity.paint.setStyle(Paint.Style.FILL);
                MainActivity.paint.setColor(Color.BLUE);
                Bitmap s = Bitmap.createBitmap(MainActivity.temp.getWidth(),MainActivity.temp.getHeight(), MainActivity.temp.getConfig());
                Canvas canvas1 = new Canvas(s);
                canvas1.drawBitmap(MainActivity.temp, 0, 0, MainActivity.paint);
                canvas1.drawCircle(50 + MainActivity.uniLen/2, 150 + MainActivity.uniLen/2, MainActivity.uniLen/2, MainActivity.paint);
                MainActivity.iv_canvas.setImageBitmap(s);
                break;
            default:
                break;
        }
    }
}
